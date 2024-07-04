package com.sparta.skillproject.service;

import com.sparta.skillproject.dto.PostRequestDto;
import com.sparta.skillproject.dto.PostResponseDto;
import com.sparta.skillproject.entity.Post;
import com.sparta.skillproject.entity.User;
import com.sparta.skillproject.exception.PostNotFoundException;
import com.sparta.skillproject.exception.UserNotFoundException;
import com.sparta.skillproject.repository.PostRepository;
import com.sparta.skillproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public PostResponseDto createPost(PostRequestDto requestDto, Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

		Post post = Post.builder().title(requestDto.getTitle()).content(requestDto.getContent()).build();

		user.addPost(post);

		postRepository.flush();
		return new PostResponseDto(post);
	}

	@Override
	public Page<PostResponseDto> getPosts(Pageable pageable) {
		return postRepository.findAll(pageable).map(PostResponseDto::new);
	}

	@Override
	public PostResponseDto getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));
		return new PostResponseDto(post);
	}

	@Override
	@Transactional
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto, Long userId) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));

		if (!(Objects.equals(post.getUser().getId(), userId))) {
			throw new SecurityException("You are not authorized to update this post");
		}

		post.update(requestDto.getTitle(), requestDto.getContent());
		return new PostResponseDto(post);
	}

	@Override
	@Transactional
	public void deletePost(Long id, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found."));

		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found"));

		if (!(Objects.equals(post.getUser().getId(), user.getId()))) {
			throw new SecurityException("You are not authorized to delete this post");
		}

		user.removePost(post);
	}
}
