package surbhi.example.com.assingment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import surbhi.example.com.assingment.Adapter.Radapter;
import surbhi.example.com.assingment.Model.RepositoryList;
import surbhi.example.com.assingment.R;

public class RepositoryActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<RepositoryList> list;
    ArrayList<String>repos=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reositority);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        repos=getIntent().getStringArrayListExtra("repository");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Radapter(repos,this);
        //adapter=new Radapter(getData(),this);
        recyclerView.setAdapter(adapter);

    }


        public ArrayList<RepositoryList> getData() {
            list = new ArrayList<>();

            RepositoryList list1 =new RepositoryList();


            list1.setTitle("Github login integration");
            list1.setDescription("details of repository ");
            list.add(list1);
            return list;
        }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}


