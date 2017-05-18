package cn.com.watchman.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.linked.erfli.library.function.scan.CaptureActivity;

/**
 * Created by 志强 on 2017.5.15.
 */

public class WatchManQRcodeActivity extends CaptureActivity {
    protected Activity mActivity = this;

    private AlertDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void handleResult(final String resultString) {
        Log.i("", "111111===1111:" + resultString);
        if (TextUtils.isEmpty(resultString)) {
            restartPreview();
        } else {
            if (mDialog == null) {
                mDialog = new AlertDialog.Builder(mActivity)
                        .setMessage(resultString)
                        .setPositiveButton("确定", null)
                        .create();
                mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Intent intent = new Intent();
                        intent.putExtra("value", resultString);
                        intent.setClass(mActivity, QRcodeShowActivity.class);
                        startActivity(intent);
                        restartPreview();
                    }
                });
            }
            if (!mDialog.isShowing()) {
                mDialog.setMessage(resultString);
                mDialog.show();
            }
        }
    }
}
