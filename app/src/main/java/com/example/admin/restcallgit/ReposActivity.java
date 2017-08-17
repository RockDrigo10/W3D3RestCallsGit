package com.example.admin.restcallgit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.restcallgit.GitRepos.Repo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReposActivity extends AppCompatActivity {
    Repo repo;
    String responseResult = " ";
    RecyclerView rvRepos;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.ItemAnimator itemAnimator;
    public static final String BASE_URL = "https://api.github.com/users/RockDrigo10/repos";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        setTitle("Repos");
        final Intent intent = getIntent();
        rvRepos = (RecyclerView) findViewById(R.id.rvRepos);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        itemAnimator = new DefaultItemAnimator();
        rvRepos.setLayoutManager(layoutManager);
        rvRepos.setItemAnimator(itemAnimator);
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
                //repo = gson.fromJson(responseResult, Repo.class);
                Type listType = new TypeToken<ArrayList<Repo>>(){}.getType();
                final List<Repo> reposList = new Gson().fromJson(responseResult, listType);
                ReposActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RepoAdapter repoAdapter = new RepoAdapter((ArrayList<Repo>) reposList);
                        rvRepos.setAdapter(repoAdapter);
                        repoAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("myState", layoutManager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        layoutManager.onRestoreInstanceState(savedInstanceState.getParcelable("myState"));
    }
}
