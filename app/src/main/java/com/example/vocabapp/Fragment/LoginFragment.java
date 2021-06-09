package com.example.vocabapp.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.vocabapp.Home;
import com.example.vocabapp.R;
import com.example.vocabapp.Users.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = LoginFragment.class.getName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RC_SIGN_IN = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;
    private Button forgetButton;
    private SignInButton signInButton;
    private LoginButton facebookLoginButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        passwordInput = view.findViewById(R.id.password_edit_text);
        emailInput = view.findViewById(R.id.email_edit_text);
        loginButton = view.findViewById(R.id.login_button);
        registerButton = view.findViewById(R.id.register_button);
        forgetButton = view.findViewById(R.id.forget_password_button);
        signInButton = view.findViewById(R.id.sign_up_google_button);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("403100613084-2i7fte6560455sh39p9uvk7447tee1ng.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        if (firebaseAuth.getCurrentUser() != null)
            startHomeActivity();

        registerButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out, R.anim.fade, R.anim.slide_in, R.anim.fade_in).replace(R.id.login_activity_container, new RegisterFragment()).addToBackStack(RegisterFragment.TAG).commit();
        });

        signInButton.setOnClickListener(v -> signIn());

        forgetButton.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out, R.anim.fade, R.anim.slide_in, R.anim.fade_in).replace(R.id.login_activity_container, new ResetPasswordFragment()).addToBackStack(ResetPasswordFragment.TAG).commit();
        });

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            final String password = passwordInput.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                emailInput.setError("Enter email address");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordInput.setError("Enter password");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (!task.isSuccessful()) {
                        if (password.length() < 6)
                            passwordInput.setError("Minimum 6 character");
                        else
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();

                    } else startHomeActivity();
                }
            });

        });
        TransitionInflater inflate = TransitionInflater.from(getContext());
        setExitTransition(inflate.inflateTransition(R.transition.fade));
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else if (!passwordInput.hasFocus()) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        passwordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            } else {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

//        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton = view.findViewById(R.id.sign_up_facebook_button);
        facebookLoginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {


            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        return view;
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("TAG","handleFacebookAccessToken");
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        startHomeActivity();
                    }
                    else{
                        Toast.makeText(getContext(),"Login failed",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //getID
                Log.d("Login", "onActivityResult");
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        startHomeActivity();
                    } else{
                        emailInput.setText("");
                        passwordInput.setText("");
                        emailInput.setError("Enter email address again");
                        Toast.makeText(getContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void startHomeActivity() {
        Intent intent = new Intent(getContext(), Home.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }


}