package com.example.demo.reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.database.Reservation;
import com.example.demo.utils.JwtUtil;

@RestController
public class SetLoadReservation {
	@Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;
    
    @GetMapping("/rev")
    public ResponseEntity<?> setReserve(@RequestHeader("Authorization") String requestHeader, @RequestBody Reservation requestBody) {
    	// 헤더에 토큰 값이 있으면
    	if(requestHeader != null) {
    		String token = requestHeader;
    		String username = JwtUtil.extractUsername(token);
    		String cage_serial_number = requestBody.getCage_serial_number();
    		String reserve_name = requestBody.getReserve_name();
    		
    		// MariaDB에서 데이터 조회
    		String sql = "SELECT reserve_name, reserve_date, reserve_time, day_loop, time_loop, reserve_type FROM reservation WHERE cage_serial_number = (SELECT cage_serial_number FROM cages WHERE username = ? AND cage_serial_number = ?) AND reserve_name = ?;";
    		
    		try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
    				PreparedStatement pstmt = con.prepareStatement(sql)) {
    			
    			pstmt.setString(1, username);
    			pstmt.setString(2, cage_serial_number);
    			pstmt.setString(3, reserve_name);
    			
    			ResultSet resultSet = pstmt.executeQuery();
    			
    			List<Reservation> reservations = new ArrayList<>();
    			
    			while(resultSet.next()) {
    				Reservation reservation = new Reservation();
    				reservation.setReserve_name(resultSet.getString("reserve_name"));
    				LocalDate date = resultSet.getObject("reserve_date", LocalDate.class);
    				reservation.setReserve_date(date);
    				LocalTime time = resultSet.getObject("reserve_time", LocalTime.class);
    				reservation.setReserve_time(time);
    				reservation.setDay_loop(resultSet.getInt("day_loop"));
    				reservation.setTime_loop(resultSet.getInt("time_loop"));
    				reservation.setReserve_type(resultSet.getString("reserve_type"));
    				reservations.add(reservation);
    			}
    			
    			if(!reservations.isEmpty()) {
    				return ResponseEntity.ok().body(reservations); // JSON 형태로 리스트 반환
				} else {
					return ResponseEntity.status(404).body("Not found for the reservation list");
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
