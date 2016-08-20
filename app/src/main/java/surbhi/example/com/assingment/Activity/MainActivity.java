package surbhi.example.com.assingment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import surbhi.example.com.assingment.R;

public class MainActivity extends AppCompatActivity {
    EditText name,password;
    Button login;
   ArrayList<String>repos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name=(EditText)findViewById(R.id.uname);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.btnlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String user="anshugoel51";
                if (name.getText().toString().isEmpty()) {
                    name.setError("Enter user name");
                    name.setFocusable(true);
                }
                else if (password.getText().toString().isEmpty()) {
                    password.setError("Enter password");
                    password.setFocusable(true);
                }
                else {

                    RepositoryService repositoryService = new RepositoryService();
                    //CommitService commitService = new CommitService();

                    try {
                        String userName = name.getText().toString();
                        List<Repository> repostriesList = repositoryService.getRepositories(userName);
                        for (Repository repo : repostriesList) {
                            repos.add(repo.getName());
                            /*List<RepositoryCommit> repoCommits = commitService.getCommits(repo);
                            if(repoCommits != null) {
                                for(RepositoryCommit commit:repoCommits){
                                    String committer = commit.getCommitter().getName();
                                }
                            }
                             */

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    /*repos.add("Anshu");
                    repos.add("Surbhi - project 1");
                    repos.add("Surbhi - project 1");
                    repos.add("Surbhi - project 1");
                    repos.add("Surbhi - project 1");*/


                    Toast.makeText(MainActivity.this, "successful login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, RepositoryActivity.class);
                    i.putStringArrayListExtra("repository", repos);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
