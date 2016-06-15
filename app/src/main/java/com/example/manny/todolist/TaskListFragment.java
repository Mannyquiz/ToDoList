package com.example.manny.todolist;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.manny.todolist.data.CheckList;
import com.example.manny.todolist.data.Task;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskListFragment extends Fragment {

    public static final String FIRE_BASE_URL = "https://scorching-inferno-4075.firebaseio.com/";

    private OnTaskListFragmentListener mCallBack;

    private Firebase myFireBase;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ImageButton fab;

    public TaskListFragment() {

    }

    // Container Activity must implement this interface
    public interface OnTaskListFragmentListener{
        void onTaskSelected(Task task);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallBack = (OnTaskListFragmentListener) getActivity();
            TaskAdapter.ViewHolder.setOnTaskListFragmentListener(mCallBack);
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement OnTaskListFragmentlistener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        myFireBase = new Firebase(FIRE_BASE_URL + Task.TASK_ROUTE);
        //myFireBase.addChildEventListener(new TasksChildEventListener());

    }

//    class TasksChildEventListener implements ChildEventListener{
//
//        @Override
//        public void onChildRemoved(DataSnapshot dataSnapshot) {
//            String taskId = dataSnapshot.getKey();
//            ArrayList<Task> myTasks = new ArrayList<>();
//            for (Task tk : myTasks){
//                if(taskId.equals(tk.getTaskId())){
//                    myTasks.remove(tk);
//                    removeTask(tk);
//                    Log.d("flow", "went threw......" );
//                    break;
//                }
//            }
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.toDoListRecycleView);

        fab = (ImageButton) layout.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("flow", "ADD BUTTON CLICKED");
                addTask();
            }
        });

        return layout;

    }

    @Override
    public void onResume() {
        super.onResume();
        myFireBase.addValueEventListener(valueEventListener);
        /*
         //load tasks again if there is no adapter set on recyclerview
        if(recyclerView.getAdapter() == null) {
            if(TaskAdapter.tasks != null) {
                loadTasks(TaskAdapter.tasks);
            }
        }
         */
    }

    private void loadTask(ArrayList<Task> tasks){

        if(tasks == null) {
            return;
        }

        adapter = new TaskAdapter(tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    private void addTask(){

        Task task = new Task();
        saveTask(task);

        mCallBack.onTaskSelected(task);

//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Task");
//        //builder.setMessage("What do you?");
//
//        final EditText inputField = new EditText(getActivity());
//
//        builder.setView(inputField);
//
//        builder.setNegativeButton("Cancel", null);
//        builder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (inputField.getText().toString().trim().length() == 0){
//
//                    inputField.setText("No Title");
//                    Log.d("flow", "No title added to task");
//                    saveTask(new Task(inputField.getText().toString(), inputField.getText().toString()));
//
//                }else {
//                    saveTask(new Task(inputField.getText().toString(), inputField.getText().toString()));
//                }
//            }
//        });
//        builder.create().show();
    }

    private void saveTask(Task task){
        /*
         Previously written
        myFireBase.push().setValue(task);
         */

        Log.d("flow", "from saveTask value of task: " + task.getTaskTitle());

        Firebase newRef = myFireBase.push();

        String taskId = newRef.getKey();
        task.setTaskId(taskId);
        newRef.setValue(task);
    }

    private void removeTask(Task task){

        myFireBase.child(task.getTaskId()).removeValue();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<Task> tasks = new ArrayList<>();

            for (DataSnapshot taskSnapshot: dataSnapshot.getChildren()) {
                Task task = taskSnapshot.getValue(Task.class);
                tasks.add(task);
                System.out.println(task.getTaskTitle() + " - " + task.getTask());
            }
            loadTask(tasks);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.d("flow", "onCancelled");
        }
    };

}
