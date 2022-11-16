package com.example.towerdriver.base_rescue.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_rescue.model.RescueListBean;

import java.util.List;

/**
 * @author 53288
 * @description 救援评价
 * @date 2021/6/21
 */
public interface IRescueCommentView extends BaseView {

    /**
     * 救援评价成功
     */
    void rescueCommentSuccess(String msg);

    /**
     * 救援评价失败
     *
     * @param msg
     */
    void rescueCommentFailure(String msg);

}
