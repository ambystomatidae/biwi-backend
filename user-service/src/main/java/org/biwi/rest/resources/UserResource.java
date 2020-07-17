package org.biwi.rest.resources;

import io.quarkus.security.identity.SecurityIdentity;
import org.apache.http.auth.AuthenticationException;
import org.biwi.rest.models.*;
import org.biwi.rest.repositories.UserRepository;
import org.biwi.rest.services.BucketManager;
import org.biwi.rest.util.RequestsHandler;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.cache.NoCache;
import org.jose4j.json.internal.json_simple.*;
import org.jose4j.json.internal.json_simple.parser.ParseException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    JsonWebToken accessToken;

    @Inject
    UserRepository userRepository;

    @Inject
    SecurityIdentity identity;

    @Inject
    BucketManager bucketManager;

    String version = ConfigProvider.getConfig().getValue("biwi.version", String.class);

    RequestsHandler requestsHandler = RequestsHandler.getInstance();

    @POST
    @Transactional
    @Path("/login")
    public Response login(Credentials credentials) {
        if (!credentials.isValid())
            return Response.status(400).build();

        try {
            JSONObject token = requestsHandler.getToken(credentials.email, credentials.password);
            BiwiUser user = userRepository.findByEmail(credentials.email);
            token.put("userId", user.id);
            return Response.ok(token).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(401).build();
        }
    }

    @POST
    @Transactional
    @Path("/register")
    public Response register(BiwiUser user) {
        if (!user.isValid())
            return Response.status(400).build();

        try {
            String location = requestsHandler.postUser(user);
            String[] parsed = location.split("/");
            String id = parsed[parsed.length - 1];
            requestsHandler.updateUsername(id);
            /*
            UUID fileId = UUID.randomUUID();
            String imageURI = bucketManager.storeImage(ImageEncoderUtil.decode(user.encodedImage), fileId.toString() + ".jpeg");
            */
            BiwiUser savedUser = new BiwiUser(id, user.email, "");

            userRepository.persist(savedUser);
            return Response.created(URI.create(version + "/" + id)).entity(savedUser).build();
        } catch (Exception e) {
            return Response.status(409).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/user")
    public Response getCurrentUser() throws IOException, ParseException {
        return getUserById(accessToken.getName());
    }

    @GET
    @Path("/user/{userId}")
    public Response getUserById(@PathParam("userId") String userId) throws IOException, ParseException {
        String token = accessToken.getRawToken();
        BiwiUser user = userRepository.findById(userId);

        if (user == null)
            return Response.status(404).build();

        JSONObject keycloakUser = requestsHandler.getUserById(userId, token);
        user.addKeycloakInfo(keycloakUser);

        return Response.ok(user).build();
    }

    @GET
    @Path("user/watchlist")
    public Response getUserWatchlist() {
        BiwiUser persistedUser = userRepository.findById(accessToken.getName());

        return Response.ok(persistedUser.watchlist).build();
    }

    @POST
    @Transactional
    @Path("user/watchlist")
    public Response addToUserWatchList(Auction auction) throws ParseException, IOException, AuthenticationException {
        if (!auction.isValid())
            return Response.status(400).build();

        BiwiUser persistedUser = userRepository.findById(accessToken.getName());

        if (persistedUser.watchlist.contains(auction))
            return Response.status(409).entity("{\"errorMessage\": \"Auction already in watchlist\"}").build();

        persistedUser.addToWatchlist(auction);
        return Response.ok(persistedUser.watchlist).build();
    }

    @DELETE
    @Transactional
    @Path("user/watchlist/{auctionId}")
    public Response removeFromWatchlist(@PathParam("auctionId") String auctionId){
        BiwiUser persistedUser = userRepository.findById(accessToken.getName());
        Auction auction = new Auction(auctionId);

        if (!persistedUser.watchlist.contains(auction))
            return Response.status(409).entity("{\"errorMessage\": \"Auction not in watchlist\"}").build();

        persistedUser.removeFromWatchlist(auction);
        return Response.ok(persistedUser.watchlist).build();
    }

    @DELETE
    @Transactional
    @Path("/watchlist/{auctionId}")
    public Response removeFromAllWatchlists(@PathParam("auctionId") String auctionId) {
        userRepository.removeFromWatchlist(new Auction(auctionId));
        return Response.ok().build();
    }

    @POST
    @Transactional
    @Path("user/review/{userId}")
    public Response addReview(@PathParam("userId") String userId, Score score) throws ParseException, IOException, AuthenticationException {
        if (!score.isValid())
            return Response.status(400).build();

        if (!requestsHandler.isValidReview(accessToken.getName(), userId, score.auctionId))
            return Response.status(403).build();

        BiwiUser persistedUser = userRepository.findById(userId);

        if (persistedUser == null)
            return Response.status(404).build();

        persistedUser.addToScore(score);
        return Response.ok().build();
    }


    @POST
    @Transactional
    @Path("/refresh")
    public Response refreshToken(Token token) {
        if (!token.isValidRefresh())
            return Response.status(400).build();

        try {
            JSONObject newToken = requestsHandler.refreshToken(token.refresh_token);
            return Response.ok(newToken).build();
        } catch (Exception e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/validate")
    @NoCache
    public User me() {
        return new User(identity);
    }

    public static class User {
        public final String username;

        User(SecurityIdentity identity) {
            this.username = identity.getPrincipal().getName();
        }
    }



    /*
    @POST
    @Transactional
    @Path("/update/{userId}")
    public Response update(@PathParam("userId") Long userId, BiwiUser user) {
        if (user != null && user.isValidUser()) {
            BiwiUser persistedUser = userRepository.findById(userId);
            if (persistedUser != null) {
                persistedUser.updateData(user);
                return Response.created(URI.create(Long.toString(persistedUser.id))).build();
            } else {
                return Response.status(409).build();
            }
        } else {
            return Response.status(400).build();
        }
    }
    */
}
