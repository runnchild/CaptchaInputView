package com.qiu.captcha;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

/**
 * Desc: 验证码输入框
 * <p>
 * Author: QiuRonaC
 * Date: 2018-08-27
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class CaptchaInputView extends AppCompatEditText {
    private int mChildWidth;
    private int box = 4;
    private int strokeWidth;
    private RectF mRect = new RectF();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private CharSequence captcha;
    private int mCurPos;
    private Rect mBounds;
    private OnInputCompleteListener mOnInputCompleteListener;

    public CaptchaInputView(Context context) {
        super(context);
    }

    public CaptchaInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CaptchaInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        setPadding(0, 0, 0, 0);
        mBounds = new Rect();
        mChildWidth = isInEditMode() ? 128 : dp2px(64);

        mPaint.setTextSize(strokeWidth = isInEditMode() ? 2 : dp2px(1));
        mPaint.setStyle(Paint.Style.STROKE);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setBackgroundColor(0);
        setOnLongClickListener(v -> true);
        setTextColor(Color.TRANSPARENT);
        setCursorVisible(false);

        setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                focusPosition();
            }
        });
        mTextPaint.setTextSize(isInEditMode() ? 60 : dp2px(30));
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorTextPrimary));
        addTextChangedListener(new DefaultTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    captcha = s;
                    focusPosition();
                    if (s.length() == box && mOnInputCompleteListener != null) {
                        mOnInputCompleteListener.onComplete(s.toString());
                    }
                }
            }
        });
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(box)});
    }

    private int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void focusPosition() {
        mCurPos = captcha == null ? 0 : captcha.length() - 1;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int childWidth = mChildWidth;
        int takeWidth = box * (childWidth);

        int space = 0;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        int releaseWidth = getMeasuredWidth() - paddingLeft - getPaddingRight() - takeWidth;
        if (releaseWidth > 0) {
            space = releaseWidth / (box - 1);
        }
        int childCount = box;
        for (int i = 0; i < childCount; i++) {
            int cl = i * (space + childWidth) + paddingLeft;
            int cr = cl + childWidth;
            int cb = paddingTop + childWidth;
            mRect.set(cl, paddingTop, cr, cb);
            int radius = isInEditMode() ? 20 : dp2px(10);
            mPaint.setColor(mCurPos == i
                    ? ContextCompat.getColor(getContext(), R.color.colorPrimary)
                    : Color.parseColor("#e7e7e7"));
            canvas.drawRoundRect(mRect, radius, radius, mPaint);

            if (captcha != null && captcha.length() > 0 && i < captcha.length()) {
                String s = String.valueOf(captcha.charAt(i));
                mTextPaint.getTextBounds(s, 0, 1, mBounds);
                float x = cl + (childWidth - mBounds.right) * 1f / 2;
                float y = paddingTop + (childWidth + (mBounds.bottom - mBounds.top)) * 1f / 2;
                canvas.drawText(s, x, y, mTextPaint);
            }
        }
    }

    public CharSequence getCaptcha() {
        return captcha;
    }

    public void setOnInputCompleteListener(OnInputCompleteListener onInputCompleteListener) {
        mOnInputCompleteListener = onInputCompleteListener;
    }

    public interface OnInputCompleteListener {
        void onComplete(String captcha);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, resolveSize(mChildWidth + strokeWidth
                + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }
}
