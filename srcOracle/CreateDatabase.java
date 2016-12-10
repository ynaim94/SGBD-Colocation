import java.sql.*;

public class CreateDatabase {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver"; 
   static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XE";

   //  Database credentials
    static final String USER = "SYSTEM";
   static final String PASS = "123456789";
   
    public static void main(String[] args) {
	Connection conn = null;
	Statement stmt = null;
	try{
	    //STEP 2: Register JDBC driver
	    Class.forName(JDBC_DRIVER);

	    //STEP 3: Open a connection
	    System.out.println("Connecting to database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    //STEP 4: Execute a query
	    System.out.println("Creating database...");
	    stmt = conn.createStatement();
      
	    String sql = "CREATE DATABASE COLOCATION";
	    stmt.executeUpdate(sql);
	    System.out.println("Database created successfully...");
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
		    stmt.close();
	    }catch(SQLException se2){
	    }// nothing we can do
	    try{
		if(conn!=null)
		    conn.close();
	    }catch(SQLException se){
		se.printStackTrace();
	    }//end finally try
	}//end try
	System.out.println("Goodbye!");
    }//end main
}//end CreateDatabase
