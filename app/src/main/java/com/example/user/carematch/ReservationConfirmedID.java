package com.example.user.carematch;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class ReservationConfirmedID {
    @Exclude
    public
    String reservationconfirmedId;

    public<T extends ReservationConfirmedID> T withId(@NonNull final String id) {
        this.reservationconfirmedId = id;
        return (T) this;
    }
}
