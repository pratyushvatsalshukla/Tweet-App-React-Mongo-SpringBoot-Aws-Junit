package com.tweetApp.blog.services;

import java.util.List;

import com.tweetApp.blog.entities.Post;
import com.tweetApp.blog.payloads.PostDto;
import com.tweetApp.blog.payloads.PostResponse;

public interface PostService {

	//Create
	 PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) ;
	 
	 
	 // Update
	 
	 PostDto updatePost(PostDto postDto, Integer postId) ;
	 
	 // Delete
	 
	 void  deletePost(Integer postId) ;
	 
	 
	 // get All posts
	 
	 PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) ;
	 
	 // Get Single Post
	 
	 PostDto getPostById(Integer postId) ;
	 
	 // Get All Post By Category
	 
	 List<PostDto> getPostsByCategory(Integer categoryId) ;
	 
	 // get All Posts By User
	 
	 List<PostDto> getPostsByUser(Integer userId) ;
	 
	 //Search Post
	 List<PostDto> searchPosts(String keyword) ;
	 
	
	
}
