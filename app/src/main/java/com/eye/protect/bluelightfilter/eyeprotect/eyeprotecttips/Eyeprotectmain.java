package com.eye.protect.bluelightfilter.eyeprotect.eyeprotecttips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eye.protect.bluelightfilter.eyeprotect.R;
import com.eye.protect.bluelightfilter.eyeprotect.eyeprotecttips.adopter.MyMovieAdapter;
import com.eye.protect.bluelightfilter.eyeprotect.eyeprotecttips.adopter.MyMovieData;

public class Eyeprotectmain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyeprotectmain);

        // implementing recyclr view here
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyMovieData[] myMovieData = new MyMovieData[]{
                new MyMovieData("Avengers","2019 film",R.drawable.navig),
                new MyMovieData("Venom","2018 film",R.drawable.navig),
                new MyMovieData("Batman Begins","2005 film",R.drawable.navig),
                new MyMovieData("Jumanji","2019 film",R.drawable.navig),
                new MyMovieData("Good Deeds","2012 film",R.drawable.navig),
                new MyMovieData("Hulk","2003 film",R.drawable.navig),
                new MyMovieData("Avatar","2009 film",R.drawable.navig),
        };

        MyMovieAdapter myMovieAdapter = new MyMovieAdapter(myMovieData,Eyeprotectmain.this);
        recyclerView.setAdapter(myMovieAdapter);

    }
}