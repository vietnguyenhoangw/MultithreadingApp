package com.example.multithrea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button button;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        /* User Theard */
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            /*download in thread*/
//                            URL url = new URL("https://cdn.cnn.com/cnnnext/dam/assets/190821123816-trump-08182019-large-tease.jpg");
//                            final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
//
//                            /*pass image to imageview in activity*/
//                            imageView.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    imageView.setImageBitmap(bitmap);
//                                }
//                            });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                thread.start();
//            }
//        });

        /* use Async Task */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadImage downloadImage = new DownloadImage();
                downloadImage.execute("https://cdn.cnn.com/cnnnext/dam/assets/190821123816-trump-08182019-large-tease.jpg");
            }
        });

        /*use picasso*/
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Picasso.get()
//                        .load("https://cdn.cnn.com/cnnnext/dam/assets/190821123816-trump-08182019-large-tease.jpg")
//                        .into(imageView);
//            }
//        });
    }

    /*
     * Multithreading using AsyncTask
     * */

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /* process dialog show text "downloading..." */
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Downloading...");
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                /*download in thread*/
                URL url = new URL(strings[0]);
                final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /* set image downloaded to image view */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);

            /* delay 5s, to make process dialog run in 5s like downloading images process*/
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }
}
