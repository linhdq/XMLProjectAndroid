package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.Price;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.util.PreferenceUtil;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();
    //
    @BindView(R.id.edt_de_price)
    protected EditText edtDePrice;
    @BindView(R.id.edt_ba_cang_price)
    protected EditText edtBaCangPrice;
    @BindView(R.id.edt_lo_price_nhan)
    protected EditText edtLoPriceNhan;
    @BindView(R.id.edt_lo_price_tra)
    protected EditText edtLoPriceTra;
    @BindView(R.id.edt_lo_xien_2_price_nhan)
    protected EditText edtLoXien2PriceNhan;
    @BindView(R.id.edt_lo_xien_2_price_tra)
    protected EditText edtLoXien2PriceTra;
    @BindView(R.id.edt_lo_xien_3_price_nhan)
    protected EditText edtLoXien3PriceNhan;
    @BindView(R.id.edt_lo_xien_3_price_tra)
    protected EditText edtLoXien3PriceTra;
    @BindView(R.id.edt_lo_xien_4_price_nhan)
    protected EditText edtLoXien4PriceNhan;
    @BindView(R.id.edt_lo_xien_4_price_tra)
    protected EditText edtLoXien4PriceTra;
    @BindView(R.id.btn_save)
    protected Button btnSave;
    @BindView(R.id.btn_change)
    protected Button btnChange;
    @BindView(R.id.view_switcher)
    protected ViewSwitcher viewSwitcher;
    private Toast toast;
    //
    private DBContext dbContext;
    private PreferenceUtil preferenceUtil;
    private GetService getService;
    //
    private User user;
    //
    private int dePrice = 0;
    private int bacangPrice = 0;
    private int loNhanPrice = 0;
    private int loTraPrice = 0;
    private int loxien2NhanPrice = 0;
    private int loxien2TraPrice = 0;
    private int loxien3NhanPrice = 0;
    private int loxien3TraPrice = 0;
    private int loxien4NhanPrice = 0;
    private int loxien4TraPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle("Thiết lập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void init() {
        dbContext = new DBContext(this);
        preferenceUtil = PreferenceUtil.getInst(this);
        changeStateEditor(false);
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        edtDePrice.setText(String.valueOf(preferenceUtil.getDePrice()));
        edtBaCangPrice.setText(String.valueOf(preferenceUtil.getBaCangPrice()));
        edtLoPriceNhan.setText(String.valueOf(preferenceUtil.getLoPriceNhan()));
        edtLoPriceTra.setText(String.valueOf(preferenceUtil.getLoPriceTra()));
        edtLoXien2PriceNhan.setText(String.valueOf(preferenceUtil.getLoXien2PriceNhan()));
        edtLoXien2PriceTra.setText(String.valueOf(preferenceUtil.getLoXien2PriceTra()));
        edtLoXien3PriceNhan.setText(String.valueOf(preferenceUtil.getLoXien3PriceNhan()));
        edtLoXien3PriceTra.setText(String.valueOf(preferenceUtil.getLoXien3PriceTra()));
        edtLoXien4PriceNhan.setText(String.valueOf(preferenceUtil.getLoXien4PriceNhan()));
        edtLoXien4PriceTra.setText(String.valueOf(preferenceUtil.getLoXien4PriceTra()));
        //
        user = dbContext.checkLoginSuccess();
    }

    private void addListener() {
        btnChange.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void changeStateEditor(boolean enable) {
        edtDePrice.setEnabled(enable);
        edtBaCangPrice.setEnabled(enable);
        edtLoPriceNhan.setEnabled(enable);
        edtLoPriceTra.setEnabled(enable);
        edtLoXien2PriceNhan.setEnabled(enable);
        edtLoXien2PriceTra.setEnabled(enable);
        edtLoXien3PriceNhan.setEnabled(enable);
        edtLoXien3PriceTra.setEnabled(enable);
        edtLoXien4PriceNhan.setEnabled(enable);
        edtLoXien4PriceTra.setEnabled(enable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                viewSwitcher.setDisplayedChild(1);
                changeStateEditor(true);
                break;
            case R.id.btn_save:
                try {
                    dePrice = Integer.parseInt(edtDePrice.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtDePrice.setText(String.valueOf(dePrice));
                }
                try {
                    bacangPrice = Integer.parseInt(edtBaCangPrice.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtBaCangPrice.setText(String.valueOf(bacangPrice));
                }
                try {
                    loNhanPrice = Integer.parseInt(edtLoPriceNhan.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoPriceNhan.setText(String.valueOf(loNhanPrice));
                }
                try {
                    loTraPrice = Integer.parseInt(edtLoPriceTra.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoPriceTra.setText(String.valueOf(loTraPrice));
                }
                try {
                    loxien2NhanPrice = Integer.parseInt(edtLoXien2PriceNhan.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien2PriceNhan.setText(String.valueOf(loxien2NhanPrice));
                }
                try {
                    loxien2TraPrice = Integer.parseInt(edtLoXien2PriceTra.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien2PriceTra.setText(String.valueOf(loxien2TraPrice));
                }
                try {
                    loxien3NhanPrice = Integer.parseInt(edtLoXien3PriceNhan.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien3PriceNhan.setText(String.valueOf(loxien3NhanPrice));
                }
                try {
                    loxien3TraPrice = Integer.parseInt(edtLoXien3PriceTra.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien3PriceTra.setText(String.valueOf(loxien3TraPrice));
                }
                try {
                    loxien4NhanPrice = Integer.parseInt(edtLoXien4PriceNhan.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien4PriceNhan.setText(String.valueOf(loxien4NhanPrice));
                }
                try {
                    loxien4TraPrice = Integer.parseInt(edtLoXien4PriceTra.getText().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    edtLoXien4PriceTra.setText(String.valueOf(loxien4TraPrice));
                }
                preferenceUtil.setDePrice(dePrice);
                preferenceUtil.setBaCangPrice(bacangPrice);
                preferenceUtil.setLoPriceNhan(loNhanPrice);
                preferenceUtil.setLoPriceTra(loTraPrice);
                preferenceUtil.setLoXien2PriceNhan(loxien2NhanPrice);
                preferenceUtil.setLoXien2PriceTra(loxien2TraPrice);
                preferenceUtil.setLoXien3PriceNhan(loxien3NhanPrice);
                preferenceUtil.setLoXien3PriceTra(loxien3TraPrice);
                preferenceUtil.setLoXien4PriceNhan(loxien4NhanPrice);
                preferenceUtil.setLoXien4PriceTra(loxien4TraPrice);
                Price price = new Price(dePrice, bacangPrice, loNhanPrice, loTraPrice, loxien2NhanPrice, loxien2TraPrice, loxien3NhanPrice, loxien3TraPrice, loxien4NhanPrice, loxien4TraPrice);
                price.setUsername(user.getUsername());
                updatePrice(price.getRequestBody());
                viewSwitcher.setDisplayedChild(0);
                changeStateEditor(false);
                break;
            default:
                break;
        }
    }

    public void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void updatePrice(RequestBody data) {
        Call<ResponseBody> call = getService.callUpdatePrice(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel responseModel = XMLParser.getInst().getResponseModel(xml);
                        if (responseModel != null) {
                            showToast(responseModel.getMessage());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Lỗi kết nối!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Lỗi kết nối!");
            }
        });
    }
}
