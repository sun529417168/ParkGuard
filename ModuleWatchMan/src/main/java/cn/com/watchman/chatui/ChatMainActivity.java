package cn.com.watchman.chatui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.chatui.adapter.ChatAdapter;
import cn.com.watchman.chatui.adapter.CommonFragmentPagerAdapter;
import cn.com.watchman.chatui.enity.FullImageInfo;
import cn.com.watchman.chatui.enity.MessageInfo;
import cn.com.watchman.chatui.enity.SocketMsgInfo;
import cn.com.watchman.chatui.fragment.ChatEmotionFragment;
import cn.com.watchman.chatui.fragment.ChatFunctionFragment;
import cn.com.watchman.chatui.interfaces.ChatMsgInterface;
import cn.com.watchman.chatui.interfaces.ChatSendPhotoInterface;
import cn.com.watchman.chatui.service.MyScoketService;
import cn.com.watchman.chatui.uiutils.Constants;
import cn.com.watchman.chatui.uiutils.GlobalOnItemClickManagerUtils;
import cn.com.watchman.chatui.uiutils.MediaManager;
import cn.com.watchman.chatui.uiutils.MyConstant;
import cn.com.watchman.chatui.uiutils.MyServiceUtils;
import cn.com.watchman.chatui.widget.EmotionInputDetector;
import cn.com.watchman.chatui.widget.GifTextView;
import cn.com.watchman.chatui.widget.MyPopWindowView;
import cn.com.watchman.chatui.widget.NoScrollViewPager;
import cn.com.watchman.chatui.widget.StateButton;


/**
 * 描    述：聊天页面
 * 作    者：zzq
 * 时    间：2017年6月8日
 * 版    本：V1.0.0
 */
public class ChatMainActivity extends AppCompatActivity implements PopupMenu.OnDismissListener, ChatMsgInterface, ChatSendPhotoInterface {

    EasyRecyclerView chatList;//聊天页面布局
    ImageView emotionVoice;//语音按钮
    EditText editText;//聊天文本框
    TextView voiceText;
    //    @Bind(R.id.emotion_button)
//    ImageView emotionButton;
    ImageView emotionAdd;//加号
    LinearLayout ll_emotion_add_layout;//加号父布局
    StateButton emotionSend;//发送按钮
    NoScrollViewPager viewpager;//
    RelativeLayout emotionLayout;//viewpage父布局

    private EmotionInputDetector mDetector;
    private ArrayList<Fragment> fragments;
    private ChatEmotionFragment chatEmotionFragment;
    private ChatFunctionFragment chatFunctionFragment;
    private CommonFragmentPagerAdapter adapter;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager layoutManager;
    private List<MessageInfo> messageInfos;
    //录音相关
    int animationRes = 0;
    int res = 0;
    AnimationDrawable animationDrawable = null;
    private ImageView animView;
    private MyPopWindowView myPopWindowView;
    private LinearLayout ll_layout;
    MyChatBroadcasReceiver myChatBroadcasReceiver;
    private String girlUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497010388545&di=da66ed1f9e340aa4022d3b357c847cc0&imgtype=0&src=http%3A%2F%2Fv1.qzone.cc%2Favatar%2F201303%2F18%2F17%2F14%2F5146daf314dfa660.jpg%2521180x180.jpg";
    private String boyUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497010481703&di=1c26989083c94c4937eb3365ff2cc8c3&imgtype=0&src=http%3A%2F%2Fimg.meimi.cc%2Ftouxiang%2F20170522%2Fswxy0j0iijy335.png";
    private TextView chat_tv_myName;
    //声明AMapLocationClient类对象
    private AMapLocationClient locationClientContinue = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat_main);
        StatusBarUtils.ff(ChatMainActivity.this);
        init();
        initLocation();
        EventBus.getDefault().register(this);
        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("myChatActionFlag");
        myChatBroadcasReceiver = new MyChatBroadcasReceiver();
        registerReceiver(myChatBroadcasReceiver, filter);
        initWidget();
    }

    private void init() {
        chatList = (EasyRecyclerView) findViewById(R.id.chat_list);
        emotionVoice = (ImageView) findViewById(R.id.emotion_voice);
        editText = (EditText) findViewById(R.id.edit_text);
        voiceText = (TextView) findViewById(R.id.voice_text);
        emotionAdd = (ImageView) findViewById(R.id.emotion_add);
        ll_emotion_add_layout = (LinearLayout) findViewById(R.id.ll_emotion_add_layout);
        emotionSend = (StateButton) findViewById(R.id.emotion_send);
        viewpager = (NoScrollViewPager) findViewById(R.id.viewpager);
        emotionLayout = (RelativeLayout) findViewById(R.id.emotion_layout);
        chat_tv_myName = (TextView) findViewById(R.id.chat_tv_myName);
        chat_tv_myName.setText(SharedUtil.getString(this, "personName"));
    }

    private void initLocation() {
        if (null == locationClientContinue) {
            locationClientContinue = new AMapLocationClient(this.getApplicationContext());
        }

        //使用连续的定位方式  默认连续
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        // 地址信息
        locationClientOption.setNeedAddress(true);
        // 每10秒定位一次
        locationClientOption.setInterval(5 * 1000);
        locationClientContinue.setLocationOption(locationClientOption);
        locationClientContinue.setLocationListener(mLocationListener);
        locationClientContinue.startLocation();
    }

    private void initWidget() {

//        myPopWindowView.setOnDismissListener((PopupWindow.OnDismissListener) MainActivity.this);

        fragments = new ArrayList<>();
        chatEmotionFragment = new ChatEmotionFragment();
        fragments.add(chatEmotionFragment);
        chatFunctionFragment = new ChatFunctionFragment();
        fragments.add(chatFunctionFragment);
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);

        mDetector = EmotionInputDetector.with(this)
                .setEmotionView(emotionLayout)
                .setViewPager(viewpager)
                .bindToContent(chatList)
                .bindToEditText(editText)
                .bindToAddButton(emotionAdd, ll_emotion_add_layout)
                .bindToSendButton(emotionSend)
                .bindToVoiceButton(emotionVoice)
                .bindToVoiceText(voiceText)
                .build();

        GlobalOnItemClickManagerUtils globalOnItemClickListener = GlobalOnItemClickManagerUtils.getInstance(this);
        globalOnItemClickListener.attachToEditText(editText);

        chatAdapter = new ChatAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //这是重点
        layoutManager.setReverseLayout(false);

        chatList.setLayoutManager(layoutManager);
        chatList.setAdapter(chatAdapter);
