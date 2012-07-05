package xpug.kata.birthday_greetings;/*
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

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface MessageSender {
    void sendMessage(String sender, String subject, String body, String recipient) throws AddressException, MessagingException;
}
