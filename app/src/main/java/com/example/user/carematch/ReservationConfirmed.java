package com.example.user.carematch;

import android.os.Parcelable;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ReservationConfirmed extends ReservationID {


    String user_name;

    String reservation_name;
    String reservation_date;
    String reservation_content;
    String reservation_time;
    String reservationConfirmed_id;

    String trans_id;
    String reservation_photoUrl;

    Date reservation_uploaddate;

//    String class_name;
//    String class_id;
//    String student_id;
//    String teacher_email;
//    String leave_photoUrl;

//    Timestamp Reservation_date;
//    Timestamp Reservation_Stime;
//    Timestamp Reservation_Etime;


    //firebase日期
    public static final Parcelable.Creator<Timestamp> CREATOR = null;


    Date leave_uploaddate;


    public ReservationConfirmed(String user_name, String reservation_name, String reservation_date, String reservation_content,
                                String reservation_time, String trans_id , String reservationConfirmed_id) {
        this.user_name = user_name;
        this.reservation_name = reservation_name;
        this.reservation_date = reservation_date;
        this.reservation_content = reservation_content;
        this.reservation_time = reservation_time;
        this.trans_id = trans_id;
        this.reservationConfirmed_id = reservationConfirmed_id;
        }





    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getReservation_name() {
        return reservation_name;
    }

    public void setReservation_name(String reservation_name) {
        this.reservation_name = reservation_name;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public String getReservation_content() {
        return reservation_content;
    }

    public void setReservation_content(String reservation_content) {
        this.reservation_content = reservation_content;
    }

    public String getReservation_time() {
        return reservation_time;
    }

    public void setReservation_time(String reservation_time) {
        this.reservation_time = reservation_time;
    }

    public String getReservation_photoUrl() {
        return reservation_photoUrl;
    }

    public void setReservation_photoUrl(String reservation_photoUrl) {
        this.reservation_photoUrl = reservation_photoUrl;
    }



    //Date
    public Date getReservation_uploaddate() {
        return reservation_uploaddate;
    }

    public void setReservation_uploaddate(Date reservation_date) {
        this.reservation_uploaddate = reservation_date;
    }

}
