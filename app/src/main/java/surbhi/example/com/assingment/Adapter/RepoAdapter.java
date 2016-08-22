package surbhi.example.com.assingment.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryCommit;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CollaboratorService;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import surbhi.example.com.assingment.Activity.CommitActivity;
import surbhi.example.com.assingment.R;
import surbhi.example.com.assingment.utils.ProgressDlg;

/**
 * Created by S on 8/16/2016.
 */
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.CustomViewHolder> {
    List<String> repoList;
    private  String userName;
    private String repoName;
    private Context context;
    private String userpassword;

    public RepoAdapter(List<String> repoList, String password, String userName, Context context){
        this.repoList =repoList;
        this.userName=userName;
        this.userpassword =password;
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        repoName = repoList.get(position);
        renderRow(holder);
    }


    @Override
    public int getItemCount() {
        if (repoList != null)
            return repoList.size();
        return 0;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView description;

        public CustomViewHolder(View itemView) {
            super(itemView);
            description=(TextView)itemView.findViewById(R.id.repoName);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            CustomViewHolder customViewHolder = (CustomViewHolder)view.getTag();
            String selectedReops= customViewHolder.description.getText().toString();
           new RepoAsync().execute(selectedReops);
        }
    };

    private void renderRow(CustomViewHolder holder) {
        holder.description.setText(repoName);
        holder.description.setTag(holder);
        holder.description.setOnClickListener(clickListener);
    }

  class RepoAsync extends AsyncTask<String ,Void,ArrayList<Integer>>{
      @Override
      protected void onPreExecute() {
          ProgressDlg.showProgressDialog(context,"please wait");
          super.onPreExecute();
      }

      @Override
      protected ArrayList<Integer> doInBackground(String ... params) {
          ArrayList<Integer> size = new ArrayList<Integer>();
          int commitSize=0;
          int collaboratorSize=0;
          String selectedReops=params[0];

          GitHubClient gitHubClient=new GitHubClient();
          gitHubClient.setCredentials(userName, userpassword);

          //Using collaborator service to get number of user working on it
          CollaboratorService collaboratorService=new CollaboratorService(gitHubClient);

          //Using commit service to get commit commitSize
          CommitService commitService=new CommitService(gitHubClient);

          RepositoryService repositoryService = new RepositoryService();
          Repository repository = null;
          try {
              repository = repositoryService.getRepository(userName, selectedReops);
          } catch (IOException e) {
              e.printStackTrace();
          }
          if(repository != null) {
              List<RepositoryCommit> repoCommits = null;
              List<User> collaborator=null;
              try {
                  repoCommits = commitService.getCommits(repository);
                  collaborator=collaboratorService.getCollaborators(repository);

              } catch (IOException e) {
                  e.printStackTrace();
              }
              if(repoCommits != null) {
                  commitSize=repoCommits.size();
              }
              if(collaborator!=null){
                  collaboratorSize=collaborator.size();
              }
          }
          size.add(commitSize);
          size.add(collaboratorSize);

          return size;
      }

      @Override
      protected void onPostExecute(ArrayList<Integer> list) {
          Intent intent = new Intent(context, CommitActivity.class);
          intent.putExtra("commitSize",list.get(0));
          intent.putExtra("collaboratorSize",list.get(1));
          context.startActivity(intent);
          ProgressDlg.hideProgressDialog();
          super.onPostExecute(list);
      }
  }
}
