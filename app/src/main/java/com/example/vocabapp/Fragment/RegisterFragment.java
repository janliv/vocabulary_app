package com.example.vocabapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vocabapp.R;
import com.example.vocabapp.Users.UserDataHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = RegisterFragment.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText displayNameInput;
    private EditText passwordAgainInput;
    private EditText ageInput;
    private CircleImageView displayPhoto;
    private Button registerButton;
    private ImageButton bacButton;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private Uri uri;
    private ProgressBar progressBar;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                uri = imageUri;
                displayPhoto.setImageURI(imageUri);
            }
        }
    }

    private void uploadDisplayPhoto(Uri imageUri) {
        StorageReference fileReference = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> Log.d("TAG", "upload photo successfully"));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        emailInput = view.findViewById(R.id.email_edit_text);
        emailInput.requestFocus();
        passwordInput = view.findViewById(R.id.password_edit_text);
        displayNameInput = view.findViewById(R.id.display_name);
        displayPhoto = view.findViewById(R.id.image_display_photo);
        passwordAgainInput = view.findViewById(R.id.password_again_edit_text);
        ageInput = view.findViewById(R.id.age_edit_text);
        registerButton = view.findViewById(R.id.register_button);
        bacButton = view.findViewById(R.id.image_back_button);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);


        bacButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus&&!passwordInput.hasFocus()&&!passwordAgainInput.hasFocus()&&!displayNameInput.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
        passwordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus&&!emailInput.hasFocus()&&!passwordAgainInput.hasFocus()&&!displayNameInput.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
        passwordAgainInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus&&!passwordInput.hasFocus()&&!emailInput.hasFocus()&&!displayNameInput.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });
        displayNameInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus&&!passwordInput.hasFocus()&&!passwordAgainInput.hasFocus()&&!emailInput.hasFocus())
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        });


        registerButton.setOnClickListener(v -> {

            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String passwordAgain = passwordAgainInput.getText().toString().trim();
            String userName = displayNameInput.getText().toString().trim();
            String age = ageInput.getText().toString().trim();

            if (!isValidEmail(email)) {
                emailInput.requestFocus();
                emailInput.setError("Invalid email");
                return;
            }
            if (userName.isEmpty() || userName.length() <= 3) {
                displayNameInput.requestFocus();
                displayNameInput.setError("Invalid name");
            }

            if (age.isEmpty()) {
                ageInput.requestFocus();
                ageInput.setError("Enter your age");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordInput.requestFocus();
                passwordInput.setError("Enter your password");
                return;
            }
            if (password.length() < 6) {
                passwordInput.requestFocus();
                passwordInput.setError("Password too short, enter minimum 6 characters!");
                return;
            }
            if (password.compareTo(passwordAgain) != 0) {
                passwordAgainInput.requestFocus();
                passwordAgainInput.setError("");
            }
            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            updateProfile(email, password, age, userName);
                            Toast.makeText(getContext(), "Sign up successful", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                        progressBar.setVisibility(View.GONE);

                    });
        });
//        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.H);
        TransitionInflater inflate = TransitionInflater.from(getContext());
        setEnterTransition(inflate.inflateTransition(R.transition.slide_right));

        displayPhoto.setOnClickListener(v -> {
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);
        });

        return view;
    }

    private void updateProfile(String email, String password, String age, String displayName) {
        if (uri != null) {
            uploadDisplayPhoto(uri);
        }

        new UserDataHelper().updateAge(age, new UserDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<String> list, String key) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {
                Log.d("TAG", "Updated age");
            }

            @Override
            public void DataIsDeleted() {

            }
        });

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(displayName).build()).addOnSuccessListener(aVoid -> Log.d("TAG", "profile updated"));
        });
        firebaseAuth.signOut();
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}