package server_project.Management;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlUtil {
	public static String extractThumbnailFromDescription(String description) {
		if (description == null || description.isEmpty()) {
			return null;
		}
		
		Document doc = (Document) Jsoup.parse(description);
		
		Element img = doc.selectFirst("img");
		if (img == null) {
			return null;
		}
		
		String src = img.attr("src");
		return (src == null || src.isEmpty()) ? null : src;
	}
}
