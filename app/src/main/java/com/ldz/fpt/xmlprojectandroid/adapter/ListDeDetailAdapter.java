package com.ldz.fpt.xmlprojectandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by linhdq on 7/4/17.
 */

public class ListDeDetailAdapter extends RecyclerView.Adapter<ListDeDetailAdapter.ViewHolder> {
    private List<DeModel> list;
    private LayoutInflater inflater;
    private Context context;
    private DeModel deModel;
    private Locale locale;
    private NumberFormat currencyFormatter;

    public ListDeDetailAdapter(Context context, List<DeModel> list) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
        this.locale = new Locale("vi", "VN");
        this.currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_de_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position && (deModel = list.get(position)) != null) {
            holder.txtIndex.setText(String.valueOf(position + 1));
            holder.txtUsername.setText(deModel.getUsername());
            holder.txtPrice.setText(currencyFormatter.format(deModel.getPrice()));
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
        private TextView txtPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIndex = (TextView) itemView.findViewById(R.id.txt_stt);
            txtUsername = (TextView) itemView.findViewById(R.id.txt_username);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
        }
    }
}
