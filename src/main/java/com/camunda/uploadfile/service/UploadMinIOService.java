package com.camunda.uploadfile.service;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadMinIOService {
    public  MinioClient minioClient;

    public void FileUploaderService(String endpoint, String accessKey, String secretKey)
            throws InvalidKeyException, NoSuchAlgorithmException, IOException {
        
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
    
    public void uploadFile(String bucketName, String objectName, MultipartFile filePath) throws IOException {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket '" + bucketName + "' already exists.");
            }

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(filePath.getInputStream(),  filePath.getSize() , 10*FileUtils.ONE_MB)
                            .build());
            System.out.println("'" + filePath + "' is successfully uploaded as object '" + objectName + "' to bucket '" + bucketName + "'.");
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}