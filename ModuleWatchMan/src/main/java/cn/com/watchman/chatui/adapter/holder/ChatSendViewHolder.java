package cn.com.watchman.chatui.adapter.holder;

import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cn.com.watchman.R;
import cn.com.watchman.chatui.adapter.ChatAdapter;
import cn.com.watchman.chatui.enity.MessageInfo;
import cn.com.watchman.chatui.uiutils.Constants;
import cn.com.watchman.chatui.uiutils.Utils;
import cn.com.watchman.chatui.widget.BubbleImageView;
import cn.com.watchman.chatui.widget.BubbleLinearLayout;
import cn.com.watchman.chatui.widget.GifTextView;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatSendViewHolder extends BaseViewHolder<MessageInfo> {

    TextView chatItemDate;
    ImageView chatItemHeader;
    GifTextView chatItemContentText;
    BubbleImageView chatItemContentImage;
    ImageView chatItemFail;
    ProgressBar chatItemProgress;
    ImageView chatItemVoice;
    BubbleLinearLayout chatItemLayoutContent;
    TextView chatItemVoiceTime;
    //new add 2017年6月13日16:34:20
    private TextView tv_chat_item_warning_EventReport;//事件上报
    private TextView tv_chat_item_warning_DateTime;//上报时间
    private TextView tv_chat_item_warning_Address;//上报地点
    private TextView tv_chat_item_warning_Id;//id
    private ImageView img_chat_item_warning_ImageView;//时间上报的第一张图片
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;
    private RelativeLayout.LayoutParams layoutParams;
    BubbleLinearLayout chat_item_warning_BubbleLinearLayout;

    public ChatSendViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_send);
        //new add 新增事件上布局

        tv_chat_item_warning_EventReport = (TextView) itemView.findViewById(R.id.tv_chat_item_warning_EventReport);
        tv_chat_item_warning_DateTime = (TextView) itemView.findViewById(R.id.tv_chat_item_warning_DateTime);
        tv_chat_item_warning_Address = (TextView) itemView.findViewById(R.id.tv_chat_item_warning_Address);
        chat_item_warning_BubbleLinearLayout = (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_warning_BubbleLinearLayout);
        tv_chat_item_warning_Id = (TextView) itemView.findViewById(R.id.tv_chat_item_warning_Id);
        img_chat_item_warning_ImageView= (ImageView) itemView.findViewById(R.id.img_chat_item_warning_ImageView);
//        ButterKnife.bind(this, itemView);
        chatItemDate = (TextView) itemView.findViewById(R.id.chat_item_date);
        chatItemHeader = (ImageView) itemView.findViewById(R.id.chat_item_header);
        chatItemContentText = (GifTextView) itemView.findViewById(R.id.chat_item_content_text);
        chatItemContentImage = (BubbleImageView) itemView.findViewById(R.id.chat_item_content_image);
        chatItemFail = (ImageView) itemView.findViewById(R.id.chat_item_fail);
        chatItemProgress = (ProgressBar) itemView.findViewById(R.id.chat_item_progress);
        chatItemVoice = (ImageView) itemView.findViewById(R.id.chat_item_voice);
        chatItemLayoutContent = (BubbleLinearLayout) itemView.findViewById(R.id.chat_item_layout_content);
        chatItemVoiceTime = (TextView) itemView.findViewById(R.id.chat_item_voice_time);

        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
        layoutParams = (RelativeLayout.LayoutParams) chatItemLayoutContent.getLayoutParams();
    }


    @Override
    public void setData(final MessageInfo data) {
        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
//        Glide.with(getContext()).load(data.getHeader()).into(chatItemHeader);
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick(getDataPosition(), data.getType());
            }
        });
        chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemTextClick(chatItemContentText, getDataPosition());
            }
        });
        chat_item_warning_BubbleLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemWarningClick(v, tv_chat_item_warning_Id);
            }
        });

        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, data.getContent(), true);
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
            chat_item_warning_BubbleLinearLayout.setVisibility(View.GONE);
            TextPaint paint = chatItemContentText.getPaint();
            // 计算textview在屏幕上占多宽
            int len = (int) paint.measureText(chatItemContentText.getText().toString().trim());
            if (len < Utils.dp2px(getContext(), 200)) {
                layoutParams.width = len + Utils.dp2px(getContext(), 30);
                layoutParams.height = Utils.dp2px(getContext(), 48);
            } else {
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            chatItemLayoutContent.setLayoutParams(layoutParams);
        } else if (data.getImageUrl() != null) {
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chat_item_warning_BubbleLinearLayout.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(data.getImageUrl()).into(chatItemContentImage);
            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                }
            });
            layoutParams.width = Utils.dp2px(getContext(), 120);
            layoutParams.height = Utils.dp2px(getContext(), 48);
            chatItemLayoutContent.setLayoutParams(layoutParams);
        } else if (data.getFilepath() != null) {
            chatItemVoice.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.VISIBLE);
            chatItemContentImage.setVisibility(View.GONE);
            chat_item_warning_BubbleLinearLayout.setVisibility(View.GONE);
            chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                }
            });
            layoutParams.width = Utils.dp2px(getContext(), 120);
            layoutParams.height = Utils.dp2px(getContext(), 48);
            chatItemLayoutContent.setLayoutParams(layoutParams);
        } else if (data.getWarning() != null) {
            Log.i("即时通讯发送数据返回结果:告警", "执行适配器里条件判断方法!");
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
            chat_item_warning_BubbleLinearLayout.setVisibility(View.VISIBLE);

            tv_chat_item_warning_EventReport.setText(data.getWarning().getWarningMsg());
            tv_chat_item_warning_DateTime.setText(data.getWarning().getWarningDatetime());
            tv_chat_item_warning_Address.setText(data.getWarning().getWarningAddress());
            tv_chat_item_warning_Id.setText(data.getWarning().getWarningId());
            if (!("").equals(data.getWarning().getWarningImgUrl())) {
                Glide.with(getContext()).load(data.getWarning().getWarningImgUrl()).into(img_chat_item_warning_ImageView);

            }
        }
        switch (data.getSendState()) {
            case Constants.CHAT_ITEM_SENDING:
                chatItemProgress.setVisibility(View.VISIBLE);
                chatItemFail.setVisibility(View.GONE);
                break;
            case Constants.CHAT_ITEM_SEND_ERROR:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.VISIBLE);
                break;
            case Constants.CHAT_ITEM_SEND_SUCCESS:
                chatItemProgress.setVisibility(View.GONE);
                chatItemFail.setVisibility(View.GONE);
                break;
        }
    }
}
