package com.ldz.fpt.xmlprojectandroid.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ldz.fpt.xmlprojectandroid.R;
import com.ldz.fpt.xmlprojectandroid.adapter.ListBaCangDetailAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListDeDetailAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoDetailAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien2DetailAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien3DetailAdapter;
import com.ldz.fpt.xmlprojectandroid.adapter.ListLoXien4DetailAdapter;
import com.ldz.fpt.xmlprojectandroid.database.DBContext;
import com.ldz.fpt.xmlprojectandroid.fragment.AdminHomeFragment;
import com.ldz.fpt.xmlprojectandroid.model.BaCangModel;
import com.ldz.fpt.xmlprojectandroid.model.DeModel;
import com.ldz.fpt.xmlprojectandroid.model.LoModel;
import com.ldz.fpt.xmlprojectandroid.model.LoXien2Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien3Model;
import com.ldz.fpt.xmlprojectandroid.model.LoXien4Model;
import com.ldz.fpt.xmlprojectandroid.util.PreferenceUtil;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDataActivity extends AppCompatActivity {
    private static final String TAG = DetailDataActivity.class.getSimpleName();
    //view
    @BindView(R.id.view_fliper)
    protected ViewFlipper viewFlipper;
    @BindView(R.id.txt_date_de)
    protected TextView txtDateDe;
    @BindView(R.id.txt_number_de)
    protected TextView txtNumberDe;
    @BindView(R.id.txt_price_de)
    protected TextView txtPriceDe;
    @BindView(R.id.recycler_view_de)
    protected RecyclerView recyclerViewDe;

    @BindView(R.id.txt_date_ba_cang)
    protected TextView txtDateBaCang;
    @BindView(R.id.txt_number_ba_cang)
    protected TextView txtNumberBaCang;
    @BindView(R.id.txt_price_ba_cang)
    protected TextView txtPriceBaCang;
    @BindView(R.id.recycler_view_ba_cang)
    protected RecyclerView recyclerViewBaCang;

    @BindView(R.id.txt_date_lo)
    protected TextView txtDateLo;
    @BindView(R.id.txt_number_lo)
    protected TextView txtNumberLo;
    @BindView(R.id.txt_point_lo)
    protected TextView txtPointLo;
    @BindView(R.id.recycler_view_lo)
    protected RecyclerView recyclerViewLo;

    @BindView(R.id.txt_date_lo_xien_2)
    protected TextView txtDateLoXien2;
    @BindView(R.id.txt_number_lo_xien_2)
    protected TextView txtNumberLoXien2;
    @BindView(R.id.txt_point_lo_xien_2)
    protected TextView txtPointLoXien2;
    @BindView(R.id.recycler_view_lo_xien_2)
    protected RecyclerView recyclerViewLoXien2;

    @BindView(R.id.txt_date_lo_xien_3)
    protected TextView txtDateLoXien3;
    @BindView(R.id.txt_number_lo_xien_3)
    protected TextView txtNumberLoXien3;
    @BindView(R.id.txt_point_lo_xien_3)
    protected TextView txtPointLoXien3;
    @BindView(R.id.recycler_view_lo_xien_3)
    protected RecyclerView recyclerViewLoXien3;

    @BindView(R.id.txt_date_lo_xien_4)
    protected TextView txtDateLoXien4;
    @BindView(R.id.txt_number_lo_xien_4)
    protected TextView txtNumberLoXien4;
    @BindView(R.id.txt_point_lo_xien_4)
    protected TextView txtPointLoXien4;
    @BindView(R.id.recycler_view_lo_xien_4)
    protected RecyclerView recyclerViewLoXien4;
    //
    private List<DeModel> listDeModel;
    private List<LoModel> listLoModel;
    private List<BaCangModel> listBaCangModel;
    private List<LoXien2Model> listLoXien2Model;
    private List<LoXien3Model> listLoXien3Model;
    private List<LoXien4Model> listLoXien4Model;
    //
    private ListDeDetailAdapter listDeDetailAdapter;
    private ListLoDetailAdapter listLoDetailAdapter;
    private ListBaCangDetailAdapter listBaCangDetailAdapter;
    private ListLoXien2DetailAdapter listLoXien2DetailAdapter;
    private ListLoXien3DetailAdapter listLoXien3DetailAdapter;
    private ListLoXien4DetailAdapter listLoXien4DetailAdapter;
    //database
    private DBContext dbContext;
    //
    private Locale locale;
    private NumberFormat currencyFormatter;
    private PreferenceUtil preferenceUtil;
    //
    private StaggeredGridLayoutManager layoutManager;
    private String date;
    private int number;
    private int price;
    private float point;
    private int number1;
    private int number2;
    private int number3;
    private int number4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        init();
        getDataFromIntent();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        this.locale = new Locale("vi", "VN");
        this.currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        this.preferenceUtil = PreferenceUtil.getInst(this);
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int type = bundle.getInt(AdminHomeFragment.TYPE);
            switch (type) {
                case AdminHomeFragment.TYPE_DE:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number = bundle.getInt(AdminHomeFragment.NUMBER);
                    txtDateDe.setText(date);
                    txtNumberDe.setText(String.format("Số: %02d", number));
                    listDeModel = dbContext.getAllDeModelByNumberAndDate(number, date);
                    price = 0;
                    for (DeModel model : listDeModel) {
                        price += model.getPrice();
                    }
                    txtPriceDe.setText(String.format("Tổng tiền: %s", currencyFormatter.format(price)));
                    listDeDetailAdapter = new ListDeDetailAdapter(this, listDeModel);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewDe.setLayoutManager(layoutManager);
                    recyclerViewDe.setAdapter(listDeDetailAdapter);
                    viewFlipper.setDisplayedChild(0);
                    break;
                case AdminHomeFragment.TYPE_BA_CANG:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number = bundle.getInt(AdminHomeFragment.NUMBER);
                    txtDateBaCang.setText(date);
                    txtNumberBaCang.setText(String.format("Số: %03d", number));
                    listBaCangModel = dbContext.getAllBaCangModelByNumberAndDate(number, date);
                    price = 0;
                    for (BaCangModel model : listBaCangModel) {
                        price += model.getPrice();
                    }
                    txtPriceBaCang.setText(String.format("Tổng tiền: %s", currencyFormatter.format(price)));
                    listBaCangDetailAdapter = new ListBaCangDetailAdapter(this, listBaCangModel);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewBaCang.setLayoutManager(layoutManager);
                    recyclerViewBaCang.setAdapter(listBaCangDetailAdapter);
                    viewFlipper.setDisplayedChild(1);
                    break;
                case AdminHomeFragment.TYPE_LO:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number = bundle.getInt(AdminHomeFragment.NUMBER);
                    txtDateLo.setText(date);
                    txtNumberLo.setText(String.format("Số: %02d", number));
                    listLoModel = dbContext.getAllLoModelByNumberAndDate(number, date);
                    point = 0;
                    for (LoModel model : listLoModel) {
                        point += model.getPoint();
                    }
                    txtPointLo.setText(String.format("Tổng điểm: %.1f\nTổng tiền: %s", point,
                            currencyFormatter.format(point * preferenceUtil.getLoPriceNhan())));
                    listLoDetailAdapter = new ListLoDetailAdapter(this, listLoModel);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewLo.setLayoutManager(layoutManager);
                    recyclerViewLo.setAdapter(listLoDetailAdapter);
                    viewFlipper.setDisplayedChild(2);
                    break;
                case AdminHomeFragment.TYPE_LO_XIEN_2:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number1 = bundle.getInt(AdminHomeFragment.NUMBER1);
                    number2 = bundle.getInt(AdminHomeFragment.NUMBER2);
                    txtDateLoXien2.setText(date);
                    txtNumberLoXien2.setText(String.format("Số: %02d, %02d", number1, number2));
                    listLoXien2Model = dbContext.getAllLoXien2ModelByNumberAndDate(number1, number2, date);
                    point = 0;
                    for (LoXien2Model model : listLoXien2Model) {
                        point += model.getPoint();
                    }
                    txtPointLoXien2.setText(String.format("Tổng điểm: %.1f\nTổng tiền: %s", point,
                            currencyFormatter.format(point * preferenceUtil.getLoXien2PriceNhan())));
                    listLoXien2DetailAdapter = new ListLoXien2DetailAdapter(this, listLoXien2Model);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewLoXien2.setLayoutManager(layoutManager);
                    recyclerViewLoXien2.setAdapter(listLoXien2DetailAdapter);
                    viewFlipper.setDisplayedChild(3);
                    break;
                case AdminHomeFragment.TYPE_LO_XIEN_3:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number1 = bundle.getInt(AdminHomeFragment.NUMBER1);
                    number2 = bundle.getInt(AdminHomeFragment.NUMBER2);
                    number3 = bundle.getInt(AdminHomeFragment.NUMBER3);
                    txtDateLoXien3.setText(date);
                    txtNumberLoXien3.setText(String.format("Số: %02d, %02d, %02d", number1, number2, number3));
                    listLoXien3Model = dbContext.getAllLoXien3ModelByNumberAndDate(number1, number2, number3, date);
                    point = 0;
                    for (LoXien3Model model : listLoXien3Model) {
                        point += model.getPoint();
                    }
                    txtPointLoXien3.setText(String.format("Tổng điểm: %.1f\nTổng tiền: %s", point,
                            currencyFormatter.format(point * preferenceUtil.getLoXien3PriceNhan())));
                    listLoXien3DetailAdapter = new ListLoXien3DetailAdapter(this, listLoXien3Model);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewLoXien3.setLayoutManager(layoutManager);
                    recyclerViewLoXien3.setAdapter(listLoXien3DetailAdapter);
                    viewFlipper.setDisplayedChild(4);
                    break;
                case AdminHomeFragment.TYPE_LO_XIEN_4:
                    date = bundle.getString(AdminHomeFragment.DATE);
                    number1 = bundle.getInt(AdminHomeFragment.NUMBER1);
                    number2 = bundle.getInt(AdminHomeFragment.NUMBER2);
                    number3 = bundle.getInt(AdminHomeFragment.NUMBER3);
                    number4 = bundle.getInt(AdminHomeFragment.NUMBER4);
                    txtDateLoXien4.setText(date);
                    txtNumberLoXien4.setText(String.format("Số: %02d, %02d, %02d, %02d", number1, number2, number3, number4));
                    listLoXien4Model = dbContext.getAllLoXien4ModelByNumberAndDate(number1, number2, number3, number4, date);
                    point = 0;
                    for (LoXien4Model model : listLoXien4Model) {
                        point += model.getPoint();
                    }
                    txtPointLoXien4.setText(String.format("Tổng điểm: %.1f\nTổng tiền: %s", point,
                            currencyFormatter.format(point * preferenceUtil.getLoXien4PriceNhan())));
                    listLoXien4DetailAdapter = new ListLoXien4DetailAdapter(this, listLoXien4Model);
                    layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    recyclerViewLoXien4.setLayoutManager(layoutManager);
                    recyclerViewLoXien4.setAdapter(listLoXien4DetailAdapter);
                    viewFlipper.setDisplayedChild(5);
                    break;
                default:
                    break;
            }
        }
    }
}
