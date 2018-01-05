package org.miage.placesearcher;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.miage.placesearcher.model.Place;
import org.miage.placesearcher.ui.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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

        // Create AsyncTask
        AsyncTask<String, Void, Response> asyncTask = new AsyncTask<String, Void, Response>() {

            @Override
            protected Response doInBackground(String... params) {
                // Here we are in a new background thread
                try {
                    final OkHttpClient okHttpClient = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(params[0])
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    return response;
                } catch (IOException e) {
                    // Silent catch, no places will be displayed
                    Log.e("Error", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                // Here we are in caller Thread (i.e. UI thread here as onResume() is called on UI Thread)
                if (response != null && response.body() != null) {
                    try {
                        Toast toast = Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG);
                        toast.show();
                    } catch (IOException e) {
                       // Silent catch, toast will not be shown
                    }
                }
            }
        };
        asyncTask.execute("https://api-adresse.data.gouv.fr/search/?q=Place%20du%20commerce");
    }
}
