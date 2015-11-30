package com.example.dam.asynctask;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.dosactividades.R;

public class ImageActivity extends AppCompatActivity {

    private android.widget.ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        this.iv = (ImageView) findViewById(R.id.iv);

        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        iv.setImageBitmap(bitmap);
    }
}
