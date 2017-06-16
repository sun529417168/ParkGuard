package cn.com.watchman.chatui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.com.watchman.R;
import cn.com.watchman.chatui.ChatWarningActivity;
import cn.com.watchman.chatui.base.BaseFragment;
import cn.com.watchman.chatui.enity.MessageInfo;
import cn.com.watchman.chatui.uiutils.Constants;


/**
 * 描述:聊天页面功能选择Fragment(拍照,相册选择图片)
 * 作者：zzq
 * 修改时间：2017年6月13日14:18:06
 * 版本:V1.0.0
 */
public class ChatFunctionFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;
    private Uri imageUri;
    private TextView chat_function_photograph;
    private TextView chat_function_photo;
    private TextView chat_function_warning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_chat_function, container, false);
            chat_function_photograph = (TextView) rootView.findViewById(R.id.chat_function_photograph);
            chat_function_photo = (TextView) rootView.findViewById(R.id.chat_function_photo);
            chat_function_photograph.setOnClickListener(this);
            chat_function_photo.setOnClickListener(this);
            chat_function_warning = (TextView) rootView.findViewById(R.id.chat_function_warning);
            chat_function_warning.setOnClickListener(this);
        }

        return rootView;
    }


    /**
     * 拍照
     */
    private void takePhoto() {
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file = new File(Environment.getExternalStorageDirectory(), "拍照");
        if (!file.exists()) {
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output = new File(file, System.currentTimeMillis() + ".jpg");

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);

    }

    /**
     * 从相册选取图片
     */
    private void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case CROP_PHOTO:
                if (res == Activity.RESULT_OK) {
                    try {
                        MessageInfo messageInfo = new MessageInfo();
                        messageInfo.setImageUrl(imageUri.getPath());
                        Log.i("发送图片后返回的的URL:1", "" + imageUri.getPath());
                        EventBus.getDefault().post(messageInfo);
                    } catch (Exception e) {
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;
            case REQUEST_CODE_PICK_IMAGE:
                if (res == Activity.RESULT_OK) {
                    try {
                        Uri uri = data.getData();
                        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                            Log.i("发送图片后返回的的URL:2", "" + path);
                            MessageInfo messageInfo = new MessageInfo();
                            messageInfo.setImageUrl(path);
                            EventBus.getDefault().post(messageInfo);
                        }
//                        Uri uri = data.getData();
//
//                        Log.i("发送图片后返回的的URL:2", "" + uri);
//
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                    }
                } else {
                    Log.d(Constants.TAG, "失败");
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                toastShow("请同意系统权限后继续");
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                toastShow("请同意系统权限后继续");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.chat_function_photograph) {
//
//
//        } else if (i == R.id.chat_function_photo) {
//
//        }
//    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.chat_function_photograph) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);

            } else {
                takePhoto();
            }

        } else if (i == R.id.chat_function_photo) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE2);

            } else {
                choosePhoto();
            }
        } else if (i == R.id.chat_function_warning) {
            Intent intent = new Intent();
            intent.setClass(getActivity(),ChatWarningActivity.class);
            getActivity().startActivityForResult(intent,2);
        }
    }
}
