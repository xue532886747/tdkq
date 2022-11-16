package com.example.towerdriver.staff.base_approval.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.staff.base_approval.model.AudioListBean;
import com.example.towerdriver.staff.base_approval.presenter.AudioListPresenter;
import com.example.towerdriver.staff.base_approval.view.IAudioListView;
import com.example.towerdriver.staff.base_approval.model.NewDepartmentListBean;
import com.example.towerdriver.staff.base_approval.ui.adapter.AudioListAdapter;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 审批人列表
 * @date 2021/7/8
 */
public class AudioListActivity extends BaseActivity<AudioListPresenter> implements IAudioListView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.cl_bottom)
    ConstraintLayout cl_bottom;
    @BindView(R.id.tv_number)
    AppCompatTextView tv_number;
    private String audit_id;

    private SmartRefreshUtils mSmartRefreshUtils;
    private AudioListAdapter mAdapter;
    private NewDepartmentListBean mPersonBean;

    @Override
    protected AudioListPresenter createPresenter() {
        return new AudioListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_audio_list;
    }

    @Override
    protected void initView() {
        audit_id = getIntent().getStringExtra("audit_id");
        LogUtils.d("audit_id = " + audit_id);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new AudioListAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.setAnimationEnable(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
    }

    @Override
    protected void initData() {
        tv_number.setText("已选择: " + 0 + " 人");
        if (rv_list.getItemDecorationCount() == 0) {
            rv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = 20;
                }
            });
        }
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof NewDepartmentListBean) {
                    setNumber(mAdapter.getData(), position);
                }
            }
        });
        sendRequest();
    }

    private void setNumber(List<NewDepartmentListBean> data, int position) {
        int number = 0;
        int pos = 0;
        mPersonBean = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSelect()) {
                data.get(i).setSelect(false);
                pos = i;
            }
        }
        mAdapter.notifyItemChanged(pos);
        if (pos != position) {
            NewDepartmentListBean newDepartmentListBean = mAdapter.getData().get(position);
            boolean select = newDepartmentListBean.isSelect();
            newDepartmentListBean.setSelect(!select);
            mAdapter.notifyItemChanged(position);
            mPersonBean = newDepartmentListBean;
            if (newDepartmentListBean.isSelect()) {
                number++;
            }
        }
        tv_number.setText("已选择: " + number + " 人");
    }

    /**
     * 下拉刷新
     */
    private void sendRequest() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.getAudioList();
        }
    }

    @OnClick({R.id.ll_back, R.id.bt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_commit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    commit();
                }
                break;
        }
    }

    /**
     * 跳转回去
     */
    private void commit() {
        if (mPersonBean != null) {
            Intent intent = getIntent();
            intent.putExtra("data", mPersonBean);
            setResult(401, intent);
            finish();
        } else {
            ToastUtils.show("您还未选择审批人");
        }

    }

    /**
     * @param departmentListBeans 列表数据
     */
    @Override
    public void audioListSuccess(List<AudioListBean.DepartmentListBean> departmentListBeans) {
        closeDialog();
        int number = 0;
        if (mSmartRefreshUtils != null) {
            mSmartRefreshUtils.success();
        }
        cl_bottom.setVisibility(View.VISIBLE);
        List<NewDepartmentListBean> listBeans = new ArrayList<>();
        for (AudioListBean.DepartmentListBean departmentListBean : departmentListBeans) {
            NewDepartmentListBean newDepartmentListBean = new NewDepartmentListBean();
            newDepartmentListBean.setType(1);
            newDepartmentListBean.setDepart_id(departmentListBean.getId());
            newDepartmentListBean.setDepart_name(departmentListBean.getName());
            listBeans.add(newDepartmentListBean);
            for (AudioListBean.DepartmentListBean.StaffListBean staffListBean : departmentListBean.getStaff_list()) {
                NewDepartmentListBean newDepartmentListBean1 = new NewDepartmentListBean();
                if (String.valueOf(staffListBean.getId()).equals(audit_id)) {
                    newDepartmentListBean1.setSelect(true);
                    number++;
                    mPersonBean = newDepartmentListBean1;
                }
                newDepartmentListBean1.setType(2);
                newDepartmentListBean1.setId(staffListBean.getId());
                newDepartmentListBean1.setName(staffListBean.getName());
                newDepartmentListBean1.setImage(staffListBean.getImage());
                listBeans.add(newDepartmentListBean1);
            }
        }
        tv_number.setText("已选择: " + number + " 人");
        mAdapter.setList(listBeans);
    }

    @Override
    public void audioListFailure(String msg) {
        loadingSuccessOrFailure(2);
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void showRefreshNoDate(String msg, List<AudioListBean.DepartmentListBean> departmentListBeans) {
        loadingSuccessOrFailure(1);
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        loadingSuccessOrFailure(2);
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        loadingSuccessOrFailure(2);
        ToastUtils.show(msg);
    }

    /**
     * 根据返回的状态判断显示空还是列表
     *
     * @param loading_type
     */
    private void loadingSuccessOrFailure(int loading_type) {
        if (loading_type == 1) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.success();
            }
            ll_noting.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
        }
    }
}
