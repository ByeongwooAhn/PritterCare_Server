package com.example.demo.cage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Cage;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
@RequestMapping("/cages")
public class AddCage {
	@Value("${spring.datasource.url}")
	 private String url;
	    
	 @Value("${spring.datasource.username}")
	 private String dbUsername;
	    
	 @Value("${spring.datasource.password}")
	 private String dbPassword;
	 
	 @PostMapping("/add")
	 public ResponseEntity<String> addCage(@RequestBody Cage request) {
		 
		 // 요청에서 전달된 데이터 사용
		 String cage_serial_number = request.getCage_serial_number();
		 String username = request.getUsername();
		 String cage_name = request.getCage_name();
		 String animal_type = request.getAnimal_type();
		 String env_temperature = request.getEnv_temperature();
		 String env_humidity = request.getEnv_humidity();
		 String env_lighting = request.getEnv_lighting();
		 String env_water_level = request.getEnv_water_level();
		 
		 String sql = "INSERT INTO cages VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		 
		 try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
				 PreparedStatement pstmt = con.prepareStatement(sql)) {
			 
			 // 전달 받은 데이터 바인딩
			 pstmt.setString(1, cage_serial_number);
			 pstmt.setString(2, username);
			 pstmt.setString(3, cage_name);
			 pstmt.setString(4, animal_type);
			 pstmt.setString(5, env_temperature);
			 pstmt.setString(6, env_humidity);
			 pstmt.setString(7, env_lighting);
			 pstmt.setString(8, env_water_level);
			 
			 // INSERT 쿼리 실행
			 int rowsAffected = pstmt.executeUpdate();
			 if(rowsAffected > 0) {
				 return ResponseEntity.ok("Add Cage Successful!");
			 } else {
				 return ResponseEntity.status(500).body("Failed to Insert Data.");
			 }
			 
		 } catch(SQLException e) {
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
		 }
	 }
}
