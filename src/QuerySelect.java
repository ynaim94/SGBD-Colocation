//STEP 1. Import required packages
import java.sql.*;

public class QuerySelect {
    // JDBC driver name and database URL
    static QueryCreation create;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/TEST";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123456789";
   
    public static void main(String[] args) {
	Connection conn = null;
	Statement stmt = null;
	try{
	    //STEP 2: Register JDBC driver
	    Class.forName("com.mysql.jdbc.Driver");

	    //STEP 3: Open a connection
	    System.out.println("Connecting to a selected database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    System.out.println("Connected database successfully...");

	    //STEP 4: Execute a query
	    System.out.println("Selecting records from the table...");
	    stmt = conn.createStatement();
      
	    ResultSet rset = stmt.executeQuery("select * from EXAMPLE");
	    ResultSetMetaData rSetMetaData = rset.getMetaData();
	    for (int i=1; i<= rSetMetaData.getColumnCount(); i++){
		System.out.print(rSetMetaData.getColumnName (i) + ' ');
	    }
	    System.out.print("\n");
	    while(rset.next()){
		//Retrieve by column name
		int id  = rset.getInt("id");
		int age = rset.getInt("age");
		String first = rset.getString("first");
		String last = rset.getString("last");

		//Display values
		System.out.print("ID: " + id);
		System.out.print(", First: " + first);
		System.out.print(", Last: " + last);
		System.out.println(", Age: " + age);
		
	    }
	    

	}catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
	}catch(Exception e){
	    //Handle errors for Class.forName
	    e.printStackTrace();
	}finally{
	    //finally block used to close resources
	    try{
		if(stmt!=null)
		    conn.close();
	    }catch(SQLException se){
	    }// do nothing
	    try{
		if(conn!=null)
		    conn.close();
	    }catch(SQLException se){
		se.printStackTrace();
	    }//end finally try
	}//end try
	System.out.println("Goodbye!");
    }//end main
}//end QuerySelect


