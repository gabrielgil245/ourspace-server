package com.revature.ourspaceserver.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;


public class EmailUtility {
    // https://www.journaldev.com/2532/javamail-example-send-mail-in-java-smtp
    public static void sendEmail(String toEmail, String userName, String status) {
        try {
            final String fromEmail = "noreply.ers.rdc@gmail.com"; //requires valid gmail id
            final String password = "P4ssw0rd!"; // correct password for gmail id
            String subject = "";
            String body = "";
            String link = "http://localhost:4200";

            if (status == "new"){
                subject = "RevaSoNet - User Registration Confirmation";
                body = "Welcome to Revature Social Network! \n\n" +
                        "This is to confirm that your email address was added to the system. \n\n" +
                        "Please click below to login \n\n" +
                        "LINK: " + link;
            } else if (status == "forgot") {
                subject = "RevaSoNet - Forgot Password Confirmation";
                body = "Hi " + userName.toUpperCase() + ",\n\n This is to confirm that you are trying to reset your password. \n\n" +
                        "Click the link to proceed: " + link + "/reset-password/" + toEmail;
            } else if (status == "reset") {
                subject = "RevaSoNet - Reset Password Confirmation";
                body = "Hi " + userName.toUpperCase() + ",\n\n This is to confirm that you have successfully updated your password. \n\n" +
                        "Click the link to login: " + link;
            } else if (status == "update") {
                subject = "RevaSoNet - Update Profile Confirmation";
                body = "Hi " + userName.toUpperCase() + ",\n\n This is to confirm that you are trying to change your password. \n\n" +
                        "Click the link to proceed: " + link + "/edit-profile/" + userName;
            }

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}