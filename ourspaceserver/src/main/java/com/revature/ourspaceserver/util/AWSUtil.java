package com.revature.ourspaceserver.util;

public class AWSUtil {
    static public String awsID = System.getenv("AWS_S3_ID");
    static public String awsKey = System.getenv("AWS_S3_KEY");
    static public String region = "us-east-2";
    static public String bucketName = "project2.rev";
}
