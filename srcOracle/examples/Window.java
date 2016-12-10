 import java.sql.*;


public class Window {

    private Connection conn;


    public Window( Connection conn1) {
	conn = conn1;
    }


    public void run (String s){

	if (s == "1")
	    consulterColocation();
	//*******Rajouter les autres requêtes après test*************

    }

    public void consulterColocation (){
	
		
    }
}
