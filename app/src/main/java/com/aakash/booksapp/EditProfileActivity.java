package com.aakash.booksapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    TextView toolBarTitle;
    EditText nameEditText, emailEditText, studentIdEditText, mobileEditText, birthdateEditText;
    Button submitButton;

    final Calendar myCalendar = Calendar.getInstance();

    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        getID();

        toolBarTitle.setText("Edit Profile");



        if(user.getEmail() != null){
            try{
                emailEditText.setText(user.getEmail());
            }
            catch (Exception e){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            emailEditText.setText("Loading..");
        }

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(user.getUid()).child("name").getValue() != null) {
                    nameEditText.setText(snapshot.child("Users").child(user.getUid()).child("name").getValue().toString());
                    studentIdEditText.setText(snapshot.child("Users").child(user.getUid()).child("studentid").getValue().toString());
                    mobileEditText.setText(snapshot.child("Users").child(user.getUid()).child("mobilenumber").getValue().toString());
                    birthdateEditText.setText(snapshot.child("Users").child(user.getUid()).child("birthdate").getValue().toString());
                }
                else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                ref.child("Users").child(user.getUid()).child("name").setValue(String.valueOf(nameEditText.getText()));
                ref.child("Users").child(user.getUid()).child("studentid").setValue(String.valueOf(studentIdEditText.getText()));
                ref.child("Users").child(user.getUid()).child("mobilenumber").setValue(String.valueOf(mobileEditText.getText()));
                ref.child("Users").child(user.getUid()).child("birthdate").setValue(String.valueOf(birthdateEditText.getText()));


            }
        });


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birthdateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    public void getID(){
        toolBarTitle = findViewById(R.id.toolbarText);
        nameEditText = findViewById(R.id.nameEditTextView);
        emailEditText = findViewById(R.id.emailEditTextView);
        studentIdEditText = findViewById(R.id.studentidEditTextView);
        mobileEditText = findViewById(R.id.mobileEditTextView);
        birthdateEditText = findViewById(R.id.birthdateEditText);

        submitButton = findViewById(R.id.saveProfileButton);
        birthdateEditText= (EditText) findViewById(R.id.birthdateEditText);
    }
}