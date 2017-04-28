package cn.com.task.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：TaskBean
 * 描    述：任务列表的实体类
 * 作    者：stt
 * 时    间：2017.1.3
 * 版    本：V1.0.0
 */

public class TaskBean implements Serializable {

    /**
     * total : 5
     * rows : [{"TaskSno":"T201702241016011","TaskName":"手机任务下发功能测试","TaskType":3,"TaskTypeName":"其他","TaskAddr":"会议室","TaskDes":"手机任务下发功能测试","PersonName":"刘新航","CreateDate":"/Date(-62135596800000)/","EndDate":"/Date(-62135596800000)/","TaskPriority":1,"TaskPriorityName":"高","TaskState":4,"TaskStateName":"未完成","IsImmediatelyName":"是","PersonID":14,"ID":5,"SearchSNO":"T20170224101601115","StartDate":"/Date(-62135596800000)/","IsCheck":true,"ImageList":[{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/0ae8bcb0-9ae6-4a01-a038-e87b7d1d347e.png","FileID":26,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"1.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/0491ad45-b626-437f-8a7d-cf0c460202f6.png","FileID":27,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"image.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/dd646da8-9ace-40fc-8d7e-d1c6dde3d8be.jpg","FileID":28,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"数据库原理.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/8c5e18a8-7eb4-4acc-9347-bb9d86380a02.xlsx","FileID":29,"AttachmentType":2,"SNO":"T20170224101601115"}],"TaskAssignedID":32,"CreateDateApi":"2017/2/24 10:16:01","StartDateApi":"2017/2/24 10:15:00","EndDateApi":"2017/2/24 11:15:00","TaskAssignedState":3,"TaskAssignedStateName":"已完成"}]
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
         * TaskSno : T201702241016011
         * TaskName : 手机任务下发功能测试
         * TaskType : 3
         * TaskTypeName : 其他
         * TaskAddr : 会议室
         * TaskDes : 手机任务下发功能测试
         * PersonName : 刘新航
         * CreateDate : /Date(-62135596800000)/
         * EndDate : /Date(-62135596800000)/
         * TaskPriority : 1
         * TaskPriorityName : 高
         * TaskState : 4
         * TaskStateName : 未完成
         * IsImmediatelyName : 是
         * PersonID : 14
         * ID : 5
         * SearchSNO : T20170224101601115
         * StartDate : /Date(-62135596800000)/
         * IsCheck : true
         * ImageList : [{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/0ae8bcb0-9ae6-4a01-a038-e87b7d1d347e.png","FileID":26,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"1.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/0491ad45-b626-437f-8a7d-cf0c460202f6.png","FileID":27,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"image.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/dd646da8-9ace-40fc-8d7e-d1c6dde3d8be.jpg","FileID":28,"AttachmentType":1,"SNO":"T20170224101601115"},{"FileName":"数据库原理.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/8c5e18a8-7eb4-4acc-9347-bb9d86380a02.xlsx","FileID":29,"AttachmentType":2,"SNO":"T20170224101601115"}]
         * TaskAssignedID : 32
         * CreateDateApi : 2017/2/24 10:16:01
         * StartDateApi : 2017/2/24 10:15:00
         * EndDateApi : 2017/2/24 11:15:00
         * TaskAssignedState : 3
         * TaskAssignedStateName : 已完成
         */

        private String TaskSno;
        private String TaskName;
        private int TaskType;
        private String TaskTypeName;
        private String TaskAddr;
        private String TaskDes;
        private String PersonName;
        private String CreateDate;
        private String EndDate;
        private int TaskPriority;
        private String TaskPriorityName;
        private int TaskState;
        private String TaskStateName;
        private String IsImmediatelyName;
        private int PersonID;
        private String ID;
        private String SearchSNO;
        private String StartDate;
        private boolean IsCheck;
        private int TaskAssignedID;
        private String CreateDateApi;
        private String StartDateApi;
        private String EndDateApi;
        private int TaskAssignedState;
        private String TaskAssignedStateName;
        private List<ImageListBean> ImageList;

        public String getTaskSno() {
            return TaskSno;
        }

