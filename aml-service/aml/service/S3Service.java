package com.appopay.aml.service;

import com.appopay.aml.Exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;


    //    public S3Service(@Value("${accessKeyId}") String accessKeyId,
//                     @Value("${secretAccessKey}") String secretAccessKey) {
//        String bucketName = "appopay-aml-frontend";
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
//        this.s3Client = S3Client.builder()
//                .region(Region.US_EAST_2)  // Set your desired region
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//        this.bucketName = bucketName;
//    }
    public S3Service() {
        String bucketName = "appopay-aml-frontend";
        this.s3Client = S3Client.builder().region(Region.US_EAST_2)  // Set your desired region
                // No explicit credentials provider, the SDK will automatically retrieve credentials from the environment or instance profile
                .build();
        this.bucketName = bucketName;
    }

    public String uploadFile(MultipartFile multipartFile, String keyName) {
        try {
            // Convert MultipartFile to a file
            Path tempFile = Files.createTempFile(keyName, multipartFile.getOriginalFilename());
            multipartFile.transferTo(tempFile.toFile());

            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();

            PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempFile));
            // Clean up the temp file
            Files.delete(tempFile);

            return response.eTag(); // Return the ETag as a confirmation of successful upload
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to upload file to S3" + e);
        }
    }

    public void deleteFile(String keyName) {
        try {
            // Create a DeleteObjectRequest
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(keyName).build();

            // Execute the delete operation
            s3Client.deleteObject(deleteObjectRequest);

            System.out.println("File deleted successfully: " + keyName);
        } catch (S3Exception e) {
            e.printStackTrace();
            throw new CustomException("Failed to delete file from S3: " + e);
        }
    }
}
