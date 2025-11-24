package server_project.Management;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

// DB는 기존 프로젝트의 상위 클래스라고 가정합니다.
// import server_project.Management.DB; 
// 🚨 [수정됨] DTO 클래스를 Travel로 임포트합니다.
import server_project.DTOPackages.Travel;

// 기존 ReviewsDB 코드를 참고하여 TravelDB로 구현합니다.
public class TravelDB extends DB{ // 🚨 [수정됨] 클래스 이름을 TravelDB로 유지합니다.
	
	// 테이블 이름을 'travel_reviews'로 가정합니다.
	private static final String TABLE_NAME = "Travel"; 

	// 🚨 [수정됨] DTO 타입과 변수 이름이 Travel로 통일됩니다.
	public static void insertTravelsDB(List<Travel> travelsList) // insertReviewsDB -> insertTravelsDB
	{
		for (Travel travel : travelsList) { // review -> travel, reviewsList -> travelsList
			insertTravelDB(travel); // insertReviewDB -> insertTravelDB
		}
	}
	
	public static void insertTravelDB(Travel travel) // insertReviewDB -> insertTravelDB, review -> travel
	{
		String sql = "INSERT INTO " + TABLE_NAME + " (title, link, bloggerName, postDate, description, thumbnail) VALUES (?, ?, ?, ?, ?, ?)";
		try(Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement(sql)
				) {

			// 🚨 [수정됨] travel 객체의 getter를 사용합니다.
			pstmt.setString(1, travel.getTitle()); 
			pstmt.setString(2, travel.getLink());
			pstmt.setString(3, travel.getBloggerName());
			pstmt.setString(4, travel.getPostDate());
			pstmt.setString(5, travel.getDescription());
			pstmt.setString(6, travel.getThumbnail());
			
			int result = pstmt.executeUpdate();
			
			System.out.println("Travel Insert Result: " + result);
			
		} catch (SQLException e) {
			//e.printStackTrace(); 
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Travel> getAllTravels() // getAllReviews -> getAllTravels, List<Reviews> -> List<Travel>
	{
		List<Travel> travelsList = new ArrayList<>(); // reviewsList -> travelsList
		
		String sql = "SELECT * FROM " + TABLE_NAME;
		try (Connection conn = connectDB();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
				) {

			while (rs.next()) {
				Travel travel = new Travel(); // Reviews -> Travel, review -> travel
				travel.setTitle(rs.getString("title"));
				travel.setLink(rs.getString("link"));
				travel.setBloggerName(rs.getString("bloggerName"));
				travel.setPostDate(rs.getString("postDate"));
				travel.setDescription(rs.getString("description"));
				travel.setThumbnail(rs.getString("thumbnail"));
				travelsList.add(travel); // reviewsList -> travelsList, review -> travel
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return travelsList; // reviewsList -> travelsList
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Travel> searchTravelsByKeyword(String keyword) { // searchReviewsByKeyword -> searchTravelsByKeyword
		List<Travel> travelsList = new ArrayList<>(); // reviewsList -> travelsList

		String sql = "SELECT * FROM " + TABLE_NAME + " where title LIKE ? OR description LIKE ?";
		try (Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement(sql);
					) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Travel travel = new Travel(); // Reviews -> Travel, review -> travel
				travel.setTitle(rs.getString("title"));
				travel.setLink(rs.getString("link"));
				travel.setBloggerName(rs.getString("bloggerName"));
				travel.setPostDate(rs.getString("postDate"));
				travel.setDescription(rs.getString("description"));
				travel.setThumbnail(rs.getString("thumbnail"));
				travelsList.add(travel); // reviewsList -> travelsList, review -> travel
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return travelsList; // reviewsList -> travelsList
		}
	}
}