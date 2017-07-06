package com.ldz.fpt.xmlprojectandroid.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ldz.fpt.xmlprojectandroid.OnItemClickListener;
import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.acitivity.HomeActivity;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.util.PreferenceUtil;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linhdq on 7/4/17.
 */

public class ClientHomeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.edt_de_number)
    protected EditText edtDeNumber;
    @BindView(R.id.edt_de_price)
    protected EditText edtDePrice;
    @BindView(R.id.btn_confirm_de)
    protected Button btnConfirmDe;
    @BindView(R.id.txt_de_price)
    protected TextView txtDePrice;
    @BindView(R.id.txt_date)
    protected TextView txtDate;

    private Dialog dialog;
    private TextView txtDateCreated;
    private TextView txtDeNumber;
    private TextView txtDePriceDialog;
    private Button btnOk;
    //
    private Context context;
    //database
    private DBContext dbContext;
    //
    private PreferenceUtil preferenceUtil;
    private NumberFormat currencyFormatter;
    private Locale locale;

    //
    private int deNumber;
    private int dePrice;
    //
    private HomeActivity homeActivity;
    //
    private User user;
    private Date date;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatDatetime = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_home, container, false);
        ButterKnife.bind(this, view);
        init(view);
        addListener();
        return view;
    }

    private void init(View view) {
        context = view.getContext();
        homeActivity = (HomeActivity) getActivity();
        //
        date = new Date();
        //
        dbContext = new DBContext(context);
        preferenceUtil = PreferenceUtil.getInst(context);
        locale = new Locale("vi", "VN");
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        //
        user = dbContext.checkLoginSuccess();
        //
        txtDePrice.setText(String.format("Trúng giải: %s ăn %s", currencyFormatter.format(1000), currencyFormatter.format(preferenceUtil.getDePrice())));
        txtDate.setText(String.format("Ngày: %s", formatDate.format(date)));
        //
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_success);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        txtDateCreated = (TextView) dialog.findViewById(R.id.txt_date);
        txtDeNumber = (TextView) dialog.findViewById(R.id.txt_number);
        txtDePriceDialog = (TextView) dialog.findViewById(R.id.txt_price);
        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
    }

    private void addListener() {
        btnConfirmDe.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    private void validateData() {
        deNumber = dePrice = 0;
        try {
            deNumber = Integer.parseInt(edtDeNumber.getText().toString());
            dePrice = Integer.parseInt(edtDePrice.getText().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (deNumber == 0 || dePrice == 0) {
            homeActivity.showToast("Thông tin ghi đề không hợp lệ!");
            return;
        }
        DeModel deModel = new DeModel(user.getUsername(), deNumber, dePrice, formatDate.format(date));
        DeModel old = null;
        if ((old = dbContext.getDeModelByNumberAndDate(deNumber, deModel.getDate())) != null) {
            deModel.setPrice(deModel.getPrice() + old.getPrice());
            dbContext.updateDeModel(deModel);
        } else {
            dbContext.addDeModel(deModel);
        }
        date = new Date();
        txtDateCreated.setText(String.format("Thời gian: %s", formatDatetime.format(date)));
        txtDeNumber.setText(String.format("Số: %d", deNumber));
        txtDePriceDialog.setText(String.format("Tổng tiền: %s", currencyFormatter.format(dePrice)));
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_de:
                validateData();
                break;
            case R.id.btn_ok:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }
}
