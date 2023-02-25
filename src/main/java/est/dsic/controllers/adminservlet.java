package est.dsic.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import est.dsic.dal.Notedao;
import est.dsic.dal.Userdao;
import est.dsic.models.User;

public class adminservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Userdao userdao=new Userdao(); 
	HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminservlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action =request.getParameter("action");
		if(action.equals("logout")){
			response.sendRedirect("auth.jsp");
		}else if(action.equals("add_user")){
			User user = new User();
    		String email = request.getParameter("email");
    		String name = request.getParameter("name");
    		String pwd = request.getParameter("pwd");
    		String Status = request.getParameter("status");
    		user.setEmail(email);
			user.setName(name);
			user.setPwd(pwd);
			if(Status.equals("User")) {
				user.setAdmin(false);
			}else{
				user.setAdmin(true);
			}
			try {
				userdao.adduser(user);
			}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("admin_interface.jsp");
		}else if(action.equals("modify_user")){
			User user_modify = new User();
    		String email = request.getParameter("email");
    		String name = request.getParameter("name");
    		String pwd = request.getParameter("pwd");
    		String Status = request.getParameter("status");
    		user_modify.setEmail(email);
    		user_modify.setName(name);
    		user_modify.setPwd(pwd);
    		System.out.println(email);
    		System.out.println(name);
    		System.out.println(pwd);
    		System.out.println(Status);
			if(Status.equals("User")) {
				user_modify.setAdmin(false);
			}else{
				user_modify.setAdmin(true);
			}
			try {
				userdao.modify_user(user_modify);
			}catch(Exception e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect("admin_interface.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		userdao.delete_user(email);
		response.sendRedirect("admin_interface.jsp");
	}

}
