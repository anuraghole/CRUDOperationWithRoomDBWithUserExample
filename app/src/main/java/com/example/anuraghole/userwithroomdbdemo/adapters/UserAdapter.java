package com.example.anuraghole.userwithroomdbdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anuraghole.userwithroomdbdemo.R;
import com.example.anuraghole.userwithroomdbdemo.activities.AddNewUserActivity;
import com.example.anuraghole.userwithroomdbdemo.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    LayoutInflater layoutInflater;
    DeleteUpdateUser deleteUpdateUser;
    private Context context;
    private List<User> userList = new ArrayList<>();

    public UserAdapter(Context context,DeleteUpdateUser deleteUpdateUser) {
        this.context = context;
        this.deleteUpdateUser = deleteUpdateUser;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_user, viewGroup, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {
        final User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.mobile.setText(user.getMobile());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser(user,position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(user,position);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
    private void updateUser(User user, int position) {
       /* Intent intent=new Intent(context,AddNewUserActivity.class);
        intent.putExtra(AddNewUserActivity.EXTRA_UPDATE,user);
        intent.putExtra("pos",position);
        intent.putExtra("flag",2);
        context.startActivity(intent);*/
       deleteUpdateUser.updateUser(user);

    }

    private void deleteUser(User user, int position) {
        userList.remove(user);
        deleteUpdateUser.deleteUser(user);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        else return 0;
    }


    public void setUser(List<User> users) {
        if (users != null) {
            userList.clear();
            userList.addAll(users);
            notifyDataSetChanged();
        }
    }
    public interface DeleteUpdateUser {
         void deleteUser(User user);
         void updateUser(User user);
    }
    static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView name, email, mobile;
        private ImageView delete,update;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobile = itemView.findViewById(R.id.mobile);
            delete = itemView.findViewById(R.id.iv_delete);
            update = itemView.findViewById(R.id.iv_update);
        }
    }
}
