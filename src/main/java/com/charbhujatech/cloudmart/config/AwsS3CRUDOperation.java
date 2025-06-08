package com.charbhujatech.cloudmart.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

@Component
public class AwsS3CRUDOperation {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private S3Client s3Client;

    AwsS3CRUDOperation(S3Client s3Client)
    {
        this.s3Client=s3Client;
    }

    public void uploadFileOnS3(MultipartFile productImage, String key) throws IOException {
        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(productImage.getContentType())
                        .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(productImage.getBytes()));
    }

    public URL generatePresignedUrl(String key) {
        //S3Presigner presigner = S3Presigner.create();
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return presigner.presignGetObject(
                b -> b.signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
        ).url();
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key) // S3 key, e.g., "products/123/image.jpg"
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public boolean isImageExists(String key) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error while checking image: " + key, e);
        }
    }
}
