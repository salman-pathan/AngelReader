package codiodes.com.angelreader.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class RootData implements Parcelable {

    String modhash;
    List<Children> children;
    String after;
    String before;

    public RootData(String modhash, List<Children> children, String after, String before) {
        this.modhash = modhash;
        this.children = children;
        this.after = after;
        this.before = before;
    }

    public String getModhash() {
        return modhash;
    }

    public List<Children> getChildren() {
        return children;
    }

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    protected RootData(Parcel in) {
        modhash = in.readString();
        if (in.readByte() == 0x01) {
            children = new ArrayList<Children>();
            in.readList(children, Children.class.getClassLoader());
        } else {
            children = null;
        }
        after = in.readString();
        before = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modhash);
        if (children == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(children);
        }
        dest.writeString(after);
        dest.writeString(before);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RootData> CREATOR = new Parcelable.Creator<RootData>() {
        @Override
        public RootData createFromParcel(Parcel in) {
            return new RootData(in);
        }

        @Override
        public RootData[] newArray(int size) {
            return new RootData[size];
        }
    };
}
