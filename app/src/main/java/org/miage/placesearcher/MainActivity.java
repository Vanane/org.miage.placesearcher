package org.miage.placesearcher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.miage.placesearcher.model.Person;
import org.miage.placesearcher.ui.PersonAdapter;

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
        List<Person> listItems = new ArrayList<Person>();
        for (int i = 0; i < 50; i ++) {
            listItems.add(new Person("FirstName" + i, "LastName" + i));
        }
        // Instanciate a PersonAdapter
        PersonAdapter adapter = new PersonAdapter(this, listItems);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        // Do NOT forget to call super.onResume()
        super.onResume();
    }
}
