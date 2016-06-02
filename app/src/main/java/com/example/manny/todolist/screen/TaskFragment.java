package com.example.manny.todolist.screen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.manny.todolist.R;
import com.example.manny.todolist.TaskListFragment;
import com.example.manny.todolist.data.Task;
import com.firebase.client.Firebase;

/**
 * Created by manny on 5/23/16.
 */
public class TaskFragment extends Fragment {

    public static final String TASK_KEY = "task_key";

    private Firebase mFireBaseRef;
    private EditText taskDescription;
    private EditText taskTitle;
    private OnTaskFragmentListener mCallback;

    private Task task;

    public interface OnTaskFragmentListener {

        void onTaskUpdated(Task task);
    }

    public static TaskFragment newInstance(Task task) {
        TaskFragment fragment = new TaskFragment();

        Bundle args = new Bundle();
        args.putParcelable(TASK_KEY, task);
        fragment.setArguments(args);

        return fragment;
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Firebase.setAndroidContext(getActivity());
//
//        mFireBaseRef = new Firebase(TaskListFragment.FIRE_BASE_URL + Task.TASK_ROUTE);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {
            task = (Task) getArguments().getParcelable(TASK_KEY);
            taskDescription.setText(task.getTask());
            taskTitle.setText(task.getTaskTitle());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_fragment, container, false);

        taskDescription = (EditText) view.findViewById(R.id.task_fullDescription);
        taskTitle = (EditText) view.findViewById(R.id.taskName);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnTaskFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnTaskListFragmentlistener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();


        if(task == null || mCallback == null) {
            return;
        }

        if(task.getTaskTitle() == null && task.getTask() == null){
            Log.d("flow", "when empty");
            task.setTaskTitle("No Title");
        }

        task.setTask(taskDescription.getEditableText().toString());
        task.setTaskTitle(taskTitle.getEditableText().toString());
        mCallback.onTaskUpdated(task);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.save_frag_btn:
//                task.setTask(taskDescription.getEditableText().toString());
//                if(mCallback != null) {
//                    mCallback.onTaskUpdated(task);
//                }
//                break;
//        }
//    }
}
