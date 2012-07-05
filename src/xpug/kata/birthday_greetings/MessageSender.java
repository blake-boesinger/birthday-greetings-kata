/*
 * -----------------------------------------------------------------------
 *
 * QATARLYST LIMITED
 *
 * -----------------------------------------------------------------------
 *
 * (C) Copyright 2012 Qatarlyst Limited. All rights reserved.
 *
 * NOTICE:  All information contained herein or attendant hereto is,
 *          and remains, the property of Qatarlyst Limited.  Many of the
 *          intellectual and technical concepts contained herein are
 *          proprietary to Qatarlyst Limited. Any dissemination of this
 *          information or reproduction of this material is strictly
 *          forbidden unless prior written permission is obtained
 *          from Qatarlyst Limited.
 *
 * -----------------------------------------------------------------------
 */
package xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MessageSender {


    private final String smtpHost;
    private final String smtpPort;

    public MessageSender(String smtpHost, String smtpPort) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendMessage( String sender, String subject, String body, String recipient) throws AddressException, MessagingException {
           // Create a mail session
           java.util.Properties props = new java.util.Properties();
           props.put("mail.smtp.host", smtpHost);
           props.put("mail.smtp.port", "" + smtpPort);
           Session session = Session.getDefaultInstance(props, null);

           // Construct the message
           Message msg = new MimeMessage(session);
           msg.setFrom(new InternetAddress(sender));
           msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
           msg.setSubject(subject);
           msg.setText(body);

           // Send the message
           sendMessage(msg);
       }

    protected void sendMessage(Message msg) throws MessagingException {
           Transport.send(msg);
       }

}
