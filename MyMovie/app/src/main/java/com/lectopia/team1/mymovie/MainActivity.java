package com.lectopia.team1.mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvDirector, tvGenre, tvActors, tvReleased, tvPlot;
    Button btnSearch;
    ImageView ivPoster;
    EditText etTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDirector = (TextView) findViewById(R.id.tvDirector);
        tvGenre = (TextView) findViewById(R.id.tvGenre);
        tvActors = (TextView) findViewById(R.id.tvActor);
        tvReleased = (TextView) findViewById(R.id.tvRelease);
        tvPlot = (TextView) findViewById(R.id.tvPlot);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        etTitle = (EditText) findViewById(R.id.etTitle);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTitle.getText() != null && !etTitle.getText().toString().isEmpty()) {
                    // MysearchTask.execute
                    String str = etTitle.getText().toString();
                    MySearchTask task = new MySearchTask();
                    task.execute(str);
                }
                else {
                    Toast.makeText(getApplicationContext(), "타이틀을 입력하세요", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class MySearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String title = strings[0];
            String str = "http://www.omdbapi.com/?apikey=93f1ed5b&t="+title;

            try {
                URL url = new URL(str);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(20000);
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);

                int responseCode = con.getResponseCode();
                StringBuilder sb = new StringBuilder();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                } else {
                    sb.append("Connection failed");
                }
                con.disconnect();
                return sb.toString();

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jobj = new JSONObject(result);
                String director = jobj.getString("Director");
                String genre = jobj.getString("Genre");
                String released = jobj.getString("Released");
                String actors = jobj.getString("Actors");
                String plot = jobj.getString("Plot");
                String poster = jobj.getString("Poster");

                tvDirector.setText("감독: " + director);
                tvGenre.setText("장르: " + genre);
                tvReleased.setText("개봉일: " + released);
                tvActors.setText("주연배우: " +actors);
                tvPlot.setText(plot);

                ImageTask it = new ImageTask();
                it.execute(poster);

            }
            catch(JSONException e) {
                e.printStackTrace();
            }

        }
    }

    class ImageTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {

            try {
                URL imageUrl = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setConnectTimeout(20000);
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                ivPoster.setImageBitmap(bitmap);
                conn.disconnect();

            }catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
