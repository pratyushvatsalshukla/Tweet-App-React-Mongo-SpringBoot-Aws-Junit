package com.tweetApp.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tweetApp.blog.entities.Category;
import com.tweetApp.blog.entities.Post;
import com.tweetApp.blog.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user) ;
	List<Post> findByCategory(Category category) ;
	List<Post> findByTitleContaining(String title) ;
	
}
