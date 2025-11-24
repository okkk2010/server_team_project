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

public class TravelNaverApi {
    
    // updateTravelDB 메서드는 생략
	
    /**
     * 주어진 키워드로 네이버 블로그 검색 API를 호출하고 결과를 반환하는 메서드.
     * JSP 파일 (blog.jsp)에서 이 메서드를 호출하여 데이터를 가져옵니다.
     * @return List<Travel>을 반환합니다.
     */
    public static List<Travel> searchTravel(String keyword) throws Exception { 
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
    
    // ----------------------------------------------------------------------
    // --- HTTP 통신을 위한 보조 메서드 구현 ---
    // ----------------------------------------------------------------------
    
    /**
     * API URL에 GET 요청을 보내고 응답 본문(Body)을 문자열로 반환합니다.
     * 이 메서드는 API 호출의 메인 로직입니다.
     */
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출 (200)
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            // 연결, 요청, 응답 과정에서 발생한 모든 I/O 오류 처리
            throw new RuntimeException("API 요청과 응답 실패", e); 
        } finally {
            con.disconnect(); // 연결 해제
        }
    }

    /**
     * 지정된 URL에 대한 HttpURLConnection 연결 객체를 생성합니다.
     */
    private static HttpURLConnection connect(String apiUrl){
        try {
            @SuppressWarnings("deprecation")
			URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            // URL 형식이 잘못되었을 때 발생하는 오류
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e); 
        } catch (IOException e) {
            // 연결 자체에 실패했을 때 발생하는 오류
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    /**
     * HTTP 응답 스트림(InputStream)을 읽어 전체 문자열로 변환합니다.
     * @param body 응답 본문 스트림 (정상 또는 오류 스트림)
     * @return 응답 본문 문자열
     */
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            // 스트림 리더에서 발생하는 오류
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}