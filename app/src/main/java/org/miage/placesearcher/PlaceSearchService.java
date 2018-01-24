package org.miage.placesearcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.miage.placesearcher.event.EventBusManager;
import org.miage.placesearcher.event.SearchResultEvent;
import org.miage.placesearcher.model.PlaceSearchResult;

import java.lang.reflect.Modifier;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by alexmorel on 05/01/2018.
 */

public class PlaceSearchService {

    private static final long REFRESH_DELAY = 2000;
    public static PlaceSearchService INSTANCE = new PlaceSearchService();
    private final PlaceSearchRESTService mPlaceSearchRESTService;
    private long mLastUpdateTime;

    private PlaceSearchService() {
        // Create GSON Converter that will be used to convert from JSON to Java
        Gson gsonConverter = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation().create();

        // Create Retrofit client
        Retrofit retrofit = new Retrofit.Builder()
                // Using OkHttp as HTTP Client
                .client(new OkHttpClient())
                // Having the following as server URL
                .baseUrl("https://api-adresse.data.gouv.fr")
                // Using GSON to convert from Json to Java
                .addConverterFactory(GsonConverterFactory.create(gsonConverter))
                .build();

        // Use retrofit to generate our REST service code
        mPlaceSearchRESTService = retrofit.create(PlaceSearchRESTService.class);
    }

    public void searchPlacesFromAddress(final String search, boolean force) {
        // First check if we already made a call recently (and if this is the case, do nothing)
        if (force || System.currentTimeMillis() - mLastUpdateTime > REFRESH_DELAY){
            mLastUpdateTime = System.currentTimeMillis();

            // Call to the REST service
            mPlaceSearchRESTService.searchForPlaces(search).enqueue(new Callback<PlaceSearchResult>() {
                @Override
                public void onResponse(Call<PlaceSearchResult> call, retrofit2.Response<PlaceSearchResult> response) {
                    // Post an event so that listening activities can update their UI
                    if (response.body() != null && response.body().features != null) {
                        EventBusManager.BUS.post(new SearchResultEvent(response.body().features));
                    } else {
                        // Null result
                        // We may want to display a warning to user (e.g. Toast)
                    }
                }

                @Override
                public void onFailure(Call<PlaceSearchResult> call, Throwable t) {
                    // Request has failed or is not at expected format
                    // We may want to display a warning to user (e.g. Toast)
                }
            });
        }
    }

    // Service describing the REST APIs
    public interface PlaceSearchRESTService {

        @GET("search/")
        Call<PlaceSearchResult> searchForPlaces(@Query("q") String search);
    }
}
