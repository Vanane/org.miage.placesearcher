package org.miage.placesearcher;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.miage.placesearcher.model.Place;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Binding ButterKnife annotations now that content view has been set
        ButterKnife.bind(this);
        
        // Define list of persons
        List<Place> places = new ArrayList<Place>();
        for (int i = 0; i < 50000; i ++) {
            places.add(new Place(0, 0, "Street" + i, "44000", "Nantes"));
        }
        // Instanciate a PersonAdapter
        PlaceAdapter adapter = new PlaceAdapter(this, places);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();

        PlaceSearchService.INSTANCE.searchPlacesFromAddress("Place du commerce");
    }
}
