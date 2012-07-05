package xpug.kata.birthday_greetings;

import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class AcceptanceTest {

	private List<Message> messagesSent;
	private BirthdayService service;
	
	@Before
	public void setUp() throws Exception {
		messagesSent = new ArrayList<Message>();

        MessageSender messageSender = new EmailMessageSender("localhost", "25") {
            @Override
            protected void sendMessage(Message msg) {
                messagesSent.add(msg);
            }

        };

		service = new BirthdayService(new FileSystemEmployeeBook("employee_data.txt"), messageSender);
	}
	
	@Test
	public void baseScenario() throws Exception {
		
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
		service.sendGreetings( new OurDate("2008/01/01"));
		
		assertEquals("what? messages?", 0, messagesSent.size());
	}
}
