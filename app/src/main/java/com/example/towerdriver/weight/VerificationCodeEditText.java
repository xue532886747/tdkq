package com.example.towerdriver.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * @author 53288
 * @description 验证码的EditText
 * @date 2021/5/22
 */
public class VerificationCodeEditText extends AppCompatEditText implements VerificationAction, TextWatcher {

    private static final int DEFAULT_CURSOR_DURATION = 400;

    private int mFigures;                        //需要输入的位数
    private int mVerCodeMargin;                  //验证码之间的间距
    private int mBottomSelectedColor;            //底部选中的颜色
    private int mBottomNormalColor;              //未选中的颜色
    private float mBottomLineHeight;             //底线的高度
    private int mSelectedBackgroundColor;        //选中的背景颜色
    private int mCursorWidth;                    //光标宽度
    private int mCursorColor;                    //光标颜色
    private int mCursorDuration;                 //光标闪烁间隔

    private OnVerificationCodeChangedListener onCodeChangedListener;
    private int mCurrentPosition = 0;
    private int mEachRectLength = 0;            //每个矩形的边长
    private Paint mSelectedBackgroundPaint;     //选择背景的画笔
    private Paint mNormalBackgroundPaint;       //正常背景的画笔
    private Paint mBottomSelectedPaint;         //底部选中的画笔
    private Paint mBottomNormalPaint;           //未选中的画笔
    private Paint mCursorPaint;                 //广标的画笔
    private boolean isCursorShowing;            //广标闪烁
    private TimerTask mCursorTimerTask;
    private Timer mCursorTimer;

    public VerificationCodeEditText(@NonNull Context context) {
        this(context, null);
    }

