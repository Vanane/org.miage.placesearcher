package org.miage.placesearcher.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// String Adapter : convert string list into Items
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private List<String> mStrings;

    public StringAdapter(Context context, List<String> strings) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mStrings = strings;
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        StringViewHolder holder = new StringViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        // Adapt the ViewHolder state to the new element
        holder.text1.setText(mStrings.get(position));
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    // Pattern ViewHolder :
    class StringViewHolder extends RecyclerView.ViewHolder
    {
        TextView text1;

        public StringViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }
    }

}