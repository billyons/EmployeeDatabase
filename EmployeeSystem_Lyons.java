import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EmployeeSystem_Lyons
{

	public static void main(String[] args)
	{
		//Greet users and start up
		System.out.println("Welcome to the Employee System Database by Bill Lyons!");
		//Initialize keyboard scanner and data structure
		Scanner keyboard = new Scanner(System.in);
		UnsortedArray_Lyons database = readFile(keyboard);		
		
		//Main menu (loop until user selects exit)
		boolean menu = true;
		int menuchoice;
		while(menu)
		{
			System.out.println("************** MAIN MENU ***************");
			System.out.println("(1) Insert Employee");
			System.out.println("(2) Delete Employee");
			System.out.println("(3) Update Employee");
			System.out.println("(4) Search For One Employee");
			System.out.println("(5) Search For All Employees With Matching Title");
			System.out.println("(6) Search For All Employees With Matching Last Name");
			System.out.println("(7) Search For All Employees With Matching Department");
			System.out.println("(8) Save And Exit Program");
			
			menuchoice = 0;
			try{
				menuchoice = Integer.parseInt(keyboard.nextLine());
			} catch(NumberFormatException e) {
				System.out.println("Input not recognized. Please type the number corresponding to the menu choice.");
			}
			
			switch(menuchoice)
			{
			case 1: insert(keyboard, database); break;
			case 2: delete(keyboard, database); break;
			case 3: update(keyboard, database); break;
			case 4: fetch(keyboard, database); break;
			case 5: fetchAll(keyboard, database, 't'); break;
			case 6: fetchAll(keyboard, database, 'l'); break;
			case 7: fetchAll(keyboard, database, 'd'); break;
			case 8: menu = false; break; 
			default: System.out.println("Input not recognized. Please choose a number between 1 and 8."); break;
			}
		} //End menu loop
		
		//Exit Program
		//Write database to file
		System.out.println("Thank you for using this program. Now saving database to 'employeeInfo_Lyons.txt'");
		//Collect information of all employees in the correct format
		String[] employees = database.toFile();
		//Overwrite storage file with current copy of database
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("employeeInfo_Lyons.txt"));
			for(int i = 0; i < employees.length; i++)
			{
			writer.write(employees[i]);
			writer.newLine();
			}
			writer.close();//Save and close file
		} catch(IOException e){
			System.out.println("Error writing database back to file employeeInfo_Lyons.txt");
		}
		//Thank users and terminate
		System.out.println("Save successful. Goodbye!");
		//Close keyboard scanner
		keyboard.close();
	}
	
	//Add employee to database (must handle untitled and with title)
	private static void insert(Scanner keyboard, UnsortedArray_Lyons database)
	{
		System.out.println("Please enter the data of the employee you wish to add in this format:");
		System.out.println("ID, lastname, firstname, hired year, salary, phone, address, department     -OR-");
		System.out.println("ID, lastname, firstname, hired year, salary, phone, address, department, title, bonus");
		String input = keyboard.nextLine(); //Read info
		if(parseEmployee(input) != null) //Create locally (if titled create as EmployeeWithTitle)
		{
		if(database.insert(parseEmployee(input)))//Add to database
			{
				System.out.println("Successfully added to the database.");
			} else {
				System.out.println("Encountered an error adding the employee to the database. Returning to main menu.");
			}
		database.showAll(); //Show all
		} else {
			System.out.println("Encountered an error reading information of one employee. Returning to main menu.");
		}
	}
	
	//Delete employee from database
	private static void delete(Scanner keyboard, UnsortedArray_Lyons database)
	{
		System.out.println("Please enter the Employee ID to remove from the database:");
		try{
			int id = Integer.parseInt(keyboard.nextLine());//Read ID to delete
			if(database.delete(id))//Delete from database
			{
				System.out.println("Employee #" + id + " has been deleted from the database.");
				database.showAll(); //Show all
			} else {
				System.out.println("Employee #" + id + " was not found in the database. Returning to main menu.");
			}
		} catch (NumberFormatException e)
		{
			System.out.println("The Employee ID must be a number. Returning to main menu.");
		}
	}
	
	//Update information of employee
	private static void update(Scanner keyboard, UnsortedArray_Lyons database)
	{
		System.out.println("Please enter the Employee ID to update information:");
		try{
			int id = Integer.parseInt(keyboard.nextLine());//Read ID to update
			Employee_Lyons employee = database.fetch(id);
			if(employee != null)//Fetch data and create local copy
			{
				System.out.println("Displaying current information about Employee #" + id + ":");
				employee.display();
				System.out.println("What information would you like to update?");
				System.out.println("(1) Department (2) Salary (3) Title (4) Address (5) Phone");
				int menuchoice = 0;
				try{
					menuchoice = Integer.parseInt(keyboard.nextLine());//Read information to change
					System.out.println("What should the new information be set to?");
					String newinfo = keyboard.nextLine();
					switch(menuchoice)
					{
					case 1: employee.setDepartment(newinfo); break;//Make changes locally
					case 2: employee.setSalary(Double.parseDouble(newinfo)); break;
					case 3:
							if(employee instanceof EmployeeWithTitle_Lyons)
							{
								((EmployeeWithTitle_Lyons)employee).setTitle(newinfo);
							} else {
								System.out.println("This employee never had a title to begin with! Returning to main menu.");
							} break;							
					case 4: employee.setAddress(newinfo); break;
					case 5: employee.setPhone(Integer.parseInt(newinfo)); break;
					default: System.out.println("Input not recognized. Please choose a number between 1 and 5. Returning to main menu."); break;
					}
				} catch(NumberFormatException e) {
					System.out.println("Input not recognized. Returning to main menu.");
					return;
				}
				database.update(id, employee);//Update database
				database.showAll(); //Show all
			} else {
				System.out.println("Employee #" + id + " was not found in the database. Returning to main menu.");
			}
		} catch (NumberFormatException e)
		{
			System.out.println("The Employee ID must be a number. Returning to main menu.");
		}
		
	}
	
	//Search for employee by ID
	private static void fetch(Scanner keyboard, UnsortedArray_Lyons database)
	{
		System.out.println("Please enter the Employee ID search for in the database:");
		try{
			int id = Integer.parseInt(keyboard.nextLine());//Read ID to search
			Employee_Lyons employee = database.fetch(id);
			if(employee != null)//Fetch from database
			{
				System.out.println("Displaying current information about Employee #" + id + ":");
				employee.display();//Display info
			} else {
				System.out.println("Employee #" + id + " was not found in the database. Returning to main menu.");
			}
		} catch (NumberFormatException e)
		{
			System.out.println("The Employee ID must be a number. Returning to main menu.");
		}
	}	
	
	//List all employees with the same last name, title, or department
	private static void fetchAll(Scanner keyboard, UnsortedArray_Lyons database, char field)
	{
		System.out.println("Please enter the value you wish to find all matches in the database:");
		String key = keyboard.nextLine(); //Read last name, title, or department to search
		Employee_Lyons[] results = database.fetchAll(field, key);//Fetch all matches from database
		if(results.length < 1)
		{
			System.out.println("There were no employees in the database with matching values to: " + key);
		} else {
			System.out.println("Found one or more matching employees. Displaying them now:"); //Display info
			for(int i = 0; i < results.length; i++)
			{
				results[i].display();
			}
		}
	}	
	
	private static Employee_Lyons parseEmployee(String input) //Creates one employee object given data string
	{
		Employee_Lyons employee;
		try{
			String[] employeeinfo = input.split(", ");
			switch(employeeinfo.length)
			{
			case 8: employee = new Employee_Lyons(Integer.parseInt(employeeinfo[0]), employeeinfo[1], employeeinfo[2],
					Integer.parseInt(employeeinfo[3]), Double.parseDouble(employeeinfo[4]), Integer.parseInt(employeeinfo[5]),
					employeeinfo[6], employeeinfo[7]);
					break;
			case 10: employee = new EmployeeWithTitle_Lyons(Integer.parseInt(employeeinfo[0]), employeeinfo[1], employeeinfo[2],
					Integer.parseInt(employeeinfo[3]), Double.parseDouble(employeeinfo[4]), Integer.parseInt(employeeinfo[5]),
					employeeinfo[6], employeeinfo[7], employeeinfo[8], Double.parseDouble(employeeinfo[9]));
					break;
			default:
					System.out.println("An error occured parsing data from this employee - incorrect number of fields.");
					employee = null;
			}
		} catch(NumberFormatException e)
		{
			System.out.println("An error occured parsing data from this employee - invalid data in numeric fields.");
			employee = null;
		}
		return employee;
	}
	
	private static UnsortedArray_Lyons readFile(Scanner keyboard)
	{
	//Attempt to open file and read information to create database
	String filepath = "employeeInfo_Lyons.txt";
	Scanner file;
	UnsortedArray_Lyons database = new UnsortedArray_Lyons();
	boolean loop = true;
	String oneline;
	
	while(loop)
	{
		System.out.println("Attempting to read database info from file: " + filepath);
		try{
				//Check default location
				file = new Scanner(new File(filepath));
				while(file.hasNextLine())
				//Create the object of Employee and insert it to the database
				{
					oneline = file.nextLine();
					if(parseEmployee(oneline) != null)
					{
					database.insert(parseEmployee(oneline));
					} else {
						System.out.println("Encountered an error reading information of one employee. Skipping to next employee.");
					}
				}
				System.out.println("Database creation successful! Listing all entries:");
				database.showAll();
				loop = false;
				file.close();
			}catch(FileNotFoundException e)
			{
					System.out.println("File " + filepath + " not found.");
					System.out.println("Please enter complete path or 'SKIP' to begin with empty database or 'EXIT' to end program:");
					//Check user specified location
					//Query to start with empty database or exit program if not found
					filepath = keyboard.nextLine();
					if(filepath.equalsIgnoreCase("SKIP"))
					{
						loop = false;
					}
					if(filepath.equalsIgnoreCase("EXIT"))
					{
						System.exit(0);
					}
			}
		}
	return database;
	}

}