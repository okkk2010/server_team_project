package server_project.Management;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

import server_project.Management.EnvConfig;
import server_project.DTOPackages.Reviews;

public class ReviewsDB extends DB{
	

	
	public static void insertReviewsDB(List<Reviews> reviewsList) 
	{
		for (Reviews review : reviewsList) {
			insertReviewDB(review);
		}
	}
	
	public static void insertReviewDB(Reviews review) 
	{
		try(Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reviews (title, link, bloggerName, postDate, description, thumbnail) VALUES (?, ?, ?, ?, ?, ?)")
				) {

			pstmt.setString(1, review.getTitle());
			pstmt.setString(2, review.getLink());
			pstmt.setString(3, review.getBloggerName());
			pstmt.setString(4, review.getPostDate());
			pstmt.setString(5, review.getDescription());
			pstmt.setString(6, review.getThumbnail());
			
			int result = pstmt.executeUpdate();
			
			System.out.println("Insert Result: " + result);
			
			
		} catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Reviews> getAllReviews() 
	{
		List<Reviews> reviewsList = new ArrayList<>();
		
		try (Connection conn = connectDB();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reviews");
			ResultSet rs = pstmt.executeQuery();
				) {

			while (rs.next()) {
				Reviews review = new Reviews();
				review.setTitle(rs.getString("title"));
				review.setLink(rs.getString("link"));
				review.setBloggerName(rs.getString("bloggerName"));
				review.setPostDate(rs.getString("postDate"));
				review.setDescription(rs.getString("description"));
				review.setThumbnail(rs.getString("thumbnail"));
				reviewsList.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return reviewsList;
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Reviews> searchReviewsByKeyword(String keyword) {
		List<Reviews> reviewsList = new ArrayList<>();

		try (Connection conn = connectDB();
				PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reviews where title LIKE ? OR description LIKE ?");
					) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Reviews review = new Reviews();
				review.setTitle(rs.getString("title"));
				review.setLink(rs.getString("link"));
				review.setBloggerName(rs.getString("bloggerName"));
				review.setPostDate(rs.getString("postDate"));
				review.setDescription(rs.getString("description"));
				review.setThumbnail(rs.getString("thumbnail"));
				reviewsList.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return reviewsList;
		}
	}
}