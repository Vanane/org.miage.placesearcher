package org.miage.placesearcher.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.miage.placesearcher.R;
import org.miage.placesearcher.model.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexmorel on 04/01/2018.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Place> mPlaces;

    public PlaceAdapter(Context context, List<Place> Places) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mPlaces = Places;
    }

    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.place_item, parent, false);
        PlaceAdapter.PlaceViewHolder holder = new PlaceAdapter.PlaceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.PlaceViewHolder holder, int position) {
        // Adapt the ViewHolder state to the new element
        holder.mPlaceStreetTextView.setText(mPlaces.get(position).getStreet());
        holder.mPlaceZipTextView.setText(mPlaces.get(position).getZipCode());
        holder.mPlaceCityTextView.setText(mPlaces.get(position).getCity());
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    // Pattern ViewHolder
    class PlaceViewHolder extends RecyclerView.ViewHolder
    {

        @BindView(R.id.place_adapter_street)
        TextView mPlaceStreetTextView;

        @BindView(R.id.place_adapter_zip)
        TextView mPlaceZipTextView;

        @BindView(R.id.place_adapter_city)
        TextView mPlaceCityTextView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}