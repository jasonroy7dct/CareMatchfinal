package com.example.user.carematch;

public class DiscussItem {


    int background;
    String profileName;
    int profilePhoto;
    int likesNumber;


    public DiscussItem(){
    }


    public DiscussItem(int background, String profileName, int profilePhoto, int likesNumber) {
        this.background = background;
        this.profileName = profileName;
        this.profilePhoto = profilePhoto;
        this.likesNumber = likesNumber;
    }

    public int getBackground() {
        return background;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }
}
