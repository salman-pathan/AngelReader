package codiodes.com.angelreader.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class Feed implements Parcelable {

    String kind;
    RootData data;

    public Feed(String kind, RootData rootData) {
        this.kind = kind;
        this.data = rootData;
    }

    public RootData getData() {
        return data;
    }

    public String getKind() {
        return kind;
    }

    protected Feed(Parcel in) {
        kind = in.readString();
        data = (RootData) in.readValue(RootData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeValue(data);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
