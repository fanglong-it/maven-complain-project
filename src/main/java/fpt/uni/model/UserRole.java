package fpt.uni.model;

public enum UserRole {
	USER("USER"), ADMIN("ADMIN"), MODERATE("MODERATE");

	private final String role;

	UserRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return role;
	}
}