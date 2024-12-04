package com.example.demo.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Reservation;
import com.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/rev")
public class SetReservation {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
	@PutMapping("/set")
	public ResponseEntity<String> setReservation(@RequestHeader("Authorization") String requestHeader, @RequestBody Reservation requestBody) {
		if(requestHeader != null) {
			
			
			String token = requestHeader; // 헤더로 들어온 토큰값 저장
			String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			
			//요청에서 전달된 데이터 사용
			String cage_serial_number = requestBody.getCage_serial_number();
    		String reserve_name = requestBody.getReserve_name();
    		LocalDate reserve_date = requestBody.getReserve_date();
    		LocalTime reserve_time = requestBody.getReserve_time();
    		int day_loop = requestBody.getDay_loop();
    		int time_loop = requestBody.getTime_loop();
    		String reserve_type = requestBody.getReserve_type();
			
    		String sql = "UPDATE reservation SET reserve_name = ?, reserve_date = ?, reserve_time = ?, day_loop = ?, time_loop = ?, reserve_type = ? FROM reservation WHERE cage_serial_number = (SELECT cage_serial_number FROM cages WHERE username = ? AND cage_serial_number = ?) AND reserve_name = ?;";
			
			try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				//전달 받은 데이터 바인딩
				pstmt.setString(1, reserve_name);
    			pstmt.setObject(2, reserve_date);
    			pstmt.setObject(3, reserve_time);
    			pstmt.setInt(4, day_loop);
    			pstmt.setInt(5, time_loop);
    			pstmt.setString(6, reserve_type);
    			pstmt.setString(7,  username);
    			pstmt.setString(8, cage_serial_number);
    			pstmt.setString(9, reserve_name);
				
				int rowsAffected = pstmt.executeUpdate();
				if(rowsAffected > 0) {
					return ResponseEntity.ok("Update Reservation Successful!");
				} else {
					return ResponseEntity.status(404).body("No matching data found to update."); // 업데이트 할 데이터 없음
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage()); // SQL 오류
			}
		} else {
			return ResponseEntity.status(401).body("Authorization header is missing or invalid");  // 인증이 만료되거나 유효하지 않아 실패함
		}
	}
}
