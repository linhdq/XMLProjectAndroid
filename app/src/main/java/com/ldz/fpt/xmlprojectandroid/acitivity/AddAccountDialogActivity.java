package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.model.XMLLoginSendForm;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlCreateAccountForm;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlDeleteAccount;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccountDialogActivity extends AppCompatActivity {

    //
    private Toast toast;
    //
    private GetService getService;
    private XMLParser xmlParser;
    private XmlCreateAccountForm xmlCreateAccountForm;
    private ResponseModel responseModel;
    private User user;
    //
    DBContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_dialog);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user == null) {
            user = dbContext.checkLoginSuccess();
        }
    }

    private void createAccountService(RequestBody requestBody) {
        Call<ResponseBody> call;
        if (user.getRole() == Constant.SUPER_ADMIN) {
            call = getService.callCreateAccountForAdmin(requestBody);
        } else {
            call = getService.callCreateAccountForClient(requestBody);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel responseModel = xmlParser.getResponseModel(xml);
                        responseExecute(responseModel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
            }
        });
    }

    private void responseExecute(ResponseModel responseModel){
        showToast(responseModel.getMessage());
        if (responseModel.isStatus()){
            this.onBackPressed();
        }
    }

    public void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }
}
