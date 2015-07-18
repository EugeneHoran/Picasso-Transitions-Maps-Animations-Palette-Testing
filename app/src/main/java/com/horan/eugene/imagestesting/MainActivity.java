package com.horan.eugene.imagestesting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    ImageView img;
    Bitmap bitmap;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Whats Up Palette");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        new LoadImage().execute("http://www.wallpaperwidgets.com/static/images/Blue-Green-beautiful-nature-21891805-1920-1200_qYT8wm6.jpg");
        img = (ImageView) findViewById(R.id.img);
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {
                img.setImageBitmap(image);
                Palette palette = Palette.from(bitmap).generate();
                Palette.Swatch swatch = palette.getVibrantSwatch();
                int bodyTextColor = swatch.getBodyTextColor();
                int titleTextColor = swatch.getTitleTextColor();
                int rgbColor = swatch.getRgb();
                CardView root = (CardView) findViewById(R.id.card);
                TextView text = (TextView) findViewById(R.id.text);
                TextView body = (TextView) findViewById(R.id.body);
                root.setCardBackgroundColor(rgbColor);
                text.setTextColor(bodyTextColor);
                body.setTextColor(titleTextColor);
            } else {
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
