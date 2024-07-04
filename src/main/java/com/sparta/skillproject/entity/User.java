package com.sparta.skillproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String introduce;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private final List<UserPasswordRecord> passwordRecords = new ArrayList<>();



	//회원 정보 수정
	public void editInfo(String nickname, String introduce) {
		this.nickname = nickname;
		this.introduce = introduce;
	}
	// 비밀 번호 변경
	public void ChangePassword(String password) {
		this.password = password;
	}
	//회원 탈퇴
	public void updateRole(UserRole role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		return this.id.equals(user.getId());
	}

	// Post 관련
	public void addPost(Post post) {
		posts.add(post);
		post.setUser(this);
	}

	public void removePost(Post post) {
		posts.remove(post);
		post.setUser(null);
	}

	public void addPasswordRecord(UserPasswordRecord record) {
		this.passwordRecords.add(record);
		record.setUser(this);
	}

	public void removePasswordRecord(UserPasswordRecord record) {
		this.passwordRecords.remove(record);
		record.setUser(null);
	}



}
