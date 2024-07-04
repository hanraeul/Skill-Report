package com.sparta.skillproject.repository;

import com.sparta.skillproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
