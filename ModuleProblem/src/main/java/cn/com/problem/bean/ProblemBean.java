package cn.com.problem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：ProblemBean
 * 描    述：问题实体类
 * 作    者：stt
 * 时    间：2017.1.3
 * 版    本：V1.0.0
 */

public class ProblemBean implements Serializable {


    /**
     * total : 16
     * rows : [{"ProblemSno":"P201702171739231","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"你好啊","Position":"1","ProblemDes":"描述","GPS":"1111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":"","ReportPerson":14,"ReportPersonName":"刘新航","Describe":"","ProcessDate":"","ID":17,"IsImage":"","ReportAttachmentList":[{"FileName":"BodyPart_16efce72-45b8-47d9-ada3-b812238d6f9d","FileUrl":"http://123.56.96.237:8001/Content/FileStore/ProblemReportFile/2017/2/17/1/c8a7a5fa-3bd2-4f42-8940-7828a959140b.jpg","FileID":45,"AttachmentType":1,"SNO":"P201702171739231ProblemReport17"}],"FindDateApi":"2017/2/17 0:00:00","ReportDateApi":"2017/2/17 17:39:25","ProcessDateApi":""},{"ProblemSno":"P201702171730501","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"你好啊","Position":"1","ProblemDes":"描述","GPS":"1111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":16,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/2/17 0:00:00","ReportDateApi":"2017/2/17 17:30:50","ProcessDateApi":null},{"ProblemSno":"P201702171729391","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"你好啊","Position":"1","ProblemDes":"描述","GPS":"1111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":15,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/2/17 0:00:00","ReportDateApi":"2017/2/17 17:29:41","ProcessDateApi":null},{"ProblemSno":"P201702171729241","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"你好啊","Position":"1","ProblemDes":"描述","GPS":"1111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":14,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/2/17 0:00:00","ReportDateApi":"2017/2/17 17:29:24","ProcessDateApi":null},{"ProblemSno":"P201702171727001","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"你好啊","Position":"1","ProblemDes":"描述","GPS":"1111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":13,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/2/17 0:00:00","ReportDateApi":"2017/2/17 17:27:04","ProcessDateApi":null},{"ProblemSno":"P201702131111451","ProblemType":0,"ProblemTypeName":"问题上报","ProblemTitle":"1111111111","Position":"操场","ProblemDes":null,"GPS":"11111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":12,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2013/2/13 0:00:00","ReportDateApi":"2017/2/13 11:11:45","ProcessDateApi":null},{"ProblemSno":"P201702111452111","ProblemType":0,"ProblemTypeName":"问题上报","ProblemTitle":"问题上报","Position":"操场","ProblemDes":"操场有人打架","GPS":"111.111","FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":11,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/2/11 0:00:00","ReportDateApi":"2017/2/11 14:52:20","ProcessDateApi":null},{"ProblemSno":"P201701201435463","ProblemType":0,"ProblemTypeName":"问题上报","ProblemTitle":"桌椅损坏","Position":"实验室","ProblemDes":"实验室的一批桌椅损坏严重","GPS":null,"FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":10,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/1/20 14:35:13","ReportDateApi":"2017/1/20 13:36:13","ProcessDateApi":null},{"ProblemSno":"P201701201345463","ProblemType":0,"ProblemTypeName":"事件上报","ProblemTitle":"事件上报：自行车棚漏雨","Position":"自行车棚","ProblemDes":"自行车棚顶有一个大洞，下雨的时候漏雨，自行车都被淋湿了","GPS":null,"FindDate":"/Date(-62135596800000)/","State":1,"StateName":"已上报","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":null,"ProcessDate":null,"ID":9,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/1/20 13:45:13","ReportDateApi":"2017/1/20 14:12:13","ProcessDateApi":null},{"ProblemSno":"P201701201411523","ProblemType":0,"ProblemTypeName":"问题上报","ProblemTitle":"操场的路灯损坏","Position":"操场","ProblemDes":"操场东北角旁边的路灯有一个损坏了","GPS":null,"FindDate":"/Date(-62135596800000)/","State":2,"StateName":"已回复","ReportDate":null,"ReportPerson":14,"ReportPersonName":"刘新航","Describe":"您的上报已被审阅","ProcessDate":null,"ID":8,"IsImage":null,"ReportAttachmentList":[],"FindDateApi":"2017/1/20 13:25:13","ReportDateApi":"2017/1/20 14:22:13","ProcessDateApi":"2017/1/20 14:32:45"}]
     * pageIndex : 0
     * pageSize : 0
     * pages : 0
     * stateEndTime :
     */

    private int total;
    private int pageIndex;
    private int pageSize;
    private int pages;
    private String stateEndTime;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getStateEndTime() {
        return stateEndTime;
    }

