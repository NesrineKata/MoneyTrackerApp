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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AuthActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        SignupFragment.OnFragmentInteractionListener {

    LoginFragment loginFragment;
    SignupFragment signUpFragment;
    private FirebaseAuth mAuth;
    private FirebaseDatabase usersRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference userRef;
    private String url;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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

                            uploadImage(user1,image_uri);
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


            userMap.put("pic",url);
            userRef.child(user.getUid()).updateChildren(userMap);
        }else
            Log.d("Error", "current user is null");

    }

    @Override
    public void onNavigateToLoginClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment)
                .commitNow();
    }
    private void uploadImage(User user1,Uri filePath) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ user1.getName()+".jpeg");
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();
                            Log.d("Here the Uri ", "lalallalallala: "+url);
                             updateUI(user1,filePath);

                            // complete the rest of your code
                        }
                    });

                }
            });

                    /*
                  .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
                           url= ref.getDownloadUrl().toString();
                            Log.d("uploaded", url);
                        }
                      })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

                     */
        }
    }

}