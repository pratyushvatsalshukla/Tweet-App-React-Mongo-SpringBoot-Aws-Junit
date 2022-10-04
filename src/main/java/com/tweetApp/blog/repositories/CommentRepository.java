package com.tweetApp.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetApp.blog.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {


}
