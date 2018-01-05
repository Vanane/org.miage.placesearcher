package org.miage.placesearcher;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PlaceDetailActivity extends AppCompatActivity {

    @BindView(R.id.activity_detail_place_street)
    TextView mPlaceStreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        mPlaceStreet.setText("Détails du lieu");
    }

    @OnClick(R.id.activity_detail_place_street)
    public void clickedOnPlaceStreet() {
        finish();
    }
}
