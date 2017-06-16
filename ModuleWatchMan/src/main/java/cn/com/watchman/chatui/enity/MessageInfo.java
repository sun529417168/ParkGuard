package cn.com.watchman.chatui.enity;

/**
 * 描述:聊天发送数据实体类
 * 作者：zzq
 * 邮箱：rance935@163.com
 */
public class MessageInfo {
    private int type;
    private String content;
    private String filepath;
    private int sendState;
    private String time;
    private String header;
    private String imageUrl;
    private long voiceTime;
    private String msgId;
    //new add
    private Warning warning;
//    private String warning;

    public Warning getWarning() {
        return warning;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

    public static class Warning {
        private String warningId;
        private String warningMsg;
        private String warningDatetime;
        private String warningAddress;
        private String warningImgUrl;



        public String getWarningImgUrl() {
            return warningImgUrl;
        }

        public void setWarningImgUrl(String warningImgUrl) {
            this.warningImgUrl = warningImgUrl;
        }



        public String getWarningMsg() {
            return warningMsg;
        }

        public void setWarningMsg(String warningMsg) {
            this.warningMsg = warningMsg;
        }

        public String getWarningDatetime() {
            return warningDatetime;
        }

        public void setWarningDatetime(String warningDatetime) {
            this.warningDatetime = warningDatetime;
        }

        public String getWarningAddress() {
            return warningAddress;
        }

        public void setWarningAddress(String warningAddress) {
            this.warningAddress = warningAddress;
        }

        public String getWarningId() {
            return warningId;
        }

        public void setWarningId(String warningId) {
            this.warningId = warningId;
        }
    }
//    public String getWarning() {
//        return warning;
//    }
//
//    public void setWarning(String warning) {
//        this.warning = warning;
//    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", filepath='" + filepath + '\'' +
                ", sendState=" + sendState +
                ", time='" + time + '\'' +
                ", header='" + header + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", voiceTime=" + voiceTime +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
