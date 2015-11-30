package com.example.dam.asynctask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.dam.dosactividades.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Bitmap bmp;
    private android.widget.ListView lv;
    private MyAdapter ad;
    private android.widget.ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pb = (ProgressBar) findViewById(R.id.progressBar);
        this.lv = (ListView) findViewById(R.id.lv);
        init();
    }

    public void init() {
        Tarea t = new Tarea();
        t.execute("http://www.istockphoto.com/photos/nature");

    }

    public class Tarea extends AsyncTask<String, Integer, ArrayList<String>> {

        ArrayList<Bitmap> bmps = new ArrayList<>();

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String linea, out = "", img;
                ArrayList<String> imgs = new ArrayList<>();
                int i;
                for (i=0; (linea = in.readLine()) != null; i++) {
                    out += linea + " ";
                    publishProgress(i);
                }
                in.close();
                int pos = 0, pos1, pos2;
                pos = out.indexOf("<img", pos + 1);
                pos1 = out.indexOf("src=\"", pos + 1);
                pos2 = out.indexOf("\"", pos1 + 5);
                img = out.substring(pos1 + 5, pos2);
                while ((pos = out.indexOf("<img", pos + 1)) != -1) {
                    imgs.add(img);
                    pos1 = out.indexOf("src=\"", pos + 1);
                    pos2 = out.indexOf("\"", pos1 + 5);
                    img = out.substring(pos1 + 5, pos2);
                }

                imgs.remove(imgs.size() - 1);
                for (String str : imgs) {
                    try {
                        URL urlimg = new URL(str);
                        bmps.add(BitmapFactory.decodeStream(urlimg.openConnection().getInputStream()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                    i++;
                }


                return imgs;
            } catch (MalformedURLException ex) {
            } catch (IOException ex) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            pb.setProgress(10);
            pb.setMax(2000);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[values.length - 1]);

        }

        @Override
        protected void onPostExecute(ArrayList<String> imgs) {
            pb.setVisibility(View.GONE);
            ad = new MyAdapter(MainActivity.this, imgs);
            lv.setAdapter(ad);
            lv.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(MainActivity.this, ImageActivity.class);
                            i.putExtra("bitmap", bmps.get(position));
                            startActivity(i);
                        }
                    }
            );
        }
    }

}
