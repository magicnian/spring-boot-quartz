package com.magicnian.quartz.springbootquartz.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by liunn on 2018/1/26.
 */
@Component
@Slf4j
public class EmailUtil {

    @Value("${email.smtphost}")
    private String smtHost;

    @Value("${email.sendaccount}")
    private String sendAccounts;

    @Value("${email.myaccount}")
    private String myAccount;

    @Value("${email.mypassword}")
    private String myPassword;


    public void sendEmail(String content) throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", smtHost);
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        Session session = Session.getInstance(props);
        session.setDebug(true);
        String[] recevieMailAccountArr = sendAccounts.split(",");
        MimeMessage message = createMimeMessage(session, myAccount, Arrays.asList(recevieMailAccountArr), content);

        Transport transport = session.getTransport();
        transport.connect(myAccount, myPassword);
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();

    }

    private static MimeMessage createMimeMessage(Session session, String sendAccount, List<String> recevieAccounts, String content) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendAccount, "京东价格爬虫上报", "utf-8"));
        List<InternetAddress> internetAddresses = new ArrayList<>();
        recevieAccounts.forEach(s -> {
            try {
                internetAddresses.add(new InternetAddress(s, "magicnian", "utf-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("add recevieAccount error:{}", e);
            }
        });

        message.setRecipients(MimeMessage.RecipientType.TO, internetAddresses.stream().toArray(InternetAddress[]::new));

        message.setSubject("显示器，内存，cpu，显卡价格结果", "utf-8");
        message.setContent(content, "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;

    }

}
