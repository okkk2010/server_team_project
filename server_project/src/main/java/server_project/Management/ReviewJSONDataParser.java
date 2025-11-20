package server_project.Management;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import server_project.DTOPackages.Reviews;

public class ReviewJSONDataParser {
	public static List<Reviews> parseReviewsFromJSON(String jsonData)
	{
		List<Reviews> reviewsList = new ArrayList<>();
		
		JSONObject jsonObject = new JSONObject(jsonData);
		JSONArray itemsArray = jsonObject.getJSONArray("items");
		
		for (int i = 0; i < itemsArray.length(); i++) {
			JSONObject item = itemsArray.getJSONObject(i);
			
			Reviews review = new Reviews();
			
			String rawDesctiption = item.getString("description");
			review.setTitle(stripHtml(item.getString("title")));
			review.setLink(item.getString("link"));
			review.setBloggerName(item.getString("bloggername"));
			review.setPostDate(item.getString("postdate"));
			review.setDescription(stripHtml(rawDesctiption));
			review.setThumbnail(item.optString(
					HtmlUtil.extractThumbnailFromDescription(rawDesctiption))
					);
			reviewsList.add(review);
		}
		
		return reviewsList;
	}
	
	private static String stripHtml(String text) 
	{ 
		if (text == null) {
			return null;
		}
		
		return text.replaceAll("<.*?>", "");
	}
}
