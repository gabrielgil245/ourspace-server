package com.revature.ourspaceserver.service;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
        String bucketName = "project2.rev";
        writeByte(bytes,fileName);
        File file2 = new File("C:\\Users\\jgild\\Revature-Projects\\P2\\uploads\\" + fileName);
        s3Client.putObject(bucketName + "/profilepics", file2.getName(), file2);
    }

    public void uploadPostPic(byte[] bytes, String fileName) throws IOException {
        AmazonS3 s3Client = initialize();
        String bucketName = "project2.rev";
        writeByte(bytes,fileName);
        File file2 = new File("C:\\Users\\jgild\\Revature-Projects\\P2\\uploads\\" + fileName);
        s3Client.putObject(bucketName + "/postpics", file2.getName(), file2);
    }

    void writeByte(byte[] bytes, String fileName)
    {
        File file = new File("C:\\Users\\jgild\\Revature-Projects\\P2\\uploads");
        try {

            // Initialize a pointer
            // in file using OutputStream
            OutputStream os = new FileOutputStream(file +"\\" + fileName);

            // Starts writing the bytes in it
            os.write(bytes);

            // Close the file
            os.close();
        }

        catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
