package uk.co.falcona.mvpproject.main.ui.updateprofile;

public class Profile {
    String name,email,phone,pic;

    public Profile(String name, String email, String phone, String pic) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pic = pic;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
