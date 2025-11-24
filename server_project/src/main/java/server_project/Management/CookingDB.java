package server_project.Management;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server_project.DTOPackages.Cooking; 

public class CookingDB extends DB {

	//리스트를 받아 cooking 테이블에 저장
	// CookingDB.java의 insertCookingList 메서드가 아래와 같아야 빠릅니다.

	public static void insertCookingList(List<Cooking> cookingList) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;

	    try {
	        conn = connectDB(); // ★ 연결을 딱 한 번만 합니다.
	        String sql = "INSERT IGNORE INTO cooking (title, link, bloggerName, postDate, description, thumbnail) VALUES (?, ?, ?, ?, ?, ?)";
	        pstmt = conn.prepareStatement(sql);

	        for (Cooking item : cookingList) {
	            pstmt.setString(1, item.getTitle());
	            pstmt.setString(2, item.getLink());
	            pstmt.setString(3, item.getBloggerName());
	            pstmt.setString(4, item.getPostDate());
	            pstmt.setString(5, item.getDescription());
	            pstmt.setString(6, item.getThumbnail());
	            
	            pstmt.addBatch(); // ★ 전송하지 않고 묶어둡니다.
	            pstmt.clearParameters();
	        }

	        pstmt.executeBatch(); // ★ 100개를 한 방에 쏩니다.

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeDB(conn, pstmt, null); // 마무리
	    }
	}

    // 단일 데이터를 cooking 테이블에 저장
    public static void insertCooking(Cooking item) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement("INSERT IGNORE INTO cooking (title, link, bloggerName, postDate, description, thumbnail) VALUES (?, ?, ?, ?, ?, ?)")) {

            pstmt.setString(1, item.getTitle());
            pstmt.setString(2, item.getLink());
            pstmt.setString(3, item.getBloggerName());
            pstmt.setString(4, item.getPostDate());
            pstmt.setString(5, item.getDescription());
            pstmt.setString(6, item.getThumbnail());

            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //cooking 테이블의 모든 데이터 조회
    public static List<Cooking> getAllCooking() {
        List<Cooking> list = new ArrayList<>();
        
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cooking");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cooking item = new Cooking();
                item.setTitle(rs.getString("title"));
                item.setLink(rs.getString("link"));
                item.setBloggerName(rs.getString("bloggerName"));
                item.setPostDate(rs.getString("postDate"));
                item.setDescription(rs.getString("description"));
                item.setThumbnail(rs.getString("thumbnail"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //cooking 테이블 내 검색
    public static List<Cooking> searchCooking(String keyword) {
        List<Cooking> list = new ArrayList<>();
        
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM cooking WHERE title LIKE ? OR description LIKE ?")) {
            
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cooking item = new Cooking();
                item.setTitle(rs.getString("title"));
                item.setLink(rs.getString("link"));
                item.setBloggerName(rs.getString("bloggerName"));
                item.setPostDate(rs.getString("postDate"));
                item.setDescription(rs.getString("description"));
                item.setThumbnail(rs.getString("thumbnail"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}