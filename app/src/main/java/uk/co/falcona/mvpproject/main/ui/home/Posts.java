package uk.co.falcona.mvpproject.main.ui.home;

public class Posts {
    String id;
    String name;
    String image;
    String profile;

    public Posts(String id, String name, String image, String profile, String title) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.profile = profile;
        this.title = title;
    }

    public Posts() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    @Override
    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", profile='" + profile + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
