package com.aakash.booksapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.aakash.booksapp.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class BooksViewActivity extends AppCompatActivity {
TextView titleTextView, descriptionTextView, toolBarTitle;
ImageView courseImageView;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);

        //getting all ids
        getID();

        //view adapter for tabbar
        viewPagerAdapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                getIntent().getStringExtra("Description"),
                getIntent().getStringExtra("Title"),
                getIntent().getStringExtra("Imagelink"),
                getIntent().getStringExtra("bookprice"),
                getIntent().getStringExtra("bookpublisher"),
                getIntent().getStringExtra("bookauthor"),
                getIntent().getStringExtra("bookcategory"),
                getIntent().getStringExtra("bookpages"),
                getIntent().getStringExtra("bookpublishingdate"));

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //getting title and setting title on custom toolbar
        String title = getIntent().getStringExtra("Title");
        toolBarTitle.setText(title);


    }

    //all ids defined here as function
    public void getID(){
        toolBarTitle = findViewById(R.id.toolbarText);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabLayout);
    }
}