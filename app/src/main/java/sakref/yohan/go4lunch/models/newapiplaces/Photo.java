package sakref.yohan.go4lunch.models.newapiplaces;

import com.google.gson.annotations.SerializedName;

public class Photo {
    private Long height;
    private String[] htmlAttributions;
    @SerializedName("photo_reference")
    private String photoReference;
    private Long width;

    public Long getHeight() { return height; }
    public void setHeight(Long value) { this.height = value; }

    public String[] getHtmlAttributions() { return htmlAttributions; }
    public void setHtmlAttributions(String[] value) { this.htmlAttributions = value; }

    public String getPhotoReference() { return photoReference; }
    public void setPhotoReference(String value) { this.photoReference = value; }

    public Long getWidth() { return width; }
    public void setWidth(Long value) { this.width = value; }
}
