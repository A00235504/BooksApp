package com.aakash.booksapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aakash.booksapp.ViewPagerFragments.FirstPage;
import com.aakash.booksapp.ViewPagerFragments.SecondPage;


public class ViewPagerAdapter
        extends FragmentPagerAdapter {
String description, title, imageurl, price, publisher,author, category,pages,publishingdate;
    public ViewPagerAdapter(
            @NonNull FragmentManager fm, String description, String title, String imageurl, String price, String publisher, String author,
            String category, String pages, String publishingdate)
    {
        super(fm);
        this.description = description;
        this.title = title;
        this.imageurl = imageurl;
        this.price = price;
        this.publisher = publisher;
        this.author = author;
        this.category = category;
        this.pages = pages;
        this.publishingdate = publishingdate;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0){
            fragment = new FirstPage();
        Bundle bundle = new Bundle();
        bundle.putString("Description", description);
        bundle.putString("Title", title);
            bundle.putString("Imagelink", imageurl);
        fragment.setArguments(bundle);
        }
        else if (position == 1) {
            fragment = new SecondPage();
            Bundle bundle = new Bundle();
            bundle.putString("price", price);
            bundle.putString("publisher", publisher);
            bundle.putString("author", author);
            bundle.putString("category", category);
            bundle.putString("pages", pages);
            bundle.putString("publishingdate", publishingdate);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Description";
        else if (position == 1)
            title = "About";

        return title;
    }
}
