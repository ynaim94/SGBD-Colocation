//STEP 1. Import required packages
import java.sql.*;

public class CreateTables {

    //    static QueryCreation create;
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
	    System.out.println("Connecting to a selected database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);
	    System.out.println("Connected database successfully...");
      
	    //STEP 4: Execute a query
	    System.out.println("Creating table in given database...");
	    stmt = conn.createStatement();
	    

	    ScriptRunner2.run(stmt,"../srcOracle/sqlscript/base.sql");
	    
	    
	    
	    /* FileReader f = new FileReader("../srcOracle/sqlscript/base.sql");
	    BufferedReader d =new  BufferedReader(f);
	    String thisLine, sqlQuery;
	    try {
		sqlQuery = "";
		while ((thisLine = d.readLine()) != null) 
		    {  
			//Skip comments and empty lines
			if(thisLine.length() > 0 && thisLine.charAt(0) == '-' || thisLine.length() == 0 ) 
			    continue;
			sqlQuery = sqlQuery + " " + thisLine;
			//If one command complete
			if(sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
			    sqlQuery = sqlQuery.replace(';' , ' '); //Remove the ; since jdbc complains
			    try {
				stmt.execute(sqlQuery);
			    }
			    catch(SQLException ex) {
			        System.out.println( "Error Creating the SQL Database : " + ex.getMessage());
				System.out.println(sqlQuery);
			    }
			    catch(Exception ex) {
			        System.out.println( "Error Creating the SQL Database : " + ex.getMessage());
				System.out.println(sqlQuery);
			    }

			    sqlQuery = "";
			}   
		    }
	    }
	     catch(IOException ex) {
		 System.out.println( "Error Creating the SQL Database : " + ex.getMessage());
	     }
	    catch(Exception ex) {
		System.out.println( "Error Creating the SQL Database : " + ex.getMessage());
	    }
	    */

	    
	    //	    ScriptRunner runner = new ScriptRunner(conn, false, false);
	    //runner.runScript(new BufferedReader(new FileReader("../srcOracle/sqlscript/base.sql")));
	    
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
