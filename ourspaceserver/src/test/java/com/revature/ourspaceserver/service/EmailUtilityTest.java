package com.revature.ourspaceserver.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EmailUtilityTest  {
//
//    @Mock
//    EmailUtility emailUtility = Mockito.mock(EmailUtility.class);
//
//    @SneakyThrows
//    @Test
//    void sendEmail() {
//        //Assign
//        Session session = Session.getInstance(props, auth);
//        MimeMessage msg = new MimeMessage(session);
//        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//        msg.addHeader("format", "flowed");
//        msg.addHeader("Content-Transfer-Encoding", "8bit");
//        msg.setSubject("Subject", "UTF-8");
//        msg.setText("body", "UTF-8");
//        msg.setSentDate(new Date());
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//        Mockito.when(sendEmail())
//
//        //Act
//
//    }


}