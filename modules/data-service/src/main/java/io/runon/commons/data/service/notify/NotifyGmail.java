package io.runon.commons.data.service.notify;

import io.runon.commons.config.Config;
import io.runon.commons.crypto.LoginCrypto;
import io.runon.commons.data.service.exception.MailSendFailException;
import io.runon.commons.data.service.exception.SendMailInfoException;
import io.runon.commons.data.service.redis.Redis;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 알림유형 정의
 * @author macle
 */
@Slf4j
public class NotifyGmail {

    public static final String NOTIFY_MAILS_REDIS_KEY = "notify_mails";

    private final String sendGmailId;
    private final String sendGmailPassword;


    private final Object lock = new Object();

    public NotifyGmail() {

        String id = Config.getConfig("send.gmail.id");
        String pwd = Config.getConfig("send.gmail.password");

        if (id == null || pwd == null){
            throw new SendMailInfoException();
        }

        if(Config.getBoolean("send.gmail.encrypt",false)){
            String [] values = LoginCrypto.decryption(id, pwd);
            sendGmailId = values[0];
            sendGmailPassword = values[1];
        }else{
            sendGmailId = id;
            sendGmailPassword = pwd;
        }
    }


    public void sendMail(String title, String content){
        String notifyMailsValue = Redis.getAsync(NOTIFY_MAILS_REDIS_KEY);

        if(notifyMailsValue == null){
            log.debug("notify mails size 0");
            return;
        }

        JSONArray jsonArray = new JSONArray(notifyMailsValue);
        if(jsonArray.length() == 0){
            log.debug("notify mails size 0");
            return;
        }

        synchronized (lock){
            try {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sendGmailId, sendGmailPassword);
                    }
                });

                InternetAddress[] addresses = new InternetAddress[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    addresses[i] = new InternetAddress(jsonArray.getString(i));
                }

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sendGmailId, "runon", "utf-8"));

                message.addRecipients(Message.RecipientType.TO, addresses);
                message.setSubject(title);
                message.setContent(content, "text/html; charset=utf-8");

                Transport.send(message);

                log.debug("send mail " + content);
            }catch (Exception e){
                throw new MailSendFailException(e);
            }

        }
    }

    public static boolean addNotifyMail(String mailAddress){
        String mailsValue = Redis.get(NOTIFY_MAILS_REDIS_KEY);
        if(mailsValue == null){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(mailAddress);
            Redis.set(NOTIFY_MAILS_REDIS_KEY, jsonArray.toString());

            return true;
        }

        JSONArray jsonArray = new JSONArray(mailsValue);
        for (int i = 0; i <jsonArray.length() ; i++) {
            String mail = jsonArray.getString(i);
            if(mail.equals(mailAddress)){
                return false;
            }
        }

        jsonArray.put(mailAddress);
        Redis.set(NOTIFY_MAILS_REDIS_KEY, jsonArray.toString());
        return true;
    }

    public static boolean removeNotifyMail(String mailAddress){
        return Redis.removeArrayValue(NOTIFY_MAILS_REDIS_KEY, mailAddress);
    }


}
