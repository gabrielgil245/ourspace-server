package com.revature.ourspaceserver.service;

import com.revature.ourspaceserver.model.User;
import com.revature.ourspaceserver.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service("userService")
public class UserService {
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() { return this.userDao.findAll(); }

    public User getUserById(Integer id) { return this.userDao.findById(id).orElse(null); }

    public User getUserByUsername(String username) {
        return this.userDao.findUserByUsername(username);
    }

    public User getUserByEmail(String email) {
        return this.userDao.findUserByEmail(email);
    }

    public User createUser(User user) {
        User temp = this.userDao.findUserByUsername(user.getUsername());
        if(temp != null) return null;
        temp = this.userDao.findUserByEmail(user.getEmail());
        if(temp != null) return null;
        return this.userDao.save(user);
    }


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

    public void uploadFile(byte[] bytes, String fileName) throws IOException {
        AmazonS3 s3Client = initialize();
        String bucketName = "project2.rev";
        writeByte(bytes,fileName);
        File file2 = new File("C:\\Users\\jgild\\Revature-Projects\\P2\\uploads\\" + fileName);
        s3Client.putObject(bucketName + "/profilepics", file2.getName(), file2);
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
