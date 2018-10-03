package com.example.anuraghole.userwithroomdbdemo.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.anuraghole.userwithroomdbdemo.models.User;
import com.example.anuraghole.userwithroomdbdemo.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;
    private LiveData<List<User>> mUserList;
    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository=new UserRepository(application);
        mUserList=mRepository.getAllUser();
    }

    public LiveData<List<User>> getAllUser(){
        return mUserList;
    }
    public void insertUser(User user){
        mRepository.insertUser(user);
    }
    public void deleteUser(User user){
        mRepository.deleteUser(user);
    }
    public void updateUser(User user){
        mRepository.updateUser(user);
    }
}
