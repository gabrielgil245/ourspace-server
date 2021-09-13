package com.revature.ourspaceserver.service;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Service;

import java.io.*;

@Service("S3Service")
public class S3Service {

    public AmazonS3 initialize(){
        String awsID = System.getenv("AWS_S3_ID");
        String awsKey = System.getenv("AWS_S3_KEY");
        String region = "us-east-2";

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsID, awsKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        return s3Client;
    }

    public void uploadProfilePic(byte[] bytes, String fileName) throws IOException {
        AmazonS3 s3Client = initialize();
        String bucketName = "project2.rev/profilepics";
        InputStream targetStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,targetStream,objectMetadata);
        s3Client.putObject(objectRequest);
    }

    public void uploadPostPic(byte[] bytes, String fileName) throws IOException {
        AmazonS3 s3Client = initialize();
        String bucketName = "project2.rev/postpics";
        InputStream targetStream = new ByteArrayInputStream(bytes);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(bytes.length);
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,targetStream,objectMetadata);
        s3Client.putObject(objectRequest);
    }

}
