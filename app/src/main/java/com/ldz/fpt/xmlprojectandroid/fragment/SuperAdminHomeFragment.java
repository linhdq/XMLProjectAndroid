package com.ldz.fpt.xmlprojectandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.acitivity.HomeActivity;
import com.ldz.fpt.xmlprojectandroid.adapter.ListUserAdapter;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.model.User;
import com.ldz.fpt.xmlprojectandroid.network.GetService;
import com.ldz.fpt.xmlprojectandroid.network.ServiceFactory;
import com.ldz.fpt.xmlprojectandroid.network.model.XMLLoginSendForm;
import com.ldz.fpt.xmlprojectandroid.util.Constant;
import com.ldz.fpt.xmlprojectandroid.xml_parser.XMLParser;

import java.io.IOException;
import java.util.ArrayList;
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

public class SuperAdminHomeFragment extends Fragment {
    private static final String TAG = SuperAdminHomeFragment.class.getSimpleName();
    //view
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;
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
    private User user;
    //
    private HomeActivity homeActivity;
    //
    private boolean isFirst;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super_admin_home, container, false);
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
        if (isFirst && user != null) {
            sendForm = new XMLLoginSendForm(user.getUsername(), user.getPassword());
            getAllAdmins(sendForm.getRequestBody());
            isFirst = false;
        }
    }

    private void init(View view) {
        homeActivity = (HomeActivity) getActivity();
        context = view.getContext();
        //
        dbContext = new DBContext(context);
        xmlParser = XMLParser.getInst();
        //network
        getService = ServiceFactory.getInstance().createService(GetService.class);
        //
        isFirst = true;
        listUser = new ArrayList<>();
    }

    private void configRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listUserAdapter = new ListUserAdapter(context, listUser);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listUserAdapter);
    }

    private void getAllAdmins(RequestBody data) {
        Call<ResponseBody> call = getService.callXmlGetAllAdmins(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constant.OK_STATUS) {
                    try {
                        String xml = response.body().string();
                        listUser.addAll(xmlParser.getAllAdmins(xml));
                        listUserAdapter.notifyDataSetChanged();
                        Log.d(TAG, "onResponse: " + listUser.size());
                        Log.d(TAG, "onResponse: " + listUser.get(0).getFullName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    homeActivity.showToast("Login failed! Please try again.");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                homeActivity.showToast("Login failed! Please check your connection.");
            }
        });
    }
}
