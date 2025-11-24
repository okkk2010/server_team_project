package server_project.Management;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import server_project.DTOPackages.Travel;
// HtmlUtil 클래스는 별도로 존재해야 합니다.

public class TravelJSONDataParser {
	
	// Travel DTO를 사용하여 여행 블로그 검색 결과를 파싱합니다.
	public static List<Travel> parseTravelFromJSON(String jsonData)
	{
		List<Travel> travelsList = new ArrayList<>(); // reviewsList -> travelsList
		
		// Gson 라이브러리 대신 org.json 라이브러리를 사용한다고 가정합니다.
		JSONObject jsonObject = new JSONObject(jsonData);
		JSONArray itemsArray = jsonObject.getJSONArray("items");
		
		for (int i = 0; i < itemsArray.length(); i++) {
			JSONObject item = itemsArray.getJSONObject(i);
			
			Travel travel = new Travel(); // review -> travel
			
			String rawDesctiption = item.getString("description");
			
			// 🚨 [주의] HtmlUtil 클래스가 필요합니다.
			// 현재 코드에서는 HtmlUtil을 사용할 수 없으므로, 일단 stripHtml만 사용하고 썸네일은 null로 설정합니다.
			// travel.setThumbnail(item.optString(HtmlUtil.extractThumbnailFromDescription(rawDesctiption))); 

			travel.setTitle(stripHtml(item.getString("title"))); // review -> travel
			travel.setLink(item.getString("link")); // review -> travel
			travel.setBloggerName(item.getString("bloggername")); // review -> travel
			travel.setPostDate(item.getString("postdate")); // review -> travel
			travel.setDescription(stripHtml(rawDesctiption)); // review -> travel
			travel.setThumbnail(null); // review -> travel
			
			travelsList.add(travel); // reviewsList -> travelsList, review -> travel
		}
		
		return travelsList; // reviewsList -> travelsList
	}
	
	// HTML 태그를 제거하는 보조 메서드
	private static String stripHtml(String text)
	{
		if (text == null) {
			return null;
		}
		
		// 모든 HTML 태그를 제거합니다.
		return text.replaceAll("<.*?>", "");
	}
}