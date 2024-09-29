package fpt.uni.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Account {
	private Long id;
	private String username;
	private String password; // Store hashed password
	private String email;
	private String googleId;
	private String role;
	private Boolean isActive;
	private Date createdAt;
	private Date lastLogin;
	private String resetToken;
	private Date resetTokenExpiry;
}
