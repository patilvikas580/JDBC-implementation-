import java.io.*;
import java.sql.*;
import java.util.*;

public class Main {
    public static final String URL = "jdbc:mysql://localhost:3306/mydb";
    
    public static final String Username = "root";
    public static final String Password = "Vikas@13";
    
    public Scanner ii=new Scanner(System.in);

    public void insertData() {
        try(Connection connection =DriverManager.getConnection(URL,Username,Password);Statement statement=connection.createStatement()){
        	String query="Insert into Students(name,age,marks)values(?,?,?)";
        	PreparedStatement p=connection.prepareStatement(query);
        	while(true)
        	{
        		System.out.println("Enter name of student");
        		String s=ii.next();
        		System.out.println("Enter age of student");
        		int a=ii.nextInt();
        		System.out.println("Enter marks of student");
        		double d=ii.nextDouble();
        		p.setString(1, s);
        		p.setInt(2, a);
        		p.setDouble(3, d);
        		int rowsAffect=p.executeUpdate();
    			if(rowsAffect>0)
    			{
    				System.out.println("Data Inserted ");
    			}
    			else {
    				System.out.println("Data not inserted");
    			}
    			System.out.println("Want to insert more data (Y/N)");
    			String choice=ii.next();
    			if(choice.toUpperCase().contains("N"))
    			{
    				break;
    			}
        	}
        }catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
    }

    public void updateData() {
        // Implementation for updating data
        try(Connection connection=DriverManager.getConnection(URL,Username,Password);Statement statement=connection.createStatement()){
        	String query="update students set name=? where id=?";
        	PreparedStatement p=connection.prepareStatement(query);
        	while(true)
        	{
        		System.out.println("Enter id of student");
        		int i=ii.nextInt();
        		System.out.println("Enter Name");
        		String n=ii.next();
        		
        		p.setString(1, n);
        		p.setInt(2, i);
        		int r=p.executeUpdate();
        		if(r>0)
        		{
        			System.out.println("Data updated successfully");
        		}else {
        			System.out.println("Data not updated");
        		}
        		System.out.println("Want to insert more data (Y/N)");
    			String choice=ii.next();
    			if(choice.toUpperCase().contains("N"))
    			{
    				break;
    			}
        	}
        	
        }catch(Exception e)
        {
        	System.out.println(e.getCause());
        }
    }

    public void deleteData() {
        // Implementation for deleting data
        try(Connection connection=DriverManager.getConnection(URL,Username,Password);)
        {
        	String query="delete from students where id=?";
        	PreparedStatement p=connection.prepareStatement(query);
        	while(true)
        	{
        		System.out.println("Enter id");
        		int i=ii.nextInt();
        		p.setInt(1, i);
        		int r=p.executeUpdate();
        		if(r>0)
        		{
        			System.out.println("Data deleted successfully");
        		}
        		else {
        			System.out.println("Data deleted failed and abort");
        		}
        		System.out.println("Want to delete more data (Y/N)");
        		String c=ii.next();
        		if(c.toUpperCase().contains("N"))
        		{
        			break;
        		}
        	}
        }catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
    }

    public void displayData() {
        // Implementation for displaying data
    	try (Connection connection = DriverManager.getConnection(URL, Username, Password);
    			
                Statement statement = connection.createStatement()) {
               String query = "SELECT * FROM students";
               ResultSet resultSet = statement.executeQuery(query);
               while (resultSet.next()) {
                   int id = resultSet.getInt("id");
                   String name = resultSet.getString("name");
                   int age = resultSet.getInt("age");
                   double marks = resultSet.getDouble("marks");

                   System.out.println("ID: " + id);
                   System.out.println("Name: " + name);
                   System.out.println("Age: " + age);
                   System.out.println("Marks: " + marks);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Main ss = new Main();
        int ch;

        do {
            System.out.println("Enter your choice:");
            System.out.println("1. Insert Data  2. Update Data  3. Delete Data  4. Display Data  5. Exit");
            ch = sc.nextInt();

            try {
                switch (ch) {
                    case 1:
                        ss.insertData();
                        break;
                    case 2:
                        ss.updateData();
                        break;
                    case 3:
                        ss.deleteData();
                        break;
                    case 4:
                        ss.displayData();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                        
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        } while (ch != 5);

        sc.close();
    }
}
