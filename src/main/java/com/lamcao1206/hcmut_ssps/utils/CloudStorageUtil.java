package com.lamcao1206.hcmut_ssps.utils;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public final class CloudStorageUtil {

    private static final String bucketName;
    private static final Storage STORAGE;

    static {
        try {
            Properties properties = new Properties();
            ClassPathResource resource = new ClassPathResource("application.properties");
            properties.load(resource.getInputStream());
            bucketName = properties.getProperty("gcs.bucket.name");
            
            if (bucketName == null || bucketName.trim().isEmpty()) {
                throw new IllegalStateException("GCS bucket name not found in application.properties");
            }

            ClassPathResource credentialsResource = new ClassPathResource("credentials.json");
            STORAGE = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(credentialsResource.getInputStream()))
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Google Cloud Storage: " + e.getMessage(), e);
        }
    }

    private CloudStorageUtil() {
        throw new AssertionError("Utility class - cannot be instantiated");
    }

    public static String uploadFile(MultipartFile file, String fileName) throws IOException {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("File cannot be null or empty");
            }
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new IllegalArgumentException("File name cannot be null or empty");
            }

            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            Blob blob = STORAGE.createFrom(blobInfo, file.getInputStream());
            return blob.signUrl(7, TimeUnit.DAYS).toString();
        } catch (StorageException e) {
            throw new IOException("Failed to upload file to GCS: " + e.getMessage(), e);
        }
    }
}