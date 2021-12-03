package org.miage.placesearcher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.gcacace.signaturepad.views.SignaturePad;

import org.miage.placesearcher.model.Command;
import org.miage.placesearcher.model.PlaceAddress;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PlaceDetailActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 42;

    @BindView(R.id.activity_detail_place_street)
    TextView mPlaceStreet;

    @BindView(R.id.activity_detail_place_pic)
    ImageView mPlacePic;

    @BindView(R.id.signature_pad)
    SignaturePad pad;

    private String mPlaceStreetValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        mPlaceStreetValue = getIntent().getStringExtra("placeStreet");
        mPlaceStreet.setText(mPlaceStreetValue);
    }

    @OnClick(R.id.activity_detail_place_street)
    public void clickedOnPlaceStreet() {
        finish();
    }

    @OnClick(R.id.activity_detail_button_search)
    public void clickedOnGoogleSearch() {
        // Open browser using an Intent
        Uri url = Uri.parse("https://www.google.fr/search?q=" + mPlaceStreetValue);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
        startActivity(launchBrowser);
    }

    @OnClick(R.id.activity_detail_valider)
    public void onValiderClick()
    {
        // Sauvegarder image dans bdd
        Bitmap signature = pad.getSignatureBitmap();
        Command c = new Command();
        c.address = new PlaceAddress();
        c.address.label = getIntent().getStringExtra("placeLabel");
        c.signature = bitmapToByteArray(signature);
        c.save();

        finish();
    }


    @OnClick(R.id.activity_detail_effacer)
    public void onEffacerClick()
    {
        pad.clear();
    }


    @Override
    protected void onStart() {
        super.onStart();
        pad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                Log.d("aaaaa", "onStartSigning: a");
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        // If we get a result from the SELECT_PHOTO query
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    // Get the selected image as bitmap
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap selectedImageBitmap = BitmapFactory.decodeStream(imageStream);

                        // Set the bitmap to the picture
                        mPlacePic.setImageBitmap(selectedImageBitmap);
                    } catch (FileNotFoundException e) {
                        // Silent catch : image will not be displayed
                    }
                }
            break;
        }
    }


    /**
     * Retourne un tableau d'octets représentant une image Bitmap
     */
    private byte[] bitmapToByteArray(Bitmap bmp)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