//        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) chatList.getRecyclerView().getLayoutManager();
//        linearLayoutManager.scrollToPositionWithOffset(100, 0);
//        chatList.getRecyclerView().getLayoutManager().smoothScrollToPosition(chatList.getRecyclerView(), null, chatAdapter.getItemCount() - 1);
        chatList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        chatAdapter.notifyDataSetChanged();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        chatAdapter.handler.removeCallbacksAndMessages(null);
                        mDetector.hideEmotionLayout(false);
                        mDetector.hideSoftInput();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        chatAdapter.addItemClickListener(itemClickListener);
        LoadData();
    }

    /**
     * 弹出Pop方法
     */
    private void openPopMethod(String result) {
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int width = dm.widthPixels * 2 / 3;
        //窗口高度
        int height = dm.heightPixels / 2;
        myPopWindowView = new MyPopWindowView(this, width, height, result);
        if (myPopWindowView == null)
            return;
        myPopWindowView.setAnimationStyle(R.style.AppTheme);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        // mTestPopwindow2弹出在某控件(button)的下面
        myPopWindowView.showAtLocation(ll_layout, Gravity.LEFT | Gravity.TOP, ll_layout.getWidth() / 2 - width / 2, ll_layout.getHeight() / 2 - height / 3);
//        myPopWindowView.showAtLocation(ll_layout, Gravity.LEFT | Gravity.TOP, ll_layout.getWidth() / 2 - screenWidth / 2, ll_layout.getHeight() / 2 - screenHeight / 2);
    }


    /**
     * item点击事件
     */
    private ChatAdapter.onItemClickListener itemClickListener = new ChatAdapter.onItemClickListener() {
        @Override
        public void onHeaderClick(int position, int type) {
            Log.i("MainActivity主页", "onHeaderClick:" + position + "#type:" + type);
            if (type == 1) {
//                openPopMethod("Beautiful Girl");
            } else if (type == 2) {
//                openPopMethod("Shiny boy");
            }
        }

        @Override
        public void onImageClick(View view, int position) {
            Log.i("MainActivity主页", "onImageClick:" + position);
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            FullImageInfo fullImageInfo = new FullImageInfo();
            fullImageInfo.setLocationX(location[0]);
            fullImageInfo.setLocationY(location[1]);
            fullImageInfo.setWidth(view.getWidth());
            fullImageInfo.setHeight(view.getHeight());
            fullImageInfo.setImageUrl(messageInfos.get(position).getImageUrl());
            EventBus.getDefault().postSticky(fullImageInfo);
            startActivity(new Intent(ChatMainActivity.this, FullImageActivity.class));
            overridePendingTransition(0, 0);
        }

        @Override
        public void onVoiceClick(final ImageView imageView, final int position) {
            if (animView != null) {
                animView.setImageResource(res);
                animView = null;
            }
            switch (messageInfos.get(position).getType()) {
                case 1:
                    animationRes = R.drawable.voice_left;
                    res = R.mipmap.icon_voice_left3;
                    break;
                case 2:
                    animationRes = R.drawable.voice_right;
                    res = R.mipmap.icon_voice_right3;
                    break;
            }
            animView = imageView;
            animView.setImageResource(animationRes);
            animationDrawable = (AnimationDrawable) imageView.getDrawable();
            animationDrawable.start();
            MediaManager.playSound(messageInfos.get(position).getFilepath(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    animView.setImageResource(res);
                }
            });
        }

        @Override
        public void onItemTextClick(GifTextView gifTextView, int position) {
            Log.i("聊天页面textview点击事件", "" + gifTextView.getText());
//            Toast.makeText(ChatMainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemWarningClick(View view, TextView tv) {
            Log.i("聊天页面Warning点击事件", "" + tv.getText());
            Intent intent = new Intent();
            intent.putExtra("id", tv.getText());
            intent.setClass(ChatMainActivity.this, WarningDetailsActivity.class);
            startActivity(intent);
        }
    };

    /**
     * 构造聊天数据
     */
    private void LoadData() {
        messageInfos = new ArrayList<>();
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setContent("我们已是好友,发条消息试一试吧![微笑]");
        messageInfo.setType(Constants.CHAT_ITEM_TYPE_LEFT);
        messageInfo.setHeader(girlUrl);
        messageInfos.add(messageInfo);


//        MessageInfo messageInfo1 = new MessageInfo();
//        messageInfo1.setFilepath("http://www.trueme.net/bb_midi/welcome.wav");
//        messageInfo1.setVoiceTime(3000);
//        messageInfo1.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
//        messageInfo1.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
//        messageInfo1.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
//        messageInfos.add(messageInfo1);
//
//        MessageInfo messageInfo2 = new MessageInfo();
//        messageInfo2.setImageUrl("http://img4.imgtn.bdimg.com/it/u=1800788429,176707229&fm=21&gp=0.jpg");
//        messageInfo2.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//        messageInfo2.setHeader("http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
//        messageInfos.add(messageInfo2);
//
//        MessageInfo messageInfo3 = new MessageInfo();
//        messageInfo3.setContent("[微笑][色][色][色]");
//        messageInfo3.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
//        messageInfo3.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
//        messageInfo3.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
//        messageInfos.add(messageInfo3);
//
//
//        MessageInfo messageInfo4 = new MessageInfo();
//        messageInfo4.setContent("我特么也是服了");
//        messageInfo4.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
//        messageInfo4.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
//        messageInfo4.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
//        messageInfos.add(messageInfo4);
//        MessageInfo messageInfo5 = new MessageInfo();
//        messageInfo5.setContent("我特么也是服了\n\n\n下班回家");
//        messageInfo5.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
//        messageInfo5.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
//        messageInfo5.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
//        messageInfos.add(messageInfo5);
//        MessageInfo messageInfo6 = new MessageInfo();
//        messageInfo6.setContent("我特么也是服了\n\n\n下班回家吃饭");
//        messageInfo6.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
//        messageInfo6.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
//        messageInfo6.setHeader("http://img.dongqiudi.com/uploads/avatar/2014/10/20/8MCTb0WBFG_thumb_1413805282863.jpg");
//        messageInfos.add(messageInfo6);
        chatAdapter.addAll(messageInfos);
    }

    MessageInfo mMessageInfo;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageEventBus(final MessageInfo messageInfo) {
        Log.i("MessageEventBus:", "messageInfo" + messageInfo.getImageUrl());
        mMessageInfo = messageInfo;
        mMessageInfo.setHeader(boyUrl);
        mMessageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
        mMessageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
        messageInfos.add(mMessageInfo);
        chatAdapter.add(mMessageInfo);
        chatList.scrollToPosition(chatAdapter.getCount() - 1);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
//                chatAdapter.notifyDataSetChanged();
//            }
//        }, 2000);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                MessageInfo message = new MessageInfo();
//                message.setContent("接收到你发送的消息:" + messageInfo.getContent());
//                message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//                message.setHeader("http://tupian.enterdesk.com/2014/mxy/11/2/1/12.jpg");
//                messageInfos.add(message);
//                chatAdapter.add(message);
//                chatList.scrollToPosition(chatAdapter.getCount() - 1);
//            }

    }

    @Override
    public void onBackPressed() {
        if (!mDetector.interceptBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(this);
        EventBus.getDefault().unregister(this);
        // 注销广播
        unregisterReceiver(myChatBroadcasReceiver);
//        wmApplication.
        if (null != locationClientContinue) {
            locationClientContinue.stopLocation();
            locationClientContinue.onDestroy();
            locationClientContinue = null;
        }
    }

    @Override
    public void onDismiss(PopupMenu menu) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null
                    && getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onChatMsgError() {
        mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChatMsgResponse() {
        mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getChatSendPhotoError() {
        mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_ERROR);
        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void getChatSendPhotoSuccess() {
        mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
        chatAdapter.notifyDataSetChanged();
    }


    public class MyChatBroadcasReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("myChatActionFlag".equals(intent.getAction())) {
                String result = intent.getStringExtra("myChat");
                Log.i("接收到的消息,Activity:", "" + judgeMessageChar(result));
                MessageInfo message = new MessageInfo();
                if (!judgeMessageChar(result)) {
                    try {
                        SocketMsgInfo msgInfo = JSON.parseObject(result, SocketMsgInfo.class);
                        int subSysType = msgInfo.getSubSysType();
                        int dataType = msgInfo.getDataType();
                        String mark = msgInfo.getMark();
                        if (subSysType == 10 && dataType == 8 && "patrolpc".equals(mark)) {
                            message.setContent(msgInfo.getData().getMessage());
                            message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
                            message.setHeader(girlUrl);
                            messageInfos.add(message);
                            chatAdapter.add(message);
                            chatList.scrollToPosition(chatAdapter.getCount() - 1);
                            chatAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                    message.setImageUrl(result);
//                    message.setType(Constants.CHAT_ITEM_TYPE_LEFT);
//                    message.setHeader(girlUrl);
//                    messageInfos.add(message);
//                    chatAdapter.add(message);
//                    chatList.scrollToPosition(chatAdapter.getCount() - 1);
//                    chatAdapter.notifyDataSetChanged();
                }

            }
        }
    }

    /**
     * 判断接受的信息是否是图片
     *
     * @param s
     * @return
     */
    private boolean judgeMessageChar(String s) {
        return s.contains(".jpg") || s.contains(".png") || s.contains(".jpeg") || s.contains(".gif") ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MyServiceUtils.isServiceRunning(MyConstant.GPSSERVICE_CLASSNAME, ChatMainActivity.this)) {
            Intent startIntent = new Intent(this, MyScoketService.class);
            startService(startIntent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!MyServiceUtils.isServiceRunning(MyConstant.GPSSERVICE_CLASSNAME, ChatMainActivity.this)) {
            Intent startIntent = new Intent(this, MyScoketService.class);
            startService(startIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("聊天页面onActivityResult方法:", "requestCode:" + requestCode + ",resultCode:" + resultCode);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    String id = data.getStringExtra("warningId");
                    String name = data.getStringExtra("warningName");
                    String address = data.getStringExtra("warningAddress");
                    String time = data.getStringExtra("warningTime");
                    String imgUrl = data.getStringExtra("warningImgUrl");
                    MessageInfo messageInfo = new MessageInfo();
                    MessageInfo.Warning warning = new MessageInfo.Warning();
                    warning.setWarningId(id);
                    warning.setWarningMsg(name);
                    warning.setWarningDatetime(time);
                    warning.setWarningAddress(address);
                    warning.setWarningImgUrl(imgUrl);
                    messageInfo.setWarning(warning);
//        messageInfo.setWarning("not null");
                    mMessageInfo = messageInfo;
                    mMessageInfo.setHeader(boyUrl);
                    mMessageInfo.setType(Constants.CHAT_ITEM_TYPE_RIGHT);
                    mMessageInfo.setSendState(Constants.CHAT_ITEM_SENDING);
                    messageInfos.add(mMessageInfo);
                    chatAdapter.add(mMessageInfo);
                    chatList.scrollToPosition(chatAdapter.getCount() - 1);
                    mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
                    chatAdapter.notifyDataSetChanged();
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//                            mMessageInfo.setSendState(Constants.CHAT_ITEM_SEND_SUCCESS);
//                            chatAdapter.notifyDataSetChanged();
//                        }
//                    }, 2000);
                }
                break;
        }
    }

    /**
     * 高德定位回调监听器
     */

    AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            // TODO Auto-generated method stub
            Log.i("经纬度:", "执行mLocationListener监听方法");
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    if ("0".equals(String.valueOf(amapLocation.getLongitude()))) {
                        SharedUtil.setString(ChatMainActivity.this, "mylon", "-1");

                    } else {
                        SharedUtil.setString(ChatMainActivity.this, "mylon", String.valueOf(amapLocation.getLongitude()));
                    }
                    if ("0".equals(String.valueOf(amapLocation.getLatitude()))) {
                        SharedUtil.setString(ChatMainActivity.this, "mylat", "-1");
                    } else {
                        SharedUtil.setString(ChatMainActivity.this, "mylat", String.valueOf(amapLocation.getLatitude()));
                    }
                    Log.i("坐标点", "getLongitude:" + SharedUtil.getString(ChatMainActivity.this, "mylon") + ",getLatitude:" + SharedUtil.getString(ChatMainActivity.this, "mylat"));
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.i("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
}
