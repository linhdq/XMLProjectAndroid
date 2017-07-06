package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.adapter.ListDrawerApdapter;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.fragment.AdminHomeFragment;
import com.ldz.fpt.xmlprojectandroid.fragment.ClientHomeFragment;
import com.ldz.fpt.xmlprojectandroid.fragment.SuperAdminHomeFragment;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.DrawerItemModel;
import com.ldz.fpt.xmlprojectandroid.model.Price;
import com.ldz.fpt.xmlprojectandroid.model.ResponseModel;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlDeUpdate;
import com.ldz.fpt.xmlprojectandroid.network.model.XmlRequestForm;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.util.MyFont;
import com.ldz.fpt.xmlprojectandroid.util.PreferenceUtil;
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

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HomeActivity.class.getSimpleName();
    //view
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;
    @BindView(R.id.btn_view_profile)
    protected Button btnProfile;
    @BindView(R.id.list_drawer)
    protected ListView listViewDrawer;
    private Toast toast;
    //database
    private DBContext dbContext;
    //
    private PreferenceUtil preferenceUtil;
    private GetService getService;
    private XMLParser xmlParser;
    //
    private MyFont myFont;
    private User user;
    //fragment
    private AdminHomeFragment adminHomeFragment;
    private SuperAdminHomeFragment superAdminHomeFragment;
    private ClientHomeFragment clientHomeFragment;
    //
    private ListDrawerApdapter listDrawerApdapter;
    //
    private List<DrawerItemModel> drawerItemModelList;
    //
    private boolean isFirst;
    //
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private Date date;
    //
    private XmlRequestForm requestForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //
        ButterKnife.bind(this);
        //
        init();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adminHomeFragment == null) {
            adminHomeFragment = new AdminHomeFragment();
        }
        if (clientHomeFragment == null) {
            clientHomeFragment = new ClientHomeFragment();
        }
        if (superAdminHomeFragment == null) {
            superAdminHomeFragment = new SuperAdminHomeFragment();
        }
        if (isFirst) {
            if (user != null) {
                if (user.getRole() == 0) {
                    openFragment(superAdminHomeFragment, false, false);
                } else if (user.getRole() == 1) {
                    openFragment(adminHomeFragment, false, false);
                } else if (user.getRole() == 2) {
                    openFragment(clientHomeFragment, false, false);
                }
            }
            requestForm = new XmlRequestForm(user.getUsername(), formatDate.format(date));
            if (user.getRole() != 0) {
                getPrice(requestForm.getRequestBody());
            }
            isFirst = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!preferenceUtil.isRememberMe()) {
            dbContext.deleteUserModel();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            if (user.getRole() == 1) {
                adminHomeFragment.updateData();
                superAdminHomeFragment.refreshData();
            } else if (user.getRole() == 0) {
                superAdminHomeFragment.refreshData();
            }
            if (user.getRole() != 0) {
                getPrice(requestForm.getRequestBody());
            }
            if (user.getRole() == 2) {
                List<DeModel> listDe = dbContext.getAllDeModelByDate(formatDate.format(date));
                XmlDeUpdate xmlDeUpdate = new XmlDeUpdate(listDe);
                updateDeData(xmlDeUpdate.getRequestBody());
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //database
        dbContext = new DBContext(this);
        user = dbContext.checkLoginSuccess();
        //
        myFont = MyFont.getInst(this);
        preferenceUtil = PreferenceUtil.getInst(this);
        xmlParser = XMLParser.getInst();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //set font
        configFont();
        //
        drawerItemModelList = new ArrayList<>();
        if (user.getRole() != 0) {
            drawerItemModelList.add(new DrawerItemModel(R.drawable.ic_report, "Thống kê"));
        }
        if (user.getRole() == 1) {
            drawerItemModelList.add(new DrawerItemModel(R.mipmap.ic_setting, "Thiết lập"));
            drawerItemModelList.add(new DrawerItemModel(R.mipmap.ic_client, "Quản lý Client"));
        }
        drawerItemModelList.add(new DrawerItemModel(R.drawable.ic_signout, "Đăng xuất"));
        listDrawerApdapter = new ListDrawerApdapter(this, drawerItemModelList);
        listViewDrawer.setAdapter(listDrawerApdapter);
        //
        isFirst = true;
        //
        date = new Date();
    }

    private void configFont() {
        btnProfile.setTypeface(myFont.getLight());
    }

    private void addListener() {
        btnProfile.setOnClickListener(this);
        listViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (user.getRole() != 0) {
                            Intent intentReport = new Intent(HomeActivity.this, ReportActivity.class);
                            startActivity(intentReport);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            dbContext.deleteUserModel();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;
                    case 1:
                        if (user.getRole() == 1) {
                            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            dbContext.deleteUserModel();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;
                    case 2:
                        if (user.getRole() == 1) {
                            openFragment(superAdminHomeFragment, true, true);
                            drawerLayout.closeDrawers();
                        } else {
                            dbContext.deleteUserModel();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                        break;
                    case 3:
                        dbContext.deleteUserModel();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void openFragment(Fragment fragment, boolean addToBackStack, boolean hasAnimation) {
        if (!fragment.isAdded()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (hasAnimation) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);
            }
            fragmentTransaction.replace(R.id.layout_container, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.toString());
            }
            fragmentTransaction.commit();
        }
    }

    public void showToast(String mess) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, mess, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_profile:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void getPrice(RequestBody data) {
        Call<ResponseBody> call = getService.callGetPrice(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        Price price = xmlParser.getPriceModel(xml);
                        if (price != null) {
                            preferenceUtil.setDePrice(price.getDePrice());
                            preferenceUtil.setBaCangPrice(price.getBaCangPrice());
                            preferenceUtil.setLoPriceNhan(price.getLoNhanPrice());
                            preferenceUtil.setLoPriceTra(price.getLoTraPrice());
                            preferenceUtil.setLoXien2PriceNhan(price.getLoXien2NhanPrice());
                            preferenceUtil.setLoXien2PriceTra(price.getLoXien2TraPrice());
                            preferenceUtil.setLoXien3PriceNhan(price.getLoXien3NhanPrice());
                            preferenceUtil.setLoXien3PriceTra(price.getLoXien3TraPrice());
                            preferenceUtil.setLoXien4PriceNhan(price.getLoXien4NhanPrice());
                            preferenceUtil.setLoXien4PriceTra(price.getLoXien4TraPrice());
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

    private void updateDeData(RequestBody data) {
        Call<ResponseBody> call = getService.callUpdateDe(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        ResponseModel responseModel = xmlParser.getResponseModel(xml);
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
