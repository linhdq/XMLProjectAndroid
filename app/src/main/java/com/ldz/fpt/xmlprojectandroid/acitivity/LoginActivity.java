package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.network.model.XMLLoginSendForm;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.util.MyFont;
import com.ldz.fpt.xmlprojectandroid.util.PreferenceUtil;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    //view
    private TextView txtWelcomeback;
    private TextView txtRememberTitle;
    private TextView txtForgotTitle;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private SwitchButton switchButton;
    private TextView txtForgotPassword;
    private RelativeLayout layoutContainer;
    private TextView txtError;
    private ProgressBar progressBar;
    //
    private Toast toast;
    //database
    private DBContext dbContext;
    //
    private MyFont myFont;
    private User user;
    //
    private GetService getService;
    private XMLLoginSendForm sendForm;
    //
    private XMLParser xmlParser;
    private PreferenceUtil preferenceUtil;
    //
    private boolean isRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        //
        setContentView(R.layout.activity_login);
        //
        init();
        addListener();
    }

    private void init() {
        //view
        txtWelcomeback = (TextView) findViewById(R.id.txt_welcome);
        txtRememberTitle = (TextView) findViewById(R.id.txt_forgot_title);
        txtForgotTitle = (TextView) findViewById(R.id.txt_forgot_title);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        switchButton = (SwitchButton) findViewById(R.id.switch_button);
        txtForgotPassword = (TextView) findViewById(R.id.txt_forgot_password);
        layoutContainer = (RelativeLayout) findViewById(R.id.layout_container);
        txtError = (TextView) findViewById(R.id.txt_error);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        //database
        dbContext = new DBContext(this);
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        xmlParser = XMLParser.getInst();
        myFont = MyFont.getInst(this);
        preferenceUtil = PreferenceUtil.getInst(this);
        //set font
        txtWelcomeback.setTypeface(myFont.getExtraBold());
        edtUsername.setTypeface(myFont.getRegular());
        edtPassword.setTypeface(myFont.getRegular());
        btnLogin.setTypeface(myFont.getRegular());
        txtRememberTitle.setTypeface(myFont.getRegular());
        txtForgotTitle.setTypeface(myFont.getLightItalic());
        txtForgotPassword.setTypeface(myFont.getRegular());
    }

    private void addListener() {
        btnLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        layoutContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftKeyboard();
                return false;
            }
        });
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRememberMe = b;
            }
        });
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }

    private boolean checkLogin() {
        progressBar.setVisibility(View.VISIBLE);
        txtError.setVisibility(View.GONE);
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            txtError.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return false;
        }
        sendForm = new XMLLoginSendForm(username, password);
        return true;
    }

    private void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                hideSoftKeyboard();
                if (checkLogin()) {

                    loginService(sendForm.getRequestBody());
                }
                break;
            case R.id.txt_forgot_password:

                break;
            default:
                break;
        }
    }

    private void loginService(RequestBody data) {
        Call<ResponseBody> call = getService.callXmlLogin(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        user = xmlParser.getUserModel(xml);
                        if (user != null) {

                            if (user.isSuccess()) {
                                dbContext.addUserModel(user);
                                preferenceUtil.setRememberMe(isRememberMe);
                                goToHomeScreen();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                txtError.setVisibility(View.VISIBLE);
                            }
                        } else {
                            showToast("Login failed! Please try again.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Login failed! Please try again.");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Login failed! Please check your connection.");
                //hide progress bar
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
