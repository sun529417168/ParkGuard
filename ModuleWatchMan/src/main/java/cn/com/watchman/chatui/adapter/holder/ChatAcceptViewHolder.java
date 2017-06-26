package cn.com.watchman.chatui.adapter.holder;

import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import cn.com.watchman.R;
import cn.com.watchman.chatui.adapter.ChatAdapter;
import cn.com.watchman.chatui.enity.MessageInfo;
import cn.com.watchman.chatui.uiutils.Utils;
import cn.com.watchman.chatui.widget.BubbleImageView;
import cn.com.watchman.chatui.widget.BubbleLinearLayout;
import cn.com.watchman.chatui.widget.GifTextView;


/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatAcceptViewHolder extends BaseViewHolder<MessageInfo> {

    TextView chatItemDate;
    ImageView chatItemHeader;
    GifTextView chatItemContentText;
    BubbleImageView chatItemContentImage;
    ImageView chatItemVoice;
    BubbleLinearLayout chatItemLayoutContent;
    TextView chatItemVoiceTime;
    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;
    private RelativeLayout.LayoutParams layoutParams;

    public ChatAcceptViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_accept);
        chatItemDate = (TextView) itemView.findViewById(R.id.chat_item_date);
        chatItemHeader = (ImageView) itemView.findViewById(R.id.chat_item_header);
        chatItemContentText = (GifTextView) itemView.findViewById(R.id.chat_item_content_text);
        chatItemContentImage = (BubbleImageView) itemView.findViewById(R.id.chat_item_content_image);
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
        //设置头像
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
                onItemClickListener.onItemTextClick(chatItemContentText,getDataPosition());
            }
        });
        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, data.getContent(), true);
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentImage.setVisibility(View.GONE);
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
        }
    }
}
