package xpug.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BirthdayService {

    public static final String SUBJECT = "Happy Birthday!";
    private final EmployeeBook employeeBook;

    public BirthdayService(EmployeeBook employeeBook) {
        this.employeeBook = employeeBook;
    }




    public void sendGreetings( OurDate ourDate, String smtpHost, int smtpPort) throws IOException, ParseException, AddressException, MessagingException {

        List<Email> toSend = new ArrayList<Email>();


        List<Employee> employeesWithBirthdayToday = employeeBook.findEmployeesBornOn(ourDate);


        for (Employee employee : employeesWithBirthdayToday) {
            String recipient = employee.getEmail();
            String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
            Email email = new Email(recipient, body);
            toSend.add(email);
        }

        for (Email email : toSend) {
            sendMessage(smtpHost, smtpPort, "sender@here.com", SUBJECT, email.body, email.recipient);
        }
    }

    private void sendMessage(String smtpHost, int smtpPort, String sender, String subject, String body, String recipient) throws AddressException, MessagingException {
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

    // made protected for testing :-(
    protected void sendMessage(Message msg) throws MessagingException {
        Transport.send(msg);
    }

    public static void main(String[] args) {
        BirthdayService service = new BirthdayService(new FileSystemEmployeeBook("employee_data.txt"));
        try {
            service.sendGreetings( new OurDate("2008/10/08"), "localhost", 25);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
