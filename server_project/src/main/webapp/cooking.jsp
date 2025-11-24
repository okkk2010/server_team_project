<%@page import="server_project.Management.ReviewsNaverApi"%>
<%@page import="server_project.Management.ReviewsDB"%>
<%@page import="server_project.DTOPackages.Reviews"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>요리 블로그 검색</title>
    <style>
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
            background-color: #f5f5f7;
        }
        .page-wrapper {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px 0;
        }
        .card {
            width: 900px;
            max-width: 95vw;
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            padding: 24px 28px 28px;
        }
        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 12px;
            margin-bottom: 16px;
        }
        .card-title { font-size: 22px; font-weight: 700; }
        .card-subtitle { font-size: 13px; color: #6b6b6b; }
        .back-link {
            font-size: 13px;
            color: #1ec800;
            text-decoration: none;
        }
        .back-link:hover { text-decoration: underline; }

        /* 검색 영역 */
        .search-box { margin-top: 8px; }
        .search-form-inline { display: flex; gap: 8px; }
        .search-input {
            flex: 1;
            padding: 8px 10px;
            border-radius: 10px;
            border: 1px solid #d0d0d0;
            font-size: 13px;
        }
        .search-input:focus {
            outline: none;
            border-color: #1ec800;
            box-shadow: 0 0 0 2px rgba(30, 200, 0, 0.15);
        }
        .search-button {
            padding: 0 16px;
            border-radius: 10px;
            border: none;
            background-color: #1ec800;
            color: #fff;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
        }
        .search-button:hover { background-color: #17b100; }

        /* 리뷰 리스트 */
        .review-list {
            display: flex;
            flex-direction: column;
            gap: 14px;
            margin-top: 12px;
        }
        .review-card {
            display: flex;
            gap: 14px;
            padding: 12px 14px;
            border-radius: 12px;
            border: 1px solid #e2e2e2;
            background-color: #fafafa;
            transition: box-shadow 0.15s, transform 0.1s, border-color 0.15s;
        }
        .review-card:hover {
            box-shadow: 0 8px 22px rgba(0, 0, 0, 0.08);
            transform: translateY(-1px);
            border-color: #1ec800;
        }
        .thumb-wrapper {
            width: 130px;
            min-width: 130px;
            height: 90px;
            border-radius: 10px;
            overflow: hidden;
            background-color: #e5e5e5;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .thumb-img { width: 100%; height: 100%; object-fit: cover; }
        .thumb-placeholder { font-size: 11px; color: #888; }
        .content-wrapper {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 6px;
        }
        .review-title {
            font-size: 15px;
            font-weight: 600;
            color: #222;
            text-decoration: none;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        .review-title:hover { text-decoration: underline; }
        .review-meta {
            display: flex;
            flex-wrap: wrap;
            gap: 6px;
            align-items: center;
            font-size: 12px;
            color: #777;
        }
        .badge {
            display: inline-block;
            padding: 2px 8px;
            border-radius: 999px;
            background-color: #1ec800;
            color: #fff;
            font-size: 11px;
            font-weight: 600;
        }
        .blogger { font-weight: 500; }
        .date { color: #aaa; }
        .review-desc {
            font-size: 13px;
            color: #555;
            line-height: 1.5;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        .review-actions {
            margin-top: 4px;
            display: flex;
            gap: 8px;
        }
        .btn-primary {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 999px;
            border: none;
            background-color: #1ec800;
            color: #ffffff;
            font-size: 12px;
            font-weight: 600;
            text-decoration: none;
            cursor: pointer;
        }
        .btn-primary:hover { background-color: #17b100; }
        .empty-message {
            margin-top: 20px;
            font-size: 13px;
            color: #777;
            text-align: center;
        }
        .pagination {
            margin-top: 18px;
            display: flex;
            justify-content: center;
            gap: 6px;
            flex-wrap: wrap;
        }
        .page-tab {
            min-width: 26px;
            padding: 4px 8px;
            border-radius: 999px;
            border: 1px solid #ddd;
            background-color: #fff;
            font-size: 12px;
            text-decoration: none;
            color: #444;
            text-align: center;
        }
        .page-tab.active {
            background-color: #1ec800;
            border-color: #1ec800;
            color: #fff;
            font-weight: 600;
        }
        @media (max-width: 640px) {
            .card { padding: 20px 18px 22px; }
            .card-header { flex-direction: column; }
            .review-card { flex-direction: column; }
            .thumb-wrapper { width: 100%; min-width: 0; height: 160px; }
        }
    </style>
</head>
<body>
<div class="page-wrapper">
    <div class="card">

        <%
            // 파라미터 읽기
            String query = request.getParameter("query");
            String pageParam = request.getParameter("page");

            int pageSize = 10;       // 한 화면 10개
            int pages = 1;           // 기본 1페이지

            if (pageParam != null) {
                try {
                    pages = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignore) { pages = 1; }
            }

           
            
            // 1. 요리 데이터 업데이트 (네이버 API -> cook 테이블)
            ReviewsNaverApi.updateCookDB(); 

            List<Reviews> blogList = null;
            if (query == null) {
                // 2. cook 테이블 전체 조회
                blogList = ReviewsDB.getAllCook();
            } else {
                // 3. cook 테이블 검색
                blogList = ReviewsDB.searchCook(query);
            }
            

            int totalCount = (blogList != null) ? blogList.size() : 0;

            // 최대 100개까지만 사용
            if (totalCount > 100) { totalCount = 100; }

            int totalPages = (totalCount == 0) ? 1 : (int) Math.ceil(totalCount / (double) pageSize);
            if (pages < 1) pages = 1;
            if (pages > totalPages) pages = totalPages;

            int startIndex = (pages - 1) * pageSize;
            int endIndex = startIndex + pageSize;
            if (endIndex > totalCount) endIndex = totalCount;

            String encodedQuery = "";
            if (query != null) {
                try {
                    encodedQuery = URLEncoder.encode(query, "UTF-8");
                } catch (Exception e) { encodedQuery = query; }
            }
        %>

        <div class="card-header">
            <div>
                <div class="card-title">요리 블로그 검색</div>
                <div class="card-subtitle">
                    네이버 블로그에서 '요리' 관련 최신 글을 가져와 보여줍니다.
                </div>

                <div class="search-box">
                    <form method="get" action="blog.jsp" class="search-form-inline">
                        <input type="text"
                               name="query"
                               class="search-input"
                               placeholder="요리 이름이나 재료를 검색하세요 (예: 김치찌개)"
                               value="<%= (query != null ? query : "") %>">
                        <button type="submit" class="search-button">검색</button>
                    </form>
                </div>
            </div>
            <a href="index.jsp" class="back-link">&larr; 카테고리 선택으로 돌아가기</a>
        </div>

        <div class="review-list">
            <%
                if (totalCount > 0 && blogList != null) {
                    for (int i = startIndex; i < endIndex; i++) {
                        Reviews item = blogList.get(i);
                        String title = item.getTitle();
                        String link = item.getLink();
                        String bloggerName = item.getBloggerName();
                        String postDate = item.getPostDate();
                        String description = item.getDescription();
                        String thumbnail = item.getThumbnail();
            %>
            <div class="review-card">
                <div class="thumb-wrapper">
                    <% if (thumbnail != null && !thumbnail.trim().equals("")) { %>
                        <img src="<%= thumbnail %>" alt="thumbnail" class="thumb-img">
                    <% } else { %>
                        <div class="thumb-placeholder">No Image</div>
                    <% } %>
                </div>

                <div class="content-wrapper">
                    <a href="<%= link %>" target="_blank" class="review-title">
                        <%= title %>
                    </a>
                    <div class="review-meta">
                        <span class="badge">요리</span>
                        <span class="blogger"><%= bloggerName %></span>
                        <span class="date"><%= postDate %></span>
                    </div>
                    <div class="review-desc">
                        <%= description %>
                    </div>
                    <div class="review-actions">
                        <a href="<%= link %>" target="_blank" class="btn-primary">
                            원문 보기
                        </a>
                    </div>
                </div>
            </div>
            <%
                    } // for
                } else {
            %>
            <div class="empty-message">
                표시할 블로그 글이 없습니다.<br>
                잠시 후 다시 시도하거나, 검색어를 확인해 주세요.
            </div>
            <%
                }
            %>
        </div>

        <div class="pagination">
            <%
                if (totalPages > 1) {
                    for (int p = 1; p <= totalPages; p++) {
                        boolean isActive = (p == pages);
            %>
            <a href="blog.jsp?query=<%= encodedQuery %>&page=<%= p %>"
               class="page-tab <%= (isActive ? "active" : "") %>">
                <%= p %>
            </a>
            <%
                    }
                }
            %>
        </div>

    </div>
</div>
</body>
</html>