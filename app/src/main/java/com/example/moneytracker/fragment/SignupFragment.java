/*package com.example.moneytracker.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneytracker.R;
import com.example.moneytracker.model.User;
import com.google.firebase.storage.FirebaseStorage;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SignupFragment extends Fragment {
    EditText emailEditText;
    EditText passEditText;
    EditText fullNameEditText;
    TextView loginButton;
    Button signupButton;
    ImageButton showUserProfile;

    private Uri uri;

    private final int PICK_IMAGE_REQUEST = 71;
    private OnFragmentInteractionListener listener;

    public SignupFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passEditText = view.findViewById(R.id.passwordEditText);
        fullNameEditText = view.findViewById(R.id.full_name_edit_text);
        loginButton = view.findViewById(R.id.login_button);
        signupButton = view.findViewById(R.id.signup_button);
        showUserProfile=view.findViewById(R.id.user_profile_photo);
        showUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }

        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNavigateToLoginClicked();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String pass = passEditText.getText().toString().trim();
                String fullName = fullNameEditText.getText().toString();
                User user;
                if (!email.equals("") && !pass.equals("") && fullName.length() != 0) {

                    user = new User(email, pass, fullName);
//                    user.setEmail(email);
//                    user.setPassword(pass);
//                    user.setName(fullName);
                    listener.onSignupClicked(user,uri);
                } else if (fullName.length() == 0) {
                    Toast.makeText(getContext(), "Full Name is required! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "invalid email or password", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSignupClicked(User user,Uri uri);

        void onNavigateToLoginClicked();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                showUserProfile.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        }
    }
}
*/
        package com.example.moneytracker.fragment;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;

        import androidx.annotation.NonNull;
        import androidx.fragment.app.Fragment;

        import android.provider.MediaStore;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.moneytracker.R;
        import com.example.moneytracker.model.User;
        import com.google.firebase.storage.FirebaseStorage;

        import org.w3c.dom.Text;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;

     //   import de.hdodenhof.circleimageview.CircleImageView;

        import de.hdodenhof.circleimageview.CircleImageView;

        import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment {
    EditText emailEditText;
    EditText passEditText;
    EditText fullNameEditText;
    TextView loginButton;
    Button signupButton;
    //ImageButton showUserProfile;
    //ImageView showUserProfile;

    private Uri uri;

    private final int PICK_IMAGE_REQUEST = 71;
    private OnFragmentInteractionListener listener;

    public SignupFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        emailEditText = view.findViewById(R.id.emailEditText);
        passEditText = view.findViewById(R.id.passwordEditText);
        fullNameEditText = view.findViewById(R.id.full_name_edit_text);
        loginButton = view.findViewById(R.id.login_button);
        signupButton = view.findViewById(R.id.signup_button);
       // showUserProfile=view.findViewById(R.id.user_profile_photo);
        /*showUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }

        });
        */

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNavigateToLoginClicked();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String pass = passEditText.getText().toString().trim();
                String fullName = fullNameEditText.getText().toString();
                User user;
                if (!email.equals("") && !pass.equals("") && fullName.length() != 0) {

                    user = new User(email, pass, fullName);
//                    user.setEmail(email);
//                    user.setPassword(pass);
//                    user.setName(fullName);
                    listener.onSignupClicked(user,uri);
                } else if (fullName.length() == 0) {
                    Toast.makeText(getContext(), "Full Name is required! ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "invalid email or password", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    /*private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

     */


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onSignupClicked(User user,Uri uri);

        void onNavigateToLoginClicked();
    }
    /*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                showUserProfile.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

     */
}