package cn.com.notice.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.base.MyTitle;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;

import cn.com.notice.R;
import cn.com.notice.Utils.MyRequest;
import cn.com.notice.adapter.DialogListViewAdapter;
import cn.com.notice.adapter.NoticeDetalPhotoAdapter;
import cn.com.notice.bean.NoticeDetailBean;
import cn.com.notice.interfaces.NoticeDetailInterface;

/**
 * 文件名：NoticeDetailActivity
 * 描    述：通知详情类
 * 作    者：stt
 * 时    间：2017.1.6
 * 版    本：V1.0.0
 */

public class NoticeDetailActivity extends BaseActivity implements View.OnClickListener, NoticeDetailInterface {
    private TextView numberText, nameText, typeText, stateText, senderText, sendTimeText, fileNameText, filePath, infoText;
    private NoticeDetailBean rowsBean = new NoticeDetailBean();
    private String path;
    private GridView gridView;
    private ArrayList<String> describeList = new ArrayList<String>();
    private NoticeDetalPhotoAdapter noticeDetalPhotoAdapter;
    private RelativeLayout notice_detail_state_fileLayout;
    private ArrayList<NoticeDetailBean.FileListBean> list;
    private DialogListViewAdapter adapter;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_notice_detail);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        MyTitle.getInstance().setTitle(this, "通告详情", PGApp, true);
        String id = getIntent().getStringExtra("noticeId");
        MyRequest.getNoticeDetailRequest(this, id);
    }

    @Override
    protected void init() {
        numberText = (TextView) findViewById(R.id.notice_detail_number);
        nameText = (TextView) findViewById(R.id.notice_detail_name);
        typeText = (TextView) findViewById(R.id.notice_detail_type);
        stateText = (TextView) findViewById(R.id.notice_detail_state);
        senderText = (TextView) findViewById(R.id.notice_detail_sender);
        sendTimeText = (TextView) findViewById(R.id.notice_detail_sendTime);
        fileNameText = (TextView) findViewById(R.id.notice_detail_fileName);
        filePath = (TextView) findViewById(R.id.notice_detail_attachment);
        filePath.setOnClickListener(this);
        infoText = (TextView) findViewById(R.id.notice_detail_info);
        gridView = (GridView) findViewById(R.id.notice_detail_gridView_describe);
        //new add 2017年5月23日09:44:15
        notice_detail_state_fileLayout = (RelativeLayout) findViewById(R.id.notice_detail_state_fileLayout);
        notice_detail_state_fileLayout.setOnClickListener(this);
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        MyTitle.getInstance().setNetText(this, netMobile);
    }

    private boolean isAttachment() {
        boolean isFlag = false;
        path = Environment.getExternalStorageDirectory() + "/ParkGuard/";
        for (NoticeDetailBean.FileListBean fileBean : rowsBean.getFileList()) {
            if (fileBean.getAttachmentType() == 2) {
                if (MyUtils.getVideoFileName(path).size() > 0) {
                    for (String fileUrl : MyUtils.getVideoFileName(path)) {
                        if (fileUrl.equals(fileBean.getFileName())) {
                            isFlag = true;
                        }
                    }
                }
            }
        }
        return isFlag;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        } else if (i == R.id.notice_detail_attachment) {
           /* if (isEmpty()) {
                if (isAttachment()) {
                    for (NoticeDetailBean.FileListBean fileBean : rowsBean.getFileList()) {
                        if (fileBean.getAttachmentType() == 2) {
                            if (MySerUtils.getVideoFileName(path).size() > 0) {
                                for (String fileUrl : MySerUtils.getVideoFileName(path)) {
                                    if (fileUrl.equals(fileBean.getFileName())) {
                                        File files = new File(path + fileUrl);// 这里更改为你的名称
                                        Log.i("fileName", fileUrl + "=======" + files.getPath());
                                        Uri path = Uri.fromFile(files);
                                        Log.i("fileName", path.toString());
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(path, "application*//*");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        try {
                                            startActivity(intent);
                                        } catch (ActivityNotFoundException e) {
                                            ToastUtil.show(this, "出现异常，请稍候再试");
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (NoticeDetailBean.FileListBean fileBean : rowsBean.getFileList()) {
                        if (fileBean.getAttachmentType() == 2) {
                            DownloadUtil down = new DownloadUtil(NoticeDetailActivity.this, fileBean.getFileName(), fileBean.getFileUrl(), filePath);
                            down.showDownloadDialog();
                        }
                    }
                }
            }*/

        }
        if (i == R.id.notice_detail_state_fileLayout) {
            createDialog().show();
        }
    }

    AlertDialog dlg;
    AlertDialog.Builder builder;

    private AlertDialog createDialog() {
        dismiss();
        builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View layout = layoutInflater.inflate(R.layout.dialog_list, null);
        ListView listview = (ListView) layout.findViewById(R.id.lv_dialogListView);
        adapter = new DialogListViewAdapter(NoticeDetailActivity.this, list, filePath);
        listview.setAdapter(adapter);
        builder.setView(layout);
        builder.setTitle("文件预览");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
//                Toast.makeText(getApplication(), "" + list.size(), Toast.LENGTH_SHORT).show();
            }
        });
        dlg = builder.create();
