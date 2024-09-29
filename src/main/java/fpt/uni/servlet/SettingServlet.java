package fpt.uni.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fpt.uni.dao.SettingDAO;
import fpt.uni.model.Account;
import fpt.uni.model.UserRole;

/**
 * Servlet implementation class SettingServlet
 */
@WebServlet("/SettingServlet")
public class SettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SettingServlet() {
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
		if (user == null || !user.getRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}
		SettingDAO settingDAO = new SettingDAO();
		Map<String, String> settings = settingDAO.getLatestSettings();
		request.setAttribute("settings", settings);
		request.getRequestDispatcher("setting.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Account user = (Account) request.getSession().getAttribute("user");
		if (user == null || !user.getRole().equalsIgnoreCase(UserRole.ADMIN.name())) {
			response.sendRedirect("login.jsp?error=auth_failure");
			return;
		}
		SettingDAO settingDAO = new SettingDAO();
		// Retrieve the settings from the request
		String siteName = request.getParameter("site_name");
		String supportEmail = request.getParameter("support_email");
		String maxUploadSize = request.getParameter("max_upload_size");
		String maintenanceMode = request.getParameter("maintenance_mode");
		String defaultLanguage = request.getParameter("default_language");

		// Here, you would need to create an update method in SettingDAO
		settingDAO.updateSetting("site_name", siteName);
		settingDAO.updateSetting("support_email", supportEmail);
		settingDAO.updateSetting("max_upload_size", maxUploadSize);
		settingDAO.updateSetting("maintenance_mode", maintenanceMode);
		settingDAO.updateSetting("default_language", defaultLanguage);

		// Redirect back to the settings page after updating
		response.sendRedirect(request.getContextPath() + "/SettingServlet");
	}

}
