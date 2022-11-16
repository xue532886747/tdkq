package com.example.towerdriver.repair.base_warehouse.ui.fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.base_setmenu.ui.activity.SelectMenuActivity;
import com.example.towerdriver.dialog.BuyDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.repair.base_warehouse.ui.adapter.RightAdapter;
import com.example.towerdriver.repair.base_warehouse.model.MountBean;
import com.example.towerdriver.repair.base_warehouse.presenter.WareHouseListPresenter;
import com.example.towerdriver.repair.base_warehouse.ui.adapter.LeftAdapter;
import com.example.towerdriver.repair.base_warehouse.view.IRepairWareHouseView;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description 维修仓库列表
 * @date 2021/7/4
 */
public class RepairWarehouseFragment extends BaseFragment<WareHouseListPresenter> implements IRepairWareHouseView {

    @BindView(R.id.rv_left)
    RecyclerView rv_left;
    @BindView(R.id.rv_right)
    RecyclerView rv_right;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    public static RepairWarehouseFragment newInstance() {
        return new RepairWarehouseFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repair_warehouse;
    }

    @Override
    protected WareHouseListPresenter createPresenter() {
        return new WareHouseListPresenter(this);
    }


    @Override
    protected void initView() {
        rv_right.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rv_left.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        leftAdapter = new LeftAdapter();
        rv_left.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter();
        rv_right.setAdapter(rightAdapter);
        rightAdapter.addChildClickViewIds(R.id.iv_sub, R.id.iv_good_add, R.id.et_number);
        leftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                leftAdapter.setSelect_num(position);
                Object o = adapter.getData().get(position);
                if (o instanceof MountBean.VarietyListBean) {
                    sendList(((MountBean.VarietyListBean) o).getId(), false);
                }
            }
        });
        rightAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_good_add:               //减
                        KeyboardUtils.hideKeyboard(view);
                        Object item = adapter.getItem(position);
                        if (item instanceof MountBean.MountingsListBean) {
                            addNumber(position, ((MountBean.MountingsListBean) item).getId() + "", ((MountBean.MountingsListBean) item).getNum());
                        }
                        break;
                    case R.id.iv_sub:          //加
                        KeyboardUtils.hideKeyboard(view);
                        Object item1 = adapter.getItem(position);
                        if (item1 instanceof MountBean.MountingsListBean) {
                            subNumber(position, ((MountBean.MountingsListBean) item1).getId() + "", ((MountBean.MountingsListBean) item1).getNum());
                        }
                        break;
                    case R.id.et_number:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.et_number)) {
                            Object item2 = adapter.getItem(position);
                            if (item2 instanceof MountBean.MountingsListBean) {
                                createDialog(position, ((MountBean.MountingsListBean) item2).getId() + "", ((MountBean.MountingsListBean) item2).getNum()
                                ,((MountBean.MountingsListBean) item2).getName());
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onVisible(boolean isFirstVisible) {
        sendList(0, true);
    }

    @Override
    protected void initData() {

    }

    /**
     * 请求数据
     *
     * @param id
     * @param isFresh
     */
    public void sendList(int id, boolean isFresh) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在加载..");
            presenter.getMenuList(id, isFresh);
        }
    }

    /**
     * 弹窗
     *
     * @param num
     */
    private void createDialog(int position, String mountings_id, Integer num,String title) {
        new BuyDialog(mContext).Builder(num + "","修改"+title+"数量").setDialogClickListener(new BuyDialog.DialogClickListener() {
            @Override
            public void cancle() {

            }

            @Override
            public void confirm(String number) {
                getPrice(position, mountings_id, (Integer.parseInt(number)));
            }
        }).show();
    }

    /**
     * 计算价格
     */
    private void getPrice(int position, String mountings_id, int number) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在刷新...");
            presenter.getMountNumber(position, mountings_id, String.valueOf(number));

        }
    }


    /**
     * 增加数量
     */
    private void addNumber(int position, String mountings_id, Integer num) {
        if (num >= 999) {
            ToastUtils.show("不能在加了");
            return;
        }
        num += 1;
        getPrice(position, mountings_id, num);
    }

    /**
     * 减少数量
     */
    private void subNumber(int position, String mountings_id, Integer num) {
        if (num <= 0) {
            ToastUtils.show("不能在减了");
            return;
        }
        num -= 1;
        getPrice(position, mountings_id, num);
    }


    /**
     * 仓库列表
     *
     * @param mountBean 数据
     * @param isRefresh 是否刷新
     */
    @Override
    public void onRepairWareHouseSuccess(MountBean mountBean, boolean isRefresh) {
        closeDialog();
        List<MountBean.VarietyListBean> classify = mountBean.getVariety_list();
        leftAdapter.setList(classify);
        List<MountBean.MountingsListBean> rent_list = mountBean.getMountings_list();
        rightAdapter.setList(rent_list);
        if (isRefresh) {
            leftAdapter.setSelect_num(0);
        }
        if (rent_list != null) {
            if (rent_list.size() == 0) {
                checkVisible(1);
            } else {
                checkVisible(2);
            }
        } else {
            checkVisible(1);
        }
    }

    @Override
    public void onRepairWareHouseFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void onRepairNumSuccess(String num, int position) {
        closeDialog();
        rightAdapter.getData().get(position).setNum(Integer.parseInt(num));
        rightAdapter.notifyItemChanged(position);
    }

    @Override
    public void onRepairNumFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    /**
     * 检查是否显示
     *
     * @param type
     */
    private void checkVisible(int type) {
        if (type == 1) {
            ll_noting.setVisibility(View.VISIBLE);
            rv_right.setVisibility(View.GONE);
        } else {
            ll_noting.setVisibility(View.GONE);
            rv_right.setVisibility(View.VISIBLE);
        }
    }


}
