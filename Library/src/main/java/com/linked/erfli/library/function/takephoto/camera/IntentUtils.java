package com.linked.erfli.library.function.takephoto.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.linked.erfli.library.function.takephoto.model.CropOptions;


/**
 * Intent工具类用于生成拍照、
 * 从相册选择照片，裁剪照片所需的Intent
 * Author: JPH
 * Date: 2016/6/7 0007 13:41
 */
public class IntentUtils {
    private static final String TAG = IntentUtils.class.getName();

    /**
     * 获取裁剪照片的Intent
     * @param targetUri 要裁剪的照片
     * @param outPutUri 裁剪完成的照片
     * @param options 裁剪配置
     * @return
     */
    public static Intent getCropIntentWithOtherApp(Uri targetUri, Uri outPutUri, CropOptions options) {
        boolean isReturnData = TUtils.isReturnData();
        Log.w(TAG, "getCaptureIntentWithCrop:isReturnData:" + (isReturnData ? "true" : "false"));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(targetUri, "image/*");
        intent.putExtra("crop", "true");
        if (options.getAspectX() * options.getAspectY() > 0) {
            intent.putExtra("aspectX", options.getAspectX());
            intent.putExtra("aspectY", options.getAspectY());
        }
        if (options.getOutputX() * options.getOutputY() > 0) {
            intent.putExtra("outputX", options.getOutputX());
            intent.putExtra("outputY", options.getOutputY());
        }
        /*// aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);*/
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        intent.putExtra("return-data", isReturnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        return intent;
    }

    /**
     * 获取拍照的Intent
     * @return
     */
    public static Intent getCaptureIntent(Uri outPutUri) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);//将拍取的照片保存到指定URI
        return intent;
    }
    /**
     * 获取选择照片的Intent
     * @return
     */
    public static Intent getPickIntentWithGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
        intent.setType("image/*");//从所有图片中进行选择
        return intent;
    }
    /**
     * 获取从文件中选择照片的Intent
     * @return
     */
    public static Intent getPickIntentWithDocuments() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }
}
