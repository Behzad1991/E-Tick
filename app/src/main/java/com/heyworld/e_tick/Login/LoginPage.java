package com.heyworld.e_tick.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heyworld.e_tick.MainMenu.PaymentPage;
import com.heyworld.e_tick.Profile.ForgotPwdPage;
import com.heyworld.e_tick.R;
import com.heyworld.e_tick.Register.RegisterPage;

public class LoginPage extends AppCompatActivity {

    private static final String TAG = "LoginPage";

    private EditText userEmail, userPassword;
    private Button loginBtn, createAccountBtn, forgotPwdBtn;
    private String sEmail, sPassword;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        firebaseAuth = FirebaseAuth.getInstance ();
        FirebaseUser user = firebaseAuth.getCurrentUser ();

        progressDialog = new ProgressDialog (this);

        /**User doesn't need log in if they have already did*/
        if(user != null){
            finish ();
            startActivity (new Intent (LoginPage.this, PaymentPage.class));
        }


        assign ();
    }
    //onCreate

    //Assign the vars to their IDs
    private void assign(){
        Log.d (TAG, "assign: Assign widgets to their ids in Login page");

        userEmail = findViewById (R.id.user_email);
        userPassword = findViewById (R.id.user_password);

        createAccountBtn = findViewById (R.id.create_account_btn);
        createAccountBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                startActivity (new Intent (LoginPage.this, RegisterPage.class));
            }
        });

        forgotPwdBtn = findViewById (R.id.forgot_pwd_btn);
        forgotPwdBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                startActivity (new Intent (LoginPage.this, ForgotPwdPage.class));
            }
        });

        loginBtn = findViewById (R.id.login_btn);
        loginBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                validate (userEmail.getText ().toString (), userPassword.getText ().toString ());
            }
        });
    }
    //Assign the vars to their IDs

    //Validate user details. It runs in assign()/logingBtn()
    private void validate(String user_email, String user_password){

        progressDialog.setTitle ("Login");
        progressDialog.setMessage ("Please wait...");
        progressDialog.show ();


        sEmail = userEmail.getText ().toString ();
        sPassword = userPassword.getText ().toString ();

        if(sEmail.isEmpty () || sPassword.isEmpty ()){
            progressDialog.dismiss ();
            Toast.makeText (this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show ();
        }else{
            firebaseAuth.signInWithEmailAndPassword (user_email, user_password).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                @Override
                public void onComplete (@NonNull Task<AuthResult> task){
                    if(task.isSuccessful ()){
                        progressDialog.dismiss ();
                        checkEmailVerification ();
                    }else{
                        Toast.makeText (LoginPage.this, "Login failed. Try again!", Toast.LENGTH_SHORT).show ();
                        progressDialog.dismiss ();
                    }
                }
            });
        }
    }
    //Validate user details. It runs in assign()/logingBtn()

    //checking email verification status. It runs inside validate()
    private void checkEmailVerification(){
        Log.d (TAG, "checkEmailVerification: checking email verification status in login page");

        FirebaseUser firebaseUser = firebaseAuth.getInstance ().getCurrentUser ();

        Boolean emailVerificationStatus = firebaseUser.isEmailVerified ();

        if(emailVerificationStatus){
            finish ();
            Toast.makeText (this, "Logged in successfully!", Toast.LENGTH_SHORT).show ();
            startActivity (new Intent (LoginPage.this, PaymentPage.class));
        }else{
            Toast.makeText (this, "You need to verify your email before login!", Toast.LENGTH_SHORT).show ();
            firebaseAuth.signOut ();
        }
    }
    //checking email verification status. It runs inside validate()


}//Main