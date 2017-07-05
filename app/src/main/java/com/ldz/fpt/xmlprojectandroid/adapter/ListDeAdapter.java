package com.ldz.fpt.xmlprojectandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ldz.fpt.xmlprojectandroid.OnItemClickListener;
import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.fragment.AdminHomeFragment;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by linhdq on 7/2/17.
 */

public class ListDeAdapter extends RecyclerView.Adapter<ListDeAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<DeModel> list;
    private DeModel model;
    private Locale locale;
    private NumberFormat currencyFormatter;
    private OnItemClickListener listener;

    public ListDeAdapter(Context context, List<DeModel> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.locale = new Locale("vi", "VN");
        this.currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_on_list_de, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list.size() > position && (model = list.get(position)) != null) {
            holder.txtSTT.setText(String.valueOf(position + 1));
            holder.txtNumber.setText(String.format("%02d",model.getNumber()));
            holder.txtPrice.setText(currencyFormatter.format(model.getPrice()));
            holder.position = position;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        //view
        private TextView txtSTT;
        private TextView txtNumber;
        private TextView txtPrice;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            //
            txtSTT = (TextView) itemView.findViewById(R.id.txt_stt);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_number);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            //
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position, AdminHomeFragment.TYPE_DE);
                }
            });
        }
    }
}
