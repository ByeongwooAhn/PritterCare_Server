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

@RestController
@RequestMapping("/cages/set")
public class SetCage {
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
	// 습도값 변경
	@PostMapping("/humidity")
	public ResponseEntity<String> setHum(@RequestBody Cage hum) {
		
		//요청에서 전달된 데이터 사용
		String setValue = hum.getSetValue();
		String cage_serial_number = hum.getCage_serial_number();
		String username = hum.getUsername();
		
		String sql = "UPDATE cages SET env_humidity = ? WHERE cage_serial_number = ? AND username = ?;";
		
		try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//전달 받은 데이터 바인딩
			pstmt.setString(1, setValue);
			pstmt.setString(2, cage_serial_number);
			pstmt.setString(3, username);
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				return ResponseEntity.ok("Set Cage Successful!");
			} else {
				return ResponseEntity.status(500).body("Failed to Update Data.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
		}
	}
	
	// 온도값 변경
	@PostMapping("/temperature")
	public ResponseEntity<String> setTemp(@RequestBody Cage temp) {
		
		//요청에서 전달된 데이터 사용
		String setValue = temp.getSetValue();
		String cage_serial_number = temp.getCage_serial_number();
		String username = temp.getUsername();
		
		String sql = "UPDATE cages SET env_temperature = ? WHERE cage_serial_number = ? AND username = ?;";
		
		try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//전달 받은 데이터 바인딩
			pstmt.setString(1, setValue);
			pstmt.setString(2, cage_serial_number);
			pstmt.setString(3, username);
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				return ResponseEntity.ok("Set Cage Successful!");
			} else {
				return ResponseEntity.status(500).body("Failed to Update Data.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
		}
	}
	
	//조명값 변경
	@PostMapping("/light")
	public ResponseEntity<String> setLight(@RequestBody Cage light) {
		
		//요청에서 전달된 데이터 사용
		String setValue = light.getSetValue();
		String cage_serial_number = light.getCage_serial_number();
		String username = light.getUsername();
		
		String sql = "UPDATE cages SET env_lighting = ? WHERE cage_serial_number = ? AND username = ?;";
		
		try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//전달 받은 데이터 바인딩
			pstmt.setString(1, setValue);
			pstmt.setString(2, cage_serial_number);
			pstmt.setString(3, username);
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				return ResponseEntity.ok("Set Cage Successful!");
			} else {
				return ResponseEntity.status(500).body("Failed to Update Data.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
		}
	}
	
	// 수위값 변경
	@PostMapping("/water-level")
	public ResponseEntity<String> setWaterLevel(@RequestBody Cage waterLevel) {
		
		//요청에서 전달된 데이터 사용
		String setValue = waterLevel.getSetValue();
		String cage_serial_number = waterLevel.getCage_serial_number();
		String username = waterLevel.getUsername();
		
		String sql = "UPDATE cages SET env_water_level = ? WHERE cage_serial_number = ? AND username = ?;";
		
		try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//전달 받은 데이터 바인딩
			pstmt.setString(1, setValue);
			pstmt.setString(2, cage_serial_number);
			pstmt.setString(3, username);
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				return ResponseEntity.ok("Set Cage Successful!");
			} else {
				return ResponseEntity.status(500).body("Failed to Update Data.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
		}
	}
}
