package com.example.demo.arduino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Cage;

@RestController
public class ArduinoConnection {
	
	@Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @PostMapping("/arduino")
    public ResponseEntity<String> arduinoConnection(@RequestBody Cage request) {
    	// 요청에서 전달된 데이터 사용
    	String cage_serial_number = request.getCage_serial_number();
    	
    	// MariaDB에서 데이터 조회
    	String sql = "SELECT username FROM cages WHERE cage_serial_number = ?;";
    	
    	try(Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
    			PreparedStatement pstmt = con.prepareStatement(sql)) {
    		
    		// 전달받은 데이터 바인딩
    		pstmt.setString(1, cage_serial_number); // 첫 번째 파라미터
    		
    		// SELECT 쿼리 실행
    		ResultSet resultSet = pstmt.executeQuery();
    		
    		// 결과 반환
    		if(resultSet.next()) {
    			String username = resultSet.getString("username");
    			return ResponseEntity.ok().body(username); // 유저 아이디 반환
    		} else {
    			return ResponseEntity.status(404).body("Not found for the username");
    		}
    	} catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
        }
    }
}