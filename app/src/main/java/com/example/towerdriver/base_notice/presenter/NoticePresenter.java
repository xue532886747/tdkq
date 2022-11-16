package com.example.towerdriver.base_notice.presenter;

import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_member_level.view.MemberLevelView;
import com.example.towerdriver.base_notice.view.INoticeView;

/**
 * @author 53288
 * @description 消息通知
 * @date 2021/5/31
 */
public class NoticePresenter extends BasePresenter<INoticeView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public NoticePresenter(INoticeView baseView) {
        super(baseView);
    }
}
