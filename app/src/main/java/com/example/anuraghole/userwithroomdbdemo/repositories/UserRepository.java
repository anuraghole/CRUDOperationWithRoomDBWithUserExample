package com.example.anuraghole.userwithroomdbdemo.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.anuraghole.userwithroomdbdemo.dao.UserDao;
import com.example.anuraghole.userwithroomdbdemo.db.UserRoomDB;
import com.example.anuraghole.userwithroomdbdemo.models.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> userList;

    public UserRepository(Application application) {
        UserRoomDB db = UserRoomDB.getUserRoomDB(application);
        userDao = db.userDao();
        userList = userDao.getAllUser();
    }

    public LiveData<List<User>> getAllUser() {
        return userList;
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public void deleteUser(User user) {
        new deleteAsyncTask(userDao).execute(user);
        //userDao.deleteUser(user);
    }

    public void updateUser(User user) {
        new updateAsyncTask(userDao).execute(user);
        //userDao.deleteUser(user);
    }

    public void insertUser(User user) {
        new insertAsyncTask(userDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            if (params[0] != null) {
                mAsyncTaskDao.insertUser(params[0]);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            if (params[0]!=null)
            mAsyncTaskDao.deleteUser(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao mAsyncTaskDao;
        updateAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final User... params) {
            if (params[0]!=null)
            mAsyncTaskDao.updateUser(params[0]);
            return null;
        }
    }


}
