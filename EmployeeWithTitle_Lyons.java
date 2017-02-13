public class EmployeeWithTitle_Lyons extends Employee_Lyons
{

	//additional data members
	private String title;
	private double bonus;
	
	//default constructor
	public EmployeeWithTitle_Lyons()
	{
		super();
		title = "default";
		bonus = 1000.0;
	}
	
	//argument constructor
	public EmployeeWithTitle_Lyons(int i, String l, String f, int h, double s, int p, String a, String d, String t, double b)
	{
		super(i, l, f, h, s, p, a, d);
		title = t;
		bonus = b;
	}
	
	//additional accessors
	public String getTitle()
	{
		return title;
	}
	
	public double getBonus() //everybody loves getting a bonus
	{
		return bonus;
	}
	
	//additional mutators
	public void setTitle(String t)
	{
		title = t;
	}
	
	public void setBonus(double b)
	{
		bonus = b;
	}
	
	//utility methods
	public String toString()
	{
		return super.toString() +
				"\nTitle: " + title +
				"\nBonus: " + bonus;
	}
	
	public String toFile()
	{
		return super.toFile() + ", " + title + ", " + bonus;
	}
	
	public Employee_Lyons deepCopy()
	{
		return new EmployeeWithTitle_Lyons(getID(), getLast(), getFirst(), getHired(), getSalary(),
				getPhone(), getAddress(), getDepartment(), title, bonus);
	}
}