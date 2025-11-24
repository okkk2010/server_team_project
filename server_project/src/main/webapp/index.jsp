<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Naver 카테고리 선택</title>
    <style>
        * {
            box-sizing: border-box;
        }

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
        }

        .card {
            width: 900px;
            max-width: 95vw;
            background-color: #ffffff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
            padding: 28px 32px 32px;
        }

        .card-header {
            margin-bottom: 24px;
        }

        .card-title {
            font-size: 24px;
            font-weight: 700;
            margin-bottom: 6px;
        }

        .card-subtitle {
            font-size: 13px;
            color: #6b6b6b;
        }

        .category-grid {
            display: grid;
            grid-template-columns: repeat(3, minmax(0, 1fr));
            gap: 16px;
            margin-top: 16px;
        }

        .category-card {
            border-radius: 14px;
            border: 1px solid #e2e2e2;
            padding: 20px 18px;
            background-color: #fafafa;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            transition: box-shadow 0.15s ease-in-out, transform 0.1s ease-in-out, border-color 0.15s;
        }

        .category-card:hover {
            box-shadow: 0 8px 22px rgba(0, 0, 0, 0.08);
            transform: translateY(-2px);
            border-color: #1ec800;
        }

        .category-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .category-desc {
            font-size: 13px;
            color: #777;
            margin-bottom: 16px;
            line-height: 1.5;
        }

        .category-button {
            margin-top: auto;
            align-self: flex-start;
            padding: 8px 16px;
            border-radius: 999px;
            border: none;
            background-color: #1ec800;
            color: #ffffff;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.15s ease-in-out, transform 0.05s;
        }

        .category-button:hover {
            background-color: #17b100;
        }

        .category-button:active {
            transform: translateY(1px);
        }

        /* 버튼을 a 링크처럼 쓰기 위한 스타일 */
        .category-link {
            text-decoration: none;
        }

        .hint {
            margin-top: 18px;
            font-size: 12px;
            color: #999;
        }

        @media (max-width: 768px) {
            .category-grid {
                grid-template-columns: 1fr;
            }

            .card {
                padding: 22px 18px 24px;
            }
        }
    </style>
</head>
<body>
<div class="page-wrapper">
    <div class="card">
        <div class="card-header">
            <div class="card-title">Naver API 카테고리 선택</div>
            <div class="card-subtitle">
                사용할 카테고리를 먼저 선택하면, 각 카테고리 전용 페이지에서 검색 기능을 구현할 수 있습니다.
            </div>
        </div>

        <!-- 카테고리 선택 버튼 영역 -->
        <div class="category-grid">
            <!-- 카테고리 1: 리뷰 -->
            <div class="category-card">
                <div>
                    <div class="category-title">리뷰 검색</div>
                    <div class="category-desc">
                        패션, 전자기기 등 사용 리뷰를 중심으로<br>
                        Naver 뉴스 검색 API를 사용하는 페이지로 이동합니다.
                    </div>
                </div>
                <a href="Reviews.jsp" class="category-link">
                    <button type="button" class="category-button">
                        리뷰 페이지로 이동
                    </button>
                </a>
            </div>

            <!-- 카테고리 2: 여행 -->
            <div class="category-card">
                <div>
                    <div class="category-title">여행 검색</div>
                    <div class="category-desc">
    					네이버 블로그의 생생한 여행 상세 리뷰를 중심으로<br>
    					Naver 블로그 검색 API를 사용하는 페이지로 이동합니다.
                    </div>
                </div>
                <a href="Travel.jsp" class="category-link">
                    <button type="button" class="category-button">
                        여행 페이지로 이동
                    </button>
                </a>
            </div>

            <!-- 카테고리 3: 요리 -->
            <div class="category-card">
                <div>
                    <div class="category-title">요리 검색</div>
                    <div class="category-desc">
                        맛있는 요리 레시피를 중심으로<br>
						Naver 블로그 검색 API를 사용하는 페이지로 이동합니다.
                    </div>
                </div>
                <a href="cooking.jsp" class="category-link">
                    <button type="button" class="category-button">
                        요리 페이지로 이동
                    </button>
                </a>
            </div>
        </div>

        <div class="hint">
            ※ <code>news.jsp</code>, <code>blog.jsp</code>, <code>book.jsp</code>에서 각각 검색창 + 결과 리스트를 구현하면 됩니다.
        </div>
    </div>
</div>
</body>
</html>
