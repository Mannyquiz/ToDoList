package com.example.manny.todolist.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manny on 6/2/16.
 */
public class CheckList implements Parcelable {

    public static final String LIST_ROUTE = "lists/";

    private String listTitle;
    private String list;
    private String listId;

    public CheckList(){}

    public CheckList(String listTitle, String list, String listId){
        this.listTitle = listTitle;
        this.list = list;
        this.listId = listId;
    }

    public CheckList(String list, String listTitle){
        this(list, listTitle, "");
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    protected CheckList(Parcel in){
        listTitle = in.readString();
        list = in.readString();
        listId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listTitle);
        dest.writeString(list);
        dest.writeString(listId);
    }

    public static final Parcelable.Creator<CheckList> CREATOR = new Parcelable.Creator<CheckList>(){
        @Override
        public CheckList createFromParcel(Parcel in) {
            return new CheckList(in);
        }

        @Override
        public CheckList[] newArray(int size) {
            return new CheckList[size];
        }
    };
}