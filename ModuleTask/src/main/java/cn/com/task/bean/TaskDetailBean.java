package cn.com.task.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 文件名：TaskDetailBean
 * 描    述：任务详情的实体类
 * 作    者：stt
 * 时    间：2017.3.23
 * 版    本：V1.0.7
 */

public class TaskDetailBean implements Serializable {


    /**
     * Task : {"TaskSno":"T201703271204361","TaskName":"文件图片","TaskType":1,"TaskTypeName":"巡逻","TaskAddr":"北京","TaskDes":"文件和图片","PersonName":"孙腾腾","CreateDate":"/Date(-62135596800000)/","EndDate":"/Date(-62135596800000)/","TaskPriority":1,"TaskPriorityName":"高","TaskState":1,"TaskStateName":"已下发","IsImmediatelyName":"是","PersonID":91,"ID":24,"SearchSNO":"T201703271204361124","StartDate":"/Date(-62135596800000)/","IsCheck":true,"ImageList":[{"FileName":"2.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/0eb1a38e-5f55-4805-bcc8-8a2154293aca.jpg","FileID":30,"AttachmentType":1,"SNO":"T201703271204361124"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/83311a6a-8478-426a-96a8-df6763ecf0b5.jpg","FileID":31,"AttachmentType":1,"SNO":"T201703271204361124"},{"FileName":"hello.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/4132e0ac-d765-484e-9d01-88d65abd2df3.xlsx","FileID":32,"AttachmentType":2,"SNO":"T201703271204361124"}],"TaskAssignedID":34,"CreateDateApi":"2017/3/27 12:04:36","StartDateApi":"2017/3/27 12:03:18","EndDateApi":"2017/3/28 12:03:00","TaskAssignedState":3,"TaskAssignedStateName":"已完成"}
     * TaskAssigned : {"ID":34,"TaskSno":"T201703271204361","PersonID":91,"PersonName":"","GroupId":0,"FeedBackDate":"","GroupIdName":"","FeedBackContent":"OK","IsCheck":true,"IsCheckName":"","FeedbackState":3,"FeedbackStateName":"已完成","IsCheckTime":"","SearchSno":"","ImageList":[{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/994792d2-31d9-400d-a600-afcc8507647c.png","FileID":34,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/7719ebcc-5b97-4727-836e-36e5082a5062.jpg","FileID":35,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/7d27d21d-11c4-42c3-a106-3fc6421dab96.png","FileID":36,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/2115f50a-688e-485f-bd67-61874d2426cd.png","FileID":37,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/936cb4f9-e75b-44a9-aea7-66ee6428f7d4.jpg","FileID":39,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/da07fb30-0052-4f92-b0ce-eca2575ebc17.jpg","FileID":40,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"hello.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/a4c4ef1d-742e-4c3e-a914-1b6ae6df2d4e.xlsx","FileID":41,"AttachmentType":1,"SNO":"T201703271204361234"}],"FeedBackDateApi":"2017/3/27 13:45:22","IsCheckTimeApi":"2017/3/27 12:04:46"}
     */

    private TaskBean Task;
    private TaskAssignedBean TaskAssigned;

    public TaskBean getTask() {
        return Task;
    }

    public void setTask(TaskBean Task) {
        this.Task = Task;
    }

    public TaskAssignedBean getTaskAssigned() {
        return TaskAssigned;
    }

    public void setTaskAssigned(TaskAssignedBean TaskAssigned) {
        this.TaskAssigned = TaskAssigned;
    }

