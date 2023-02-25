package est.dsic.dal;

import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class database {
	private static Connection cnx;

	
	public static Connection getConnection() throws ClassNotFoundException{
		
		if (cnx==null)
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/db_notes");
			 cnx = ds.getConnection();
			if(cnx == null) {
					System.out.println("error connection");
			}
			return cnx;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnx;
	}
}
