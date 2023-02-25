package est.dsic.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import est.dsic.models.Note;
import est.dsic.models.User;

public class Userdao {
	ArrayList<User> users = new ArrayList<User>();
	Connection cnx;

	public User authenticat(String email, String pwd) throws ClassNotFoundException, SQLException {
		cnx = database.getConnection();
		User user = null;
		java.sql.Statement state = cnx.createStatement();
		ResultSet resultat = state.executeQuery(
				"SELECT email,pwd,isadmin  FROM  `users` WHERE    email  ='" + email + "'  and pwd =  '" + pwd + "'");
		String email1, pwd1 = null;
		Boolean isadmin;
		while (resultat.next()) {
			email1 = resultat.getString("email");
			pwd1 = resultat.getString("pwd");
			isadmin = resultat.getBoolean("isadmin");
			user = new User();
			user.setEmail(email1);
			user.setPwd(pwd1);
			user.setAdmin(isadmin);
			System.out.println(email1);
		}
		return user;
	}

	public ArrayList<User> getAllusers(User user_current) throws SQLException, ClassNotFoundException {
		cnx = database.getConnection();
		User user = null;
		java.sql.Statement state = cnx.createStatement();
		ResultSet resultat = state.executeQuery("SELECT email,name,isadmin  FROM `users` where email !='"+user_current.getEmail()+"'");
		String email,name = null;
		Boolean isadmin;
		while (resultat.next()){
			email = resultat.getString("email");
			name = resultat.getString("name");
			isadmin = resultat.getBoolean("isadmin");
			user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setAdmin(isadmin);
			users.add(user);
			
		}
		return users;
	}
	public void adduser(User user) {
		
        try {
        	cnx=database.getConnection();
    		Statement state =  cnx.createStatement();
    		String Status =null;
    		if(user.isAdmin()) {
    			Status="1";
    		}else {
    			Status="0";
    		}
    		
			int result= state.executeUpdate("INSERT INTO  `users`  (`email`,`name`,`pwd`,`isadmin`) values ('" + user.getEmail() + "','" + user.getName() + "','" + user.getPwd() + "','" + Status + "' )");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete_user(String email) {
		 try {
	        	cnx=database.getConnection();
	    		Statement state =  cnx.createStatement();

				int result= state.executeUpdate("delete FROM  `users` where email='"+email+"'");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public User getinformationuser(String email) throws Exception {
		cnx = database.getConnection();
		User user = null;
		java.sql.Statement state = cnx.createStatement();
		ResultSet resultat = state.executeQuery(
				"SELECT name,email,pwd,isadmin  FROM  `users` WHERE    email  ='" + email + "'");
		String name,email1, pwd1 = null;
		Boolean isadmin;
		while (resultat.next()) {
			name = resultat.getString("name");
			email1 = resultat.getString("email");
			pwd1 = resultat.getString("pwd");
			isadmin = resultat.getBoolean("isadmin");
			user = new User();
			user.setEmail(email1);
			user.setPwd(pwd1);
			user.setName(name);
			user.setAdmin(isadmin);
			System.out.println(email1);
		}
		return user;
	}
	
	public void modify_user(User user) throws Exception {
		cnx=database.getConnection();
		java.sql.Statement state = cnx.createStatement();
		String Status =null;
		if(user.isAdmin()) {
			Status="1";
		}else {
			Status="0";
		}System.out.println(user.getName());
       int resultat = state.executeUpdate("update `users` set name='" +user.getName()+"' ,pwd='" +user.getPwd()+"' , isadmin='"+Status+"' where email='"+user.getEmail()+"'");
		//String sql ="update users set name=? a pwd=";
		
	}

	
}
