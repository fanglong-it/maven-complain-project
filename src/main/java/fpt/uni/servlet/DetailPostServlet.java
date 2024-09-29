package fpt.uni.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fpt.uni.dao.PostDAO;
import fpt.uni.model.Account;
import fpt.uni.model.Post;
import fpt.uni.model.UserRole;

/**
 * Servlet implementation class DetailPostServlet
 */
@WebServlet("/DetailPostServlet")
public class DetailPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailPostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Account account = (Account) request.getSession().getAttribute("user");
		if(account == null || !account.getRole().equals(UserRole.MODERATE.name())) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}
		String id = request.getParameter("id");
		if (id != null) {
			PostDAO postDAO = new PostDAO();
            long postId = Long.parseLong(id);
            Post post = postDAO.getPostById(postId); // Fetch the post details
            
            if (post != null) {
                request.setAttribute("post", post);
                request.getRequestDispatcher("post-detail.jsp").forward(request, response);
            } else {
                // Handle the case when the post is not found
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            }
        } else {
            // Handle the case when the ID is not provided
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Post ID is required");
        }
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
