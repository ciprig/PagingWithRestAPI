package com.androidkt.pagingwithrestapi.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.androidkt.pagingwithrestapi.repository.NetworkState;
import com.androidkt.pagingwithrestapi.repository.inMemory.byItem.GitHubUserDataSourceFactory;
import com.androidkt.pagingwithrestapi.repository.inMemory.byItem.ItemKeyedUserDataSource;
import com.androidkt.pagingwithrestapi.vo.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by brijesh on 18/9/17.
 */

public class UserViewModel extends ViewModel {

    public LiveData<PagedList<User>> userList;
    public LiveData<NetworkState> networkState;

    public UserViewModel() {
        Executor executor = Executors.newFixedThreadPool(5);
        GitHubUserDataSourceFactory gitHubUserDataSourceFactory = new GitHubUserDataSourceFactory();

        networkState = Transformations.switchMap(gitHubUserDataSourceFactory.getMutableLiveData(),
                ItemKeyedUserDataSource::getNetworkState);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = new LivePagedListBuilder<>(gitHubUserDataSourceFactory, pagedListConfig)
                .setBackgroundThreadExecutor(executor)
                .build();
    }
}
