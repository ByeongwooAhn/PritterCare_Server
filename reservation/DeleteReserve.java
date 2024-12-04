package com.example.demo.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Reservation;
import com.example.demo.utils.JwtUtil;

@RestController
public class DeleteReserve {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
	@DeleteMapping("/rev")
public ResponseEntity<String> deleteCage(@RequestHeader("Authorization") String requestHeader, @RequestBody Reservation requestBody) {
		
		if(requestHeader != null) {
			
			String token = requestHeader;
			String username = JwtUtil.extractUsername(token);
			
			String cage_serial_number = requestBody.getCage_serial_number();
			String reserve_name = requestBody.getReserve_name();
			
			String sql = "DELETE FROM reservation WHERE cage_serial_number = (SELECT cage_serial_number FROM cages WHERE username = ? AND cage_serial_number = ?) AND reserve_name = ?;";
			
			try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				pstmt.setString(1, username);
				pstmt.setString(2, cage_serial_number);
				pstmt.setString(3, reserve_name);
				
				int rowsAffected = pstmt.executeUpdate();
				if(rowsAffected > 0) {
					return ResponseEntity.ok("Delete Reservation Successful!");
				} else {
					return ResponseEntity.status(500).body("Failed to Delete Data.");
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