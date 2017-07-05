package com.ldz.fpt.xmlprojectandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.model.LoXien4Model;

import java.util.List;

/**
 * Created by linhdq on 7/4/17.
 */

public class ListLoXien4DetailAdapter extends RecyclerView.Adapter<ListLoXien4DetailAdapter.ViewHolder> {
    private List<LoXien4Model> list;
    private LayoutInflater inflater;
    private Context context;
    private LoXien4Model loModel;

    public ListLoXien4DetailAdapter(Context context, List<LoXien4Model> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_lo_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position && (loModel = list.get(position)) != null) {
            holder.txtIndex.setText(String.valueOf(position + 1));
            holder.txtUsername.setText(loModel.getUsername());
            holder.txtPoint.setText(String.valueOf(loModel.getPoint()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        //view
        private TextView txtIndex;
        private TextView txtUsername;
        private TextView txtPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txt_stt);
            txtUsername = (TextView) itemView.findViewById(R.id.txt_username);
            txtPoint = (TextView) itemView.findViewById(R.id.txt_point);
        }
    }
}
