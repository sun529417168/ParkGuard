package cn.com.problem.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：ProblemDetailBean
 * 描    述：问题详情实体类
 * 作    者：stt
 * 时    间：2017.3.24
 * 版    本：V1.0.8
 */

public class ProblemDetailBean implements Serializable {

    /**
     * ProblemSno : P201703242233171
     * ProblemType : 7
     * ProblemTypeName : 乱停乱放
     * ProblemTitle : 测试问题
     * Position : 地铁口
     * ProblemDes : 测试内容
     * GPS : null
     * FindDate : /Date(-62135596800000)/
     * State : 2
     * StateName : 已回复
     * ReportDate :
     * ReportPerson : 91
     * ReportPersonName : 孙腾腾
     * Describe : 已经收到，尽快处理
     * ProcessDate :
     * ID : 4
     * IsImage :
     * ReportAttachmentList : [{"FileName":"/storage/emulated/0/XiBeiProblem/1490365974857.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/ProblemReportFile/2017/3/24/1/a8706741-a45d-4e6e-b054-12f908be063a.jpg","FileID":24,"AttachmentType":1,"SNO":"P20170324223317134"}]
     * FindDateApi : 2017/2/24 22:32:00
     * ReportDateApi : 2017/3/24 22:33:17
     * ProcessDateApi : 2017/3/24 22:34:18
     * SearchSNO :
     * ProblemProcessID : 4
     */

    private String ProblemSno;
    private String ProblemType;
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
    private String SearchSNO;
    private int ProblemProcessID;
    private List<ReportAttachmentListBean> ReportAttachmentList;

    public String getProblemSno() {
        return ProblemSno;
    }

    public void setProblemSno(String ProblemSno) {
        this.ProblemSno = ProblemSno;
    }

    public String getProblemType() {
        return ProblemType;
    }

    public void setProblemType(String ProblemType) {
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

    public String getSearchSNO() {
        return SearchSNO;
    }

    public void setSearchSNO(String SearchSNO) {
        this.SearchSNO = SearchSNO;
    }

    public int getProblemProcessID() {
        return ProblemProcessID;
    }

    public void setProblemProcessID(int ProblemProcessID) {
        this.ProblemProcessID = ProblemProcessID;
    }

    public List<ReportAttachmentListBean> getReportAttachmentList() {
        return ReportAttachmentList;
    }

    public void setReportAttachmentList(List<ReportAttachmentListBean> ReportAttachmentList) {
        this.ReportAttachmentList = ReportAttachmentList;
    }

    public static class ReportAttachmentListBean implements Serializable {
        /**
         * FileName : /storage/emulated/0/XiBeiProblem/1490365974857.jpg
         * FileUrl : http://123.56.96.237:8001/Content/FileStore/ProblemReportFile/2017/3/24/1/a8706741-a45d-4e6e-b054-12f908be063a.jpg
         * FileID : 24
         * AttachmentType : 1
         * SNO : P20170324223317134
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
