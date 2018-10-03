package com.example.anuraghole.userwithroomdbdemo.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.anuraghole.userwithroomdbdemo.dao.UserDao;
import com.example.anuraghole.userwithroomdbdemo.models.User;


//Adding multiple entities
//@Database(entities = { Repo.class, User.class }, version = 1)

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserRoomDB extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile UserRoomDB INSTANCE;

    static public UserRoomDB getUserRoomDB(Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDB.class, "db_user")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private UserDao mDao;

        PopulateDbAsync(UserRoomDB db) {
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            /*mDao.deleteAllUser();
            User user = new User();
            user.setName("Anurag Hole");
            user.setEmail("anu@gmail.com");
            user.setMobile("8888555522");
            mDao.insertUser(user);*/
            return null;
        }
    }

}
