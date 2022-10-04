package com.tweetApp.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetApp.blog.payloads.ApiResponse;
import com.tweetApp.blog.payloads.CategoryDto;
import com.tweetApp.blog.services.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService ;
	
	// Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto) ;
		return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.OK) ;
	}
	
	// Update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto,categoryId) ;
		return new ResponseEntity<CategoryDto>(updatedCategoryDto, HttpStatus.OK) ;
	}
	
	// Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId) ;
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted SuccessFully", true), HttpStatus.OK) ;
	}
	
	// Get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId){
		CategoryDto getCategoryDto = this.categoryService.getCategory(categoryId) ;
		
		return new ResponseEntity<CategoryDto>(getCategoryDto, HttpStatus.OK) ;
	}
	
	// GetAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		List<CategoryDto> getAllCategoryDto = this.categoryService.getCategories() ;
		
		return  ResponseEntity.ok(getAllCategoryDto) ;
	}


}
