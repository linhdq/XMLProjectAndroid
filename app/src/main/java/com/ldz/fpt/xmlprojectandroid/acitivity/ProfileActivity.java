package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlRequestUpdateUserForm;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    //view
    @BindView(R.id.imv_profile)
    protected CircleImageView imvProfile;
    @BindView(R.id.txt_role)
    protected TextView txtRole;
    @BindView(R.id.edt_username)
    protected EditText edtUsername;
    @BindView(R.id.edt_fullname)
    protected EditText edtFullName;
    @BindView(R.id.edt_phone_number)
    protected EditText edtPhoneNumber;
    @BindView(R.id.view_fliper)
    protected ViewFlipper viewFlipper;
    @BindView(R.id.btn_change_password)
    protected Button btnChangePassword;
    @BindView(R.id.btn_update_profile)
    protected Button btnUpdateProfile;
    @BindView(R.id.btn_change)
    protected Button btnChange;
    @BindView(R.id.btn_save)
    protected Button btnSave;
    @BindView(R.id.edt_current_password)
    protected EditText edtCurrentPassword;
    @BindView(R.id.edt_new_password)
    protected EditText edtNewPassword;
    @BindView(R.id.edt_confirm_password)
    protected EditText edtConfirmPassword;
    @BindView(R.id.view_switcher)
    protected ViewSwitcher viewSwitcher;

    private Toast toast;
    //
    private User user;
    //database
    private DBContext dbContext;
    //
    private GetService getService;
    //
    private XMLParser xmlParser;
    //
    private String fullName;
    private String phoneNumber;
    private String currentPass;
    private String newPass;
    private String confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //
        getSupportActionBar().setTitle("Thông tin cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //
        ButterKnife.bind(this);
        //
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
        //view
        imvProfile.setEnabled(false);
        //
        dbContext = new DBContext(this);
        user = dbContext.checkLoginSuccess();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        xmlParser = XMLParser.getInst();
        //
        String role = "";
        if (user.getRole() == 0) {
            role = "SuperAdmin";
        } else if (user.getRole() == 1) {
            role = "Admin";
        } else {
            role = "Client";
        }
        txtRole.setText(String.format("Role: %s", role));
        edtUsername.setText(user.getUsername());
        edtFullName.setText(user.getFullName());
        edtPhoneNumber.setText(user.getPhoneNumber());
    }

    private void addListener() {
        imvProfile.setOnClickListener(this);
        btnChange.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        btnUpdateProfile.setOnClickListener(this);
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
            case R.id.btn_change:
                currentPass = edtCurrentPassword.getText().toString();
                newPass = edtNewPassword.getText().toString();
                confirmPass = edtConfirmPassword.getText().toString();
                if (!currentPass.equals(user.getPassword())) {
                    showToast("Lỗi! Mật khẩu hiện tại không đúng.");
                } else {
                    if (!newPass.isEmpty() && newPass.toCharArray().length >= 6 && newPass.equals(confirmPass)) {
                        XmlRequestUpdateUserForm updateUserForm = new XmlRequestUpdateUserForm(user.getUsername(),
                                newPass, user.getFullName(), user.getPhoneNumber());
                        updatePassword(updateUserForm.getRequestBody());
                    } else if (newPass.toCharArray().length < 6) {
                        showToast("Lỗi! Mật khẩu phải chứa ít nhất 6 ký tự.");
                    } else {
                        showToast("Lỗi! Xác nhận mật khẩu không khớp.");
                    }
                }
                break;
            case R.id.btn_save:
                edtFullName.setEnabled(false);
                edtPhoneNumber.setEnabled(false);
                imvProfile.setEnabled(false);
                viewFlipper.setDisplayedChild(0);
                txtRole.requestFocus();

                phoneNumber = edtPhoneNumber.getText().toString();
                fullName = edtFullName.getText().toString();
                if (!phoneNumber.isEmpty() && !fullName.isEmpty()) {
                    XmlRequestUpdateUserForm updateUserForm = new XmlRequestUpdateUserForm(user.getUsername(),
                            user.getPassword(), fullName, phoneNumber);
                    updateUser(updateUserForm.getRequestBody());
                } else {
                    edtFullName.setText(user.getFullName());
                    edtPhoneNumber.setText(user.getPhoneNumber());
                }
                break;
            case R.id.btn_change_password:
                edtCurrentPassword.setText("");
                edtNewPassword.setText("");
                edtConfirmPassword.setText("");
                viewFlipper.setDisplayedChild(2);
                viewSwitcher.setDisplayedChild(1);
                break;
            case R.id.btn_update_profile:
                edtFullName.setEnabled(true);
                edtPhoneNumber.setEnabled(true);
                imvProfile.setEnabled(true);
                viewFlipper.setDisplayedChild(1);
                edtFullName.requestFocus();
                break;
            case R.id.imv_profile:

                break;
            default:
                break;
        }
    }

    private void updateUser(RequestBody data) {
        Call<ResponseBody> call = getService.callXmlUpdateUser(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel model = xmlParser.getResponseModel(xml);
                        if (model != null) {
                            showToast(model.getMessage());
                            if (model.isStatus()) {
                                user.setFullName(fullName);
                                user.setPhoneNumber(phoneNumber);
                                dbContext.updateUser(user);
                                edtFullName.setText(user.getFullName());
                                edtPhoneNumber.setText(user.getPhoneNumber());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Login failed! Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Login failed! Please check your connection.");
            }
        });
    }

    private void updatePassword(RequestBody data) {
        Call<ResponseBody> call = getService.callXmlUpdateUser(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel model = xmlParser.getResponseModel(xml);
                        if (model != null) {
                            showToast(model.getMessage());
                            if (model.isStatus()) {
                                user.setPassword(newPass);
                                dbContext.updateUser(user);
                                viewFlipper.setDisplayedChild(0);
                                viewSwitcher.setDisplayedChild(0);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Login failed! Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Login failed! Please check your connection.");
            }
        });
    }
}
