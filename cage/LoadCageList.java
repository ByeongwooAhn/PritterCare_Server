package com.example.demo.cage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Cage;
import com.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/cages")
public class LoadCageList {
	
	@Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
	
	@GetMapping("/list")
	public ResponseEntity<?> LoadCage(@RequestHeader("Authorization") String request) {
        
        // 헤더에 토큰 값이 있으면
		if(request != null) {
			String token = request;
			String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
			
			// MariaDB에서 데이터 조회
	        String sql = "SELECT cage_serial_number, cage_name, animal_type FROM cages WHERE username = ?;"; // SQL SELECT 쿼리
			
			try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
	                PreparedStatement pstmt = con.prepareStatement(sql)) {
				
				// 추출한 username 바인딩
				pstmt.setString(1, username); // 첫 번째 파라미터
				
				// SELECT 쿼리 실행
				ResultSet resultSet = pstmt.executeQuery();
				
				// 케이지 리스트 생성
				List<Cage> cages = new ArrayList<>();
				
				// 결과를 반복하면서 리스트에 추가
				while(resultSet.next()) {
					Cage cage = new Cage();
					cage.setCage_serial_number(resultSet.getString("cage_serial_number")); // 케이지 시리얼 넘버
					cage.setCage_name(resultSet.getString("cage_name")); // 케이지 이름
					cage.setAnimal_type(resultSet.getString("animal_type")); // 동물 타입
					cages.add(cage);
				}
				
				if(!cages.isEmpty()) {
					return ResponseEntity.ok().body(cages); // JSON 형태로 리스트 반환
				} else {
					return ResponseEntity.status(404).body("Not found for the cagelist");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
			}
		} else {
			return ResponseEntity.status(400).body("Authorization header is missing or invalid");
		}
	}
}