package com.example.demo.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Reservation;

@RestController
@RequestMapping("/rev")
public class AddReservation {
	@Value("${spring.datasource.url}")
	 private String url;
	    
	 @Value("${spring.datasource.username}")
	 private String dbUsername;
	    
	 @Value("${spring.datasource.password}")
	 private String dbPassword;
	 
	 @PostMapping("/add")
	 public ResponseEntity<String> addCage(@RequestHeader ("Authorization") String requestHeader, @RequestBody Reservation requestBody) {
		 
		 // 헤더에 토큰값이 있으면
		 if(requestHeader != null) {
			 
			//요청에서 전달된 데이터 사용
			String cage_serial_number = requestBody.getCage_serial_number();
	   		String reserve_name = requestBody.getReserve_name();
	   		LocalDate reserve_date = requestBody.getReserve_date();
	   		LocalTime reserve_time = requestBody.getReserve_time();
	   		int day_loop = requestBody.getDay_loop();
    		int time_loop = requestBody.getTime_loop();
    		String reserve_type = requestBody.getReserve_type();
			 
			 // MariaDB에 데이터 삽입
			 String sql = "INSERT INTO reservation VALUES(?, ?, ?, ?, ?, ?, ?);";
			 
			 try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					 PreparedStatement pstmt = con.prepareStatement(sql)) {
				 
				// 전달 받은 데이터 바인딩
				pstmt.setString(1, cage_serial_number);
				pstmt.setString(2, reserve_name);
	    		pstmt.setObject(3, reserve_date);
	    		pstmt.setObject(4, reserve_time);
	   			pstmt.setInt(5, day_loop);
	   			pstmt.setInt(6, time_loop);
	   			pstmt.setString(7, reserve_type);
			 
				 // INSERT 쿼리 실행
				 int rowsAffected = pstmt.executeUpdate();
				 if(rowsAffected > 0) {
					 return ResponseEntity.ok("Add Reservation Successful!");
				 } else {
					 return ResponseEntity.status(500).body("Failed to Insert Data.");
				 }
				 
			 } catch(SQLException e) {
				 e.printStackTrace();
				 return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
			 }
		 } else {
			 return ResponseEntity.status(400).body("Authorization header is missing or invalid");
		 }
	 }
}
