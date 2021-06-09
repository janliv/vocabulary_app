package com.example.vocabapp.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = ResetPasswordFragment.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageButton backButton;
    private Button resetButton;
    private TextView emailInput;
    private FirebaseAuth firebaseAuth;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        backButton = view.findViewById(R.id.image_back_button);
        resetButton = view.findViewById(R.id.reset_button);
        emailInput = view.findViewById(R.id.email_edit_text);
        emailInput.requestFocus();
        firebaseAuth = firebaseAuth.getInstance();

        backButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        resetButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getContext(), "Enter email", Toast.LENGTH_LONG).show();
                return;
            }

            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            getActivity().getSupportFragmentManager().popBackStack();
                            Toast.makeText(getContext(), "We have sent you instruction to reset your password", Toast.LENGTH_LONG).show();
                        } else {
                            emailInput.setError("enter email again");
                            Toast.makeText(getContext(), "Failed to send reset email", Toast.LENGTH_LONG).show();
                        }

                    });

        });
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
        emailInput.setOnFocusChangeListener((v,hasFocus)->{
            if (hasFocus) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else{
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
//        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        TransitionInflater inflate = TransitionInflater.from(getContext());
        setEnterTransition(inflate.inflateTransition(R.transition.slide_right));

        return view;
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }
}