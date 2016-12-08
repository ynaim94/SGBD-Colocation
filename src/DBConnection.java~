import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class SdzConnection{
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    
    static final String DB_URL = "jdbc:mysql://localhost/TEST";
    
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "123456789";
    
    private static Connection connect = null;
    
    public static Connection getInstance(){
	if(connect == null){
	    try {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Connecting to a selected database...");
		connect = DriverManager.getConnection(DB_URL, USER, PASS);
		System.out.println("Connected database successfully...");
	    
	    } catch (SQLException e) {
		JOptionPane.showMessageDialog(null, e.getMessage(), "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
		
	    }catch(Exception e){
		//Handle errors for Class.forName
		e.printStackTrace();
		
	    }		
	}
	return connect;	
    }
}
