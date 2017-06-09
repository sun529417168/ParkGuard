package cn.com.watchman.chatui.enity;

/**
 * Created by 志强 on 2017.6.9.
 */

public class SocketMsgInfo {

    /**
     * subSysType : 10
     * dataType : 8
     * mark : patrolpc
     * data : {" message_type":1,"message":"1","send_time":"1497007238337"," user_id":90}
     */

    private int subSysType;
    private int dataType;
    private String mark;
    private DataBean data;

    public int getSubSysType() {
        return subSysType;
    }

    public void setSubSysType(int subSysType) {
        this.subSysType = subSysType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         *  message_type : 1
         * message : 1
         * send_time : 1497007238337
         *  user_id : 90
         */

        private int message_type;
        private String message;
        private String send_time;
        private int user_id;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getMessage_type() {
            return message_type;
        }

        public void setMessage_type(int message_type) {
            this.message_type = message_type;
        }
    }
}
