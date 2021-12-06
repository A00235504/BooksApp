package com.aakash.booksapp.ViewPagerFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.aakash.booksapp.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstPage extends Fragment {
TextView getdescription;
ImageView imageViewBookImage;

    public FirstPage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);

        getdescription = v.findViewById(R.id.getdescription);
        imageViewBookImage = v.findViewById(R.id.imageViewBook);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


        try {
            String myValue = getArguments().getString("Description");
            String title = getArguments().getString("Title");
            String imagelink = getArguments().getString("Imagelink");
            getdescription.setText(myValue);
            Glide.with(getContext()).load(imagelink).into(imageViewBookImage);
        }
        catch (Exception e){
            Log.e("Error","error!");
        }



        return v;
    }
}