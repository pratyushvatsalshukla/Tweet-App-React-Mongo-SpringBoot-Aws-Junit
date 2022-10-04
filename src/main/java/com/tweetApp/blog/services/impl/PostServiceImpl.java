package com.tweetApp.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tweetApp.blog.entities.Category;
import com.tweetApp.blog.entities.Post;
import com.tweetApp.blog.entities.User;
import com.tweetApp.blog.exceptions.ResourceNotFoundException;
import com.tweetApp.blog.payloads.PostDto;
import com.tweetApp.blog.payloads.PostResponse;
import com.tweetApp.blog.repositories.CategoryRepository;
import com.tweetApp.blog.repositories.PostRepository;
import com.tweetApp.blog.repositories.UserRepository;
import com.tweetApp.blog.services.PostService;


@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo ;
	
	@Autowired
	private ModelMapper modelMapper ;
	
	@Autowired
	private UserRepository userRepo ;
	
	@Autowired
	private CategoryRepository categoryRepo ;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) { // Created !!
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", "UserId ", userId)) ;
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category ", "CategoryId ", categoryId)) ; 

		Post createdPost = this.modelMapper.map(postDto, Post.class) ;
		createdPost.setImageName("default.png") ;
		createdPost.setAddedDate(new Date());
		createdPost.setUser(user);
		createdPost.setCategory(category);
		Post newPost = this.postRepo.save(createdPost) ;
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override // Working Fine
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ", " Post id ", postId)) ;
		// Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get() ;
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post) ;
		PostDto updatedPostDto = this.modelMapper.map(updatedPost, PostDto.class) ;
		
		return updatedPostDto;
	}

	@Override  // Working Fine
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post ", " post Id ", postId)) ;
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {       //6.33ms
		
		Sort sort = null ;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort = Sort.by(sortBy).ascending() ;
		}
		else
		{
			sort = Sort.by(sortBy).descending() ;
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort) ;  // Can use Sort.by(sortBy).ascending() or .descending()
		Page<Post> pagePosts = this.postRepo.findAll(p) ;
		List<Post> posts = pagePosts.getContent() ;
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()) ;
		PostResponse postResponse = new PostResponse() ;
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements()) ;
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastpage(pagePosts.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id ", postId)) ;
		PostDto postDto = this.modelMapper.map(post, PostDto.class) ;
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category ", "Category Id ", categoryId)) ;
		List<Post> posts = this.postRepo.findByCategory(category) ;
		List<PostDto> postDtos =  posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos ;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Post By User ", "User Id ", userId)) ;
		List<Post> posts = this.postRepo.findByUser(user) ;
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword) ;
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()) ;
		return postDtos;
	}
	

}
