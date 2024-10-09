package fpt.uni.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		
		String province = request.getParameter("province");
		String district = request.getParameter("district");
		String ward = request.getParameter("ward");
		String location = request.getParameter("location");
		String address = String.format("%s, %s, %s, %s", province, district, ward, location);

		Post post = new Post();
		post.setContent(content);
//		post.setLabelId(labelId);
		post.setAccountId(user.getId());
		post.setLocation(address);

		PostDAO postDAO = new PostDAO();
		postDAO.createPost(post); // Your method to save the post in the database

		response.sendRedirect("PostServlet"); // Redirect after submission
	}

}
