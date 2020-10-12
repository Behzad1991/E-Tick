package com.heyworld.e_tick.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.heyworld.e_tick.Login.LoginPage;
import com.heyworld.e_tick.R;

public class ForgotPwdPage extends AppCompatActivity {

    private static final String TAG = "ForgotPwdPage";

    private EditText resetPwdEmail;
    private Button resetPwdBtn;

    private FirebaseAuth firebaseAuth;

    //onCreate
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView (R.layout.forgot_pwd_page);

        firebaseAuth = FirebaseAuth.getInstance ();

        resetPwd ();
    }
    //onCreate

    //Reset pwd function. It runs inside onCreate()
    private void resetPwd(){
        Log.d (TAG, "resetPwd: reset pwd function");

        resetPwdEmail = findViewById (R.id.forgot_pwd_email);
        resetPwdBtn = findViewById (R.id.reset_pwd_btn);

        resetPwdBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
             String sEmail = resetPwdEmail.getText ().toString ().trim ();

             if(sEmail.isEmpty ()){
                 Toast.makeText (ForgotPwdPage.this, "Field cannot be empty. Enter the email registered to the app", Toast.LENGTH_SHORT).show ();
             }else{
                 firebaseAuth.sendPasswordResetEmail (sEmail).addOnCompleteListener (new OnCompleteListener<Void> () {
                     @Override
                     public void onComplete (@NonNull Task<Void> task){
                         if(task.isSuccessful ()){
                             Toast.makeText (ForgotPwdPage.this, "Reset password link has been sent!", Toast.LENGTH_SHORT).show ();
                             finish ();
                             startActivity (new Intent (ForgotPwdPage.this, LoginPage.class));
                         }else{
                             Toast.makeText (ForgotPwdPage.this, "Unable to send reset password link. Please try again!", Toast.LENGTH_SHORT).show ();
                         }
                     }
                 });
             }
            }
        });
    }
    //Reset pwd function. It runs inside onCreate()

}//Main