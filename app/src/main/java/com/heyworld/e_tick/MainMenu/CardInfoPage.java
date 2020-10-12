package com.heyworld.e_tick.MainMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.heyworld.e_tick.R;

public class CardInfoPage extends AppCompatActivity {

    private static final String TAG = "CardInfoPage";

    CardForm cardForm;
    Button saveCardDetailsBtn;
    AlertDialog.Builder alertBuilder;

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_info_page);

        cardInfoDetails ();
    }
    //onCreate

    //Card info details. It runs inside onCreate()
    private void cardInfoDetails(){
        Log.d (TAG, "cardInfoDetails: card info checker ran");

        cardForm = findViewById (R.id.card_form);
        saveCardDetailsBtn = findViewById (R.id.save_card_details_btn);

        cardForm.cardRequired (true)
                .expirationRequired (true)
                .cvvRequired (true)
                .postalCodeRequired (true)
                //.mobileNumberExplanation ("SMS required on this number")
                .setup (CardInfoPage.this);

       //cardForm.getCvvEditText ().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        saveCardDetailsBtn.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view){
                if(cardForm.isValid ()){

                    alertBuilder = new AlertDialog.Builder (CardInfoPage.this);
                    alertBuilder.setTitle ("Confirm your details");
                    alertBuilder.setMessage ("Card number: " + cardForm.getCardNumber () + "\n" +
                            "Card expiry month: " + cardForm.getExpirationMonth () + "\n" +
                            "Card expiry year: " + cardForm.getExpirationYear () + "\n" +
                            "Post code: " + cardForm.getPostalCode () + "\n" //+
                            //"Phone number: " + cardForm.getMobileNumber ()
                    );

                    /*Confirming card details*/
                    alertBuilder.setPositiveButton ("Confirm", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick (DialogInterface dialogInterface, int i){
                            dialogInterface.dismiss ();
                            Toast.makeText (CardInfoPage.this, "Thank you for using E-Tick", Toast.LENGTH_SHORT).show ();
                            finish ();
                            startActivity (new Intent (CardInfoPage.this, PaymentPage.class));
                        }
                    });

                    /*Declining card details*/
                    alertBuilder.setNegativeButton ("Cancel", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick (DialogInterface dialogInterface, int i){
                            dialogInterface.dismiss ();
                        }
                    });

                    /**/
                    AlertDialog alertDialog = alertBuilder.create ();
                    alertDialog.show ();

                }else{
                    Toast.makeText (CardInfoPage.this, "Please fill in missing sections", Toast.LENGTH_SHORT).show ();
                }
            }
        });
    }
    //Card info details. It runs inside onCreate()


}//Main