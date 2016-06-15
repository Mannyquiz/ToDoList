package com.example.manny.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manny.todolist.data.CheckList;

import java.util.ArrayList;

/**
 * Created by manny on 6/5/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    public static ArrayList<CheckList> checkLists;

    public ListAdapter(ArrayList<CheckList> checkLists){
        this.checkLists = checkLists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView textListView;
        private LinearLayout layout;

        private static CheckBoxListFragment.OnCheckBoxListFragmentListener mCallback;

        public static void setOnCheckBoxListFragmentListener(CheckBoxListFragment.OnCheckBoxListFragmentListener mCallback){
            ViewHolder.mCallback = mCallback;
        }

        public ViewHolder(View view){
            super(view);
            textListView = (TextView) view.findViewById(R.id.listName);
            layout = (LinearLayout) view.findViewById(R.id.listRowLayout);

            layout.setOnClickListener(this);
        }

        public TextView getTextListView(){return textListView;}

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.listRowLayout:
                    if(mCallback != null){
                        Log.d("flow", "CLICKED ON LIST");
                        mCallback.onCheckBoxSelected(checkLists.get(getAdapterPosition()));
                    }
                    break;
            }

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextListView().setText(checkLists.get(position).getListTitle());
    }

    @Override
    public int getItemCount() {
        return checkLists.size();
    }


}
