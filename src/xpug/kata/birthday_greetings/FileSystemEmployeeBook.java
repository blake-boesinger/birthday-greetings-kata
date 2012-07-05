
package xpug.kata.birthday_greetings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class FileSystemEmployeeBook implements EmployeeBook{
    private String fileName;

    public FileSystemEmployeeBook(String fileName) {
        this.fileName = fileName;
    }

    public List<Employee> findEmployeesBornOn(OurDate ourDate) throws IOException, ParseException {


        List<Employee> employeesWithBirthdayToday = new ArrayList<Employee>();

        BufferedReader in = new BufferedReader(new FileReader(fileName));
                String str = "";
                str = in.readLine(); // skip header
                while ((str = in.readLine()) != null) {
                    String[] employeeData = str.split(", ");
                    Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
                    if (employee.isBirthday(ourDate)) {
                        employeesWithBirthdayToday.add(employee);
                    }
                }

        return employeesWithBirthdayToday;
    }
}
