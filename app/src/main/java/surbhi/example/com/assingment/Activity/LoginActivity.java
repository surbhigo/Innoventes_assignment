package surbhi.example.com.assingment.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CollaboratorService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import surbhi.example.com.assingment.R;
import surbhi.example.com.assingment.utils.ProgressDlg;

public class LoginActivity extends AppCompatActivity {
    private EditText name;
    private EditText password;
    private Button login;
    private CheckBox loggedIn;
    private SharedPreferences sharedPreferences;
    //  ProgressDialog progressDialog;
    static final String MyPreference = "my_preference";
    static final String uName = "name_key";
    static final String uPassword = "password_key";
    
    ArrayList<String> repoList = new ArrayList<String>();

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences(MyPreference, Context.MODE_PRIVATE);
        name = (EditText) findViewById(R.id.uname);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnlogin);
        loggedIn = (CheckBox) findViewById(R.id.checkBox);
        name.setText(sharedPreferences.getString(uName, ""));
        password.setText(sharedPreferences.getString(uPassword, ""));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userPassword = password.getText().toString();
                if (loggedIn.isChecked()) {
                    sharedPreferences.edit().putString(uName, userName).apply();
                    sharedPreferences.edit().putString(uPassword, userPassword).apply();
                }

                //login validation
                if (userName.isEmpty()) {
                    name.setError("Enter user name");
                    name.setFocusable(true);
                } else if (userPassword.isEmpty()) {
                    password.setError("Enter password");
                    password.setFocusable(true);
                }

                // Using github repository API's to get repositories list of particular user
                else {
                    new LoginAsync().execute(userName, userPassword);
                }
            }
        });

    }



    private boolean loginValidation(String userName, String userPassword) {
        boolean result=true;

        GitHubClient gitHubClient = new GitHubClient();
        gitHubClient.setCredentials(userName, userPassword);

        //Using collaborator service to number of user working on it
        CollaboratorService collaboratorService = new CollaboratorService(gitHubClient);
        RepositoryService repositoryService = new RepositoryService();
        List<Repository> repositoryList=null;

        try {
            repositoryList= repositoryService.getRepositories(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (repositoryList != null) {
            List<User> collaborator = null;
            try {
                collaborator = collaboratorService.getCollaborators(repositoryList.get(0));

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (collaborator != null)
                result=true;
            else
                result=false;
        }
   return result;
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
    private Activity thisActivity =this;
    class LoginAsync extends AsyncTask<String,Void,ArrayList<String>>{
        String userName;
        String userPassword;
        @Override
        protected void onPreExecute() {
            ProgressDlg.showProgressDialog(thisActivity,"please wait");

            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            RepositoryService repositoryService = new RepositoryService();
            userName = params[0];
            userPassword = params[1];

            try {
                List<Repository> repositoriesList = repositoryService.getRepositories(params[0]);
                for (Repository repo : repositoriesList) {
                    repoList.add(repo.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return repoList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> repoList) {
            super.onPostExecute(repoList);
           ProgressDlg.hideProgressDialog();
            boolean result=loginValidation(userName, userPassword);
            if(result==true) {
                Toast.makeText(LoginActivity.this, "successful login", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, RepositoryActivity.class);
                i.putStringArrayListExtra("repository", repoList);
                i.putExtra("username", userName);
                i.putExtra("password", userPassword);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, "incorrect username and password", Toast.LENGTH_SHORT).show();
        }
    }
}
