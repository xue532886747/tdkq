package com.example.towerdriver.utils.timeutil;

/**
 * @author 53288
 * @description
 * @date 2021/6/2
 */
public interface ITimeOver {

    void onFinish();

    void onTick(long millisUntilFinished);
}
