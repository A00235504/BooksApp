package com.aakash.booksapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddCourses extends AppCompatActivity {
TextView toolBarTitle;
EditText courseNameEditText, courseDescriptionEditText, courseOpportunityEditText,courseImageLinkEditText, bookauthorEditText,bookcategoryEditText, bookpagesEditText,bookpublisherEditText,bookpublishingdateEditText;
    Button courseAddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        //calling a function for all ids
        getIDs();

        //firebase database reference
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Books");

        toolBarTitle.setText("Add Books");

        courseAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.orderByChild("Courses").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //Toast.makeText(getApplicationContext(), String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                        int value = Math.toIntExact(dataSnapshot.getChildrenCount() + 1);
                        String mKey = UUID.randomUUID().toString();
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookname").setValue(String.valueOf(courseNameEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookdescription").setValue(String.valueOf(courseDescriptionEditText.getText()));
                        if(courseImageLinkEditText.getText().length() > 1){
                            ref.child(String.valueOf(courseNameEditText.getText())).child("image").setValue(String.valueOf(courseImageLinkEditText.getText()));

                        }else{
                            ref.child(String.valueOf(courseNameEditText.getText())).child("image").setValue("https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Closed_Book_Icon.svg/2048px-Closed_Book_Icon.svg.png");
                        }
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookprice").setValue(String.valueOf(courseOpportunityEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookauthor").setValue(String.valueOf(bookauthorEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookpages").setValue(String.valueOf(bookpagesEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookpublisher").setValue(String.valueOf(bookpublisherEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookpublishingdate").setValue(String.valueOf(bookpublishingdateEditText.getText()));
                        ref.child(String.valueOf(courseNameEditText.getText())).child("bookcategory").setValue(String.valueOf(bookcategoryEditText.getText()));

                        Toast.makeText(getApplicationContext(), "Book added success!", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

//all ids defined here
    public void getIDs(){
        toolBarTitle = findViewById(R.id.toolbarText);
        courseAddButton = findViewById(R.id.courseAddButton);
        courseNameEditText = findViewById(R.id.courseNameEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        courseOpportunityEditText = findViewById(R.id.courseOpportunityEditText);
        courseImageLinkEditText = findViewById(R.id.courseImageLinkEditText);

        bookauthorEditText =findViewById(R.id.bookauthorEditText);
        bookcategoryEditText = findViewById(R.id.bookcategoryEditText);
        bookpagesEditText = findViewById(R.id.bookpagesEditText);
        bookpublisherEditText = findViewById(R.id.bookpublisherEditText);
        bookpublishingdateEditText = findViewById(R.id.bookpublishingdateEditText);
    }

}