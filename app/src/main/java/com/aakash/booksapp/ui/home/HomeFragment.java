package com.aakash.booksapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aakash.booksapp.AllBooksRecyclerViewAdapter;
import com.aakash.booksapp.Model.BookStore;
import com.aakash.booksapp.Model.Popularbooks;
import com.aakash.booksapp.PopularBooksRecyclerViewAdapter;
import com.aakash.booksapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    TextView toolBarTitle;
    private FirebaseAuth.AuthStateListener authStateListener;

    TextView allCoursesButton,profilenameTextView, noticeTextView, toolbarTitle,toolbarTextDarkMode;
    ImageView profileImage,profileImageNavigationdrawerImageView;
    private RecyclerView popularCoursesRecyclerView,allCoursesRecyclerView;
    PopularBooksRecyclerViewAdapter adapter;
    AllBooksRecyclerViewAdapter allCoursesAdapter;
    DatabaseReference mbase, allCoursesDatabaseRef, ref;


    public ActionBarDrawerToggle actionBarDrawerToggle;
    ImageView menuImageViewButton,menuButtonDarkMode;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = view.findViewById(R.id.toolbarText);

        allCoursesButton = view.findViewById(R.id.allcourses);

        allCoursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getContext(),CoursesListActivity.class));
            }
        });


        ref = FirebaseDatabase.getInstance().getReference();
        mbase = ref.child("Popularbooks");
        Toast.makeText(getContext(), mbase.toString(), Toast.LENGTH_SHORT).show();
        popularCoursesRecyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager popularcoursesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularCoursesRecyclerView.setLayoutManager(popularcoursesLayoutManager);
        //popularCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FirebaseRecyclerOptions<Popularbooks> options
                = new FirebaseRecyclerOptions.Builder<Popularbooks>()
                .setQuery(mbase, Popularbooks.class)
                .build();

        adapter = new PopularBooksRecyclerViewAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        popularCoursesRecyclerView.setAdapter(adapter);



        allCoursesDatabaseRef = ref.child("Books");



        allCoursesRecyclerView = view.findViewById(R.id.recyclerView1);
        LinearLayoutManager allcoursesLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        allCoursesRecyclerView.setLayoutManager(allcoursesLayout);
        //popularCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        FirebaseRecyclerOptions<BookStore> options1
                = new FirebaseRecyclerOptions.Builder<BookStore>()
                .setQuery(allCoursesDatabaseRef, BookStore.class)
                .build();

        allCoursesAdapter = new AllBooksRecyclerViewAdapter(options1);
        // Connecting Adapter class with the Recycler view*/
        allCoursesRecyclerView.setAdapter(allCoursesAdapter);



        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
        allCoursesAdapter.startListening();

    }
    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
        allCoursesAdapter.stopListening();
    }
}