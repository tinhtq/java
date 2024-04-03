package com.camunda.uploadfile.service;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class UploadMinIOService {
    private static MinioClient minioClient;

    public static void FileUploaderService(String endpoint, String accessKey, String secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    public void uploadFile(String bucketName, String objectName, String filePath) throws IOException {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket '" + bucketName + "' already exists.");
            }

            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(filePath)
                            .build());
            System.out.println("'" + filePath + "' is successfully uploaded as object '" + objectName + "' to bucket '" + bucketName + "'.");
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}