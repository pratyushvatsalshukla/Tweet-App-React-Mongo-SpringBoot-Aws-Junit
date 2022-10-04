package com.tweetApp.blog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tweetApp.blog.configurations.AppConstants;
import com.tweetApp.blog.entities.Role;
import com.tweetApp.blog.repositories.RoleRepository;

@SpringBootApplication
@EnableAutoConfiguration
public class TweetAppApplication  implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder ;
	@Autowired
	private RoleRepository roleRepo ;
	
	public static void main(String[] args) {
		SpringApplication.run(TweetAppApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();}
	
	@Override
	public void run(String ...args) throws Exception{
		System.out.println(this.passwordEncoder.encode("abcdef"));
		
		try {
			Role role = new Role() ;
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			Role role1 = new Role() ;
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = Arrays.asList(role,role1) ;
			List<Role> result = this.roleRepo.saveAll(roles) ;
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
		}
		catch(Exception e) {
			
		}
		
	}

}
