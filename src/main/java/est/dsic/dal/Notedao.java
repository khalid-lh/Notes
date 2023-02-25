package est.dsic.dal;

import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import est.dsic.controllers.authservlet;
import est.dsic.models.Note;
import est.dsic.models.User;
import jakarta.servlet.http.HttpSession;

public class Notedao {

	Connection cnx;
	ArrayList< Note> notes;
	Note note_user;
	
	public void addnote(Note note, est.dsic.models.User user) throws SQLException, ClassNotFoundException{
		cnx=database.getConnection();
		
		Statement state = cnx.createStatement();
        int result= state.executeUpdate("INSERT INTO  `notes`  (`note`,`email`,`date`) values ('" + note.getNote() + "','" + user.getEmail() + "','" + note.getDate() + "' )");
        if(result==1) {
        	System.out.println("nadi");
        }else {
        	System.out.println("9awad");
        } 
	}
	
	public ArrayList<Note> getAllNotes(User user) throws SQLException, ClassNotFoundException{
		cnx=database.getConnection();
		notes=new ArrayList<Note>();
		java.sql.Statement state = cnx.createStatement();
        ResultSet resultat = state.executeQuery("SELECT id,note,date FROM  `notes` where email='"+user.getEmail()+"'");
        String note,date=null;
        int id=0;
        while(resultat.next()){
            note= resultat.getString("note");
            date = resultat.getString("date"); 
            id=resultat.getInt("id");
            note_user= new Note();
            note_user.setId(id);
            note_user.setDate(date);
            note_user.setNote(note);
            notes.add(note_user);
        }
		return notes;
	}
	public void deleteNote(int id) throws ClassNotFoundException, SQLException {
		cnx=database.getConnection();
		java.sql.Statement state = cnx.createStatement();
        int resultat = state.executeUpdate("delete FROM  `notes` where id='"+id+"'");
	}

	public void modify_note(int id,String note) throws ClassNotFoundException, SQLException{
		cnx=database.getConnection();
		java.sql.Statement state = cnx.createStatement();
        int resultat = state.executeUpdate("update   `notes` set note='" +note+"' where id='"+id+"'");
	}
	

}
