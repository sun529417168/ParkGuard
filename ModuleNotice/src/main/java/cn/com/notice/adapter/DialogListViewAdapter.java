package cn.com.notice.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linked.erfli.library.base.MyBaseAdapter;
import com.linked.erfli.library.utils.DownloadUtil;
import com.linked.erfli.library.utils.MyUtils;
import com.linked.erfli.library.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;

import cn.com.notice.R;
import cn.com.notice.bean.NoticeDetailBean;


/**
 * Created by 志强 on 2017.5.23.
 */

public class DialogListViewAdapter extends MyBaseAdapter {

    Context mContext;
    ArrayList<NoticeDetailBean.FileListBean> list;
    String path = Environment.getExternalStorageDirectory() + "/ParkGuard/";
    //    NoticeFileDownloadOrOpenInterface noticeFileDownloadOrOpenInterface;
    TextView filePath;

    public DialogListViewAdapter(Context mContext, ArrayList<NoticeDetailBean.FileListBean> list, TextView textView) {
        super(mContext, list);
        this.mContext = mContext;
        this.list = list;

        this.filePath = textView;
    }

    @Override
    public int getContentView() {
        return R.layout.dialog_list_item;
    }

    @Override
    public void onInitView(View view, final int position) {
        TextView tv_fileName = (TextView) view.findViewById(R.id.tv_fileName);
        TextView tv_fileOpenOrDownload = (TextView) view.findViewById(R.id.tv_fileOpenOrDownload);
        tv_fileName.setText(list.get(position).getFileName());
        String fileName = list.get(position).getFileName();
        tv_fileOpenOrDownload.setText(getFileExist(position) ? "打开" : "下载");
        tv_fileOpenOrDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                noticeFileDownloadOrOpenInterface.fileDownLoadOrOpenInterface(position);
                if (getFileExist(position)) {
                    File files = new File(path + list.get(position).getFileName());// 这里更改为你的名称
                    Log.i("fileName", "=======" + files.getPath());
                    Uri path = Uri.fromFile(files);
                    Log.i("fileName", path.toString());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/*");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    try {
                        mContext.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        ToastUtil.show(mContext, "出现异常，请稍候再试");
                    }
                } else {
//                    if (down.mDownloadDialog.isShowing()) {
                    DownloadUtil down = new DownloadUtil(mContext, list.get(position).getFileName(), list.get(position).getFileUrl(), filePath);
                    down.showDownloadDialog();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }


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
