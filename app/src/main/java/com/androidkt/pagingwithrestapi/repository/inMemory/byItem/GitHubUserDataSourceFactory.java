package com.androidkt.pagingwithrestapi.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.androidkt.pagingwithrestapi.vo.User;

/**
 * Created by brijesh on 25/12/17.
 */

public class GitHubUserDataSourceFactory implements DataSource.Factory<Long, User> {

    private MutableLiveData<ItemKeyedUserDataSource> mutableLiveData;

    public GitHubUserDataSourceFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }


    @Override
    public DataSource<Long, User> create() {
        ItemKeyedUserDataSource itemKeyedUserDataSource = new ItemKeyedUserDataSource();
        mutableLiveData.postValue(itemKeyedUserDataSource);
        return itemKeyedUserDataSource;
    }

    public MutableLiveData<ItemKeyedUserDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}
