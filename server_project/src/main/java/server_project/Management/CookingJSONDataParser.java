package server_project.Management;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import server_project.DTOPackages.Cooking; 

public class CookingJSONDataParser {
    
	// JSON 데이터를 받아 요리 목록으로 변환
    public static List<Cooking> parseCookingFromJSON(String jsonData) {
        List<Cooking> cookingList = new ArrayList<>(); 
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject item = itemsArray.getJSONObject(i);
            
            Cooking cookPost = new Cooking(); 
            
            String rawDescription = item.getString("description");
            
            cookPost.setTitle(stripHtml(item.getString("title")));
            cookPost.setLink(item.getString("link"));
            cookPost.setBloggerName(item.getString("bloggername"));
            cookPost.setPostDate(item.getString("postdate"));
            cookPost.setDescription(stripHtml(rawDescription));
            
            cookPost.setThumbnail(item.optString(
                    HtmlUtil.extractThumbnailFromDescription(rawDescription))
            );
            
            cookingList.add(cookPost);
        }
        return cookingList;
    }
    
    private static String stripHtml(String text) { 
        if (text == null) return null;
        return text.replaceAll("<.*?>", "");
    }
}