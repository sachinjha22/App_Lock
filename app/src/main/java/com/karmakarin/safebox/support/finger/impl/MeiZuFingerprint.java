package com.karmakarin.safebox.support.finger.impl;


import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.fingerprints.service.FingerprintManager;
import com.karmakarin.safebox.support.finger.BaseFingerprint;

public class MeiZuFingerprint extends BaseFingerprint {

    private FingerprintManager mMeiZuFingerprintManager;

    public MeiZuFingerprint(Context context, ExceptionListener exceptionListener) {
        super(context, exceptionListener);

        try {
            mMeiZuFingerprintManager = FingerprintManager.open();
            if (mMeiZuFingerprintManager != null) {
                setHardwareEnable(isMeiZuDevice());
                int[] fingerprintIds = mMeiZuFingerprintManager.getIds();
                setRegisteredFingerprint(fingerprintIds != null && fingerprintIds.length > 0);
            }
        } catch (Throwable e) {
            onCatchException(e);
        }

        releaseMBack();
    }

    @Override
    protected void doIdentify() {
        try {
            mMeiZuFingerprintManager = FingerprintManager.open();
            mMeiZuFingerprintManager.startIdentify(new FingerprintManager.IdentifyCallback() {
                @Override
                public void onIdentified(int i, boolean b) {
                    onSucceed();
                }

                @Override
                public void onNoMatch() {
                    onNotMatch();
                }
            }, mMeiZuFingerprintManager.getIds());
        } catch (Throwable e) {
            onCatchException(e);
            onFailed(false);
        }
    }

    @Override
    protected void doCancelIdentify() {
        releaseMBack();
    }

    private void releaseMBack() {
        try {
            if (mMeiZuFingerprintManager != null) {
                mMeiZuFingerprintManager.release();
            }
        } catch (Throwable e) {
            onCatchException(e);
        }
    }

    private boolean isMeiZuDevice() {
        return !TextUtils.isEmpty(Build.MANUFACTURER) && Build.MANUFACTURER.toUpperCase().contains("MEIZU");
    }
}
