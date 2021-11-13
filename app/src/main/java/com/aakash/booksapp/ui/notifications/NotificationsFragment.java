package com.aakash.booksapp.ui.notifications;

import android.os.Bundle;
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

import com.aakash.booksapp.R;
import com.aakash.booksapp.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {
    Button btnLogOut, removeCoursesButton, registerUserButton, editProfileButton, addCoursesButton;
    FirebaseAuth firebaseAuth;
    TextView nameTextView, emailTextView, toolBarTitle, studentIDTextView, mobileTextView, birthdateTextView;
    private FirebaseAuth.AuthStateListener authStateListener;
    ImageView profileImageView;

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return  inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}