    public VerificationCodeEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        initPaint();
        initCursorTimer();
        setFocusableInTouchMode(true);
        super.addTextChangedListener(this);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.VerCodeEditText);
        mFigures = ta.getInteger(R.styleable.VerCodeEditText_figures, 4);
        mVerCodeMargin = (int) ta.getDimension(R.styleable.VerCodeEditText_verCodeMargin, 0);
        mBottomSelectedColor = ta.getColor(R.styleable.VerCodeEditText_bottomLineSelectedColor,
                getCurrentTextColor());
        mBottomNormalColor = ta.getColor(R.styleable.VerCodeEditText_bottomLineNormalColor,
                getColor(android.R.color.darker_gray));
        mBottomLineHeight = ta.getDimension(R.styleable.VerCodeEditText_bottomLineHeight,
                DisPlayUtils.dp2px(5));
        mSelectedBackgroundColor = ta.getColor(R.styleable.VerCodeEditText_selectedBackgroundColor,
                getColor(android.R.color.darker_gray));
        mCursorWidth = (int) ta.getDimension(R.styleable.VerCodeEditText_cursorWidth, DisPlayUtils.dp2px(1));
        mCursorColor = ta.getColor(R.styleable.VerCodeEditText_cursorColor, getColor(android.R.color.darker_gray));
        mCursorDuration = ta.getInteger(R.styleable.VerCodeEditText_cursorDuration, DEFAULT_CURSOR_DURATION);
        ta.recycle();
        setLayoutDirection(LAYOUT_DIRECTION_LTR);
    }


    private void initPaint() {
        mSelectedBackgroundPaint = new Paint();
        mSelectedBackgroundPaint.setColor(mSelectedBackgroundColor);
        mNormalBackgroundPaint = new Paint();
        mNormalBackgroundPaint.setColor(getColor(android.R.color.transparent));

        mBottomSelectedPaint = new Paint();
        mBottomNormalPaint = new Paint();
        mBottomSelectedPaint.setColor(mBottomSelectedColor);
        mBottomNormalPaint.setColor(mBottomNormalColor);
        mBottomSelectedPaint.setAntiAlias(true);
        mBottomSelectedPaint.setStrokeCap(Paint.Cap.ROUND);
        mBottomSelectedPaint.setStrokeWidth(mBottomLineHeight);
        mBottomNormalPaint.setStrokeWidth(mBottomLineHeight);
        mBottomNormalPaint.setAntiAlias(true);
        mBottomNormalPaint.setStrokeCap(Paint.Cap.ROUND);

        mCursorPaint = new Paint();
        mCursorPaint.setAntiAlias(true);
        mCursorPaint.setColor(mCursorColor);
        mCursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCursorPaint.setStrokeWidth(mCursorWidth);
    }

    private void initCursorTimer() {
        mCursorTimerTask = new TimerTask() {
            @Override
            public void run() {
                // 通过光标间歇性显示实现闪烁效果
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        mCursorTimer = new Timer();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 启动定时任务，定时刷新实现光标闪烁
        mCursorTimer.scheduleAtFixedRate(mCursorTimerTask, 0, mCursorDuration);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCursorTimer.cancel();
    }


    @Override
    final public void setCursorVisible(boolean visible) {
        super.setCursorVisible(visible);//隐藏光标的显示
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthResult = 0, heightResult = 0;
        //最终的宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            widthResult = widthSize;
        } else {
            widthResult = MyApplication.getScreenWidth();
        }
        //每个矩形形的宽度
        mEachRectLength = (widthResult - (mVerCodeMargin * (mFigures - 1))) / mFigures;
        //最终的高度
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            heightResult = heightSize;
        } else {
            heightResult = mEachRectLength;
        }
        setMeasuredDimension(widthResult, heightResult);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            requestFocus();
            setSelection(getText().length());
            showKeyBoard(getContext());
            return false;
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mCurrentPosition = getText().length();
        int width = mEachRectLength - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        for (int i = 0; i < mFigures; i++) {
            canvas.save();
            int start = width * i + i * mVerCodeMargin;
            int end = width + start;
            if (i == mCurrentPosition) {
                canvas.drawRect(start, 0, end, height, mSelectedBackgroundPaint);
            } else {
                canvas.drawRect(start, 0, end, height, mNormalBackgroundPaint);
            }
            canvas.restore();
        }
        //绘制文字
        String value = getText().toString();
        for (int i = 0; i < value.length(); i++) {
            canvas.save();
            int start = width * i + i * mVerCodeMargin;
            float x = start + width / 2;
            TextPaint paint = getPaint();
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(getCurrentTextColor());
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float baseline = (height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(String.valueOf(value.charAt(i)), x, baseline, paint);
            canvas.restore();
        }
        //绘制底线
        for (int i = 0; i < mFigures; i++) {
            canvas.save();
            float lineY = height - mBottomLineHeight / 2;
            int start = (int) (width * i + i * mVerCodeMargin + mBottomLineHeight / 2);
            int end = (int) (width + start - mBottomLineHeight);
            if (i < mCurrentPosition) {
                canvas.drawLine(start, lineY, end, lineY, mBottomSelectedPaint);
            } else {
                canvas.drawLine(start, lineY, end, lineY, mBottomNormalPaint);
            }
            canvas.restore();
        }
        //绘制光标
        if (!isCursorShowing && isCursorVisible() && mCurrentPosition < mFigures && hasFocus()) {
            canvas.save();
            int startX = mCurrentPosition * (width + mVerCodeMargin) + width / 2;
            int startY = height / 4;
            int endX = startX;
            int endY = height - height / 4;
            canvas.drawLine(startX, startY, endX, endY, mCursorPaint);
            canvas.restore();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mCurrentPosition = getText().length();
        postInvalidate();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mCurrentPosition = getText().length();
        postInvalidate();
        if (onCodeChangedListener != null) {
            onCodeChangedListener.onVerCodeChanged(getText(), start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        mCurrentPosition = getText().length();
        postInvalidate();
        if (getText().length() == mFigures) {
            if (onCodeChangedListener != null) {
                onCodeChangedListener.onInputCompleted(getText());
                KeyboardUtils.hideKeyboard(this);
            }
        } else if (getText().length() > mFigures) {
            getText().delete(mFigures, getText().length());
        }
    }

    /**
     * 返回颜色
     */
    private int getColor(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
    }


    @Override
    public void setFigures(int figures) {
        mFigures = figures;
        postInvalidate();
    }

    @Override
    public void setVerCodeMargin(int margin) {
        mVerCodeMargin = margin;
        postInvalidate();
    }

    @Override
    public void setBottomSelectedColor(int bottomSelectedColor) {
        mBottomSelectedColor = getColor(bottomSelectedColor);
        postInvalidate();
    }

    @Override
    public void setBottomNormalColor(int bottomNormalColor) {
        mBottomSelectedColor = getColor(bottomNormalColor);
        postInvalidate();
    }

    @Override
    public void setSelectedBackgroundColor(int selectedBackground) {
        mSelectedBackgroundColor = getColor(selectedBackground);
        postInvalidate();
    }

    @Override
    public void setBottomLineHeight(int bottomLineHeight) {
        this.mBottomLineHeight = bottomLineHeight;
        postInvalidate();
    }

    @Override
    public void setOnVerificationCodeChangedListener(OnVerificationCodeChangedListener listener) {
        this.onCodeChangedListener = listener;
    }


    public void showKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED);
    }
}
