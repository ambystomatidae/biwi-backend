package org.biwi.rest.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;


import org.biwi.rest.models.Token;
import org.eclipse.microprofile.config.ConfigProvider;
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

    String activeAuctionsServiceUrl = ConfigProvider.getConfig().getValue("active-auctions.service.url", String.class);

    String getPath = ConfigProvider.getConfig().getValue("active-auctions.service.get-auction.path", String.class);

    String removePath = ConfigProvider.getConfig().getValue("active-auctions.service.remove-auction.path", String.class);

    String tokenServiceUrl = ConfigProvider.getConfig().getValue("keycloak.token.service", String.class);

    String client_id = ConfigProvider.getConfig().getValue("quarkus.oidc.client-id", String.class);

    String secret = ConfigProvider.getConfig().getValue("quarkus.oidc.credentials.secret", String.class);

    String adminUsername = ConfigProvider.getConfig().getValue("keycloak.admin.username", String.class);

    String adminPassword = ConfigProvider.getConfig().getValue("keycloak.admin.password", String.class);

    Token adminToken;


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

    public JSONObject getAuctionData(String auctionId) throws IOException, ParseException, AuthenticationException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(activeAuctionsServiceUrl + "/" + getPath + "/" + auctionId);
        httpGet.addHeader("Authorization", "Bearer " + token);

        HttpResponse httpResponse = client.execute(httpGet);

        String response = new BasicResponseHandler().handleResponse(httpResponse);
        JSONParser js = new JSONParser();

        return (JSONObject) js.parse(response);
    }

    public String removeAuction(String auctionId) throws IOException, AuthenticationException, ParseException {
        String token = getAdminToken();
        HttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(activeAuctionsServiceUrl + "/" + removePath + "/" + auctionId);

        httpDelete.addHeader("Authorization", "Bearer " + token);

        HttpResponse httpResponse = client.execute(httpDelete);

        return new BasicResponseHandler().handleResponse(httpResponse);
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
}
