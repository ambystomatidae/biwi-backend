package org.biwi.rest.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.biwi.rest.exceptions.InvalidRegisterExeption;
import org.biwi.rest.models.Auction;
import org.biwi.rest.models.BiwiUser;
import org.biwi.rest.models.Score;
import org.biwi.rest.models.Token;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestsHandler {

    private static RequestsHandler single_instance = null;

    private RequestsHandler() {
    }

    public static RequestsHandler getInstance() {
        if (single_instance == null)
            single_instance = new RequestsHandler();

        return single_instance;
    }

    Token adminToken;

    String client_id = ConfigProvider.getConfig().getValue("quarkus.oidc.client-id", String.class);

    String secret = ConfigProvider.getConfig().getValue("quarkus.oidc.credentials.secret", String.class);

    String tokenServiceUrl = ConfigProvider.getConfig().getValue("keycloak.token.service", String.class);

    String userServiceUrl = ConfigProvider.getConfig().getValue("keycloak.user.service", String.class);

    String adminUsername = ConfigProvider.getConfig().getValue("keycloak.admin.username", String.class);

    String adminPassword = ConfigProvider.getConfig().getValue("keycloak.admin.password", String.class);

    String activeAuctionsServiceUrl = ConfigProvider.getConfig().getValue("active-auctions.service.url", String.class);

    String activeAuctionsGetPath = ConfigProvider.getConfig().getValue("active-auctions.service.get-auction.path", String.class);

    String closedAuctionsServiceUrl = ConfigProvider.getConfig().getValue("closed-auctions.service.url", String.class);

    String closedAuctionsGetPath = ConfigProvider.getConfig().getValue("closed-auctions.service.get-auction.path", String.class);


    public String postUser(BiwiUser user) throws IOException, InvalidRegisterExeption, AuthenticationException, ParseException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(userServiceUrl);

        JSONArray credentialsArray = new JSONArray();
        JSONObject credentials = new JSONObject();
        credentials.put("type", "password");
        credentials.put("value", user.password);
        credentials.put("temporary", false);
        credentialsArray.add(credentials);
        credentialsArray.add(credentials);

        JSONObject json = new JSONObject();
        json.put("email", user.email);
        json.put("emailVerified", false);
        json.put("enabled", true);
        json.put("credentials", credentialsArray);
        json.put("username", user.email);
        json.put("firstName", user.firstName);
        json.put("lastName", user.lastName);

        StringEntity params = new StringEntity(json.toString());
        httpPost.addHeader("content-type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setEntity(params);

        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 201)
            return response.getFirstHeader("Location").getValue();

        BasicResponseHandler handler = new BasicResponseHandler();
        throw new InvalidRegisterExeption(handler.handleEntity(response.getEntity()));
    }

    public void updateUsername(String userId) throws IOException, AuthenticationException, ParseException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(userServiceUrl + "/" + userId);

        JSONObject json = new JSONObject();
        json.put("username", userId);


        StringEntity params = new StringEntity(json.toString());
        httpPut.addHeader("content-type", "application/json");
        httpPut.addHeader("Authorization", "Bearer " + token);
        httpPut.setEntity(params);

        client.execute(httpPut);
    }


    public boolean isActiveAuction(Auction auction) throws IOException, ParseException, AuthenticationException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(activeAuctionsServiceUrl + "/" + activeAuctionsGetPath + "/" + auction.auctionId);
        httpGet.addHeader("Authorization", "Bearer " + token);

        HttpResponse httpResponse = client.execute(httpGet);

        if (httpResponse.getStatusLine().getStatusCode() == 200)
            return true;

        return false;
    }

    public boolean isValidReview(String reviewerId, String reviewdId, String auctionId) throws ParseException, IOException, AuthenticationException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(closedAuctionsServiceUrl + "/" + closedAuctionsGetPath + "/" + auctionId);
        httpGet.addHeader("Authorization", "Bearer " + token);

        HttpResponse httpResponse = client.execute(httpGet);

        if(httpResponse.getStatusLine().getStatusCode() != 200)
            return false;

        JSONParser js = new JSONParser();
        JSONObject json = (JSONObject) js.parse(new BasicResponseHandler().handleResponse(httpResponse));

        return Score.isValidReview(reviewerId, reviewdId, (String) json.get("sellerId"), (String) json.get("winnerId"));
    }

    private String getUserService(String url, String token) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        httpGet.addHeader("Authorization", "Bearer " + token);

        HttpResponse httpResponse = client.execute(httpGet);

        return new BasicResponseHandler().handleResponse(httpResponse);
    }

    private JSONObject postTokenService(List<NameValuePair> params) throws IOException, AuthenticationException, ParseException {
        JSONParser js = new JSONParser();
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(tokenServiceUrl);

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(client_id, secret);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        HttpResponse response = client.execute(httpPost);
        return (JSONObject) js.parse(new BasicResponseHandler().handleResponse(response));
    }



    private String getAdminToken() throws IOException, AuthenticationException, ParseException {
        if (adminToken == null) {
            System.out.println("Getting new admin token");
            adminToken = new Token(getToken(adminUsername, adminPassword));
        }

        if (!adminToken.isValid()) {
            System.out.println("Refreshing admin token");
            adminToken = new Token(refreshToken(adminToken.refresh_token));
        }
        return adminToken.access_token;
    }

    public JSONObject getUserById(String id, String token) throws IOException, ParseException {
        String response = getUserService(userServiceUrl + "/" + id, token);

        JSONParser js = new JSONParser();

        return (JSONObject) js.parse(response);
    }


    public JSONObject getToken(String username, String password) throws IOException, AuthenticationException, ParseException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("grant_type", "password"));

        return postTokenService(params);
    }

    public JSONObject refreshToken(String refreshToken) throws IOException, AuthenticationException, ParseException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("refresh_token", refreshToken));
        params.add(new BasicNameValuePair("grant_type", "refresh_token"));

        return postTokenService(params);
    }


}
