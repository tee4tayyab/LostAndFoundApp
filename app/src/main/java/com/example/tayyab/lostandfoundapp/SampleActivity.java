/*package com.example.tayyab.lostandfoundapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tayyab.lostandfoundapp.models.SampleModel;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    ArrayList<SampleModel> SampleListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_recycler_layout_activity);


        // DatSource


        SampleListData = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            SampleListData.add(new SampleModel(i, "username " + i, "description http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg" + i, true, "http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg", "http://badboyroy.com/wp-content/uploads/2014/09/image-placeholder-940x470-940x470.jpg"));
        }


        RecyclerView recyclerLostFound = findViewById(R.id.recyclerLostFound);

        recyclerLostFound.setAdapter(new SampleAdapter(SampleListData));
       // recyclerLostFound.setLayoutManager(RecyclerView.LayoutManager);



    }
}*/
