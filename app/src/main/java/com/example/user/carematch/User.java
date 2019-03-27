package com.example.user.carematch;




public class User {

    private String username;
    private String oldname;
    private String email;
    private String phone;
    private String address;

    private String deviceToken;
    private String membership;
    private String image;
    private String thumb_image;

    public User(String username, String oldname , String email, String phone , String address ,String deviceToken , String membership, String image, String thumb_image) {
        this.username = username;
        this.oldname = oldname;
        this.email = email;
        this.phone = phone;
        this.address = address;

        this.deviceToken = deviceToken;
        this.membership = membership;
        this.image = image;
        this.thumb_image = thumb_image;
    }



    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public User() {
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


}
