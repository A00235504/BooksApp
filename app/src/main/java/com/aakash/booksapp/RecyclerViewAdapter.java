package com.aakash.booksapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.booksapp.Model.BookStore;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<BookStore, RecyclerViewAdapter.personsViewholder> {
    List<String> myListData;

    public RecyclerViewAdapter(
            @NonNull FirebaseRecyclerOptions<BookStore> options)
    {
        super(options);
    }

    @Override
    protected void
    onBindViewHolder(@NonNull personsViewholder holder,
                     int position, @NonNull BookStore model)
    {

        myListData = new ArrayList<>();
        holder.firstname.setText(model.getbookname());
        Glide.with(holder.itemView.getContext()).load(model.getImage()).into(holder.imageurl);


        holder.imageurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();

                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null) {
                            String name = dataSnapshot.child(model.getbookname()).getKey();

                            Intent i = new Intent(v.getContext(), BooksViewActivity.class);

                            i.putExtra("Title", dataSnapshot.child("Books").child(name).child("bookname").getValue().toString());
                            i.putExtra("Description", dataSnapshot.child("Books").child(name).child("bookdescription").getValue().toString());
                            i.putExtra("Imagelink", dataSnapshot.child("Books").child(name).child("image").getValue().toString());
                            i.putExtra("bookprice", dataSnapshot.child("Books").child(name).child("bookprice").getValue().toString());
                            i.putExtra("bookpublisher",dataSnapshot.child("Books").child(name).child("bookpublisher").getValue().toString());
                            i.putExtra("bookauthor",dataSnapshot.child("Books").child(name).child("bookauthor").getValue().toString());
                            i.putExtra("bookcategory",dataSnapshot.child("Books").child(name).child("bookcategory").getValue().toString());
                            i.putExtra("bookpages",dataSnapshot.child("Books").child(name).child("bookpages").getValue().toString());
                            i.putExtra("bookpublishingdate",dataSnapshot.child("Books").child(name).child("bookpublishingdate").getValue().toString());

                            v.getContext().startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                }
        });

        holder.addCourseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        if (dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue() != null) {

                            if (dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString().contains(model.getbookname())) {
                                Log.e("snapmain", "data exist");
                                Toast.makeText(v.getContext(), "Course Already added!", Toast.LENGTH_SHORT).show();
                            } else {
                                String olddata = dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString();
                                //Log.e("snapmain", dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getValue().toString());
//                                ref.child("Users").child(user.getUid()).child("MyCourses").
//                                        setValue(String.valueOf(model.getFirstname() + " ,\n" + olddata));
                                myListData.clear();
                                for(int i=0;i<dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").getChildrenCount();i++){
                                    Log.e("getdata",dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(i)).getValue(String.class));
                                    myListData.add(dataSnapshot.child("Users").child(user.getUid()).child("MyCourses").child(String.valueOf(i)).getValue(String.class));
                                }


                                myListData.add(model.getbookname().toString());

                                ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
                                Toast.makeText(v.getContext(), "Course added Success!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            myListData.add(model.getbookname().toString());

                            ref.child("Users").child(user.getUid()).child("MyCourses").setValue(myListData);
//                                ref.child("Users").child(user.getUid()).child("MyCourses").
//                                        setValue(String.valueOf(model.getFirstname()));
                            }
                        }



                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                String datas = String.valueOf(ref.child("Users").child(user.getUid()).child("MyCourses").);
//                Log.e("datas",datas);
//                ref.child("Users").child(user.getUid()).child("MyCourses").updateChildren()
//                        setValue(String.valueOf(model.getFirstname()));

            }
        });

    }


    @NonNull
    @Override
    public personsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new personsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class personsViewholder
            extends RecyclerView.ViewHolder {
        TextView firstname, addCourseTextView, age;
        ImageView imageurl;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);

            firstname = itemView.findViewById(R.id.textViewName);
            imageurl = itemView.findViewById(R.id.imageViewurl);
            addCourseTextView = itemView.findViewById(R.id.addCourseTextView);
//            lastname = itemView.findViewById(R.id.lastname);
//            age = itemView.findViewById(R.id.age);
        }
    }
}