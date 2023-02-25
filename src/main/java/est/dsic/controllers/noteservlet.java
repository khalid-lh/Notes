package est.dsic.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Vector;

import est.dsic.dal.Notedao;
import est.dsic.models.Note;
import est.dsic.models.User;

@WebServlet("/noteservlet")
public class noteservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Notedao notedao = new Notedao();
	HttpSession session;
	Vector<Note> vector_note = null;
	Vector<Note> vector_notes = null;
	Vector<Integer> notes_deleted = null;
	HashMap<Integer, String> notes_to_modify=null;
	
	public noteservlet(){
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if (action.equals("note_user")) {
			Note note = new Note();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDateTime now = LocalDateTime.now();
			note.setNote(request.getParameter("note"));
			note.setDate(dtf.format(now));
			
			if (vector_notes == null) {
				vector_notes = new Vector<Note>();
			}
			vector_notes.add(note);
			session = request.getSession();
			session.setAttribute("vector_notes", vector_notes);
			response.sendRedirect("user_interface.jsp");
		} else if (action.equals("save")) {
			session = request.getSession();
			Vector<Note> vector_note = new Vector<Note>();
			Vector<Integer> note_deleted = new Vector<Integer>();
			// delete notes
			if (notes_deleted == null) {
			} else {
				note_deleted = (Vector<Integer>) session.getAttribute("notes_deleted");
				for (Integer id : note_deleted) {
					try {
						notedao.deleteNote(id);
					} catch (ClassNotFoundException e){

						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				notes_deleted.removeAllElements();
			}
			// add notes
			if (vector_notes==null){
			} else {
				vector_note = (Vector<Note>) session.getAttribute("vector_notes");
				if (vector_note.isEmpty()) {

				} else {
					for (Note note : vector_note) {
						try {
							User user_current = (User) session.getAttribute("user");
							notedao.addnote(note, user_current);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					vector_notes.removeAllElements();
				}
			}
			// modify notes
			if(notes_to_modify==null){
				
			}else{
				notes_to_modify= (HashMap<Integer,String>) session.getAttribute("notes_to_modify");
				 for (Entry<Integer, String> m : notes_to_modify.entrySet()) {
					 try{
							notedao.modify_note(m.getKey(),m.getValue());
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				 notes_to_modify.clear();
			}
			response.sendRedirect("user_interface.jsp");
		}else if(action.equals("modify_note")){
			int id = Integer.parseInt(request.getParameter("id_note_modify"));
			String note_to_modify=request.getParameter("note_to_modify");
			
			if(notes_to_modify==null) {
				notes_to_modify = new HashMap<Integer,String>();
			}
			notes_to_modify.put(id, note_to_modify);
			session.setAttribute("notes_to_modify", notes_to_modify);
			/*try{
				notedao.modify_note(id,note_to_modify);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			response.sendRedirect("user_interface.jsp");
		} else if (action.equals("logout")) {
			if (vector_notes == null) {
				response.sendRedirect("auth.jsp");
			}else{
				vector_notes.removeAllElements();
				// vector_notes=null;
				response.sendRedirect("auth.jsp");
			}

		} else {
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		/*
		 * try{ notedao.deleteNote(id); }catch(ClassNotFoundException | SQLException e){
		 * e.printStackTrace(); }
		 */
		if (notes_deleted == null) {
			notes_deleted = new Vector<Integer>();
		}
		notes_deleted.add(id);
		session.setAttribute("notes_deleted", notes_deleted);
		response.sendRedirect("user_interface.jsp");
	}

}
