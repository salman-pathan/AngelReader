package codiodes.com.angelreader.entity;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class Data {

    String id;
    String author;
    String url;
    String title;
    int ups;
    int num_comments;

    public Data(String id, String author, String url, String title, int ups, int num_comments) {
        this.id = id;
        this.author = author;
        this.url = url;
        this.title = title;
        this.ups = ups;
        this.num_comments = num_comments;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getUps() {
        return ups;
    }

    public int getNum_comments() {
        return num_comments;
    }
}
