package com.ldz.fpt.xmlprojectandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by linhdq on 7/4/17.
 */

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<User> list;
    private User user;

    public ListUserAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < list.size() && (user = list.get(position)) != null) {
            holder.txtFullName.setText(user.getFullName());
            holder.txtUsername.setText(user.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        //view
        private CircleImageView circleImageView;
        private TextView txtFullName;
        private TextView txtUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.imv_profile);
            txtFullName = (TextView) itemView.findViewById(R.id.txt_full_name);
            txtUsername = (TextView) itemView.findViewById(R.id.txt_username);
        }
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeInserted(position, list.size());
    }
}
