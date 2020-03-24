package com.example.voting.Ui.Activities.Election;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.voting.ListAdapter;
import com.example.voting.R;

public class ElectionActivity extends AppCompatActivity {
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.foure, R.drawable.five, R.drawable.six, R.drawable.seven};

    String[] version = {"Android Alpha", "Android Beta", "Android Cupcake", "Android Donut", "Android Eclair", "Android Froyo", "Android Nougat"};

    String[] versionNumber = {"1.0", "1.1", "1.5", "1.6", "2.0", "2.2", "2.3"};
    ListView lView;

    ListAdapter lAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election);
        lView = (ListView) findViewById(R.id.androidList);

        lAdapter = new ListAdapter(ElectionActivity.this, version, versionNumber, images);
        lView.setAdapter(lAdapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ElectionActivity.this, version[i]+" "+versionNumber[i], Toast.LENGTH_SHORT).show();

            }
        });

    }

    }



