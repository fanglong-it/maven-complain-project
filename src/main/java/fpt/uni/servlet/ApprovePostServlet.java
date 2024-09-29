package fpt.uni.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fpt.uni.dao.PostDAO;
import fpt.uni.dao.SettingDAO;
import fpt.uni.model.Account;
import fpt.uni.model.Post;
import fpt.uni.model.UserRole;
import fpt.uni.utils.EmailSender;

/**
 * Servlet implementation class ApprovePostServlet
 */
@WebServlet("/ApprovePostServlet")
public class ApprovePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApprovePostServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		long postId = Long.parseLong(request.getParameter("postId"));

		Account account = (Account) request.getSession().getAttribute("user");
		if (account == null || !account.getRole().equals(UserRole.MODERATE.name())) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}
		
		String status = request.getParameter("status");
		String comment = request.getParameter("comment");

		PostDAO postDAO = new PostDAO();

		// Logic to approve/reject the post
		postDAO.updatePostStatus(postId, status, comment, account.getId()); // Pass adminId to the method

		
		SettingDAO settingDAO = new SettingDAO();
		Map<String, String> settings = settingDAO.getLatestSettings();

		// Access settings
		String supportEmail = settings.get("support_email");
		
		Post post = postDAO.getPostById(postId);
		
		EmailSender.sendEmail(supportEmail, status, post.getContent());
		
		response.sendRedirect("DetailPostServlet?id=" + postId); // Redirect back to the post detail page
	}

}