        public void setTaskSno(String TaskSno) {
            this.TaskSno = TaskSno;
        }

        public String getTaskName() {
            return TaskName;
        }

        public void setTaskName(String TaskName) {
            this.TaskName = TaskName;
        }

        public int getTaskType() {
            return TaskType;
        }

        public void setTaskType(int TaskType) {
            this.TaskType = TaskType;
        }

        public String getTaskTypeName() {
            return TaskTypeName;
        }

        public void setTaskTypeName(String TaskTypeName) {
            this.TaskTypeName = TaskTypeName;
        }

        public String getTaskAddr() {
            return TaskAddr;
        }

        public void setTaskAddr(String TaskAddr) {
            this.TaskAddr = TaskAddr;
        }

        public String getTaskDes() {
            return TaskDes;
        }

        public void setTaskDes(String TaskDes) {
            this.TaskDes = TaskDes;
        }

        public String getPersonName() {
            return PersonName;
        }

        public void setPersonName(String PersonName) {
            this.PersonName = PersonName;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public int getTaskPriority() {
            return TaskPriority;
        }

        public void setTaskPriority(int TaskPriority) {
            this.TaskPriority = TaskPriority;
        }

        public String getTaskPriorityName() {
            return TaskPriorityName;
        }

        public void setTaskPriorityName(String TaskPriorityName) {
            this.TaskPriorityName = TaskPriorityName;
        }

        public int getTaskState() {
            return TaskState;
        }

        public void setTaskState(int TaskState) {
            this.TaskState = TaskState;
        }

        public String getTaskStateName() {
            return TaskStateName;
        }

        public void setTaskStateName(String TaskStateName) {
            this.TaskStateName = TaskStateName;
        }

        public String getIsImmediatelyName() {
            return IsImmediatelyName;
        }

        public void setIsImmediatelyName(String IsImmediatelyName) {
            this.IsImmediatelyName = IsImmediatelyName;
        }

        public int getPersonID() {
            return PersonID;
        }

        public void setPersonID(int PersonID) {
            this.PersonID = PersonID;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getSearchSNO() {
            return SearchSNO;
        }

        public void setSearchSNO(String SearchSNO) {
            this.SearchSNO = SearchSNO;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public boolean isIsCheck() {
            return IsCheck;
        }

        public void setIsCheck(boolean IsCheck) {
            this.IsCheck = IsCheck;
        }

        public int getTaskAssignedID() {
            return TaskAssignedID;
        }

        public void setTaskAssignedID(int TaskAssignedID) {
            this.TaskAssignedID = TaskAssignedID;
        }

        public String getCreateDateApi() {
            return CreateDateApi;
        }

        public void setCreateDateApi(String CreateDateApi) {
            this.CreateDateApi = CreateDateApi;
        }

        public String getStartDateApi() {
            return StartDateApi;
        }

        public void setStartDateApi(String StartDateApi) {
            this.StartDateApi = StartDateApi;
        }

        public String getEndDateApi() {
            return EndDateApi;
        }

        public void setEndDateApi(String EndDateApi) {
            this.EndDateApi = EndDateApi;
        }

        public int getTaskAssignedState() {
            return TaskAssignedState;
        }

        public void setTaskAssignedState(int TaskAssignedState) {
            this.TaskAssignedState = TaskAssignedState;
        }

        public String getTaskAssignedStateName() {
            return TaskAssignedStateName;
        }

        public void setTaskAssignedStateName(String TaskAssignedStateName) {
            this.TaskAssignedStateName = TaskAssignedStateName;
        }

        public List<ImageListBean> getImageList() {
            return ImageList;
        }

        public void setImageList(List<ImageListBean> ImageList) {
            this.ImageList = ImageList;
        }

        public static class ImageListBean implements Serializable {
            /**
             * FileName : $$%Z9K$]}C)E2S2)F$5J_ZG.png
             * FileUrl : http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/2/24/1/0ae8bcb0-9ae6-4a01-a038-e87b7d1d347e.png
             * FileID : 26
             * AttachmentType : 1
             * SNO : T20170224101601115
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
