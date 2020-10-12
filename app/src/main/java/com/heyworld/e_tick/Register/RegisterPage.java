package com.heyworld.e_tick.Register;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heyworld.e_tick.Login.LoginPage;
import com.heyworld.e_tick.Profile.ConstructorProfile;
import com.heyworld.e_tick.R;

public class RegisterPage extends AppCompatActivity {

    private static final String TAG = "RegisterPage";

    //Vars
    private EditText newEmail, newPwd;
    private Button registerBtn, alreadyHaveAccountBtn;
    private String sEmail, sPassword, reg_email, reg_password;

    //Firebase
    private FirebaseAuth firebaseAuth;


    //onCreate
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView (R.layout.register_page);

        firebaseAuth = FirebaseAuth.getInstance ();

        assignVars ();
        executeRegBtn ();
    }
    //onCreate

    //Assign the vars to their IDs
    private void assignVars(){
        Log.d (TAG, "assignVars: assgin vars in reg page");

        newEmail = findViewById (R.id.new_email);
        newPwd = findViewById (R.id.new_password);
        registerBtn = findViewById (R.id.register_btn);
        alreadyHaveAccountBtn = findViewById (R.id.already_have_account_btn);
        alreadyHaveAccountBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                startActivity (new Intent (RegisterPage.this, LoginPage.class));
            }
        });
    }
    //Assign the vars to their IDs

    //Validate user details. It runs inside executeRegBtn()
    private Boolean validate(){
        Log.d (TAG, "validate: validate new user details");

        Boolean result = false;

        sEmail = newEmail.getText ().toString ();
        sPassword = newPwd.getText ().toString ();

        if(sEmail.isEmpty () || sPassword.isEmpty ()){
            Toast.makeText (this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show ();
        }else{
            result = true;
        }
        return result;
    }
    //Validate user details. It runs inside executeRegBtn()

    //Execute reg btn & create an account. It runs inside OnCreate()
    private void executeRegBtn(){
        Log.d (TAG, "executeRegBtn: execute reg btn and create an account");

        registerBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                if(validate ()){
                    /*Create a user with email and password*/

                    reg_email = newEmail.getText ().toString ().trim ();
                    reg_password = newPwd.getText ().toString ().trim ();

                    firebaseAuth.createUserWithEmailAndPassword (reg_email, reg_password).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                        @Override
                        public void onComplete (@NonNull Task<AuthResult> task){
                          if(task.isSuccessful ()){
                              /*send user details to database*/
                              sendEmailVerification ();
                          }else{
                              Toast.makeText (RegisterPage.this, "Unable to create an account! Please check details and try again", Toast.LENGTH_SHORT).show ();
                          }
                        }
                    });
                }
            }
        });
    }
    //Execute reg btn & create an account. It runs inside OnCreate()

    //Sending email verification after sign up. It runs inside executeRegButton()
    private void sendEmailVerification(){
        Log.d (TAG, "sendEmailVerification: sending email verification in sign up page");

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser ();

        if(firebaseUser != null){
            firebaseUser.sendEmailVerification ().addOnCompleteListener (new OnCompleteListener<Void> () {
                @Override
                public void onComplete (@NonNull Task<Void> task){
                    if(task.isSuccessful ()){

                        sendUserDataToDatabase ();
                        Toast.makeText (RegisterPage.this, "Account created. Verification email sent!", Toast.LENGTH_SHORT).show ();
                        firebaseAuth.signOut ();
                        finish ();
                        startActivity (new Intent (RegisterPage.this, LoginPage.class));
                    }else{
                        Toast.makeText (RegisterPage.this, "Couldn't send verification email! Please try again later", Toast.LENGTH_SHORT).show ();
                    }
                }
            });
        }
    }
    //Sending email verification after sign up. It runs inside sendEmailVerification()

    //Sending new user data to database. It runs inside executeRegBtn()
    private void sendUserDataToDatabase(){
        Log.d (TAG, "sendUserDataToDatabase: sending new user data to database");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance ();
        DatabaseReference databaseReference = firebaseDatabase.getReference (firebaseAuth.getUid ());

        ConstructorProfile constructorProfile = new ConstructorProfile (sEmail);

        databaseReference.setValue (constructorProfile);
    }
    //Sending new user data to database. It runs inside sendEmailVerification()

}//Main