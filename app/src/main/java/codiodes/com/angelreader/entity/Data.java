package codiodes.com.angelreader.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class Data implements Parcelable {

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

    protected Data(Parcel in) {
        id = in.readString();
        author = in.readString();
        url = in.readString();
        title = in.readString();
        ups = in.readInt();
        num_comments = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(title);
        dest.writeInt(ups);
        dest.writeInt(num_comments);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
