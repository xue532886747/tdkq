package com.example.towerdriver.base_driver.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description
 * @date 2021/7/8
 */
public interface IDriveReleaseView extends BaseView {
    /**
     * 新建审批发布成功
     *
     * @param msg
     */
    void contentReleaseSuccess(String msg);

    /**
     * 新建审批发布失败
     *
     * @param msg
     */
    void contentReleaseFailure(String msg);


    /**
     * 文件转为url成功
     */
    void ImageToUrlSuccess(String url);

    /**
     * 文件转为url失败
     */
    void ImageToUrlFailure(String msg);
}
