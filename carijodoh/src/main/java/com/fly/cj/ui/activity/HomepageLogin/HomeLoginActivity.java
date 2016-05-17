package com.fly.cj.ui.activity.HomepageLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.GridView;

import com.fly.cj.R;

public class HomeLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelogin_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){

                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);

                // Pass image index
                i.putExtra("id", position);
                startActivity(i);
            }
        });*/
    }
}

