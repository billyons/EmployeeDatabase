public class Employee_Lyons
{

	//data members
	private int id, hired, phone;
	private String first, last, address, department;
	private double salary;
	
	//default constructor
	public Employee_Lyons()
	{
		id = 1000000;
		hired = 2000;
		phone = 1000000000;
		first = "default";
		last = "default";
		address = "default";
		department = "default";
		salary = 40000.0;
	}
	
	//argument constructor
	public Employee_Lyons(int i, String l, String f, int h, double s, int p, String a, String d)
	{
		id = i;
		last = l;
		first = f;
		hired = h;
		salary = s;
		phone = p;
		address = a;
		department = d;
	}
	
	//accessor methods
	public int getID()
	{
		return id;
	}
	
	public String getLast()
	{
		return last;
	}
	
	public String getFirst()
	{
		return first;
	}
	
	public int getHired() //aww yeah
	{
		return hired;
	}
	
	public double getSalary()
	{
		return salary;
	}
	
	public int getPhone()
	{
		return phone;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	//mutator methods
	public void setID(int i)
	{
		id = i;
	}
	
	public void setLast(String l)
	{
		last = l;
	}
	
	public void setFirst(String f)
	{
		first = f;
	}
	
	public void setHired(int h)
	{
		hired = h;
	}
	
	public void setSalary(double s)
	{
		salary = s;
	}
	
	public void setPhone(int p)
	{
		phone = p;
	}
	
	public void setAddress(String a)
	{
		address = a;
	}
	
	public void setDepartment(String d)
	{
		department = d;
	}
	
	//Utility methods
	public String toString()
	{
		return    "Employee ID: " + id +
				"\nEmployee name: " + last.toUpperCase() + ", " + first +
				"\nHired year: " + hired +
				"\nSalary: " + salary +
				"\nPhone: " + phone +
				"\nAddress: " + address +
				"\nDepartment: " + department;
	}
	
	public String toFile()
	{
		return id + ", " + last + ", " + first + ", " + hired + ", " +
				salary + ", " + phone + ", " + address + ", " + department;
	}
	
	public void display()
	{
		System.out.println("****************************************\n" + toString() +
				"\n****************************************");
	}
	
	public int compareTo(Employee_Lyons emp)
	{
		return (id - emp.getID());
	}
	
	public Employee_Lyons deepCopy()
	{
		return new Employee_Lyons(id, last, first, hired, salary, phone, address, department);
	}
	
}