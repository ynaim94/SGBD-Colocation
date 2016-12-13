import java.sql.*;

public class CreateTables {

    
    public static void main(String[] args) {
	Connection conn = DBConnection.getInstance();
	Statement stmt = null;
	
	try{
	    //Create a Statement
	    System.out.println("Creating table in given database...");
	    stmt = conn.createStatement();
	    
	    // Run the script to create Tables
	    ScriptRunner.run(stmt,"../sql/base.sql");

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
