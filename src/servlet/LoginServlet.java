package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserAccount;
import utils.DBUtils;
import utils.MyUtils;
@WebServlet(urlPatterns= {"/login"})
public class LoginServlet extends HttpServlet{
	
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		RequestDispatcher dispatcher=
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String un=req.getParameter("username");
		String pw=req.getParameter("password");
		String rememberMe = req.getParameter("rememberMe");
		System.out.println("rememberMe="+rememberMe);
		boolean remember = "Y".equals(rememberMe);
		UserAccount user = null;
		boolean hasError = false;
		String errorString = null;
		if(un==null || pw==null || un.length()==0 || pw.length()==0) {
			hasError=true;
			errorString = "Required username and password";
			System.out.println("Required username and password");
		}else {
			Connection conn = MyUtils.getStoredConnection(req);
			try {
				user=DBUtils.findUser(conn, un, pw);
				if(user == null) {
					hasError = true;
	                errorString = "Username or password invalid";
	                System.out.println("Username or password invalid");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				hasError = true;
                errorString = e.getMessage();
			}
		}
		if(hasError) {
			user = new UserAccount();
			user.setUserName(un);
			user.setPassword(pw);
			req.setAttribute("errorString", errorString);
			req.setAttribute("user", user);
			RequestDispatcher dispatcher= 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(req, resp);			
		}else {
			HttpSession session = req.getSession();
			MyUtils.storeLoginedUser(session, user);
			if(remember) {
				MyUtils.storeUserCookie(resp, user);
			} else {
				MyUtils.deleteUserCookie(resp);
			}
			resp.sendRedirect(req.getContextPath()+"/userInfo");
		}
	}
	
}
