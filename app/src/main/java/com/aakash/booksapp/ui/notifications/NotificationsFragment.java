package com.aakash.booksapp.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aakash.booksapp.AboutPage;
import com.aakash.booksapp.AddCourses;
import com.aakash.booksapp.EditProfileActivity;
import com.aakash.booksapp.GlobalData.GlobalData;
import com.aakash.booksapp.LoginActivity;
import com.aakash.booksapp.R;
import com.aakash.booksapp.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {
    Button logoutButton, registerUserButton, editProfileButton, addCoursesButton, aboutAppButton;
    FirebaseAuth firebaseAuth;
    TextView nameTextView, emailTextView, toolBarTitle, studentIDTextView, mobileTextView, birthdateTextView;
    private FirebaseAuth.AuthStateListener authStateListener;
    ImageView profileImageView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = view.findViewById(R.id.toolbarText);

        aboutAppButton = view.findViewById(R.id.aboutButton);
        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        emailTextView = view.findViewById(R.id.emailTextView);
        studentIDTextView = view.findViewById(R.id.studentIDTextView);
        mobileTextView = view.findViewById(R.id.mobileTextView);
        birthdateTextView = view.findViewById(R.id.birthdateTextView);
        registerUserButton = view.findViewById(R.id.themechangeButton);
        editProfileButton = view.findViewById(R.id.editProfileButton);

        addCoursesButton = view.findViewById(R.id.addCourseButton);


        if (!GlobalData.showAdminOptions) {
            toolBarTitle.setText("Profile");
            registerUserButton.setVisibility(View.GONE);
            addCoursesButton.setVisibility(View.GONE);
        } else {
            toolBarTitle.setText("Admin Profile");
            registerUserButton.setVisibility(View.VISIBLE);
            addCoursesButton.setVisibility(View.VISIBLE);
        }

        registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(getContext(), RegisterActivity.class);
                startActivity(I);
            }
        });


        aboutAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutPage.class));
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(getContext(), LoginActivity.class);
                startActivity(I);
                GlobalData.showAdminOptions = false;

            }
        });



        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteditprofile = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intenteditprofile);
            }
        });

        addCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenteditprofile = new Intent(getContext(), AddCourses.class);
                startActivity(intenteditprofile);
            }
        });

        emailTextView.setText(user.getEmail());
        nameTextView = view.findViewById(R.id.nameTextView);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).getValue() != null) {
                    try {
                        nameTextView.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                        studentIDTextView.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                        mobileTextView.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                        birthdateTextView.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());

                        if (snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString().contentEquals("image")) {
                            profileImageView.setVisibility(View.VISIBLE);
                        } else {
                            //Glide.with(getContext()).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);
                        }
                    } catch (Exception e) {
                        Log.e("error", "error in profile page");
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        emailTextView.setText(user.getEmail());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("name").getValue() != null) {
                    try {
                        nameTextView.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                        studentIDTextView.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                        mobileTextView.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                        birthdateTextView.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                        if (snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString().contentEquals("image")) {
                            profileImageView.setVisibility(View.VISIBLE);
                        } else {
                            //Glide.with(ProfileActivity.this).load(snapshot.child("Users").child(user.getUid()).child("profileimage").getValue().toString()).into(profileImageView);
                        }
                    } catch (Exception e) {
                        Log.e("error", "error in profile page");
                    }

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}