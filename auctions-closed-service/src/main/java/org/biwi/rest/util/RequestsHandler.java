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


    String userServiceUrl = ConfigProvider.getConfig().getValue("user.service.url", String.class);

    String watchlistPath = ConfigProvider.getConfig().getValue("user.service.watchlist.path", String.class);

    String removePath = ConfigProvider.getConfig().getValue("active-auctions.service.remove-auction.path", String.class);

    String tokenServiceUrl = ConfigProvider.getConfig().getValue("keycloak.token.service", String.class);

    String client_id = ConfigProvider.getConfig().getValue("quarkus.oidc.client-id", String.class);

    String secret = ConfigProvider.getConfig().getValue("quarkus.oidc.credentials.secret", String.class);

    String adminUsername = ConfigProvider.getConfig().getValue("keycloak.admin.username", String.class);

    String adminPassword = ConfigProvider.getConfig().getValue("keycloak.admin.password", String.class);

    Token adminToken;

    public JSONObject getAuctionData(String auctionId) throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete deleteFromActive = new HttpDelete(activeAuctionsServiceUrl + "/" + removePath + "/" + auctionId);

        HttpResponse httpResponse = client.execute(deleteFromActive);

        String response = new BasicResponseHandler().handleResponse(httpResponse);
        JSONParser js = new JSONParser();

        return (JSONObject) js.parse(response);
    }

    public void removeAuction(String auctionId) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete deleteFromWatchlist = new HttpDelete(userServiceUrl + "/" + watchlistPath + "/" + auctionId);

        HttpResponse httpResponse = client.execute(deleteFromWatchlist);
    }


}
