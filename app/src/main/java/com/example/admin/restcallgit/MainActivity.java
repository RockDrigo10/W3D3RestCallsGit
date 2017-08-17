package com.example.admin.restcallgit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.restcallgit.model.ProfileGit;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ProfileGit profileGit;
    String responseResult = " ";
    @BindView(R.id.tvProfile)
    TextView tvProfile;
    @BindView(R.id.tvUser)
    TextView tvUser;
    @BindView(R.id.tvCreated)
    TextView tvCreated;
    @BindView(R.id.tvGitHub)
    TextView tvGitHub;
    @BindView(R.id.ivImage)
    ImageView ivImage;
    String img;
    Bitmap bitmap;
    public static final String BASE_URL = "https://api.github.com/users/RockDrigo10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Profile");
        ButterKnife.bind(this);
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(BASE_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseResult = response.body().string();
                final Gson gson = new Gson();
                profileGit = gson.fromJson(responseResult, ProfileGit.class);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvProfile.setText(profileGit.getName());
                        tvUser.setText(profileGit.getLogin());
                        tvCreated.setText(String.valueOf(profileGit.getCreatedAt()));
                        tvGitHub.setText(profileGit.getHtmlUrl());
                        img = profileGit.getAvatarUrl();
                        Log.d(TAG, "onResponse: " + profileGit.getName()
                                + " " + profileGit.getAvatarUrl()
                                + " " + profileGit.getFollowers()
                                + " " + profileGit.getLogin()
                                + " " + profileGit.getHtmlUrl());
                    }
                });
            }
        });
        getImageByUrl(img);

    }
    public void getImageByUrl(String url){
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
            ivImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToRepos(View view) {
        Intent intent = new Intent(this, ReposActivity.class);
        startActivity(intent);
    }
}
