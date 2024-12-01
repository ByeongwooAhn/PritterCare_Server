package com.example.demo.cage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
@RequestMapping("/cages/set")
public class SetCage {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
		// 케이지 이름 변경
		@PatchMapping("/name")
		public ResponseEntity<String> SetCageName(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage cageName) {
			
			if(requestHeader != null) {
				
				
				String token = requestHeader; // 헤더로 들어온 토큰값 저장
				String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
				
				//요청에서 전달된 데이터 사용
				String cage_name = cageName.getCage_name();
				String cage_serial_number = cageName.getCage_serial_number();
				
				String sql = "UPDATE cages SET cage_name = ? WHERE cage_serial_number = ? AND username = ?;";
				
				try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
						PreparedStatement pstmt = con.prepareStatement(sql)) {
					
					//전달 받은 데이터 바인딩
					pstmt.setString(1, cage_name);
					pstmt.setString(2, cage_serial_number);
					pstmt.setString(3, username);
					
					int rowsAffected = pstmt.executeUpdate();
					if(rowsAffected > 0) {
						return ResponseEntity.ok("Update Cage Name Successful!");
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
	
	// 온도값 변경
	@PatchMapping("/temperature")
	public ResponseEntity<String> SetTemperature(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage temp) {
		
		if(requestHeader != null) {
			
			
			String token = requestHeader; // 헤더로 들어온 토큰값 저장
			String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			
			//요청에서 전달된 데이터 사용
			String env_temperature = temp.getEnv_temperature();
			String cage_serial_number = temp.getCage_serial_number();
			
			String sql = "UPDATE cages SET env_temperature = ? WHERE cage_serial_number = ? AND username = ?;";
			
			try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				//전달 받은 데이터 바인딩
				pstmt.setString(1, env_temperature);
				pstmt.setString(2, cage_serial_number);
				pstmt.setString(3, username);
				
				int rowsAffected = pstmt.executeUpdate();
				if(rowsAffected > 0) {
					return ResponseEntity.ok("Update Temperature Successful!");
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
	
		// 습도값 변경
		@PatchMapping("/humidity")
		public ResponseEntity<String> SetHumidity(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage hum) {
			
			if(requestHeader != null) {
				
				
				String token = requestHeader; // 헤더로 들어온 토큰값 저장
				String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
				
				//요청에서 전달된 데이터 사용
				String env_humidity = hum.getEnv_humidity();
				String cage_serial_number = hum.getCage_serial_number();
				
				String sql = "UPDATE cages SET env_humidity = ? WHERE cage_serial_number = ? AND username = ?;";
				
				try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
						PreparedStatement pstmt = con.prepareStatement(sql)) {
					
					//전달 받은 데이터 바인딩
					pstmt.setString(1, env_humidity);
					pstmt.setString(2, cage_serial_number);
					pstmt.setString(3, username);
					
					int rowsAffected = pstmt.executeUpdate();
					if(rowsAffected > 0) {
						return ResponseEntity.ok("Update Humidity Successful!");
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
	
	//조명값 변경
	@PatchMapping("/light")
	public ResponseEntity<String> SetLight(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage light) {
		
		if(requestHeader != null) {
			
			
			String token = requestHeader; // 헤더로 들어온 토큰값 저장
			String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			
			//요청에서 전달된 데이터 사용
			String env_lighting = light.getEnv_lighting();
			String cage_serial_number = light.getCage_serial_number();
			
			String sql = "UPDATE cages SET env_lighting = ? WHERE cage_serial_number = ? AND username = ?;";
			
			try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				//전달 받은 데이터 바인딩
				pstmt.setString(1, env_lighting);
				pstmt.setString(2, cage_serial_number);
				pstmt.setString(3, username);
				
				int rowsAffected = pstmt.executeUpdate();
				if(rowsAffected > 0) {
					return ResponseEntity.ok("Update Light Successful!");
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
	
	// 수위값 변경
	@PatchMapping("/water-level")
public ResponseEntity<String> SetWaterLevel(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage waterLevel) {
		
		if(requestHeader != null) {
			
			
			String token = requestHeader; // 헤더로 들어온 토큰값 저장
			String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			
			//요청에서 전달된 데이터 사용
			String env_water_level = waterLevel.getEnv_water_level();
			String cage_serial_number = waterLevel.getCage_serial_number();
			
			String sql = "UPDATE cages SET env_water_level = ? WHERE cage_serial_number = ? AND username = ?;";
			
			try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				//전달 받은 데이터 바인딩
				pstmt.setString(1, env_water_level);
				pstmt.setString(2, cage_serial_number);
				pstmt.setString(3, username);
				
				int rowsAffected = pstmt.executeUpdate();
				if(rowsAffected > 0) {
					return ResponseEntity.ok("Update Water Level Successful!");
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
