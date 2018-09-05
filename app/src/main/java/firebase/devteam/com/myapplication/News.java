package firebase.devteam.com.myapplication;

/**
 * Created by xrezut on 03/03/2018.
 */

public class News {

    private String title;
    private String desc;
    private String image;
    private String url;
    String publishDate;



    public News(String title, String desc, String image, String url, String publishDate) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.url = url;
        this.publishDate = publishDate;

    }

    public News(){

    }
    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
