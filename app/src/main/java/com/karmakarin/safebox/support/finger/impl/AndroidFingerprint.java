package com.karmakarin.safebox.support.finger.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;

import androidx.annotation.NonNull;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.karmakarin.safebox.support.finger.BaseFingerprint;

public class AndroidFingerprint extends BaseFingerprint {

    private CancellationSignal mCancellationSignal;
    private FingerprintManagerCompat mFingerprintManagerCompat;

    @SuppressLint("RestrictedApi")
    public AndroidFingerprint(Context context, ExceptionListener exceptionListener, boolean iSupportAndroidL) {
        super(context, exceptionListener);

        if (!iSupportAndroidL) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return;
            }
        }

        try {
            mFingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
            setHardwareEnable(mFingerprintManagerCompat.isHardwareDetected());
            setRegisteredFingerprint(mFingerprintManagerCompat.hasEnrolledFingerprints());
        } catch (Throwable e) {
            onCatchException(e);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void doIdentify() {
        try {
            mCancellationSignal = new CancellationSignal();
            mFingerprintManagerCompat.authenticate(null, 0, mCancellationSignal, new FingerprintManagerCompat.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    onSucceed();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    onNotMatch();
                }

                @Override
                public void onAuthenticationError(int errMsgId, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);

                    if (errMsgId == FingerprintManager.FINGERPRINT_ERROR_CANCELED ||
                            errMsgId == FingerprintManager.FINGERPRINT_ERROR_USER_CANCELED) {
                        return;
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                        onFailed(errMsgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT ||
                                errMsgId == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT_PERMANENT);
                    }
                }
            }, null);
        } catch (Throwable e) {
            onCatchException(e);
            onFailed(false);
        }
    }

    @Override
    protected void doCancelIdentify() {
        try {
            if (mCancellationSignal != null) {
                mCancellationSignal.cancel();
            }
        } catch (Throwable e) {
            onCatchException(e);
        }
    }

    @Override
    protected boolean needToCallDoIdentifyAgainAfterNotMatch() {
        return false;
    }
}
