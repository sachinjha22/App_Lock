package com.karmakarin.safebox.support.finger;

import android.content.Context;

import com.karmakarin.safebox.support.finger.impl.AndroidFingerprint;
import com.karmakarin.safebox.support.finger.impl.MeiZuFingerprint;
import com.karmakarin.safebox.support.finger.impl.SamsungFingerprint;

public class FingerprintIdentify {

    protected Context mContext;
    protected BaseFingerprint.ExceptionListener mExceptionListener;

    protected boolean mIsSupportAndroidL = false;

    protected BaseFingerprint mFingerprint;
    protected BaseFingerprint mSubFingerprint;

    public FingerprintIdentify(Context context) {
        mContext = context;
    }

    public void setSupportAndroidL(boolean supportAndroidL) {
        mIsSupportAndroidL = supportAndroidL;
    }

    public void setExceptionListener(BaseFingerprint.ExceptionListener exceptionListener) {
        mExceptionListener = exceptionListener;
    }

    public void init() {
        AndroidFingerprint androidFingerprint = new AndroidFingerprint(mContext, mExceptionListener, mIsSupportAndroidL);
        if (androidFingerprint.isHardwareEnable()) {
            mSubFingerprint = androidFingerprint;
            if (androidFingerprint.isRegisteredFingerprint()) {
                mFingerprint = androidFingerprint;
                return;
            }
        }

        SamsungFingerprint samsungFingerprint = new SamsungFingerprint(mContext, mExceptionListener);
        if (samsungFingerprint.isHardwareEnable()) {
            mSubFingerprint = samsungFingerprint;
            if (samsungFingerprint.isRegisteredFingerprint()) {
                mFingerprint = samsungFingerprint;
                return;
            }
        }

        MeiZuFingerprint meiZuFingerprint = new MeiZuFingerprint(mContext, mExceptionListener);
        if (meiZuFingerprint.isHardwareEnable()) {
            mSubFingerprint = meiZuFingerprint;
            if (meiZuFingerprint.isRegisteredFingerprint()) {
                mFingerprint = meiZuFingerprint;
            }
        }
    }

    // DO
    public void startIdentify(int maxAvailableTimes, BaseFingerprint.IdentifyListener listener) {
        if (!isFingerprintEnable()) {
            return;
        }

        mFingerprint.startIdentify(maxAvailableTimes, listener);
    }

    public void cancelIdentify() {
        if (mFingerprint != null) {
            mFingerprint.cancelIdentify();
        }
    }

    public void resumeIdentify() {
        if (!isFingerprintEnable()) {
            return;
        }

        mFingerprint.resumeIdentify();
    }

    // GET & SET
    public boolean isFingerprintEnable() {
        return mFingerprint != null && mFingerprint.isEnable();
    }

    public boolean isHardwareEnable() {
        return isFingerprintEnable() || (mSubFingerprint != null && mSubFingerprint.isHardwareEnable());
    }

    public boolean isRegisteredFingerprint() {
        return isFingerprintEnable() || (mSubFingerprint != null && mSubFingerprint.isRegisteredFingerprint());
    }
}
