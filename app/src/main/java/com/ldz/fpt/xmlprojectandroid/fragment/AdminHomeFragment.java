package com.ldz.fpt.xmlprojectandroid.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ldz.fpt.xmlprojectandroid.OnItemClickListener;
import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.acitivity.DetailDataActivity;
import com.ldz.fpt.xmlprojectandroid.acitivity.HomeActivity;
import com.ldz.fpt.xmlprojectandroid.adapter.List3CangAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListDeAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien2Adapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien3Adapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien4Adapter;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.BaCangModel;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.LoModel;
import com.ldz.fpt.xmlprojectandroid.model.LoXien2Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien3Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien4Model;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
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

/**
 * Created by linhdq on 7/4/17.
 */

public class AdminHomeFragment extends Fragment implements OnItemClickListener {
    private static final String TAG = AdminHomeFragment.class.getSimpleName();
    public static final int TYPE_DE = 1;
    public static final int TYPE_BA_CANG = 2;
    public static final int TYPE_LO = 3;
    public static final int TYPE_LO_XIEN_2 = 4;
    public static final int TYPE_LO_XIEN_3 = 5;
    public static final int TYPE_LO_XIEN_4 = 6;

    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String NUMBER = "number";
    public static final String NUMBER1 = "number1";
    public static final String NUMBER2 = "number2";
    public static final String NUMBER3 = "number3";
    public static final String NUMBER4 = "number4";
    //view
    @BindView(R.id.txt_date)
    protected TextView txtDate;
    @BindView(R.id.time_updated)
    protected TextView txtUpdateTime;
    @BindView(R.id.view_switcher_update)
    protected ViewSwitcher viewSwitcherUpdate;
    @BindView(R.id.view_switcher_de)
    protected ViewSwitcher viewSwitcherDe;
    @BindView(R.id.view_switcher_3_cang)
    protected ViewSwitcher viewSwitcher3Cang;
    @BindView(R.id.view_switcher_lo)
    protected ViewSwitcher viewSwitcherLo;
    @BindView(R.id.view_switcher_lo_xien_2)
    protected ViewSwitcher viewSwitcherLoXien2;
    @BindView(R.id.view_switcher_lo_xien_3)
    protected ViewSwitcher viewSwitcherLoXien3;
    @BindView(R.id.view_switcher_lo_xien_4)
    protected ViewSwitcher viewSwitcherLoXien4;
    @BindView(R.id.recycler_view_de)
    protected RecyclerView recyclerViewDe;
    @BindView(R.id.recycler_view_3_cang)
    protected RecyclerView recyclerView3Cang;
    @BindView(R.id.recycler_view_lo)
    protected RecyclerView recyclerViewLo;
    @BindView(R.id.recycler_view_lo_xien_2)
    protected RecyclerView recyclerViewLoXien2;
    @BindView(R.id.recycler_view_lo_xien_3)
    protected RecyclerView recyclerViewLoXien3;
    @BindView(R.id.recycler_view_lo_xien_4)
    protected RecyclerView recyclerViewLoXien4;
    //
    private Context context;
    //database
    private DBContext dbContext;
    //
    private PreferenceUtil preferenceUtil;
    private XMLParser xmlParser;
    private GetService getService;
    private User user;
    //
    private MyFont myFont;
    //
    private ListDeAdapter listDeAdapter;
    private List3CangAdapter list3CangAdapter;
    private ListLoAdapter listLoAdapter;
    private ListLoXien2Adapter listLoXien2Adapter;
    private ListLoXien3Adapter listLoXien3Adapter;
    private ListLoXien4Adapter listLoXien4Adapter;
    //
    private List<DeModel> listDe;
    private List<BaCangModel> list3Cang;
    private List<LoModel> listLoModel;
    private List<LoXien2Model> listLoXien2Model;
    private List<LoXien3Model> listLoXien3Model;
    private List<LoXien4Model> listLoXien4Model;
    //
    private final SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    private final SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
    private Date date;
    private XmlRequestForm requestForm;
    //
    private HomeActivity homeActivity;
    //
    private boolean isFirst;
    private String currentDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        ButterKnife.bind(this, view);
        init(view);
        configRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user == null) {
            user = dbContext.checkLoginSuccess();
        }
        if (user != null && isFirst) {
            Date date = new Date();
            requestForm = new XmlRequestForm(user.getUsername(), formatDate.format(date));
            listDe.clear();
            listDe.addAll(dbContext.getAllDeModelByDate(requestForm.getDate()));
            list3Cang.clear();
            list3Cang.addAll(dbContext.getAllBaCangModelByDate(requestForm.getDate()));
            listLoModel.clear();
            listLoModel.addAll(dbContext.getAllLoModelByDate(requestForm.getDate()));
            listLoXien2Model.clear();
            listLoXien2Model.addAll(dbContext.getAllLoXien2ModelByDate(requestForm.getDate()));
            listLoXien3Model.clear();
            listLoXien3Model.addAll(dbContext.getAllLoXien3ModelByDate(requestForm.getDate()));
            listLoXien4Model.clear();
            listLoXien4Model.addAll(dbContext.getAllLoXien4ModelByDate(requestForm.getDate()));

            viewSwitcherUpdate.setDisplayedChild(1);
            getAllDeDataByDate(requestForm.getRequestBody());

            isFirst = false;
        }
        checkVisibleView();
        listDeAdapter.notifyDataSetChanged();
        list3CangAdapter.notifyDataSetChanged();
        listLoAdapter.notifyDataSetChanged();
        listLoXien2Adapter.notifyDataSetChanged();
        listLoXien3Adapter.notifyDataSetChanged();
        listLoXien4Adapter.notifyDataSetChanged();
    }

    private void init(View view) {
        context = view.getContext();
        //database
        dbContext = new DBContext(context);
        //
        myFont = MyFont.getInst(context);
        preferenceUtil = PreferenceUtil.getInst(context);
        //
        date = new Date();
        this.currentDate = formatDate.format(date);
        txtDate.setText(String.format("Ngày: %s", currentDate));
        //
        xmlParser = XMLParser.getInst();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        homeActivity = (HomeActivity) getActivity();
        //
        isFirst = true;
        //
        txtUpdateTime.setText(preferenceUtil.getLastTimeUpdated(formatDate.format(date)));
    }

    private void configRecyclerView() {
        //
        listDe = new ArrayList<>();

        list3Cang = new ArrayList<>();

        listLoModel = new ArrayList<>();

        listLoXien2Model = new ArrayList<>();

        listLoXien3Model = new ArrayList<>();

        listLoXien4Model = new ArrayList<>();

        //layoutManager
        StaggeredGridLayoutManager layoutManagerDe = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManager3Cang = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerLo = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerLoXien2 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerLoXien3 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManagerLoXien4 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        //
        listDeAdapter = new ListDeAdapter(context, listDe, this);
        list3CangAdapter = new List3CangAdapter(context, list3Cang, this);
        listLoAdapter = new ListLoAdapter(context, listLoModel, this);
        listLoXien2Adapter = new ListLoXien2Adapter(context, listLoXien2Model, this);
        listLoXien3Adapter = new ListLoXien3Adapter(context, listLoXien3Model, this);
        listLoXien4Adapter = new ListLoXien4Adapter(context, listLoXien4Model, this);
        //de
        recyclerViewDe.setLayoutManager(layoutManagerDe);
        recyclerViewDe.setAdapter(listDeAdapter);
        //3 cang
        recyclerView3Cang.setLayoutManager(layoutManager3Cang);
        recyclerView3Cang.setAdapter(list3CangAdapter);
        //lo
        recyclerViewLo.setLayoutManager(layoutManagerLo);
        recyclerViewLo.setAdapter(listLoAdapter);
        //lo xien 2
        recyclerViewLoXien2.setLayoutManager(layoutManagerLoXien2);
        recyclerViewLoXien2.setAdapter(listLoXien2Adapter);
        //lo xien 3
        recyclerViewLoXien3.setLayoutManager(layoutManagerLoXien3);
        recyclerViewLoXien3.setAdapter(listLoXien3Adapter);
        //lo xien 4
        recyclerViewLoXien4.setLayoutManager(layoutManagerLoXien4);
        recyclerViewLoXien4.setAdapter(listLoXien4Adapter);
    }

    public void updateData() {
        if (viewSwitcherUpdate.getDisplayedChild() == 0) {
            viewSwitcherUpdate.setDisplayedChild(1);
            getAllDeDataByDate(requestForm.getRequestBody());
        }
    }

    private void checkVisibleView() {
        if (listDe != null && listDe.size() != 0) {
            viewSwitcherDe.setDisplayedChild(1);
        } else {
            viewSwitcherDe.setDisplayedChild(0);
        }

        if (list3Cang != null && list3Cang.size() != 0) {
            viewSwitcher3Cang.setDisplayedChild(1);
        } else {
            viewSwitcher3Cang.setDisplayedChild(0);
        }
        if (listLoModel != null && listLoModel.size() != 0) {
            viewSwitcherLo.setDisplayedChild(1);
        } else {
            viewSwitcherLo.setDisplayedChild(0);
        }
        if (listLoXien2Model != null && listLoXien2Model.size() != 0) {
            viewSwitcherLoXien2.setDisplayedChild(1);
        } else {
            viewSwitcherLoXien2.setDisplayedChild(0);
        }
        if (listLoXien3Model != null && listLoXien3Model.size() != 0) {
            viewSwitcherLoXien3.setDisplayedChild(1);
        } else {
            viewSwitcherLoXien3.setDisplayedChild(0);
        }
        if (listLoXien4Model != null && listLoXien4Model.size() != 0) {
            viewSwitcherLoXien4.setDisplayedChild(1);
        } else {
            viewSwitcherLoXien4.setDisplayedChild(0);
        }
    }

    private void getAllDeDataByDate(final RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllDeByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listDe.clear();
                        listDe.addAll(xmlParser.getListDeModel(xml));
                        listDeAdapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteDeByDate(requestForm.getDate());
                        for (DeModel model : listDe) {
                            dbContext.addDeModel(model);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getAllBaCangDataByDate(data);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void getAllBaCangDataByDate(final RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllBaCangByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        list3Cang.clear();
                        list3Cang.addAll(xmlParser.getListBaCangModel(xml));
                        list3CangAdapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteBaCangByDate(requestForm.getDate());
                        for (BaCangModel model : list3Cang) {
                            dbContext.addBaCangModel(model);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getAllLoDataByDate(data);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void getAllLoDataByDate(final RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllLoByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listLoModel.clear();
                        listLoModel.addAll(xmlParser.getListLoModel(xml));
                        listLoAdapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteLoByDate(requestForm.getDate());
                        for (LoModel model : listLoModel) {
                            dbContext.addLoModel(model);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getAllLoXien2DataByDate(data);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void getAllLoXien2DataByDate(final RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllLoXien2ByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listLoXien2Model.clear();
                        listLoXien2Model.addAll(xmlParser.getListLoXien2Model(xml));
                        listLoXien2Adapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteLoXien2ByDate(requestForm.getDate());
                        for (LoXien2Model model : listLoXien2Model) {
                            dbContext.addLoXien2Model(model);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getAllLoXien3DataByDate(data);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void getAllLoXien3DataByDate(final RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllLoXien3ByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listLoXien3Model.clear();
                        listLoXien3Model.addAll(xmlParser.getListLoXien3Model(xml));
                        listLoXien3Adapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteLoXien3ByDate(requestForm.getDate());
                        for (LoXien3Model model : listLoXien3Model) {
                            dbContext.addLoXien3Model(model);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                getAllLoXien4DataByDate(data);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void getAllLoXien4DataByDate(RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllLoXien4ByDate(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listLoXien4Model.clear();
                        listLoXien4Model.addAll(xmlParser.getListLoXien4Model(xml));
                        listLoXien4Adapter.notifyDataSetChanged();
                        //insert to db
                        dbContext.deleteLoXien4ByDate(requestForm.getDate());
                        for (LoXien4Model model : listLoXien4Model) {
                            dbContext.addLoXien4Model(model);
                        }
                        checkVisibleView();
                        Date date = new Date();
                        String timeUpdated = formatTime.format(date);
                        txtUpdateTime.setText(timeUpdated);
                        preferenceUtil.setLastTimeUpdated(requestForm.getDate(), timeUpdated);
                        viewSwitcherUpdate.setDisplayedChild(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                        viewSwitcherUpdate.setDisplayedChild(0);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Lỗi kết nối! Vui lòng thử lại.");
                if (viewSwitcherUpdate.getDisplayedChild() == 1) {
                    viewSwitcherUpdate.setDisplayedChild(0);
                }
            }
        });
    }

    private void goToDeDetail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_DE);
        bundle.putInt(NUMBER, listDe.get(position).getNumber());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToBaCangDetail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_BA_CANG);
        bundle.putInt(NUMBER, list3Cang.get(position).getNumber());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToLoDetail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_LO);
        bundle.putInt(NUMBER, listLoModel.get(position).getNumber());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToLoXien2Detail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_LO_XIEN_2);
        bundle.putInt(NUMBER1, listLoXien2Model.get(position).getNumber1());
        bundle.putInt(NUMBER2, listLoXien2Model.get(position).getNumber2());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToLoXien3Detail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_LO_XIEN_3);
        bundle.putInt(NUMBER1, listLoXien3Model.get(position).getNumber1());
        bundle.putInt(NUMBER2, listLoXien3Model.get(position).getNumber2());
        bundle.putInt(NUMBER3, listLoXien3Model.get(position).getNumber3());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToLoXien4Detail(int position) {
        Intent intent = new Intent(context, DetailDataActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, TYPE_LO_XIEN_4);
        bundle.putInt(NUMBER1, listLoXien4Model.get(position).getNumber1());
        bundle.putInt(NUMBER2, listLoXien4Model.get(position).getNumber2());
        bundle.putInt(NUMBER3, listLoXien4Model.get(position).getNumber3());
        bundle.putInt(NUMBER4, listLoXien4Model.get(position).getNumber4());
        bundle.putString(DATE, currentDate);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, int type) {
        switch (type) {
            case TYPE_DE:
                goToDeDetail(position);
                break;
            case TYPE_BA_CANG:
                goToBaCangDetail(position);
                break;
            case TYPE_LO:
                goToLoDetail(position);
                break;
            case TYPE_LO_XIEN_2:
                goToLoXien2Detail(position);
                break;
            case TYPE_LO_XIEN_3:
                goToLoXien3Detail(position);
                break;
            case TYPE_LO_XIEN_4:
                goToLoXien4Detail(position);
                break;
            default:
                break;
        }
    }
}
