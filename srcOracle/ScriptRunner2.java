import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.io.File;
import java.sql.SQLException;
import java.io.FileNotFoundException;

public class ScriptRunner2 {

    public static  void run(Statement stmt,String fileName){
	
	//Now read line by line
	

	String thisLine, sqlQuery;
	try {
	    
	    FileReader f = new FileReader(fileName);
	    BufferedReader d =new  BufferedReader(f);
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
    }
}
