package org.biwi.rest.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;


import org.biwi.rest.models.Score;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.json.internal.json_simple.parser.JSONParser;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import java.io.IOException;

public class RequestsHandler {

    private static RequestsHandler single_instance = null;

    private RequestsHandler() {
    }

    public static RequestsHandler getInstance() {
        if (single_instance == null)
            single_instance = new RequestsHandler();

        return single_instance;
    }

    String descriptionServiceUrl = ConfigProvider.getConfig().getValue("description.service.url", String.class);

    String activeAuctionsServiceUrl = ConfigProvider.getConfig().getValue("active-auctions.service.url", String.class);

    String userServiceUrl = ConfigProvider.getConfig().getValue("user.service.url", String.class);

    String watchlistPath = ConfigProvider.getConfig().getValue("user.service.watchlist.path", String.class);

    String reviewPath = ConfigProvider.getConfig().getValue("user.service.review.path", String.class);

    String removePath = ConfigProvider.getConfig().getValue("active-auctions.service.remove-auction.path", String.class);


    public JSONObject getAuctionData(String auctionId) throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete deleteFromActive = new HttpDelete(activeAuctionsServiceUrl + "/" + removePath + "/" + auctionId);

        HttpResponse httpResponse = client.execute(deleteFromActive);

        String response = new BasicResponseHandler().handleResponse(httpResponse);
        JSONParser js = new JSONParser();

        return (JSONObject) js.parse(response);
    }

    public String getAuctionName(String auctionId) throws IOException, ParseException {
        HttpClient client = HttpClients.createDefault();
        HttpGet getName = new HttpGet(descriptionServiceUrl + "/" + auctionId + "/short");

        HttpResponse httpResponse = client.execute(getName);

        String response = new BasicResponseHandler().handleResponse(httpResponse);
        JSONParser js = new JSONParser();

        return (String) ((JSONObject) js.parse(response)).get("name");
    }

    public void removeAuction(String auctionId) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpDelete deleteFromWatchlist = new HttpDelete(userServiceUrl + "/" + watchlistPath + "/" + auctionId);

        HttpResponse httpResponse = client.execute(deleteFromWatchlist);
    }


    public void addToUserScore(Score score, String token) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(userServiceUrl + "/" + reviewPath + "/" + score.userId);

        JSONObject reviewData = new JSONObject();
        reviewData.put("rating", score.rating);
        reviewData.put("auctionId", score.auctionId);

        StringEntity params = new StringEntity(reviewData.toString());
        httpPost.addHeader("content-type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + token);
        httpPost.setEntity(params);

        HttpResponse response = client.execute(httpPost);
    }
}
