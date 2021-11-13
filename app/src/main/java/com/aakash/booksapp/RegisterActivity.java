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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aakash.booksapp.GlobalData.GlobalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    public EditText emailId, passwd, userNameEditText,birthdateEditText,mobileEditText,profileimageEditText, studentIDEditText;
    Button btnSignUp;
    FirebaseAuth firebaseAuth;
    ImageView profileImageView;
    final Calendar myCalendar = Calendar.getInstance();
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getID();
        clickListners();
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthdateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            profileImageView.setImageURI(data.getData());

            imageURL = data.getData().toString();
        }
    }
    public void getID(){
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);
        btnSignUp = findViewById(R.id.signUpButton);
        mobileEditText = findViewById(R.id.mobileNumberEditText);
        studentIDEditText = findViewById(R.id.studentIDEditText);
        //profileImageView = findViewById(R.id.profileSetImageView);

        userNameEditText = findViewById(R.id.username);
        birthdateEditText= (EditText) findViewById(R.id.birthdateEditText);
    }
    public void clickListners()
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailID = emailId.getText().toString();
                String paswd = passwd.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (emailID.isEmpty() && paswd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(
                            RegisterActivity.this, new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                                "SignUp unsuccessful: " + task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();

                                    } else {

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                        ref.child("Users").child(user.getUid()).child("name").setValue(String.valueOf(userNameEditText.getText()));
                                        ref.child("Users").child(user.getUid()).child("mobilenumber").setValue(String.valueOf(mobileEditText.getText()));

                                        ref.child("Users").child(user.getUid()).child("studentid").setValue(String.valueOf(studentIDEditText.getText()));
                                        ref.child("Users").child(user.getUid()).child("birthdate").setValue(String.valueOf(birthdateEditText.getText()));

                                        Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                                "Registration sucess",
                                                Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                                        FirebaseAuth.getInstance().signOut();
                                        Intent I = new Intent(RegisterActivity.this, MainActivity.class);
                                        finish();
                                        startActivity(I);
                                        GlobalData.showAdminOptions = false;
                                    }
                                }
                            });


                } else {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        birthdateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
}