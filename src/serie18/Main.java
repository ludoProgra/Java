package serie18;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		
		Connection connection;
		try {
			connection = AccessBDGen.connecter("dbinstallations", "root","root");
			FenetrePrincipale f = new FenetrePrincipale(connection); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