    public void setStateEndTime(String stateEndTime) {
        this.stateEndTime = stateEndTime;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable {
        /**
         * ProblemSno : P201702171739231
         * ProblemType : 0
         * ProblemTypeName : 事件上报
         * ProblemTitle : 你好啊
         * Position : 1
         * ProblemDes : 描述
         * GPS : 1111
         * FindDate : /Date(-62135596800000)/
         * State : 1
         * StateName : 已上报
         * ReportDate :
         * ReportPerson : 14
         * ReportPersonName : 刘新航
         * Describe :
         * ProcessDate :
         * ID : 17
         * IsImage :
         * ReportAttachmentList : [{"FileName":"BodyPart_16efce72-45b8-47d9-ada3-b812238d6f9d","FileUrl":"http://123.56.96.237:8001/Content/FileStore/ProblemReportFile/2017/2/17/1/c8a7a5fa-3bd2-4f42-8940-7828a959140b.jpg","FileID":45,"AttachmentType":1,"SNO":"P201702171739231ProblemReport17"}]
         * FindDateApi : 2017/2/17 0:00:00
         * ReportDateApi : 2017/2/17 17:39:25
         * ProcessDateApi :
         */

        private String ProblemSno;
        private int ProblemType;
        private String ProblemTypeName;
        private String ProblemTitle;
        private String Position;
        private String ProblemDes;
        private String GPS;
        private String FindDate;
        private int State;
        private String StateName;
        private String ReportDate;
        private int ReportPerson;
        private String ReportPersonName;
        private String Describe;
        private String ProcessDate;
        private int ID;
        private String IsImage;
        private String FindDateApi;
        private String ReportDateApi;
        private String ProcessDateApi;
        private List<ReportAttachmentListBean> ReportAttachmentList;

        public String getProblemSno() {
            return ProblemSno;
        }

        public void setProblemSno(String ProblemSno) {
            this.ProblemSno = ProblemSno;
        }

        public int getProblemType() {
            return ProblemType;
        }

        public void setProblemType(int ProblemType) {
            this.ProblemType = ProblemType;
        }

        public String getProblemTypeName() {
            return ProblemTypeName;
        }

        public void setProblemTypeName(String ProblemTypeName) {
            this.ProblemTypeName = ProblemTypeName;
        }

        public String getProblemTitle() {
            return ProblemTitle;
        }

        public void setProblemTitle(String ProblemTitle) {
            this.ProblemTitle = ProblemTitle;
        }

        public String getPosition() {
            return Position;
        }

        public void setPosition(String Position) {
            this.Position = Position;
        }

        public String getProblemDes() {
            return ProblemDes;
        }

        public void setProblemDes(String ProblemDes) {
            this.ProblemDes = ProblemDes;
        }

        public String getGPS() {
            return GPS;
        }

        public void setGPS(String GPS) {
            this.GPS = GPS;
        }

        public String getFindDate() {
            return FindDate;
        }

        public void setFindDate(String FindDate) {
            this.FindDate = FindDate;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String StateName) {
            this.StateName = StateName;
        }

        public String getReportDate() {
            return ReportDate;
        }

        public void setReportDate(String ReportDate) {
            this.ReportDate = ReportDate;
        }

        public int getReportPerson() {
            return ReportPerson;
        }

        public void setReportPerson(int ReportPerson) {
            this.ReportPerson = ReportPerson;
        }

        public String getReportPersonName() {
            return ReportPersonName;
        }

        public void setReportPersonName(String ReportPersonName) {
            this.ReportPersonName = ReportPersonName;
        }

        public String getDescribe() {
            return Describe;
        }

        public void setDescribe(String Describe) {
            this.Describe = Describe;
        }

        public String getProcessDate() {
            return ProcessDate;
        }

        public void setProcessDate(String ProcessDate) {
            this.ProcessDate = ProcessDate;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getIsImage() {
            return IsImage;
        }

        public void setIsImage(String IsImage) {
            this.IsImage = IsImage;
        }

        public String getFindDateApi() {
            return FindDateApi;
        }

        public void setFindDateApi(String FindDateApi) {
            this.FindDateApi = FindDateApi;
        }

        public String getReportDateApi() {
            return ReportDateApi;
        }

        public void setReportDateApi(String ReportDateApi) {
            this.ReportDateApi = ReportDateApi;
        }

        public String getProcessDateApi() {
            return ProcessDateApi;
        }

        public void setProcessDateApi(String ProcessDateApi) {
            this.ProcessDateApi = ProcessDateApi;
        }

        public List<ReportAttachmentListBean> getReportAttachmentList() {
            return ReportAttachmentList;
        }

        public void setReportAttachmentList(List<ReportAttachmentListBean> ReportAttachmentList) {
            this.ReportAttachmentList = ReportAttachmentList;
        }

        public static class ReportAttachmentListBean implements Serializable {
            /**
             * FileName : BodyPart_16efce72-45b8-47d9-ada3-b812238d6f9d
             * FileUrl : http://123.56.96.237:8001/Content/FileStore/ProblemReportFile/2017/2/17/1/c8a7a5fa-3bd2-4f42-8940-7828a959140b.jpg
             * FileID : 45
             * AttachmentType : 1
             * SNO : P201702171739231ProblemReport17
             */

            private String FileName;
            private String FileUrl;
            private int FileID;
            private int AttachmentType;
            private String SNO;

            public String getFileName() {
                return FileName;
            }

            public void setFileName(String FileName) {
                this.FileName = FileName;
            }

            public String getFileUrl() {
                return FileUrl;
            }

            public void setFileUrl(String FileUrl) {
                this.FileUrl = FileUrl;
            }

            public int getFileID() {
                return FileID;
            }

            public void setFileID(int FileID) {
                this.FileID = FileID;
            }

            public int getAttachmentType() {
                return AttachmentType;
            }

            public void setAttachmentType(int AttachmentType) {
                this.AttachmentType = AttachmentType;
            }

            public String getSNO() {
                return SNO;
            }

            public void setSNO(String SNO) {
                this.SNO = SNO;
            }
        }
    }
}
