package com.gong.android.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gong.android.demo.R;

public class MainActivity extends AppCompatActivity {

    private Button btn_intent_camera;
    private Button btn_view_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_intent_camera = (Button) findViewById(R.id.btn_intent_camera);
        btn_intent_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CameraIntentActivity.class);
                startActivity(intent);
            }
        });

        btn_view_circle = (Button) findViewById(R.id.btn_view_circle);
        btn_view_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CircleDrawableActivity.class);
                startActivity(intent);
            }
        });
    }
}
