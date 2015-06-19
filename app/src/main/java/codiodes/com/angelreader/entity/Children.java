package codiodes.com.angelreader.entity;

import java.util.List;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class Children {

    String kind;
    Data data;

    public Children(String kind, Data data) {
        this.kind = kind;
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public Data getData() {
        return data;
    }
}
