package com.tweetApp.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tweetApp.blog.payloads.ApiResponse;
import com.tweetApp.blog.payloads.PostDto;
import com.tweetApp.blog.payloads.PostResponse;
import com.tweetApp.blog.services.FileService;
import com.tweetApp.blog.services.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController{

	@Autowired
	private PostService postService ;
	
	@Autowired
	private FileService fileService ;
	
	@Value("${project.image}")
	private String path ;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(  // Successfully Implemented
											@RequestBody PostDto postDto,
											@PathVariable Integer userId,
											@PathVariable Integer categoryId
											){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId) ;
		return new ResponseEntity<PostDto>(createPost, HttpStatus.OK) ;
	}
	
	// Get By User         SuccessFully Implemented
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts = this.postService.getPostsByUser(userId) ;
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	@GetMapping("/category/{categoryId}/posts") // WORKING NOW
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId) ;
		
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		
	}
	
	// Get All Posts          // WORKING
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "100", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, 
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir) ;
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK) ;
	}
	
	// Get Post Details By Id    WORKING
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		 PostDto post = this.postService.getPostById(postId) ;
		return new ResponseEntity<PostDto>(post,HttpStatus.OK) ;
	}
	
	// Delete Post By Id      // Working Fine
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId) ;
		return new ApiResponse("Post Deleted SuccessFully !!", true) ;
	}
	
	//Updatepost By Id
	
	@PutMapping("/posts/{postId}")     // Working Fine
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId) ;
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK) ;
	}
	
	// Search     // Working Fine
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable String keywords
			){
		List<PostDto> searchPosts = this.postService.searchPosts(keywords) ;
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK) ;
	}
	
	// Post Image Upload !!
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image, 
			@PathVariable Integer postId
			) throws IOException{
		
		String fileName = this.fileService.uploadImage(path, image) ;
		PostDto postDto = this.postService.getPostById(postId) ;
		postDto.setImageName(fileName) ;
		PostDto updatePost = this.postService.updatePost(postDto, postId) ;
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK) ;
	}
	
	// Method to serve files
	
	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException{
		InputStream resource = this.fileService.getResource(path, imageName) ;
		response.setContentType(MediaType.IMAGE_JPEG_VALUE) ;
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
