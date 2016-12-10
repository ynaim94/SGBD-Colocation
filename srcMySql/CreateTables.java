//STEP 1. Import required packages
import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;



public class CreateTables {
    // JDBC driver name and database URL
    static QueryCreation create;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/COLOCATION";

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
	    System.out.println("Creating table in given database...");
	    stmt = conn.createStatement();
	    
	    ScriptRunner runner = new ScriptRunner(conn, false, false);
	    runner.runScript(new BufferedReader(new FileReader("../srcMySql/sqlscript/base.sql")));
	    
	    //String sql = create.CreateColocation() ;
	    /*       String sql = "CREATE TABLE EXAMPLE " +
		     "(id INTEGER not NULL, " +
		     " first VARCHAR(255), " + 
		     " last VARCHAR(255), " + 
		     " age INTEGER, " + 
		     " PRIMARY KEY ( id ))"; */
		
	    //stmt.executeUpdate(sql);
	    System.out.println("Created table in given database...");
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
}//end CreateTables
