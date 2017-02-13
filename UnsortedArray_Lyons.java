public class UnsortedArray_Lyons
{

	//data members
	private Employee_Lyons[] data;
	private int size, next;
	
	//default constructor
	public UnsortedArray_Lyons()
	{
		size = 10; //Default storage size is 10 employees
		next = 0;
		data = new Employee_Lyons[size];
		for(int i = 0; i < size; i++)
		{
			data[i] = null;
		}
	}
	
	//argument constructor
	public UnsortedArray_Lyons(int s)
	{
		size = s;
		next = 0;
		data = new Employee_Lyons[size];
		for(int i = 0; i < size; i++)
		{
			data[i] = null;
		}
	}
	
	public boolean insert(Employee_Lyons emp)
	{
		if(next == size) //if size limit reached, expand before inserting
		{
			System.out.println("Database reached maximum size, please wait while it creates room for more entries.");
			expand();
		}
		data[next] = emp.deepCopy();
		next++;
		return true;
	}
	
	public Employee_Lyons fetch(int empID)
	{
		for(int i = 0; i < next; i++)
		{
			if(data[i].getID() == empID)
			{
				return data[i].deepCopy();
			}
		}
		return null;
	}
	
	public boolean delete(int empID)
	{
		for(int i = 0; i < next; i++)
		{
			if(data[i].getID() == empID)
			{
				for(int j = i; j < next - 1; j++)
				{
					data[j] = data[j+1];
				}
				next--;
				data[next] = null;
				return true;
			}
		}
		return false;
	}
	
	public boolean update(int empID, Employee_Lyons emp)
	{
		if(!delete(empID)) //if failed to delete original employee
		{
			return false; //update failed
		}
		if(!insert(emp)) //if failed to add updated version
		{
			return false; //update failed
		}
		return true; //otherwise, update success
	}
	
	public Employee_Lyons[] fetchAll(char field, String key)
	{
		int count = 0;
		boolean[] indices = new boolean[next];
		switch(field) //searches the entire data array and sets indices true when key is matched
		{
		//search by last name
		case 'l':
			for(int i = 0; i < next; i++)
			{
				if(data[i].getLast().equals(key))
				{
					count++;
					indices[i] = true;
				}
			}
			break;
		//search by title
		case 't':
			for(int i = 0; i < next; i++)
			{
				if(data[i] instanceof EmployeeWithTitle_Lyons)
				{
					if(((EmployeeWithTitle_Lyons)data[i]).getTitle().equals(key))
					{
						count++;
						indices[i] = true;
					}
				}
			}
			break;
		case 'd':
			for(int i = 0; i < next; i++)
			{
				if(data[i].getDepartment().equals(key))
				{
					count++;
					indices[i] = true;
				}
			}
			break;
		default: //something went wrong if this code is reached
		}
		//adds matching employees to a new array of employees of the proper size
		//(two passes are needed to create an array of the correct size to store matches)
		Employee_Lyons[] list = new Employee_Lyons[count];
		int j = 0;
		for(int i = 0; j < count; i++)
		{
			if(indices[i])
			{
				list[j] = data[i].deepCopy();
				j++;
			}
		}
		return list;
	}
	
	public void showAll()
	{
		System.out.println("********DISPLAYING ALL EMPLOYEES********");
		for(int i = 0; i < next; i++)
		{
			System.out.println(data[i].toString() + "\n****************************************");
		}
	}
	
	public String[] toFile() //prepares the database to be written to a file by creating an array of strings
	//each containing the data of one employee
	{
		String[] employees = new String[next];
		for(int i = 0; i < next; i++)
		{
			employees[i] = data[i].toFile();
		}
		return employees;
	}
	
	private void expand() //doubles the size of the database array, called by insert automatically when needed
	{
		Employee_Lyons[] bigger = new Employee_Lyons[(size*2)];
		for(int i = 0; i < size; i++)
		{
			bigger[i] = data[i];
		}
		for(int j = size; j < (size*2); j++)
		{
			bigger[j] = null;
		}
		size = size*2;
		data = bigger;
	}
	
}