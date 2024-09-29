package fpt.uni.servlet;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fpt.uni.dao.LabelDAO;
import fpt.uni.dao.PostDAO;
import fpt.uni.model.Account;
import fpt.uni.model.Post;

/**
 * Servlet implementation class CreatePostServlet
 */
@WebServlet("/CreatePostServlet")
public class CreatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreatePostServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Account user = (Account) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}

		LabelDAO labelDAO = new LabelDAO();
		request.setAttribute("labels", labelDAO.getAllLabels());

		// Forward the request to the create post JSP page
		request.getRequestDispatcher("/create-post.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		Account user = (Account) request.getSession().getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}

		String content = request.getParameter("content");
		Long labelId = Long.parseLong(request.getParameter("labelId"));

		Post post = new Post();
		post.setContent(content);
		post.setLabelId(labelId);
		post.setAccountId(user.getId());

		PostDAO postDAO = new PostDAO();
		postDAO.createPost(post); // Your method to save the post in the database

		response.sendRedirect("PostServlet"); // Redirect after submission
	}

}
