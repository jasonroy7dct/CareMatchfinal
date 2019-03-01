package com.example.user.carematch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View feedBackView= inflater.inflate(R.layout.fragment_test,container,false);


        final TextView test =(EditText) feedBackView.findViewById(R.id.select_date);

        return feedBackView;
    }

}
