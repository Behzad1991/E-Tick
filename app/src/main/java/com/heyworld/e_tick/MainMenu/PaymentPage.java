package com.heyworld.e_tick.MainMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;
import com.heyworld.e_tick.Login.LoginPage;
import com.heyworld.e_tick.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class PaymentPage extends AppCompatActivity {

    private static final String TAG = "PaymentPage";

    private FirebaseAuth firebaseAuth;
    EditText QRValue;
    Button QRGeneratorBtn;
    ImageView QRImage;

    //onCreate
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate (savedInstanceState);
        setContentView (R.layout.payment_page);

        firebaseAuth = FirebaseAuth.getInstance ();
        generateQRcode ();
    }
    //onCreate

    //Log out method. it runs wherever needed.
    private void logOut(){
        Log.d (TAG, "logOut: log out from payment page!");

        firebaseAuth.signOut ();
        finish ();
        startActivity (new Intent (PaymentPage.this, LoginPage.class));
    }
    //Log out method. it runs wherever needed.

    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater ().inflate (R.menu.menu, menu);
        return true;
    }
    //inflate the menu

    //execute the items in menu bar
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){

        switch (item.getItemId ()){
            case R.id.log_out_menu:{
                logOut ();
                break;
            }

            case R.id.card_details_menu:{
                startActivity (new Intent (PaymentPage.this, CardInfoPage.class));
                break;
            }
        }
        return super.onOptionsItemSelected (item);
    }
    //execute the items in menu bar

    //Generating QR code. It runs inside onCreate()
    private void generateQRcode(){
        Log.d (TAG, "generateQRcode: generating QR code");

        QRValue = findViewById (R.id.new_code_generator);
        QRGeneratorBtn = findViewById (R.id.code_generator_btn);
        QRImage = findViewById (R.id.qr_code);

        QRGeneratorBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                String data = QRValue.getText ().toString ();

                QRGEncoder qrgEncoder = new QRGEncoder (data, null, QRGContents.Type.TEXT, 500);

                if(data.isEmpty ()){
                    QRValue.setError ("Please enter your details");
                }else{
                        Bitmap qrBitmap = qrgEncoder.getBitmap ();
                        QRImage.setImageBitmap (qrBitmap);
                }
            }
        });
    }
    //Generating QR code. It runs inside onCreate()



}//Main