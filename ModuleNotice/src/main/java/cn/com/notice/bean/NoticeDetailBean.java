package cn.com.notice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/23.
 */

public class NoticeDetailBean implements Serializable {
    private int ID;
    private int PersonID;
    private String PersonName;
    private String ContentInfo;
    private String CreateTime;
    private String InformSno;
    private String Name;
    private int State;
    private String StateName;
    private int InformType;
    private String InformTypeName;
    private String IsCheckTime;
    private int AcceptState;
    private int AcceptID;
    private int AcceptPersonID;
    private String AcceptPersonIDName;
    private String AcceptStateName;
    private String ApiCreateTime;
    private boolean IsCheck;
    private List<FileListBean> FileList;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPersonID() {
        return PersonID;
    }

    public void setPersonID(int PersonID) {
        this.PersonID = PersonID;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String PersonName) {
        this.PersonName = PersonName;
    }

    public String getContentInfo() {
        return ContentInfo;
    }

    public void setContentInfo(String ContentInfo) {
        this.ContentInfo = ContentInfo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getInformSno() {
        return InformSno;
    }

    public void setInformSno(String InformSno) {
        this.InformSno = InformSno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
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

    public int getInformType() {
        return InformType;
    }

    public void setInformType(int InformType) {
        this.InformType = InformType;
    }

    public String getInformTypeName() {
        return InformTypeName;
    }

    public void setInformTypeName(String InformTypeName) {
        this.InformTypeName = InformTypeName;
    }

    public String getIsCheckTime() {
        return IsCheckTime;
    }

    public void setIsCheckTime(String IsCheckTime) {
        this.IsCheckTime = IsCheckTime;
    }

    public int getAcceptState() {
        return AcceptState;
    }

    public void setAcceptState(int AcceptState) {
        this.AcceptState = AcceptState;
    }

    public int getAcceptID() {
        return AcceptID;
    }

    public void setAcceptID(int AcceptID) {
        this.AcceptID = AcceptID;
    }

    public int getAcceptPersonID() {
        return AcceptPersonID;
    }

    public void setAcceptPersonID(int AcceptPersonID) {
        this.AcceptPersonID = AcceptPersonID;
    }

    public String getAcceptPersonIDName() {
        return AcceptPersonIDName;
    }

    public void setAcceptPersonIDName(String AcceptPersonIDName) {
        this.AcceptPersonIDName = AcceptPersonIDName;
    }

    public String getAcceptStateName() {
        return AcceptStateName;
    }

    public void setAcceptStateName(String AcceptStateName) {
        this.AcceptStateName = AcceptStateName;
    }

    public String getApiCreateTime() {
        return ApiCreateTime;
    }

    public void setApiCreateTime(String ApiCreateTime) {
        this.ApiCreateTime = ApiCreateTime;
    }

    public boolean isIsCheck() {
        return IsCheck;
    }

    public void setIsCheck(boolean IsCheck) {
        this.IsCheck = IsCheck;
    }

    public List<FileListBean> getFileList() {
        return FileList;
    }

    public void setFileList(List<FileListBean> FileList) {
        this.FileList = FileList;
    }

    public static class FileListBean implements Serializable {
        /**
         * FileName : new_file.html
         * FileUrl : http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/7/1/7221faee-bb18-4ec6-b711-af5c4f6b382d.html
         * FileID : 84
         * AttachmentType : 2
         * SNO : I201703071107241314
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
