package com.sparta.skillproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "refresh_token")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private LocalDateTime expirationAt;

	public void updateToken(String token) {
		this.token = token;
	}

	public void updateExpirationAt(LocalDateTime expirationAt) {
		this.expirationAt = expirationAt;
	}
}
