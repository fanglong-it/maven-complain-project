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
public class Approval {
	private Long id;
	private Long postId; // Foreign key reference to Post
	private Long adminId; // Foreign key reference to Account
	private String status;
	private Date approvalTime;
	private String comment; // Admin comment for approval/rejection
}