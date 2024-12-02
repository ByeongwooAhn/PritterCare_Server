package com.example.demo.cage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Cage;
import com.example.demo.utils.JwtUtil;

@RestController
@RequestMapping("/cages")
public class CageControlData {
	@Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @GetMapping("/lastdata")
    public ResponseEntity<?> LoadLastData(@RequestHeader("Authorization") String requestHeader, @RequestBody Cage requestBody) {
    	
    	// 헤더에 토큰 값이 있으면
    	if(requestHeader != null) {
    		// 헤더 값을 토큰에 저장
    		String token = requestHeader;
    		
    		// 요청에서 전달된 데이터 사용
    		String cage_serial_number = requestBody.getCage_serial_number();
    		
    		String username = JwtUtil.extractUsername(token); // 토큰값에서 username 추출
    		
    		// MariaDB에서 데이터 조회
    		String sql = "SELECT env_temperature, env_humidity, env_lighting, env_water_level FROM cages WHERE username = ? AND cage_serial_number = ?;";
    		
    		try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
    				PreparedStatement pstmt = con.prepareStatement(sql)) {
    			
    			// 추출한 username 바인딩
    			pstmt.setString(1, username);
    			pstmt.setString(2,  cage_serial_number);
    			
    			// SELECT 쿼리 실행
    			ResultSet resultSet = pstmt.executeQuery();
    			
    			// 케이지 리스트 생성
    			List<Cage> cages = new ArrayList<>();
    			
    			// 결과를 반복하면서 리스트에 추가하기
    			while(resultSet.next()) {
    				Cage cage = new Cage();
    				cage.setEnv_temperature(resultSet.getString("env_temperature"));
    				cage.setEnv_humidity(resultSet.getString("env_humidity"));
    				cage.setEnv_lighting(resultSet.getString("env_lighting"));
    				cage.setEnv_water_level(resultSet.getString("env_water_level"));
    				cages.add(cage);
    			}
    			
    			if(!cages.isEmpty()) {
    				return ResponseEntity.ok().body(cages); // JSON 형태로 리스트 반환
    			} else {
    				return ResponseEntity.status(404).body("Not found for the cage data");
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
