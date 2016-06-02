package com.example.manny.todolist.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by manny on 5/17/16.
 */
public class Task implements Parcelable {

    public static final String TASK_ROUTE = "tasks/";
    private String task;
    private String taskTitle;
    private String taskId;
    private ArrayList<Task> tasks;

    public Task(){}

    public Task(String task, String taskTitle, String taskId){
        this.task = task;
        this.taskTitle = taskTitle;
        this.taskId = taskId;
    }

    public Task (String task, String taskTitle) {
        this(task, taskTitle, "");
    }

    public String getTask() {
        return task;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    protected Task(Parcel in){
        taskTitle = in.readString();
        task = in.readString();
        taskId = in.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskTitle);
        dest.writeString(task);
        dest.writeString(taskId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
