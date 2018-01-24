package org.miage.placesearcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.miage.placesearcher.event.EventBusManager;
import org.miage.placesearcher.event.SearchResultEvent;
import org.miage.placesearcher.model.PlaceSearchResult;

import java.lang.reflect.Modifier;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by alexmorel on 05/01/2018.
 */

public class PlaceSearchService {

    private static final long REFRESH_DELAY = 650;
    public static PlaceSearchService INSTANCE = new PlaceSearchService();
    private final PlaceSearchRESTService mPlaceSearchRESTService;
    private ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture mLastScheduleTask;

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

    public void searchPlacesFromAddress(final String search) {
        // Cancel last scheduled network call (if any)
        if (mLastScheduleTask != null && !mLastScheduleTask.isDone()) {
            mLastScheduleTask.cancel(true);
        }

        // Schedule a network call in REFRESH_DELAY ms
        mLastScheduleTask = mScheduler.schedule(new Runnable() {
            public void run() {
                // Call to the REST service
                mPlaceSearchRESTService.searchForPlaces(search).enqueue(new Callback<PlaceSearchResult>() {
                    @Override
                    public void onResponse(Call<PlaceSearchResult> call, Response<PlaceSearchResult> response) {
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
        }, REFRESH_DELAY, TimeUnit.MILLISECONDS);
    }

    // Service describing the REST APIs
    public interface PlaceSearchRESTService {

        @GET("search/")
        Call<PlaceSearchResult> searchForPlaces(@Query("q") String search);
    }
}
