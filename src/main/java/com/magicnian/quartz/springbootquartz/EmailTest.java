//package com.magicnian.quartz.springbootquartz;
//
//import lombok.extern.slf4j.Slf4j;
//
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.UnsupportedEncodingException;
//import java.util.*;
//
///**
// * 发邮件测试
// * Created by liunn on 2018/1/26.
// */
//@Slf4j
//public class EmailTest {
//
//    private static String myEmailAccount = "944968025@qq.com";
//    private static String myEmailPassword = "hupayhfndylibfhj";
//
//    private static String myEmailSMTPHost = "smtp.qq.com";
//
//    private static String recevieMailAccount = "moshunian@163.com,liuniannian1223@gmail.com";
//
//    public static void main(String[] args) throws Exception {
//        Properties props = new Properties();
//        props.setProperty("mail.transport.protocol", "smtp");
//        props.setProperty("mail.smtp.host", myEmailSMTPHost);
//        props.setProperty("mail.smtp.auth", "true");
//
//        final String smtpPort = "465";
//        props.setProperty("mail.smtp.port", smtpPort);
//        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
//
//
//        Session session = Session.getInstance(props);
//        session.setDebug(true);
//        String[] recevieMailAccountArr = recevieMailAccount.split(",");
//
//
//        MimeMessage message = createMimeMessage(session, myEmailAccount, Arrays.asList(recevieMailAccountArr));
//
//        Transport transport = session.getTransport();
//        transport.connect(myEmailAccount, myEmailPassword);
//        transport.sendMessage(message, message.getAllRecipients());
//
//        transport.close();
//    }
////
////    public static MimeMessage createMimeMessage(Session session, String sendAccount, List<String> recevieAccounts) throws Exception {
////        MimeMessage message = new MimeMessage(session);
////        message.setFrom(new InternetAddress(sendAccount, "京东价格爬虫上报", "utf-8"));
////        List<InternetAddress> internetAddresses = new ArrayList<>();
////        recevieAccounts.forEach(s -> {
////            try {
////                internetAddresses.add(new InternetAddress(s, "magicnian", "utf-8"));
////            } catch (UnsupportedEncodingException e) {
////                log.error("add recevieAccount error:{}", e);
////            }
////        });
////
////        message.setRecipients(MimeMessage.RecipientType.TO, internetAddresses.stream().toArray(InternetAddress[]::new));
////
////        message.setSubject("显示器，内存，cpu，显卡价格结果", "utf-8");
////        message.setContent("for test", "text/html;charset=UTF-8");
////        message.setSentDate(new Date());
////        message.saveChanges();
////        return message;
////
////    }
//}
