package server_project.Management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	
	protected static Connection connectDB() 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			
			return DriverManager.getConnection(EnvConfig.getProperty("DB_URL"),
					  EnvConfig.getProperty("DB_USER"), EnvConfig.getProperty("DB_PASSWORD"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB connection failed");
			return null;
		}
	}
	
	protected static void closeDB(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