//        dlg.show();
        return dlg;
    }

    public void dismiss() {
        if (dlg != null && dlg.isShowing()) {
            dlg.dismiss();
        }
    }

    private boolean isEmpty() {
        if (rowsBean.getFileList().size() == 0) {
            ToastUtil.show(this, "没有可下载的文件");
            return false;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + "/ParkGuard";
            return true;
        } else {
            ToastUtil.show(this, "没有SD卡");
            return false;
        }
    }

    @Override
    public void getNoticeDetail(NoticeDetailBean noticeDetailBean) {
        list = new ArrayList<>();
        rowsBean = noticeDetailBean;
        numberText.setText(rowsBean.getInformSno());
        nameText.setText(rowsBean.getName());
        typeText.setText(rowsBean.getInformTypeName());
        stateText.setText(rowsBean.getStateName());
        senderText.setText(rowsBean.getPersonName());
        sendTimeText.setText(rowsBean.getApiCreateTime());
        filePath.setText(isAttachment() ? "打开" : "下载");
        for (NoticeDetailBean.FileListBean fileBean : rowsBean.getFileList()) {
            if (fileBean.getAttachmentType() == 2) {
                fileNameText.setText("文件(" + fileBean.getFileName() + ")");
                list.add(fileBean);
            }
            if (fileBean.getAttachmentType() == 1) {
                describeList.add(fileBean.getFileUrl());
            }
        }
        if (TextUtils.isEmpty(fileNameText.getText().toString().trim())) {
            findViewById(R.id.notice_detail_state_fileLayout).setVisibility(View.GONE);
        }
        infoText.setText(rowsBean.getContentInfo());
        noticeDetalPhotoAdapter = new NoticeDetalPhotoAdapter(this, describeList);
        gridView.setAdapter(noticeDetalPhotoAdapter);
    }

//    @Override
//    public void fileDownLoadOrOpenInterface(int position) {
////        String fileName = list.get(position).getFileName();
////        path = Environment.getExternalStorageDirectory() + "/ParkGuard/";
////        MySerUtils.getVideoFileName(path);
//        if (getFileExist(position)) {
//            File files = new File(path + list.get(position).getFileName());// 这里更改为你的名称
//            Log.i("fileName", "=======" + files.getPath());
//            Uri path = Uri.fromFile(files);
//            Log.i("fileName", path.toString());
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(path, "application/*");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            try {
//                startActivity(intent);
//            } catch (ActivityNotFoundException e) {
//                ToastUtil.show(this, "出现异常，请稍候再试");
//            }
//        } else {
//            DownloadUtil down = new DownloadUtil(NoticeDetailActivity.this, list.get(position).getFileName(), list.get(position).getFileUrl(), filePath );
//
//            down.showDownloadDialog();
////            dismiss();
//            createDialog().show();
//        }
//    }

    public boolean getFileExist(int position) {
        boolean isFlag = false;
        if (MyUtils.getVideoFileName(path).size() > 0) {
            for (String fileUrl : MyUtils.getVideoFileName(path)) {
                if (fileUrl.equals(list.get(position).getFileName())) {
                    isFlag = true;
                }
            }
        }
        return isFlag;
    }
}
