package codiodes.com.angelreader.entity;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class Feed {

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
}
