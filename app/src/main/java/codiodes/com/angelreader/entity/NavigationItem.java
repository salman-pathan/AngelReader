package codiodes.com.angelreader.entity;

/**
 * Created by Salman Khan on 11/7/15.
 */
public class NavigationItem {
    int ImageId;
    String Title;
    String SubTitle;

    public NavigationItem(int imageId, String title, String subTitle) {
        ImageId = imageId;
        Title = title;
        SubTitle = subTitle;
    }

    public int getImageId() {
        return ImageId;
    }

    public String getTitle() {
        return Title;
    }

    public String getSubTitle() {
        return SubTitle;
    }
}
