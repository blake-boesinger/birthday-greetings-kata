package xpug.kata.birthday_greetings;

import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class AcceptanceTest {

    private List<Message> messagesSent;
    private BirthdayService service;
    private EmployeeBook employeeBook;
    private MessageSender messageSender;

    @Before
    public void setUp() throws Exception {
        messagesSent = new ArrayList<Message>();

        messageSender = new EmailMessageSender("localhost", "25") {
            @Override
            protected void sendMessage(Message msg) {
                messagesSent.add(msg);
            }

        };


    }

    @Test
    public void baseScenario() throws Exception {

        employeeBook = new EmployeeBook() {
                    public List<Employee> findEmployeesBornOn(OurDate ourDate) throws IOException, ParseException {
                        return    Arrays.asList( new Employee("John", null, "2008/10/08", "john.doe@foobar.com"));
                    }
                };

                service = new BirthdayService(employeeBook, messageSender);

        service.sendGreetings(new OurDate("2008/10/08"));

        assertEquals("message not sent?", 1, messagesSent.size());
        Message message = messagesSent.get(0);
        assertEquals("Happy Birthday, dear John!", message.getContent());
        assertEquals("Happy Birthday!", message.getSubject());
        assertEquals(1, message.getAllRecipients().length);
        assertEquals("john.doe@foobar.com", message.getAllRecipients()[0].toString());
    }

    @Test
    public void willNotSendEmailsWhenNobodysBirthday() throws Exception {

        employeeBook = new EmployeeBook() {
                           public List<Employee> findEmployeesBornOn(OurDate ourDate) throws IOException, ParseException {
                               return    Arrays.asList( );
                           }
                       };

                       service = new BirthdayService(employeeBook, messageSender);
        service.sendGreetings(new OurDate("2008/01/01"));

        assertEquals("what? messages?", 0, messagesSent.size());
    }
}
