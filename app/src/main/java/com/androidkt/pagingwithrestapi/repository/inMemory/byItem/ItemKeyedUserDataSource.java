package com.androidkt.pagingwithrestapi.repository.inMemory.byItem;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.ItemKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidkt.pagingwithrestapi.api.GitHubApi;
import com.androidkt.pagingwithrestapi.api.GitHubService;
import com.androidkt.pagingwithrestapi.repository.NetworkState;
import com.androidkt.pagingwithrestapi.vo.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by brijesh on 20/9/17.
 */

public class ItemKeyedUserDataSource extends ItemKeyedDataSource<Long, User> {
    private static final String TAG = "ItemKeyedUserDataSource";
    private GitHubService gitHubService;
    private MutableLiveData<NetworkState> networkState;

    ItemKeyedUserDataSource() {
        gitHubService = GitHubApi.createGitHubService();
        networkState = new MutableLiveData<>();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<User> callback) {
        Log.i(TAG, "Loading Rang " + 1 + " Count " + params.requestedLoadSize);
        List<User> gitHubUser = new ArrayList<>();
        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(1, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    gitHubUser.addAll(response.body());
                    callback.onResult(gitHubUser);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(NetworkState.failed(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String errorMessage = t.getMessage();
                if (errorMessage == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(NetworkState.failed(errorMessage));
            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        List<User> gitHubUser = new ArrayList<>();

        networkState.postValue(NetworkState.LOADING);
        gitHubService.getUser(params.key, params.requestedLoadSize).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    gitHubUser.addAll(response.body());
                    callback.onResult(gitHubUser);
                    networkState.postValue(NetworkState.LOADED);
                } else {
                    networkState.postValue(NetworkState.failed(response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (errorMessage == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(NetworkState.failed(errorMessage));
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.userId;
    }

}
