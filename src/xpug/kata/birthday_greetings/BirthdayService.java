package xpug.kata.birthday_greetings;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class BirthdayService {

    public static final String SUBJECT = "Happy Birthday!";
    private final EmployeeBook employeeBook;
    private final MessageSender messageSender;

    public BirthdayService(EmployeeBook employeeBook, MessageSender messageSender) {
        this.employeeBook = employeeBook;
        this.messageSender = messageSender;
    }


    public void sendGreetings(OurDate ourDate) throws IOException, ParseException, MessagingException {
        List<Email> toSend = new ArrayList<Email>();

        List<Employee> employeesWithBirthdayToday = employeeBook.findEmployeesBornOn(ourDate);

        for (Employee employee : employeesWithBirthdayToday) {
            String recipient = employee.getEmail();
            String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
            Email email = new Email(recipient, body);
            toSend.add(email);
        }

        for (Email email : toSend) {
            messageSender.sendMessage("sender@here.com", SUBJECT, email.body, email.recipient);
        }
    }


    public static void main(String[] args) {
        BirthdayService service = new BirthdayService(new FileSystemEmployeeBook("employee_data.txt"), new EmailMessageSender("localhost", "25"));
        try {
            service.sendGreetings(new OurDate("2008/10/08"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
