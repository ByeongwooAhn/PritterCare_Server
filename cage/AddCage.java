package com.example.demo.cage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Cage;
import com.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/cages")
public class AddCage {
	@Value("${spring.datasource.url}")
	 private String url;
	    
	 @Value("${spring.datasource.username}")
	 private String dbUsername;
	    
	 @Value("${spring.datasource.password}")
	 private String dbPassword;
	 
	 @PatchMapping("/serial")
	 public ResponseEntity<String> serialCheck(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage requestBody) {
		 if(requestHeader != null) {
			 String token = requestHeader;
			 String username = JwtUtil.extractUsername(token);
			 
			 String sql = "SELECT cage_serial_number FROM cages WHERE cage_serial_number = ?;";
			 
			 try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					 PreparedStatement pstmt = con.prepareStatement(sql)) {
				 
				 String cage_serial_number = requestBody.getCage_serial_number();
				 
				 pstmt.setString(1, cage_serial_number);
				 
				 ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					return ResponseEntity.ok("Found Cage Serial Number!");
				} else {
					return ResponseEntity.status(404).body("Not Found Cage Serial Number."); // 업데이트 할 데이터 없음
				}
			 } catch(SQLException e) {
				 e.printStackTrace();
				 return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage()); // SQL 오류
			 }
		} else {
			return ResponseEntity.status(401).body("Authorization header is missing or invalid");  // 인증이 만료되거나 유효하지 않아 실패함
		}
	 }
	 
	 @PatchMapping("/add")
	 public ResponseEntity<String> addCage(@RequestHeader ("Authorization") String requestHeader, @RequestBody Cage requestBody) {
		 
		 // 헤더에 토큰값이 있으면
		 if(requestHeader != null) {
			 String token = requestHeader;
			 String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			 
			 // MariaDB에 데이터 삽입
			 String sql = "UPDATE cages SET username = ?, cage_name = ?, animal_type = ?, env_temperature = ?, env_humidity = ?, env_lighting = ?, env_water_level = ? WHERE cage_serial_number = ?;";
			 
			 try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					 PreparedStatement pstmt = con.prepareStatement(sql)) {
				 
				 // 요청에서 전달된 데이터 사용
				 String cage_serial_number = requestBody.getCage_serial_number();
				 String cage_name = requestBody.getCage_name();
				 String animal_type = requestBody.getAnimal_type();
				 String env_temperature = requestBody.getEnv_temperature();
				 String env_humidity = requestBody.getEnv_humidity();
				 String env_lighting = requestBody.getEnv_lighting();
				 String env_water_level = requestBody.getEnv_water_level();
				 
				 // 전달 받은 데이터 바인딩
				 pstmt.setString(1, username);
				 pstmt.setString(2, cage_name);
				 pstmt.setString(3, animal_type);
				 pstmt.setString(4, env_temperature);
				 pstmt.setString(5, env_humidity);
				 pstmt.setString(6, env_lighting);
				 pstmt.setString(7, env_water_level);
				 pstmt.setString(8, cage_serial_number);
				 
				 // INSERT 쿼리 실행
				 int rowsAffected = pstmt.executeUpdate();
				 if(rowsAffected > 0) {
					 return ResponseEntity.ok("Add Cage Successful!");
				 } else {
					 return ResponseEntity.status(404).body("Not Found Cage Serial Number or Username.");
				 }
				 
			 } catch(SQLException e) {
				 e.printStackTrace();
				 return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
			 }
		 } else {
			 return ResponseEntity.status(401).body("Authorization header is missing or invalid");
		 }
	 }
}
