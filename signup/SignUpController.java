package com.example.demo.signup;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.User;

import org.springframework.beans.factory.annotation.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/accounts")
public class SignUpController {
	
	 @Value("${spring.datasource.url}")
	 private String url;
	    
	 @Value("${spring.datasource.username}")
	 private String dbUsername;
	    
	 @Value("${spring.datasource.password}")
	 private String dbPassword;
	 
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody User request) {
    	// 요청에서 전달된 데이터 사용
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();
        
        // MariaDB에 데이터 삽입
        String sql = "INSERT INTO users VALUES(?, ?, ?)"; // SQL 쿼리
        
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
                PreparedStatement pstmt = con.prepareStatement(sql)) {

               // 전달받은 데이터 바인딩
               pstmt.setString(1, username);  // 첫 번째 파라미터 (ID)
               pstmt.setString(2, password);  // 두 번째 파라미터 (PW)
               pstmt.setString(3, email);     // 세 번째 파라미터 (EMAIL)

               // INSERT 쿼리 실행
               int rowsAffected = pstmt.executeUpdate();
               if (rowsAffected > 0) {
                   return ResponseEntity.ok("Sign Up Successful!");
               } else {
                   return ResponseEntity.status(500).body("Failed to insert data.");
               }
           } catch (SQLException e) {
               e.printStackTrace();
               return ResponseEntity.status(500).body("Error during database query execution: " + e.getMessage());
           }
    }
}
