package edu.coloradomesa.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.net.Socket;
import java.net.URL;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;
import java.util.Vector;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    Intent LoginIntent;

    Vector<String>ProjectLabels;
    Vector<String>ProjectSubLabels;
    Vector<CT1> projects;
    Vector<String>TaskLabels;

    String state;// = "Projects";
    double pageNumber;
    boolean changePage;// = false;
    int currentCT1;
    int currentTask;
    String username;

    ToggleButton changeColorsButton;

    TextView titleLabel;// = (TextView) findViewById(R.id.helloText);
    Button mAddProjectButton;// = (Button) findViewById(R.id.add_project_button);
    EditText addProjectTextbox;
    Button backButton;

    ListView projectList;// = (ListView) findViewById(R.id.projectList);
    ArrayAdapter<String> projectAdapter;
    void initialize()
    {
        projects = new Vector<CT1>();
        state = "Projects";
        pageNumber = 0; //viewing projects main page
        changePage = false;
        titleLabel = (TextView) findViewById(R.id.title_line);
        mAddProjectButton = (Button) findViewById(R.id.add_project_button);
        backButton = (Button) findViewById(R.id.back_button);
        addProjectTextbox = (EditText) findViewById(R.id.project_add);
        projectList = (ListView) findViewById(R.id.projectList);

        ProjectLabels = new Vector<String>();
        ProjectSubLabels = new Vector<String>();
        TaskLabels = new Vector<String>();

        changeColorsButton = (ToggleButton) findViewById(R.id.toggleButton);

        projectAdapter = new ArrayAdapter<String>(this, R.layout.listview, R.id.textView, ProjectLabels);
        projectList.setAdapter(projectAdapter);

        username = "";

        ChangePage();
    }
    void ChangePage() {
        //Resetting Look
        mAddProjectButton.setVisibility(View.INVISIBLE);
        addProjectTextbox.setVisibility(View.INVISIBLE);
        projectList.setVisibility(View.INVISIBLE);
        changeColorsButton.setVisibility((View.INVISIBLE));
        backButton.setVisibility(View.INVISIBLE);

        //Updating Labels
        if (pageNumber == 0) {
            setSubLabels();
            CustomList newCustomList = new CustomList(MainActivity.this, ProjectLabels , ProjectSubLabels);
            projectList.setAdapter(newCustomList);
        }
        else if (pageNumber == 1){
            TaskLabels = new Vector<String>();
            for(int i = 0; i < projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.size(); i++){
                TaskLabels.add(projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.elementAt(i).name);
            }
            setSubLabels();
            CustomList newCustomList = new CustomList(MainActivity.this, TaskLabels , ProjectSubLabels);
            projectList.setAdapter(newCustomList);
        }




        if (state.startsWith("Project")) { //Projects Page
            if (pageNumber == 0) {
                mAddProjectButton.setVisibility(View.VISIBLE);
                mAddProjectButton.setText("Add Project");
                titleLabel.setText("Viewing Projects " + projects.size());
                projectList.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
            else if (pageNumber == 0.1){
                mAddProjectButton.setVisibility(View.VISIBLE);
                titleLabel.setText("What would you like to name this project?");
                addProjectTextbox.setText("");
                addProjectTextbox.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
            else if (pageNumber == 1){
                mAddProjectButton.setVisibility(View.VISIBLE);
                mAddProjectButton.setText("Add Task");
                titleLabel.setText("Viewing Tasks");
                projectList.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }
            else if (pageNumber == 1.1){
                mAddProjectButton.setVisibility(View.VISIBLE);
                titleLabel.setText("What would you like to name this task?");
                addProjectTextbox.setText("");
                addProjectTextbox.setVisibility(View.VISIBLE);
                backButton.setVisibility(View.VISIBLE);
            }

            else if (pageNumber == 2) {
                mAddProjectButton.setVisibility(View.VISIBLE);
                String label = "Viewing " + projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.elementAt(currentTask).name
                        + "\n";
                if (projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.elementAt(currentTask).done) label += "Completed Task";
                else label += "Incomplete Task";
                titleLabel.setText(label);
                mAddProjectButton.setText("Complete Task");
                backButton.setVisibility(View.VISIBLE);
            }
        } else if (state.startsWith("Group")) {
            titleLabel.setText("Viewing Group");
        } else if (state.startsWith("Settings")) {
            titleLabel.setText("Inverse Colors");
            changeColorsButton.setVisibility(View.VISIBLE);
        }
        else titleLabel.setText("No State Found: " + '"' + state + '"');
    }

    void setSubLabels(){
        if (pageNumber == 0) {
            ProjectSubLabels = new Vector<String>();
            for (int i = 0; i < projects.size(); i++)
            {
                String label = "Total Tasks: ";
                label += projects.elementAt(i).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.size();
                ProjectSubLabels.add(label);
            }
        }

        else if (pageNumber == 1) {
            ProjectSubLabels = new Vector<String>();
            for (int i = 0; i < projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.size(); i++)
            {
                String Label = "";
                if (projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.elementAt(i).done) Label = "Complete";
                else Label = " NOT Complete";
                ProjectSubLabels.add(Label);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String dataString = (data.getStringExtra("valueName"));
                System.out.println("Parsing " + dataString);
                int i = 8;
                int currentCT1 = -1;
                String command = "";
                String name = "";
                String user = "";
                while(dataString.charAt(i) != '|')
                {
                    user += dataString.charAt(i);
                    i++;
                }
                i+=2;
                username = user;
                user = "";

                while(i < dataString.length() && dataString.charAt(i) != '#')
                {
                    while(dataString.charAt(i) != '@')
                    {
                        command += dataString.charAt(i);
                        i+=1;
                    }
                    i+=1;
                    while(dataString.charAt(i) != '|')
                    {
                        name += dataString.charAt(i);
                        i+=1;
                    }
                    i+=2;

                    System.out.println("command: '" + command + "' name " + name);

                    if (command.equals("P1"))
                    {
                        System.out.println("Adding Project: " + name);
                        currentCT1+=1;
                        CT1 tempProject = new CT1(name);
                        tempProject.ProjectName = name;
                        CT2 tempct2 = new CT2(false);
                        tempProject.CT2s.add(tempct2);
                        CT3 tempct3 = new CT3(false);
                        tempProject.CT2s.elementAt(0).CT3s.add(tempct3);
                        CT4 tempct4 = new CT4(true);
                        tempProject.CT2s.elementAt(0).CT3s.elementAt(0).CT4s.add(tempct4);

                        projects.add(tempProject);
                        System.out.println("size: " + projects.size());

                        int temp = 0;
                        String tempName = "";
                        while (name.charAt(temp) != '_')
                        {
                            tempName += name.charAt(temp);
                            temp++;
                        }

                        ProjectLabels.add(tempName);
                        ChangePage();

                    }

                    else if (command.equals("T"))
                    {
                        System.out.println("Adding Task: " + name);

                        int temp = 0;
                        String tempName = "";
                        while (name.charAt(temp) != '_')
                        {
                            tempName += name.charAt(temp);
                            temp++;
                        }

                        Task tempTask = new Task(tempName);
                        //System.out.println("Done? " + name.charAt(name.length()-1));
                        if (name.charAt(name.length()-1) == '0') tempTask.done = true;
                        projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.add(tempTask);
                    }

                    command = "";
                    name = "";
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        //final int i = 0;
        //final TextView label = (TextView) findViewById(R.id.helloText);

        LoginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(LoginIntent, 0);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                state = item.toString();
                //state = item.getItemId() - 2131558571;
                System.out.println("State = " + state);
                ChangePage();
                return true;
            }
        });

        mAddProjectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNumber == 0) pageNumber = 0.1;
                else if (pageNumber == 0.1) {
                    System.out.println("Adding Project " + addProjectTextbox.getText());
                    CT1 tempProject = new CT1(addProjectTextbox.getText().toString());
                    tempProject.ProjectLevel = 1;
                    CT2 tempCT2 = new CT2(false);
                    tempProject.CT2s.add(tempCT2);
                    CT3 tempCT3 = new CT3(false);
                    tempProject.CT2s.elementAt(0).CT3s.add(tempCT3);
                    CT4 tempCT4 = new CT4(true);
                    tempProject.CT2s.elementAt(0).CT3s.elementAt(0).CT4s.add(tempCT4);
                    projects.add(tempProject);

                    ProjectLabels.add(addProjectTextbox.getText().toString());
                    pageNumber = 0;

                    String[] command = new String[3];
                    command[0] = "ChangeProjects|Add|" + username + "|@P1@" + addProjectTextbox.getText() + "|";
                    command[1] = "";
                    new Connect().execute(command);
                }
                else if (pageNumber == 1){
                    pageNumber = 1.1;
                }
                else if (pageNumber == 1.1){
                    System.out.println("Adding Task");
                    Task tempTask = new Task(addProjectTextbox.getText().toString());
                    projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.add(tempTask);

                    pageNumber = 1;

                    String[] command = new String[3];
                    command[0] = "ChangeProjects|Add|" + username + "|@T@" + currentCT1 + "000|" + addProjectTextbox.getText() + "|";
                    command[1] = "";
                    new Connect().execute(command);
                }

                else if (pageNumber == 2){
                    projects.elementAt(currentCT1).CT2s.elementAt(0).CT3s.elementAt(0).CT4s.elementAt(0).Tasks.elementAt(currentTask).DoTask();

                    String[] command = new String[3];
                    command[0] = "ChangeProjects|Add|" + username + "|@P1@" + addProjectTextbox.getText() + "|";
                    command[1] = "";
                    new Connect().execute(command);

                    ChangePage();
                }
                ChangePage();
            }
        });

        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageNumber == 0.1 || pageNumber == 1) pageNumber = 0;
                else if (pageNumber == 1.1) pageNumber = 1;
                else if (pageNumber == 2) pageNumber = 1;
                ChangePage();
            }
        });

        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() { //view a Project
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pageNumber == 0) {
                    currentCT1 = i;
                    pageNumber = 1;
                    ChangePage();
                }

                else if (pageNumber == 1) {
                    currentTask = i;
                    pageNumber = 2;
                    ChangePage();
                }
            }
        });

        projectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("Trying to delete a task");
                return false;
            }
        });

        changeColorsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Toggling Colors");
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        System.out.println("Resuming main activity");
        onActivityResult(0,0,LoginIntent);
        setSubLabels();
    }
}

