package com.example.manny.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.manny.todolist.data.CheckList;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by manny on 6/6/16.
 */
public class CheckBoxListFragment extends Fragment {

    public static final String FIRE_BASE_URL = "https://scorching-inferno-4075.firebaseio.com/";
    private OnCheckBoxListFragmentListener mCallback;
    private Firebase mFirebaseRef;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private ImageButton listImgBtn;

    public CheckBoxListFragment(){

    }

    public interface OnCheckBoxListFragmentListener{
        void onCheckBoxSelected(CheckList checkList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnCheckBoxListFragmentListener) getActivity();
            ListAdapter.ViewHolder.setOnCheckBoxListFragmentListener(mCallback);

        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement OnCheckBoxListFragmentListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(getActivity());
        mFirebaseRef = new Firebase(FIRE_BASE_URL + CheckList.LIST_ROUTE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.toDoListRecycleView);

        listImgBtn = (ImageButton) layout.findViewById(R.id.listImgBtn);

        listImgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("flow", "LIST BUTTON CLICKED");
                addCheckList();
            }
        });

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirebaseRef.addValueEventListener(valueEventListener);
    }

    private void loadCheckList(ArrayList<CheckList> checkLists){
        if (checkLists == null){
            return;
        }

        adapter = new ListAdapter(checkLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    private void addCheckList(){
        CheckList checkList = new CheckList();

        saveCheckList(checkList);

        mCallback.onCheckBoxSelected(checkList);

    }


    private void saveCheckList(CheckList checkList){
        Log.d("flow", "frome savechecklist value of task: " + checkList.getListTitle());

        Firebase newRef = mFirebaseRef.push();

        String listId = newRef.getKey();
        checkList.setListId(listId);
        newRef.setValue(checkList);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ArrayList<CheckList> checkLists = new ArrayList<>();

            for(DataSnapshot listSnapshot: dataSnapshot.getChildren()){
                CheckList checkList = listSnapshot.getValue(CheckList.class);
                checkLists.add(checkList);
                System.out.print(checkList.getListTitle() + " - " + checkList.getList());
            }
            loadCheckList(checkLists);
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            Log.d("flow", "onCancelled from check list");
        }
    };
}





































