import java.time.LocalDate;
import java.util.Date;

/**
 * POJO class for Employee Objects obtained from retrieving data
 * from the database;
 */
public class Employee {
    private int id;
    private String name, address;
    private char gender;
    private double salary;
    private Date date;
    private long phone;
    String department;
    String company_name;
    int company_id;
    private String active;

    public Employee(int id, String name, char gender, double salary, Date date, long phone, String address, String department) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.date = date;
        this.phone = phone;
        this.address = address;
        this.department = department;
    }

    public Employee(int id, String name, char gender, double salary, Date date, long phone, String address, String department, String company_name, String active) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.salary = salary;
        this.date = date;
        this.phone = phone;
        this.department = department;
        this.company_name = company_name;
        this.active = active;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public char getGender() { return gender; }

    public void setGender(char gender) { this.gender = gender; }

    public double getSalary() { return salary; }

    public void setSalary(double salary) { this.salary = salary; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public long getPhone() { return phone; }

    public void setPhone(long phone) { this.phone = phone; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department;}

    public String getActive() { return active; }

    public void setActive(String active) { this.active = active; }

    public String getCompany_name() { return company_name; }

    public void setCompany_name(String company_name) { this.company_name = company_name; }

    @Override
    public String toString() {
        return "Id: " + this.id + " | Name: " + this.name + " | Gender: " + this.gender + " | Salary: " + this.salary
                + " | Date: " + this.date + " | Phone: " + this.phone + " | Address: " + this.address
                + " | Department: " + this.department;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        Employee e = (Employee) obj;
        return Integer.compare(this.getId(), e.getId()) == 0
                && this.getName().equals(e.getName())
                && Character.compare(this.getGender(), e.getGender()) == 0
                && Double.compare(this.getSalary(), e.getSalary()) == 0
                && String.valueOf(this.getDate()).equals(String.valueOf(e.getDate()))
                && Long.compare(this.getPhone(), e.getPhone()) == 0
                && this.getAddress().equals(e.getAddress())
                && this.getDepartment().equals(e.getDepartment());
    }
}
