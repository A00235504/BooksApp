package com.aakash.booksapp.ViewPagerFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.aakash.booksapp.R;

public class SecondPage extends Fragment {
    TextView priceTextView, bookPublisherTextView, bookAuthorTextView, bookCategoryTextView, bookPagesTextView, bookPublishingDateTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second_page, container, false);

        priceTextView = v.findViewById(R.id.priceTextView);
        bookPublisherTextView = v.findViewById(R.id.bookPublisherTextView);
        bookAuthorTextView = v.findViewById(R.id.bookAuthorTextView);
        bookCategoryTextView = v.findViewById(R.id.bookCategoryTextView);
        bookPagesTextView = v.findViewById(R.id.bookPagesTextView);
        bookPublishingDateTextView = v.findViewById(R.id.bookPublishingDateTextView);

        try {
            String myValue = getArguments().getString("price","hello");
            priceTextView.setText("$"+myValue);


            bookPublisherTextView.setText(getArguments().getString("publisher","hello"));
            bookAuthorTextView.setText(getArguments().getString("author","hello"));
            bookCategoryTextView.setText(getArguments().getString("category","hello"));
            bookPagesTextView.setText(getArguments().getString("pages","hello"));
            bookPublishingDateTextView.setText(getArguments().getString("publishingdate","hello"));

        }
        catch (Exception e){

        }

        return v;
    }
}