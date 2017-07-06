package com.ldz.fpt.xmlprojectandroid.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.acitivity.HomeActivity;
import com.ldz.fpt.xmlprojectandroid.adapter.ListUserAdapter;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.network.model.XMLLoginSendForm;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlCreateAccountForm;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlDeleteAccount;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by linhdq on 7/4/17.
 */

public class SuperAdminHomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = SuperAdminHomeFragment.class.getSimpleName();
    //view
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
    @BindView(R.id.view_switcher_update)
    protected ViewSwitcher viewSwitcher;
    @BindView(R.id.txt_date)
    protected TextView txtDate;
    @BindView(R.id.floating_button_add_account)
    protected FloatingActionButton btnAddAccount;
    @BindView(R.id.txt_title)
    protected TextView txtTitle;

    private TextView txtDialogTitle;
    private EditText edtUsername;
    private EditText edtFullName;
    private EditText edtPhoneNumber;
    private EditText edtPassword;
    private EditText edtConfPass;
    private Button btnCancel;
    private Button btnSignUp;
    //
    private Dialog dialogCreateAccount;
    //
    private Context context;
    //
    private ListUserAdapter listUserAdapter;
    //
    private List<User> listUser;
    //
    private DBContext dbContext;
    //
    private GetService getService;
    private XMLParser xmlParser;
    private XMLLoginSendForm sendForm;
    private XmlDeleteAccount xmlDeleteAccount;
    private User user;
    //
    private HomeActivity homeActivity;
    //
    private boolean isFirst;
    private String username;
    private String password;
    private String confPassword;
    private String fullName;
    private String phoneNumber;
    //
    private Paint paint;
    private AlertDialog.Builder alertDialog;
    //
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_admin_home, container, false);
        ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        configRecyclerView();
        if (user == null) {
            user = dbContext.checkLoginSuccess();
        }
        if (isFirst && user != null) {
            if (user.getRole() == 0) {
                txtTitle.setText("Danh sách admin");
            } else if (user.getRole() == 1) {
                txtTitle.setText("Danh sách client");
                txtDialogTitle.setText("Tạo mới tài khoản Client");
            }
            sendForm = new XMLLoginSendForm(user.getUsername(), user.getPassword());
            viewSwitcher.setDisplayedChild(1);
            getAllAdmins(sendForm.getRequestBody());
            isFirst = false;
        }
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        context = view.getContext();
        //
        dialogCreateAccount = new Dialog(context);
        dialogCreateAccount.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogCreateAccount.setContentView(R.layout.custom_dialog_create_account);
        dialogCreateAccount.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogCreateAccount.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        //
        txtDialogTitle = (TextView) dialogCreateAccount.findViewById(R.id.txt_title);
        edtUsername = (EditText) dialogCreateAccount.findViewById(R.id.edt_username);
        edtFullName = (EditText) dialogCreateAccount.findViewById(R.id.edt_fullname);
        edtPhoneNumber = (EditText) dialogCreateAccount.findViewById(R.id.edt_phone_number);
        edtPassword = (EditText) dialogCreateAccount.findViewById(R.id.edt_password);
        edtConfPass = (EditText) dialogCreateAccount.findViewById(R.id.edt_conf_password);
        btnCancel = (Button) dialogCreateAccount.findViewById(R.id.btn_cancel);
        btnSignUp = (Button) dialogCreateAccount.findViewById(R.id.btn_sign_up);
        //
        dbContext = new DBContext(context);
        xmlParser = XMLParser.getInst();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        isFirst = true;
        listUser = new ArrayList<>();
        //
        paint = new Paint();
        //
        Date date = new Date();
        txtDate.setText(String.format("Ngày: %s", dateFormat.format(date)));
        //
        btnAddAccount.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    private void configRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listUserAdapter = new ListUserAdapter(context, listUser);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listUserAdapter);
        initSwipe();
    }

    public void refreshData() {
        if(viewSwitcher!=null) {
            viewSwitcher.setDisplayedChild(1);
            getAllAdmins(sendForm.getRequestBody());
        }
    }

    private void getAllAdmins(RequestBody data) {
        Call<ResponseBody> call;
        if (user.getRole() == Constant.SUPER_ADMIN) {
            call = getService.callXmlGetAllAdmins(data);
        } else {
            call = getService.callXmlGetAllClients(data);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listUser.clear();
                        listUser.addAll(xmlParser.getAllAdmins(xml));
                        listUserAdapter.notifyDataSetChanged();
                        for (User u : listUser) {
                            if (dbContext.checkUserIsExists(u.getUsername()) != null) {
                                dbContext.updateUser(u);
                            } else {
                                dbContext.addUserModel(u);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    homeActivity.showToast("Lỗi kết nối!");
                    listUser.clear();
                    listUser.addAll(dbContext.getAllAdmins());
                    listUserAdapter.notifyDataSetChanged();
                }
                viewSwitcher.setDisplayedChild(0);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối!");
                listUser.clear();
                listUser.addAll(dbContext.getAllAdmins());
                listUserAdapter.notifyDataSetChanged();
                viewSwitcher.setDisplayedChild(0);
            }
        });
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    initDialog(dbContext.getUserModel().getUsername(), listUser.get(position).getUsername(), position);
                } else {
                    callToUser(listUser.get(position).getPhoneNumber());
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone_white_48dp);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    } else {
                        paint.setColor(Color.parseColor("#FF0000"));
                        RectF backGround = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(backGround, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white_48dp);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    }
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void callToUser(String phoneNumber) {
        Uri call = Uri.parse("tel:" + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL, call);
        startActivity(callIntent);
    }

    private void reloadData(ResponseModel responseModel, int position) {
        if (responseModel.isStatus()) {
            dbContext.deleteUserByUsername(listUser.get(position).getUsername());
            getAllAdmins(sendForm.getRequestBody());
        }
        homeActivity.showToast(responseModel.getMessage());
    }

    private void deleteAccountService(RequestBody requestBody, final int position) {
        Call<ResponseBody> call;
        if (user.getRole() == Constant.SUPER_ADMIN) {
            call = getService.callDeleteAccountAdmin(requestBody);
        } else {
            call = getService.callDeleteAccountClient(requestBody);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel responseModel = xmlParser.getResponseModel(xml);
                        reloadData(responseModel, position);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    homeActivity.showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
                    configRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
            }
        });
    }

    private void initDialog(final String userNameRequest, final String userNameDelete, final int position) {
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Xác nhận")
                .setMessage("Xoá tài khoản này khỏi hệ thống?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        xmlDeleteAccount = new XmlDeleteAccount(userNameRequest, userNameDelete);
                        deleteAccountService(xmlDeleteAccount.getRequestBody(), position);
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        configRecyclerView();
                    }
                })
                .show();
    }

    private void validateData() {
        username = edtUsername.getText().toString();
        fullName = edtFullName.getText().toString();
        phoneNumber = edtPhoneNumber.getText().toString();
        password = edtPassword.getText().toString();
        confPassword = edtConfPass.getText().toString();
        if (username.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
            homeActivity.showToast("Bạn phải điền đầy đủ thông tin đăng ký!");
            return;
        }
        if (password.toCharArray().length < 6) {
            homeActivity.showToast("Mật khẩu phải chứa ít nhất 6 ký tự!");
            return;
        }
        if (!password.equals(confPassword)) {
            homeActivity.showToast("Xác nhận mật khẩu không khớp!");
            return;
        }
        XmlCreateAccountForm accountForm = new XmlCreateAccountForm(user.getUsername(), username, password, fullName, phoneNumber);
        createAccountService(accountForm.getRequestBody());
    }

    private void createAccountService(final RequestBody requestBody) {
        Call<ResponseBody> call = null;
        if (user.getRole() == 0) {
            call = getService.callCreateAccountForAdmin(requestBody);
        } else if (user.getRole() == 1) {
            call = getService.callCreateAccountForClient(requestBody);
        }
        if (call != null) {
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == Constant.OK_STATUS) {
                        try {
                            String xml = response.body().string();
                            ResponseModel responseModel = xmlParser.getResponseModel(xml);
                            homeActivity.showToast(responseModel.getMessage());
                            if (responseModel.isStatus()) {
                                getAllAdmins(sendForm.getRequestBody());
                            }
                            dialogCreateAccount.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        homeActivity.showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    homeActivity.showToast("Đã xảy ra lỗi! Vui lòng thử lại sau.");
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floating_button_add_account:
                edtUsername.setText("");
                edtFullName.setText("");
                edtPhoneNumber.setText("");
                edtPassword.setText("");
                edtConfPass.setText("");
                dialogCreateAccount.show();
                break;
            case R.id.btn_cancel:
                dialogCreateAccount.dismiss();
                break;
            case R.id.btn_sign_up:
                validateData();
                break;
            default:
                break;
        }
    }
}
