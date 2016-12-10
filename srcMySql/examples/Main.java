import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

public class Joker {
    public static void main(String[] args)
	throws SQLException, ClassNotFoundException, java.io.IOException {
	// Preparation de la connexion.
	OracleDataSource ods = new OracleDataSource();
	// URL de connexion, on remarque que le pilote utilise est "thin".
	//ods.setURL("jdbc:oracle:thin:@localhost:1521/oracle");

	Connection conn = null;

	Window win;
	
	// Generation de la fenêtre, les boutons de requêtes, fenêtre d'affichage, lier les boutons au différentes requêtes, chaque requête étant codé dans une méthode.
	try {
	    conn = ods.getConnection();
	    //Creation de la fenêtre avec comme argument la connection à la BD.
	    win = new Window(conn);
	    // win.afficher;
	    win.request(args[1]);
	    //
	}
	finally{
	    if (conn != null) {
		conn.close();
	    }
	}
    }
}
