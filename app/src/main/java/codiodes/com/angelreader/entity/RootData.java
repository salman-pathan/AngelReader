package codiodes.com.angelreader.entity;

import java.util.List;

/**
 * Created by Salman Khan on 16/6/15.
 */
public class RootData {

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
}
