package com.example.user.carematch;

public class MyBook extends MyBookId {

    private String MyBook_name;
    private String Username;

    private String MyBook_image;
    private String MyBook_time;
    private String MyBook_care;
    private String MyBook_details;



    public MyBook() {
    }

        public MyBook(String Username,String myBook_name, String myBook_image, String myBook_time, String myBook_care, String myBook_details){
            MyBook_name=myBook_name;
            Username = Username;
            MyBook_image=myBook_image;
            MyBook_time=myBook_time;
            MyBook_care=myBook_care;
            MyBook_details=myBook_details;
        }

        public String getMyBook_name() {
            return MyBook_name;
        }

        public void setMyBook_name(String myBook_name) {
            MyBook_name=myBook_name;
        }

    public String getUserName() {
        return Username;
    }

    public void setUserName(String Username) {
        this.Username = Username;
    }

        public String getMyBook_image() {
            return MyBook_image;
        }

        public void setMyBook_image(String myBook_image) {
            MyBook_image=myBook_image;
        }
        public String getMyBook_time() {
            return MyBook_time;
        }

        public void setMyBook_time(String myBook_time) {
        MyBook_time=myBook_time;
    }

        public String getMyBook_care() {
        return MyBook_care;
    }

        public void setMyBook_care(String myBook_care) {
        MyBook_care=myBook_care;
    }

        public String getMyBook_details() {
        return MyBook_details;
    }

        public void setMyBook_details(String myBook_details) {
        MyBook_details=myBook_details;
    }




    }



