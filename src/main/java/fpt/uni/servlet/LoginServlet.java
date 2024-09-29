package fpt.uni.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fpt.uni.dao.AccountDAO;
import fpt.uni.model.Account;

/**
 * Servlet implementation class LoginServlet
 */

@WebServlet(name = "LoginServlet", urlPatterns = { "/LoginServlet" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		AccountDAO accountDAO = new AccountDAO();
		Account account = accountDAO.getAccountByUsername(username); // Fetch account by username

		if (account != null && account.getPassword().equals(password)) { // Check password (use hashed password in real
																			// case)
			HttpSession session = request.getSession();
			session.setAttribute("user", account); // Store the account object in the session
			response.sendRedirect("PostServlet"); // Redirect to a welcome page or dashboard
		} else {
			request.setAttribute("errorMessage", "Invalid username or password.");
			request.getRequestDispatcher("login.jsp").forward(request, response); // Forward back to the login form
		}
	}

}
