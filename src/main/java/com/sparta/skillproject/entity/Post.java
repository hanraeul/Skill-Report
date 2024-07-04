package com.sparta.skillproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



    @Builder
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    void setUser(User user) {
        this.user = user;
        if (user != null) {
            user.getPosts().add(this);
        }
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Post post = (Post) obj;
        return Objects.equals(id, post.id) &&
                Objects.equals(title, post.title) &&
                Objects.equals(content, post.content) &&
                Objects.equals(user, post.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, user);
    }

}
