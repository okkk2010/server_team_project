package server_project.DTOPackages;

public class Cooking {
    private String title;
    private String link;
    private String bloggerName;
    private String postDate;
    private String description;
    private String thumbnail;

    // getter / setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getBloggerName() { return bloggerName; }
    public void setBloggerName(String bloggerName) { this.bloggerName = bloggerName; }

    public String getPostDate() { return postDate; }
    public void setPostDate(String postDate) { this.postDate = postDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
}