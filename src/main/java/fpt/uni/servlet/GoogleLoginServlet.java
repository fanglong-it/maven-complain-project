package fpt.uni.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fpt.uni.dao.AccountDAO;
import fpt.uni.model.Account;
import fpt.uni.model.UserRole;

/**
 * Servlet implementation class GoogleLoginServlet
 */
@WebServlet(name = "GoogleLoginServlet", urlPatterns = { "/googleLogin" })
public class GoogleLoginServlet extends HttpServlet {
	private static final String CLIENT_ID = "";
	private static final String CLIENT_SECRET = "";
	private static final String REDIRECT_URI = "http://localhost:8080/complain-project/googleLogin";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String code = request.getParameter("code");

		if (code != null) {
			String accessToken = exchangeCodeForToken(code);
			if (accessToken != null) {
				// Fetch user info
				JsonObject userInfo = getUserInfo(accessToken);
				if (userInfo != null) {
					String email = userInfo.get("email").getAsString();
					String googleId = userInfo.get("id").getAsString();

					// Check if user exists in the database
					AccountDAO accountDAO = new AccountDAO();
					Account account = accountDAO.getAccountByGoogleId(googleId);

					if (account == null) {
						// Optionally, create a new account
						account = new Account();
						account.setEmail(email);
						account.setGoogleId(googleId);
						account.setUsername(email); // Use email as username
						account.setRole(UserRole.USER.name()); // Set default role
						accountDAO.createAccount(account); // Add new account to the database
					}

					// Log the user in (store account in session or any other mechanism)
					request.getSession().setAttribute("user", account);
					response.sendRedirect("PostServlet"); // Redirect to success page
				} else {
					response.sendRedirect("login.jsp?error=auth_failure");
				}
			} else {
				response.sendRedirect("login.jsp?error=invalid_token");
			}
		} else {
			response.sendRedirect("login.jsp?error=invalid_code");
		}
	}

	private String exchangeCodeForToken(String code) {
		try {
			String urlString = "https://oauth2.googleapis.com/token";
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String parameters = "code=" + code + "&client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
					+ "&redirect_uri=" + REDIRECT_URI + "&grant_type=authorization_code";

			try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
				out.print(parameters);
				out.flush();
			}

			// Read the response
			try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()))) {
				StringBuilder response = new StringBuilder();
				while (scanner.hasNextLine()) {
					response.append(scanner.nextLine());
				}
				return parseAccessToken(response.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String parseAccessToken(String jsonResponse) {
		JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
		return jsonObject.has("access_token") ? jsonObject.get("access_token").getAsString() : null;
	}

	private JsonObject getUserInfo(String accessToken) {
		try {
			String urlString = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// Read the user info response
			try (Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()))) {
				StringBuilder response = new StringBuilder();
				while (scanner.hasNextLine()) {
					response.append(scanner.nextLine());
				}
				return JsonParser.parseString(response.toString()).getAsJsonObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
