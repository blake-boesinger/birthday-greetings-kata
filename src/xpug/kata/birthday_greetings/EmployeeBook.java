
package xpug.kata.birthday_greetings;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface EmployeeBook {

    List<Employee> findEmployeesBornOn(OurDate ourDate) throws IOException, ParseException;
}
