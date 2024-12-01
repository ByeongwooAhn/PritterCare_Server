package com.example.demo.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.User;
import com.example.demo.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
@RequestMapping("/accounts")
public class LogInController {

    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody User request) {
        // 요청에서 전달된 데이터 사용
        String username = request.getUsername();
        String password = request.getPassword();
        
        // MariaDB에서 데이터 조회
        String sql = "SELECT username FROM users WHERE username = ? AND password = ?;"; // SQL SELECT 쿼리
        
        try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // 전달받은 데이터 바인딩
            pstmt.setString(1, username);  // 첫 번째 파라미터 (ID)
            pstmt.setString(2, password);  // 두 번째 파라미터 (PW)

         // SELECT 쿼리 실행
            ResultSet resultSet = pstmt.executeQuery();
            
            // 결과 반환
            if (resultSet.next()) {
                String token = JwtUtil.generateToken(username); // 토큰 생성
                return ResponseEntity.ok().body(token); // 토큰 반환
            } else {
                return ResponseEntity.status(404).body("Not found for the user");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Database error");
        }
    }
}
