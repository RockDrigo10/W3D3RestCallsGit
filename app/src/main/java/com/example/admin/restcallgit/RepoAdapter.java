package com.example.admin.restcallgit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.restcallgit.GitRepos.Repo;

import java.util.ArrayList;


public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    ArrayList<Repo> repoList = new ArrayList<>();
    Context context;

    public RepoAdapter(ArrayList<Repo> repoList) {
        this.repoList = repoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRepoName,tvRepoDescription,tvRepoURl,tvRepoCreated;
         public ViewHolder(View itemView) {
            super(itemView);
             tvRepoName = (TextView) itemView.findViewById(R.id.tvRepoName);
             tvRepoDescription = (TextView) itemView.findViewById(R.id.tvRepoDescription);
             tvRepoURl = (TextView) itemView.findViewById(R.id.tvRepoURl);
             tvRepoCreated = (TextView) itemView.findViewById(R.id.tvRepoCreated);

        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Repo repo = repoList.get(position);
        holder.tvRepoName.setText(repo.getName());
        holder.tvRepoDescription.setText(repo.getDescription());
        holder.tvRepoURl.setText(repo.getHtmlUrl());
        holder.tvRepoCreated.setText(repo.getCreatedAt());

    }

     @Override
    public int getItemCount() {
        return repoList.size();
    }

}
