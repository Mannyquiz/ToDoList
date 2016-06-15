package com.example.manny.todolist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.manny.todolist.data.CheckList;
import com.example.manny.todolist.data.Task;
import com.example.manny.todolist.screen.TaskFragment;
import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TaskListFragment.OnTaskListFragmentListener, TaskFragment.OnTaskFragmentListener, CheckBoxListFragment.OnCheckBoxListFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.container_layout) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TaskListFragment taskListFragment = new TaskListFragment();
            CheckBoxListFragment checkBoxListFragment = new CheckBoxListFragment();

            //Add fragment to container layout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_layout, taskListFragment).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.container_layout, checkBoxListFragment).commit();
        }
    }

    public void replaceFragment(Fragment newFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, newFragment).addToBackStack(null).commit();
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

    @Override
    public void onTaskSelected(Task task) {
        replaceFragment(TaskFragment.newInstance(task));
    }


    @Override
    public void onCheckBoxSelected(CheckList checkList) {

    }

    @Override
    public void onTaskUpdated(Task task) {
        Log.d("flow", "Task updated");
        //save task
        Map<String, Object> nickname = new HashMap<>();

        nickname.put("taskTitle", task.getTaskTitle());
        nickname.put("task", task.getTask());

        Firebase myFirebaseRef = new Firebase(TaskListFragment.FIRE_BASE_URL + Task.TASK_ROUTE).child(task.getTaskId());
        myFirebaseRef.updateChildren(nickname);
    }
}
