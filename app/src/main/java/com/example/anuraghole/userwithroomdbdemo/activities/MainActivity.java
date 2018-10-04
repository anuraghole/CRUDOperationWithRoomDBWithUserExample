package com.example.anuraghole.userwithroomdbdemo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.anuraghole.userwithroomdbdemo.R;
import com.example.anuraghole.userwithroomdbdemo.ViewModels.UserViewModel;
import com.example.anuraghole.userwithroomdbdemo.adapters.UserAdapter;
import com.example.anuraghole.userwithroomdbdemo.models.User;
import com.example.anuraghole.userwithroomdbdemo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.DeleteUpdateUser,SearchView.OnQueryTextListener {
    public static final int USER_INSERT_ACTIVITY_REQUEST_CODE = 1;
    public static final int USER_UPDATE_ACTIVITY_REQUEST_CODE = 2;
    public static final String ACTION_FLAG="actionFlag";
    private static final String TAG = MainActivity.class.getSimpleName();

    private UserAdapter adapter;
    RecyclerView recyclerView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private UserViewModel userViewModel;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, this);
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users!=null){
                    userList=users;
                    adapter.setUser(users);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, AddNewUserActivity.class);
                intent.putExtra(ACTION_FLAG,USER_INSERT_ACTIVITY_REQUEST_CODE);
                startActivityForResult(intent, USER_INSERT_ACTIVITY_REQUEST_CODE);

            }
        });
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setQueryHint("Search by Name/Mobile/Email");
        searchView.setOnQueryTextListener(this);
        return true;

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        searchText = searchText.toLowerCase();
        ArrayList<User> filterList = new ArrayList<>();
        for (User user : userList) {
            String name = user.getName().toLowerCase();
            String email = user.getEmail().toLowerCase();
            String mobile = user.getMobile();
            if (name.contains(searchText) || email.contains(searchText)||mobile.contains(searchText))
                filterList.add(user);
        }
        adapter.setFilter(filterList);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void deleteUser(User user) {
        userViewModel.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
       // userViewModel.updateUser(user);
        Intent intent = new Intent(MainActivity.this, AddNewUserActivity.class);
        intent.putExtra(AddNewUserActivity.EXTRA_UPDATE,user);
        intent.putExtra(ACTION_FLAG,USER_UPDATE_ACTIVITY_REQUEST_CODE);
        startActivityForResult(intent, USER_UPDATE_ACTIVITY_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USER_INSERT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = null;
            if (data != null) {
                user = data.getParcelableExtra(AddNewUserActivity.EXTRA_REPLY);
                Log.i(TAG, "insert onActivityResult: " + user);
                userViewModel.insertUser(user);
            }else {
                Toast.makeText(getApplicationContext(), "Not Saved", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == USER_UPDATE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            User user = null;
            if (data != null) {
                user = data.getParcelableExtra(AddNewUserActivity.EXTRA_UPDATE);
                Log.i(TAG, "update onActivityResult: " + user);
                userViewModel.updateUser(user);
            }else {
                Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_LONG).show();
            }
        }

    }


}
