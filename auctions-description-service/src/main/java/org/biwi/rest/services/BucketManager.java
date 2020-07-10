package org.biwi.rest.services;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import javax.enterprise.context.RequestScoped;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RequestScoped
public class BucketManager {

    private String bucketName = "imgs-biwi";
    private String projectId = "biwi1920";

    public BucketManager() {
        this.bucketName = "imgs-biwi";
        this.projectId = "biwi1920";
    }

    public String storeImage(byte[] image, String name) {
        String uri = null;
        try {
            Credentials creds = GoogleCredentials.fromStream(new FileInputStream("classes/biwi1920-2e1ab49121ea.json"));
            Storage storage = StorageOptions.newBuilder()
                    .setCredentials(creds)
                    .setProjectId(this.projectId)
                    .build().getService();
            BlobId blobId = BlobId.of(this.bucketName, name);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            image = Files.readAllBytes(Paths.get("classes/test_portrait.jpeg"));
            storage.create(blobInfo, image);
            uri = "http://storage.googleapis.com/" + this.bucketName + "/" + name;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return uri;
    }
}
