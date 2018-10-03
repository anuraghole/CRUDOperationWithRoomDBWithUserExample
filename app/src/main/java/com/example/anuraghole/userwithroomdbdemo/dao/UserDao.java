package com.example.anuraghole.userwithroomdbdemo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.anuraghole.userwithroomdbdemo.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("DELETE FROM tbl_user")
    void deleteAllUser();

    @Query("DELETE FROM tbl_user WHERE id =:id")
    void deleteUser(int id);

    @Query("SELECT * from tbl_user ORDER BY id ASC")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM tbl_user WHERE id=:id")
    LiveData<User> getUser(int id);

    /*@Query("UPDATE tbl_user SET name=:name,email=:email, mobile_no=:mobile WHERE id=:id")
    LiveData<User> updateUser(int id,String name,String email,String mobile);
*/
    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM tbl_user WHERE name LIKE :search "
            + "OR email LIKE :search")
    List<User> findUserWithName(String search);
}
