package com.androidkt.pagingwithrestapi;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.androidkt.pagingwithrestapi.ui.UserAdapter;
import com.androidkt.pagingwithrestapi.ui.UserViewModel;
import com.androidkt.pagingwithrestapi.util.ListItemClickListener;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.userList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        final UserAdapter userUserAdapter = new UserAdapter(this);

        viewModel.userList.observe(this, userUserAdapter::setList);

        viewModel.networkState.observe(this, userUserAdapter::setNetworkState);

        recyclerView.setAdapter(userUserAdapter);

    }

    @Override
    public void onRetryClick(View view, int position) {
        Log.d(TAG, "Position " + position);
    }
}

