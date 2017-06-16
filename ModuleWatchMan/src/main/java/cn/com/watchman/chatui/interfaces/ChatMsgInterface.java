package cn.com.watchman.chatui.interfaces;

/**
 * Created by 志强 on 2017.6.12.
 */

public interface ChatMsgInterface {
    /**
     * 信息发送失败
     */
    void onChatMsgError();

    /**
     * 信息发送成功
     */
    void onChatMsgResponse();
}
