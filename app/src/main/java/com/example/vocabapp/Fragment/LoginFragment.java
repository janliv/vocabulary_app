package com.example.vocabapp.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.vocabapp.Home;
import com.example.vocabapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

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
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext, lefttoright, righttoleft, fadein;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private TextView forgetButton;
    private Button signInButton;
    private Button facebookLoginButton;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private Collection<String> permissions = Arrays.asList("public_profile ", "email");


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

    @Override
    public void onStart() {
        super.onStart();
        signInButton.setAnimation(righttoleft);
        facebookLoginButton.setAnimation(lefttoright);
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
        registerButton = view.findViewById(R.id.register_text_view);
        forgetButton = view.findViewById(R.id.forget_password_text_view);
        signInButton = view.findViewById(R.id.sign_up_google_button);
        facebookLoginButton = view.findViewById(R.id.sign_up_facebook_button);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();

        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);
        lefttoright = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right);
        righttoleft = AnimationUtils.loadAnimation(getContext(), R.anim.right_to_left);
        fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("403100613084-2i7fte6560455sh39p9uvk7447tee1ng.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        if (firebaseAuth.getCurrentUser() != null)
            startHomeActivity();

        registerButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out, R.anim.fade, R.anim.slide_in, R.anim.fade_in).replace(R.id.login_activity_container, new RegisterFragment()).addToBackStack(RegisterFragment.TAG).commit());

        signInButton.setOnClickListener(v -> signIn());

        forgetButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_out, R.anim.fade, R.anim.slide_in, R.anim.fade_in).replace(R.id.login_activity_container, new ResetPasswordFragment()).addToBackStack(ResetPasswordFragment.TAG).commit());

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            final String password = passwordInput.getText().toString().trim();

            if (!isValidEmail(email)) {
                emailInput.setSelectAllOnFocus(true);
                emailInput.requestFocus();
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
                        else {
                            Toast.makeText(getContext(), "Sign in unsuccessfully", Toast.LENGTH_LONG).show();
                            emailInput.setSelectAllOnFocus(true);
                            emailInput.requestFocus();
                            passwordInput.setText("");
                        }

                    } else {
                        startHomeActivity();
                    }
                }
            });

        });
        TransitionInflater inflate = TransitionInflater.from(getContext());
        setExitTransition(inflate.inflateTransition(R.transition.fade));
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(INPUT_METHOD_SERVICE);
        emailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!passwordInput.hasFocus()&&!hasFocus) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        passwordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus&&!emailInput.hasFocus()) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        facebookLoginButton.setOnClickListener(v -> connect());

        return view;
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("TAG", "handleFacebookAccessToken");
        progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        startHomeActivity();
                    } else {
                        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
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

        if (callbackManager != null)
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
                    } else {
                        emailInput.setText("");
                        passwordInput.setText("");
                        emailInput.setError("Enter email address again");
                        Toast.makeText(getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void connect() {
        callbackManager = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();
        loginManager.logInWithReadPermissions(this, permissions);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult != null)
                    handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
            }
        });
    }
}