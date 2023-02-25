package est.dsic.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;

import est.dsic.dal.database;
import est.dsic.dal.Notedao;
import est.dsic.dal.Userdao;
import est.dsic.models.Note;
import est.dsic.models.User;

public class authservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private  HttpSession session;
    Userdao userdao=new Userdao();
    
   
    public authservlet(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action =request.getParameter("action");
		if(action.equals("authentication")){
        	User user = new User();
    		String email = request.getParameter("email");
    		String password = request.getParameter("pwd");
    		     session = request.getSession();
    			//request.getRequestDispatcher("user_interface.jsp").forward(request, response);
               try {
    			user=userdao.authenticat(email, password);
    			if(user==null){
    				response.sendRedirect("cnx_erreur.jsp");
    			}else {
    				if(user.isAdmin()) {
    					session.setAttribute("user", user);
    					response.sendRedirect("admin_interface.jsp");
    				}
    				else{
    					session.setAttribute("user", user);
    					
    					response.sendRedirect("user_interface.jsp");
    				}	
    			}
    		}catch(ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (SQLException e){
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }else {
        	
        }
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
