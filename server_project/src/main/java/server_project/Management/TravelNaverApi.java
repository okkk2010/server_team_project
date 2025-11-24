package server_project.Management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server_project.DTOPackages.Travel; 
// 🚨 [가정] TravelJSONDataParser와 EnvConfig 클래스는 존재하고 임포트됩니다.
// import server_project.Management.TravelJSONDataParser;
// import server_project.Management.EnvConfig;
// import server_project.Management.TravelDB;

public class TravelNaverApi {
    
    // updateTravelDB 메서드는 생략 (구조가 복잡해지므로)
	
    // updateTravelDB()와 보조 메서드들은 이전에 작성한 코드를 그대로 사용한다고 가정합니다.
    
    /**
     * 주어진 키워드로 네이버 블로그 검색 API를 호출하고 결과를 반환하는 메서드.
     * JSP 파일 (blog.jsp)에서 이 메서드를 호출하여 데이터를 가져옵니다.
     * @return List<Travel>을 반환합니다.
     */
    public static List<Travel> searchTravel(String keyword) throws Exception { // ✅ [수정됨] throws Exception 추가
        String clientId = EnvConfig.getProperty("NAVER_CLIENT_ID");
        String clientSecret = EnvConfig.getProperty("NAVER_CLIENT_SECRET");
        
        String encodedKeyword = null;
        try {
        	encodedKeyword = URLEncoder.encode(keyword, "UTF-8");
        	} catch (UnsupportedEncodingException e) {
        		throw new RuntimeException("검색어 인코딩 실패",e);
        		}

        String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + encodedKeyword + "&display=100";
        
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        
        String responseBody = get(apiURL, requestHeaders);
        
        // TravelJSONDataParser의 메서드를 호출하여 JSON을 Travel DTO 리스트로 변환합니다.
        return TravelJSONDataParser.parseTravelFromJSON(responseBody);
    }
    
    // --- (이하 get, connect, readBody 보조 메서드들은 이전에 작성한 코드가 이 파일 내에 존재한다고 가정합니다) ---
    
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        // ... (이전에 제공된 get 메서드 내용) ...
        return null; // 임시 반환
    }
    private static HttpURLConnection connect(String apiUrl){
        return null; // 임시 반환
    }
    private static String readBody(InputStream body){
        return null; // 임시 반환
    }
}