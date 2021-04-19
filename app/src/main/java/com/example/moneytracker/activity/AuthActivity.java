package com.example.moneytracker.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.fragment.LoginFragment;
import com.example.moneytracker.fragment.SignupFragment;
import com.example.moneytracker.model.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AuthActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener {

    LoginFragment loginFragment;
    SignupFragment signUpFragment;
    private FirebaseAuth mAuth;
    private FirebaseDatabase usersRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference userRef;
    private StorageReference picRef;
    private FirebaseStorage picsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user!= null){
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance();
        picsRef = FirebaseStorage.getInstance();
       picRef = picsRef.getReference();
        userRef=usersRef.getReference("/users/id");

        loginFragment = LoginFragment.newInstance();
        signUpFragment = SignupFragment.newInstance();
        onNavigateToLoginClicked();


//        updateUI(currentUser);
    }

    @Override
    public void onLoginClicked(User user) {
        signIn(user.getEmail(), user.getPassword());
    }

    private void loginAndNavigate(User user) {
        signIn(user.getEmail(), user.getPassword());
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onNavigationToSignupClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, signUpFragment)
                .commitNow();
    }

    @Override
    public void onSignupClicked(User user, Uri image_uri) {

        createAccount(user,image_uri);
    }

    private void createAccount(User user1,Uri image_uri) {
        //final User user1 = new User();
        //user1.setEmail(email);
        //user1.setPassword(password);

        mAuth.createUserWithEmailAndPassword(user1.getEmail(), user1.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Create user with password success");
                            updateUI(user1,image_uri);
                            loginAndNavigate(user1);


                            //loader.dismiss();
                        } else {
                            Log.w("TAG", "Create user with password failure", task.getException());
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(User user1,Uri image_uri) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null) {
            String uid = user.getUid();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("name", user1.getName());
            userMap.put("email", user1.getEmail());
            userMap.put("logedinon",date);

            Log.d("Error", "okokokokokokOK");
           // userRef.child(user.getUid()).child("message").setValue("hello");

            final StorageReference ref = picRef.child("images/pic");
            UploadTask uploadTask = ref.putFile(image_uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String image=downloadUri.toString();
                        userMap.put("profilePic",image);

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
            userRef.child(user.getUid()).updateChildren(userMap);


          /*  userRef.child(user.getUid()).setValue(user1);/*.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "user data added successfuly");


                    } else {
                        Log.w("TAG", "user data failure", task.getException());
                    }
                }
            });*/
        }else
            Log.d("Error", "current user is null");

    }

    @Override
    public void onNavigateToLoginClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitNow();
    }

}