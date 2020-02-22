package com.neighborhood.icescreamhorror.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.neighborhood.icescreamhorror.guide.R;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
    }
}
