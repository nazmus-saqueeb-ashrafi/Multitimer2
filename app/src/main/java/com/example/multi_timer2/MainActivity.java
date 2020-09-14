
package com.example.multi_timer2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartTimerClick(View view){
        Intent getRunScreenIntent = new Intent(this,
                RunScreen.class);

        final int result =1;

        startActivity(getRunScreenIntent);

    }


}










