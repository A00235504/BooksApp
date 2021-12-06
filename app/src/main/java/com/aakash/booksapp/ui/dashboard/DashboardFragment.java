package com.aakash.booksapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aakash.booksapp.CustomAdapter;
import com.aakash.booksapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    TextView toolBarTitle;
    private FirebaseAuth.AuthStateListener authStateListener;

    ArrayList<String> arrayList;
    ListView listview;
    TextView noCoursesTextView;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        toolBarTitle = view.findViewById(R.id.toolbarText);
        toolBarTitle = view.findViewById(R.id.toolbarText);
        noCoursesTextView = view.findViewById(R.id.noCourseTextView);
        listview = view.findViewById(R.id.mylistview);
        noCoursesTextView = view.findViewById(R.id.noCourseTextView);

getFirebaseData();
        return view;
    }



    public void getFirebaseData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("MyCourses");

        arrayList = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    noCoursesTextView.setVisibility(View.GONE);
                    arrayList.clear();
                    for(DataSnapshot dss:snapshot.getChildren()){
                        String nn = dss.getValue(String.class);
                        arrayList.add(nn);

                    }

                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i=0;i<arrayList.size();i++){
                        stringBuilder.append(arrayList.get(i) + ",");
                    }
                    Log.e("dldata",stringBuilder.toString());
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrayList );
                    CustomAdapter adapter = new CustomAdapter(getContext(),  arrayList);

                    listview.setAdapter(adapter);

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(), "the position:"+position, Toast.LENGTH_SHORT).show();
                            arrayList.remove(position);
                            ref.setValue(null);
                            ref.setValue(arrayList);
                            listview.setAdapter(arrayAdapter);
                        }
                    });
                }
                else{
                    noCoursesTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}