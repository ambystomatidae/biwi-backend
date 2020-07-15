package org.biwi.rest.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.io.InputStream;


@RequestScoped
public class BucketManager {

    private final String bucketName;
    private final String projectId;

    public BucketManager() {
        this.bucketName = "imgs-biwi";
        this.projectId = "biwi1920";
    }

    public String storeImage(byte[] image, String name) {
        String uri = null;
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("biwi1920-2e1ab49121ea.json");
            Credentials creds = GoogleCredentials.fromStream(resourceAsStream);
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(creds)
                    .setProjectId(this.projectId)
                    .build().getService();
            BlobId blobId = BlobId.of(this.bucketName, name);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, image);
            uri = "http://storage.googleapis.com/" + this.bucketName + "/" + name;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
