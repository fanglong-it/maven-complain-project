package fpt.uni.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fpt.uni.dao.LabelDAO;
import fpt.uni.dao.PostDAO;
import fpt.uni.filter.PostFilter;
import fpt.uni.model.Label;
import fpt.uni.model.Post;

/**
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private PostDAO postDao = new PostDAO();
	private LabelDAO labelDao = new LabelDAO(); // Assume you have a LabelDao
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set request character encoding to UTF-8
		request.setCharacterEncoding("UTF-8");

		// Initialize PostFilter
		PostFilter filter = new PostFilter();
		filter.setLabelId(
				request.getParameter("labelId") != null ? Long.parseLong(request.getParameter("labelId")) : null);
		filter.setContent(request.getParameter("content"));

		// Initialize pagination
		int pageSize = 5; // Set your desired page size
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		// Get data from DAO
		PostDAO postDAO = new PostDAO();
		LabelDAO labelDAO = new LabelDAO();

		List<Post> posts = postDAO.getPosts(filter, page, pageSize);
		List<Label> labels = labelDAO.getAllLabels();
		int totalPosts = postDAO.getTotalPosts(filter);
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

		filter.setLabels(labels);
		filter.setTotalPages(totalPages);

		// Set attributes for JSP
		request.setAttribute("posts", posts);
		request.setAttribute("currentPage", page);
		request.setAttribute("filter", filter);

		// Forward to JSP
		request.getRequestDispatcher("posts.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set request character encoding to UTF-8
		request.setCharacterEncoding("UTF-8");

		// Initialize PostFilter
		PostFilter filter = new PostFilter();

		// Get filter parameters from the request
		String labelIdParam = request.getParameter("labelId");
		String contentParam = request.getParameter("content");

		filter.setLabelId(labelIdParam != null && !labelIdParam.isEmpty() ? Long.parseLong(labelIdParam) : null);
		filter.setContent(contentParam != null ? contentParam : "");

		// Initialize pagination
		int pageSize = 5; // Set your desired page size
		int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

		// Get data from DAO
		PostDAO postDAO = new PostDAO();
		LabelDAO labelDAO = new LabelDAO();

		List<Post> posts = postDAO.getPosts(filter, page, pageSize);
		List<Label> labels = labelDAO.getAllLabels();
		int totalPosts = postDAO.getTotalPosts(filter);
		int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
		filter.setLabels(labels);
		filter.setTotalPages(totalPages);
		// Set attributes for JSP
		request.setAttribute("posts", posts);
		request.setAttribute("currentPage", page);
		request.setAttribute("filter", filter);

		// Forward to JSP
		request.getRequestDispatcher("posts.jsp").forward(request, response);
	}

}
