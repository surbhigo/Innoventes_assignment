package surbhi.example.com.assingment.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import surbhi.example.com.assingment.R;

public class CommitActivity extends AppCompatActivity {
    private TextView txtCommitSize;
    private TextView txtCollaboratorSize;
    int commitSize;
    int collaboratorSize;
    ArrayList<String> reposList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //Get commit size and collaborator size
        reposList = getIntent().getStringArrayListExtra("repository");
        collaboratorSize=getIntent().getIntExtra("collaboratorSize",0);
        commitSize=getIntent().getIntExtra("commitSize",0);
        txtCommitSize=(TextView)findViewById(R.id.textsize);
        txtCollaboratorSize=(TextView)findViewById(R.id.collaboratesize);
        txtCollaboratorSize.setText(Integer.toString(collaboratorSize));
        txtCommitSize.setText(Integer.toString(commitSize));
    }

    //going back to repository activty
    @Override
    public void onBackPressed() {
        this.finish();
    }

}