    public static class TaskBean {
        /**
         * TaskSno : T201703271204361
         * TaskName : 文件图片
         * TaskType : 1
         * TaskTypeName : 巡逻
         * TaskAddr : 北京
         * TaskDes : 文件和图片
         * PersonName : 孙腾腾
         * CreateDate : /Date(-62135596800000)/
         * EndDate : /Date(-62135596800000)/
         * TaskPriority : 1
         * TaskPriorityName : 高
         * TaskState : 1
         * TaskStateName : 已下发
         * IsImmediatelyName : 是
         * PersonID : 91
         * ID : 24
         * SearchSNO : T201703271204361124
         * StartDate : /Date(-62135596800000)/
         * IsCheck : true
         * ImageList : [{"FileName":"2.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/0eb1a38e-5f55-4805-bcc8-8a2154293aca.jpg","FileID":30,"AttachmentType":1,"SNO":"T201703271204361124"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/83311a6a-8478-426a-96a8-df6763ecf0b5.jpg","FileID":31,"AttachmentType":1,"SNO":"T201703271204361124"},{"FileName":"hello.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/4132e0ac-d765-484e-9d01-88d65abd2df3.xlsx","FileID":32,"AttachmentType":2,"SNO":"T201703271204361124"}]
         * TaskAssignedID : 34
         * CreateDateApi : 2017/3/27 12:04:36
         * StartDateApi : 2017/3/27 12:03:18
         * EndDateApi : 2017/3/28 12:03:00
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
        private int ID;
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

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
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

        public static class ImageListBean {
            /**
             * FileName : 2.jpg
             * FileUrl : http://123.56.96.237:8001/Content/FileStore/TasIssuedFile/2017/3/27/1/0eb1a38e-5f55-4805-bcc8-8a2154293aca.jpg
             * FileID : 30
             * AttachmentType : 1
             * SNO : T201703271204361124
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

    public static class TaskAssignedBean {
        /**
         * ID : 34
         * TaskSno : T201703271204361
         * PersonID : 91
         * PersonName :
         * GroupId : 0
         * FeedBackDate :
         * GroupIdName :
         * FeedBackContent : OK
         * IsCheck : true
         * IsCheckName :
         * FeedbackState : 3
         * FeedbackStateName : 已完成
         * IsCheckTime :
         * SearchSno :
         * ImageList : [{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/994792d2-31d9-400d-a600-afcc8507647c.png","FileID":34,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"QQ图片20170214141158.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/7719ebcc-5b97-4727-836e-36e5082a5062.jpg","FileID":35,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/7d27d21d-11c4-42c3-a106-3fc6421dab96.png","FileID":36,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"$$%Z9K$]}C)E2S2)F$5J_ZG.png","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/2115f50a-688e-485f-bd67-61874d2426cd.png","FileID":37,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/936cb4f9-e75b-44a9-aea7-66ee6428f7d4.jpg","FileID":39,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"timg.jpg","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/da07fb30-0052-4f92-b0ce-eca2575ebc17.jpg","FileID":40,"AttachmentType":1,"SNO":"T201703271204361234"},{"FileName":"hello.xlsx","FileUrl":"http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/a4c4ef1d-742e-4c3e-a914-1b6ae6df2d4e.xlsx","FileID":41,"AttachmentType":1,"SNO":"T201703271204361234"}]
         * FeedBackDateApi : 2017/3/27 13:45:22
         * IsCheckTimeApi : 2017/3/27 12:04:46
         */

        private int ID;
        private String TaskSno;
        private int PersonID;
        private String PersonName;
        private int GroupId;
        private String FeedBackDate;
        private String GroupIdName;
        private String FeedBackContent;
        private boolean IsCheck;
        private String IsCheckName;
        private int FeedbackState;
        private String FeedbackStateName;
        private String IsCheckTime;
        private String SearchSno;
        private String FeedBackDateApi;
        private String IsCheckTimeApi;
        private List<ImageListBeanX> ImageList;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTaskSno() {
            return TaskSno;
        }

        public void setTaskSno(String TaskSno) {
            this.TaskSno = TaskSno;
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

        public int getGroupId() {
            return GroupId;
        }

        public void setGroupId(int GroupId) {
            this.GroupId = GroupId;
        }

        public String getFeedBackDate() {
            return FeedBackDate;
        }

        public void setFeedBackDate(String FeedBackDate) {
            this.FeedBackDate = FeedBackDate;
        }

        public String getGroupIdName() {
            return GroupIdName;
        }

        public void setGroupIdName(String GroupIdName) {
            this.GroupIdName = GroupIdName;
        }

        public String getFeedBackContent() {
            return FeedBackContent;
        }

        public void setFeedBackContent(String FeedBackContent) {
            this.FeedBackContent = FeedBackContent;
        }

        public boolean isIsCheck() {
            return IsCheck;
        }

        public void setIsCheck(boolean IsCheck) {
            this.IsCheck = IsCheck;
        }

        public String getIsCheckName() {
            return IsCheckName;
        }

        public void setIsCheckName(String IsCheckName) {
            this.IsCheckName = IsCheckName;
        }

        public int getFeedbackState() {
            return FeedbackState;
        }

        public void setFeedbackState(int FeedbackState) {
            this.FeedbackState = FeedbackState;
        }

        public String getFeedbackStateName() {
            return FeedbackStateName;
        }

        public void setFeedbackStateName(String FeedbackStateName) {
            this.FeedbackStateName = FeedbackStateName;
        }

        public String getIsCheckTime() {
            return IsCheckTime;
        }

        public void setIsCheckTime(String IsCheckTime) {
            this.IsCheckTime = IsCheckTime;
        }

        public String getSearchSno() {
            return SearchSno;
        }

        public void setSearchSno(String SearchSno) {
            this.SearchSno = SearchSno;
        }

        public String getFeedBackDateApi() {
            return FeedBackDateApi;
        }

        public void setFeedBackDateApi(String FeedBackDateApi) {
            this.FeedBackDateApi = FeedBackDateApi;
        }

        public String getIsCheckTimeApi() {
            return IsCheckTimeApi;
        }

        public void setIsCheckTimeApi(String IsCheckTimeApi) {
            this.IsCheckTimeApi = IsCheckTimeApi;
        }

        public List<ImageListBeanX> getImageList() {
            return ImageList;
        }

        public void setImageList(List<ImageListBeanX> ImageList) {
            this.ImageList = ImageList;
        }

        public static class ImageListBeanX {
            /**
             * FileName : $$%Z9K$]}C)E2S2)F$5J_ZG.png
             * FileUrl : http://123.56.96.237:8001/Content/FileStore/TaskAssignedFile/2017/3/27/1/994792d2-31d9-400d-a600-afcc8507647c.png
             * FileID : 34
             * AttachmentType : 1
             * SNO : T201703271204361234
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
