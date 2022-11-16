package com.example.towerdriver.utils.timeutil;

import android.os.CountDownTimer;

/**
 * @author 53288
 * @description 针对CountDownTimer的封装，会产生内存泄露,不要了
 * @date 2021/8/19
 */
public class MainCodeTimeUtils {
    private CountDownTimer countDownTimer;
    private ITimeOver iTimeOver;

    public MainCodeTimeUtils(ITimeOver iTimeOver, long time) {
        this.iTimeOver = iTimeOver;
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                iTimeOver.onTick(millisUntilFinished );
            }

            @Override
            public void onFinish() {
                iTimeOver.onFinish();
            }
        };
    }

    public void start() {
        if (countDownTimer != null) {
            countDownTimer.start();
        }
    }

    public void cancel() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (iTimeOver != null) {
            iTimeOver = null;
        }
    }

}
