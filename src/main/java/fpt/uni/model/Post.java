package fpt.uni.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Post {
	private Long id;
	private Long accountId; // Foreign key reference to Account
	private Long labelId; // Foreign key reference to Label
	private String content;
	private String status;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt; // For soft delete
	
	private String username;
	private String city;
	private String location;
}
