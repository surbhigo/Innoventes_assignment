package surbhi.example.com.assingment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.service.CommitService;

import java.util.List;

import surbhi.example.com.assingment.Activity.CommitActivity;
import surbhi.example.com.assingment.R;
import surbhi.example.com.assingment.utils.Config;

/**
 * Created by S on 8/16/2016.
 */
public class Radapter extends RecyclerView.Adapter<Radapter.CustomViewHolder> {
    List<String> list;
    private String repoName;
    private Context context;

    public Radapter(List<String> list, Context context){
        this.list=list;
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
      //  ArrayList<String>list=new ArrayList<>();

        repoName = list.get(position);
        renderRow(holder);
    }


    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        return 0;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView title,description;

        public CustomViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            description=(TextView)itemView.findViewById(R.id.description);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            CommitService commitService=new CommitService();
            List<RepositoryCommit> repoCommits = commitService.getCommits();
            if(repoCommits != null) {

            }
            Intent intent = new Intent(context, CommitActivity.class);
            intent.putExtra(Config.REPOSITORY_URL, "<repo_url>");
            context.startActivity(intent);
        }
    };

    private void renderRow(CustomViewHolder holder) {
        holder.description.setText(repoName);
        holder.title.setText(repoName);
        holder.description.setTag(holder);
        holder.description.setOnClickListener(clickListener);
    }
}
