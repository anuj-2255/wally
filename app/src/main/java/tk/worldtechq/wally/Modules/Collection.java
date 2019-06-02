package tk.worldtechq.wally.Modules;

import com.google.gson.annotations.SerializedName;

public class Collection {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("total_photos")
    private int total_photos;
    @SerializedName("cover_photo")
    private Photo cover_photo = new Photo();
    @SerializedName("user")
    private User user = new User();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public Photo getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(Photo cover_photo) {
        this.cover_photo = cover_photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
