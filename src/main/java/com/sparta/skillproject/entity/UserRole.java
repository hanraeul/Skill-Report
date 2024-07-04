package com.sparta.skillproject.entity;

import lombok.Getter;

@Getter
public enum UserRole {

	USER(Authority.USER),
	WITHDRAWAL(Authority.WITHDRAWAL),
	DELETED(Authority.DELETED);

	private final String authority;

	UserRole(String authority) {
		this.authority = authority;
	}

	public static class Authority {
		public static final String USER = "ROLE_USER";  // 사용자
		public static final String WITHDRAWAL = "ROLE_WITHDRAWAL";
		public static final String DELETED = "ROLE_DELETED";
	}

	public static UserRole from(String value) {
		for (UserRole status : UserRole.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}

	public String getValue(){
		return this.authority;
	}
}
