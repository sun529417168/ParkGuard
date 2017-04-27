package cn.com.notice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：NoticeBean
 * 描    述：通知实体类
 * 作    者：stt
 * 时    间：2017.1.6
 * 版    本：V1.0.0
 */

public class NoticeBean implements Serializable {


    /**
     * total : 10
     * rows : [{"ID":14,"PersonID":94,"PersonName":"张晓乐","ContentInfo":"下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务","CreateTime":"/Date(1488856044097)/","InformSno":"I201703071107241","Name":"下发任务","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":"/Date(1489125802373)/","AcceptState":2,"AcceptID":28,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"new_file.html","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/7/1/7221faee-bb18-4ec6-b711-af5c4f6b382d.html","FileID":84,"AttachmentType":2,"SNO":"I201703071107241314"},{"FileName":"金木君.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/c6924e9a-5bfd-43db-937f-9c41e7523214.jpg","FileID":85,"AttachmentType":1,"SNO":"I201703071107241314"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/c2eb61d6-844c-44d8-9a4c-a183ad8e0e3d.png","FileID":100,"AttachmentType":1,"SNO":"I201703071107241314"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/2386fa10-dbd3-4d0f-b8ac-66318e210766.jpg","FileID":101,"AttachmentType":1,"SNO":"I201703071107241314"}],"AcceptStateName":"已查阅","ApiCreateTime":"2017/3/7 11:07:24","IsCheck":true},{"ID":10,"PersonID":14,"PersonName":"刘新航","ContentInfo":"测试","CreateTime":"/Date(1488424098267)/","InformSno":"I201703021108181","Name":"返回参数","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":15,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 11:08:18","IsCheck":false},{"ID":9,"PersonID":14,"PersonName":"刘新航","ContentInfo":"测试","CreateTime":"/Date(1488423732187)/","InformSno":"I201703021102121","Name":"返回参数","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":14,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 11:02:12","IsCheck":false},{"ID":8,"PersonID":14,"PersonName":"刘新航","ContentInfo":"测试","CreateTime":"/Date(1488423475350)/","InformSno":"I201703021057551","Name":"返回参数","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":13,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/ccc9c345-1817-4abe-aaa4-92adedd59ff2.jpg","FileID":75,"AttachmentType":1,"SNO":"I20170302105755138"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:57:55","IsCheck":false},{"ID":7,"PersonID":14,"PersonName":"刘新航","ContentInfo":"返回值内容测试","CreateTime":"/Date(1488423137023)/","InformSno":"I201703021052171","Name":"返回值类型测试","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":11,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"1.png","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/11f3f1df-9a59-4a04-973e-e385494bff50.png","FileID":74,"AttachmentType":1,"SNO":"I20170302105217137"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:52:17","IsCheck":false},{"ID":6,"PersonID":14,"PersonName":"刘新航","ContentInfo":"%E6%B5%8B%E8%AF%95%E6%8E%A5%E5%8F%A3%E8%B0%83%E7%94%A8","CreateTime":"/Date(1488423036517)/","InformSno":"I201703021050361","Name":"%E6%B5%8B%E8%AF%95%E6%8E%A5%E5%8F%A3%E8%B0%83%E7%94%A8","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":10,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/717919d8-2ce1-44a4-ad3a-1d49a50b83fb.png","FileID":72,"AttachmentType":1,"SNO":"I20170302105036136"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/67df7bb4-4f1b-4fa3-9986-38ee5109abf8.jpg","FileID":73,"AttachmentType":1,"SNO":"I20170302105036136"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:50:36","IsCheck":false},{"ID":5,"PersonID":14,"PersonName":"刘新航","ContentInfo":"测试同步异步","CreateTime":"/Date(1488422575857)/","InformSno":"I201703021042551","Name":"测试同步异步","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":7,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"金木君.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/d34e9d14-9395-4fa1-97e8-6e53a4293e07.jpg","FileID":71,"AttachmentType":1,"SNO":"I20170302104255135"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:42:55","IsCheck":false},{"ID":4,"PersonID":14,"PersonName":"刘新航","ContentInfo":"测试返回值","CreateTime":"/Date(1488422477413)/","InformSno":"I201703021041171","Name":"测试返回值","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":5,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"打架.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/adafd20e-6f00-4a64-ba52-e9af40c97fd2.jpg","FileID":69,"AttachmentType":1,"SNO":"I20170302104117134"},{"FileName":"金木君.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/62185ebf-f5f8-48d4-98f7-e03a2385eae2.jpg","FileID":70,"AttachmentType":1,"SNO":"I20170302104117134"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:41:17","IsCheck":false},{"ID":3,"PersonID":14,"PersonName":"刘新航","ContentInfo":"通知公告功能测试","CreateTime":"/Date(1488421855617)/","InformSno":"I201703021030551","Name":"  通知公告功能测试","State":1,"StateName":"已下发","InformType":1,"InformTypeName":"任务通知","IsCheckTime":null,"AcceptState":1,"AcceptID":3,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"金木君.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/96021ff0-f82b-43d7-a335-614cbfe74572.jpg","FileID":67,"AttachmentType":1,"SNO":"I20170302103055133"},{"FileName":"打架.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/c391e2d8-3744-41d1-a94c-15b737624322.jpg","FileID":68,"AttachmentType":1,"SNO":"I20170302103055133"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:30:55","IsCheck":false},{"ID":2,"PersonID":14,"PersonName":"刘新航","ContentInfo":"下发功能测试","CreateTime":"/Date(1488420849457)/","InformSno":"I201703021014121","Name":"公告通知下发功能测试","State":1,"StateName":"已下发","InformType":4,"InformTypeName":"会议通知","IsCheckTime":null,"AcceptState":1,"AcceptID":1,"AcceptPersonID":14,"AcceptPersonIDName":"刘新航","FileList":[{"FileName":"1.png","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/6a8fa010-dc75-4c60-af10-b480c25205ed.png","FileID":64,"AttachmentType":1,"SNO":"I20170302101412132"},{"FileName":"iPhone5S 1.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/62e9df52-50f8-407e-9d6e-0dc75c600209.jpg","FileID":65,"AttachmentType":1,"SNO":"I20170302101412132"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/2/1/f3d1a07d-f7af-4d81-bd38-96f374023e00.jpg","FileID":66,"AttachmentType":1,"SNO":"I20170302101412132"}],"AcceptStateName":"未查阅","ApiCreateTime":"2017/3/2 10:14:09","IsCheck":false}]
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
         * ID : 14
         * PersonID : 94
         * PersonName : 张晓乐
         * ContentInfo : 下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务下发任务
         * CreateTime : /Date(1488856044097)/
         * InformSno : I201703071107241
         * Name : 下发任务
         * State : 1
         * StateName : 已下发
         * InformType : 1
         * InformTypeName : 任务通知
         * IsCheckTime : /Date(1489125802373)/
         * AcceptState : 2
         * AcceptID : 28
         * AcceptPersonID : 14
         * AcceptPersonIDName : 刘新航
         * FileList : [{"FileName":"new_file.html","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/7/1/7221faee-bb18-4ec6-b711-af5c4f6b382d.html","FileID":84,"AttachmentType":2,"SNO":"I201703071107241314"},{"FileName":"金木君.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/c6924e9a-5bfd-43db-937f-9c41e7523214.jpg","FileID":85,"AttachmentType":1,"SNO":"I201703071107241314"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/c2eb61d6-844c-44d8-9a4c-a183ad8e0e3d.png","FileID":100,"AttachmentType":1,"SNO":"I201703071107241314"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://192.168.0.90:8001/Content/FileStore/InformIssuedFile/2017/3/9/1/2386fa10-dbd3-4d0f-b8ac-66318e210766.jpg","FileID":101,"AttachmentType":1,"SNO":"I201703071107241314"}]
         * AcceptStateName : 已查阅
         * ApiCreateTime : 2017/3/7 11:07:24
         * IsCheck : true
         */

        private String ID;
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

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
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
}
