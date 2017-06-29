package com.linked.erfli.library.config;

/**
 * 文件名：UrlConfig
 * 描    述：接口的Url地址
 * 作    者：stt
 * 时    间：2017.1.18
 * 版    本：V1.0.0
 */

public class UrlConfig {
    // public static final String BaseUrl = "http://123.56.96.237:8001";// 正式
    //public static final String BaseUrl = "http://192.168.0.90:8001";// 测试
    public static final String BaseUrl = "http://nwpu-ism.dlax.com.cn";// 正式域名

    //周丽杰本机ip
    public static final String BaseRrl2 = "http://192.168.0.150:12321/Service";
    public static final String BaseRrl3 = "http://sps.dlax.com.cn/Service";

    //新登录接口ip
    public static final String BaseLoginUrl = "http://123.56.96.237:9001/ApiPersonInfo";
    /**
     * 新登录接口
     */
    public static final String NEWLOGIN = BaseLoginUrl + "/LoginToReturn";
    /**
     * 登陆接口返回token的
     */
    public static final String URL_LOGIN = BaseUrl + "/API/Login/LoginbySSO";

    /**
     * 事件上报.数据提交
     */
    public static final String URL_WMReport = BaseRrl3 + "/PatrolDataService.asmx/PhonePatrolData";
    /**
     * 事件上报.数据提交
     */
    public static final String URL_SENDIMAGE = BaseRrl3 + "/PatrolDataService.asmx/PhonePatrolData";
    /**
     * 返回人员基本信息
     */
    public static final String URL_GETPERSONINFO = BaseUrl + "/API/ApiPersonInfo/GetPersonInfo";
    /**
     * 完善人员信息接口
     */
    public static final String URL_EDITTEXTUSERINFO = BaseUrl + "/API/ApiPersonInfo/UodatePersonInfoOrAndPassWord";
    /**
     * 判断密码是否正确
     */
    public static final String URL_ISCHECKPASSWORD = BaseUrl + "/API/ApiPersonInfo/IsCheckPassWord";
    /**
     * 任务下发列表
     */
    public static final String URL_GETTASISSUEDLIST = BaseUrl + "/API/ApiTasIssued/GetTasIssuedList";
    /**
     * 任务详情
     */
    public static final String URL_GETTASKINFOBYID = BaseUrl + "/API/ApiTasIssued/GetTaskInfoByID";
    /**
     * 任务详情+反馈详情
     */
    public static final String URL_GETTASKINFOANDTASKASSIGNEDINFOBYTASKASSIGNEDID = BaseUrl + "/API/ApiTasIssued/GetTaskInfoAndTaskAssignedInfoByTaskAssignedID";
    /**
     * 新增任务下发功能
     */
    public static final String URL_UPLOADTASK = BaseUrl + "/API/ApiTasIssued/UploadTask";
    /**
     * 点击修改查阅状态
     */
    public static final String URL_ISCHECK = BaseUrl + "/API/ApiTasIssued/IsCheckState";
    /**
     * 任务反馈信息
     */
    public static final String URL_TASKASSIGNEDINFO = BaseUrl + "/API/ApiTasIssued/ImgUpload";
    /**
     * 问题列表
     */
    public static final String URL_GETPROBLEMREPORTINFO = BaseUrl + "/API/ApiProblemReport/GetProblemReportInfo";
    /**
     * 问题详情
     */
    public static final String URL_GETPROBLEMDETAIL = BaseUrl + "/API/ApiProblemReport/GetProblemReportInfoAndAcceptInformInfo";
    /**
     * 新增问题返回信息+图片
     */
    public static final String URL_IMGUPLOAD = BaseUrl + "/API/ApiProblemReport/ImgUpload";
    /**
     * 修改密码
     */
    public static final String URL_UPDATEPASSWORD = BaseUrl + "/API/ApiPersonInfo/UpdatePassWord";
    /**
     * 获取任务反馈信息
     */
    public static final String URL_GETTASKASSIGNEDINFO = BaseUrl + "/API/ApiTasIssued/GetTaskAssignedInfo";
    /**
     * 获取一级节点信息
     */
    public static final String URL_GETBEGINNINGENTITY = BaseUrl + "/API/ApiProblemReport/GetBeginningEntity";
    /**
     * 获取二级节点信息（根据code）
     */
    public static final String URL_GETTYPELISTBYCODE = BaseUrl + "/API/ApiProblemReport/GetTypeListByCode";
    /**
     * 获取通知信息列表
     */
    public static final String URL_GETINFORMISSUEDINFO = BaseUrl + "/API/ApiInformIssued/GetInformIssuedInfoByPersonID";
    /**
     * 获取通知详情
     */
    public static final String URL_GETINFORMISSUEDINFOBYID = BaseUrl + "/API/ApiInformIssued/GetInformIssuedInfoByID";
    /**
     * 更改通知查阅状态接口
     */
    public static final String URL_NOTICEISCHECK = BaseUrl + "/API/ApiInformIssued/IsCheckState";
    /**
     * 任务下发统计
     */
    public static final String URL_TASISSUEDDATASECTOR = BaseUrl + "/API/ApiTasIssued/DataSector";
    /**
     * 通知统计
     */
    public static final String URL_INFORMISSUEDDATASECTOR = BaseUrl + "/API/ApiInformIssued/DataSector";
    /**
     * 问题统计
     */
    public static final String URL_PROBLEMREPORTDATASECTOR = BaseUrl + "/API/ApiProblemReport/DataSector";
    /**
     * 获取任务下发类型
     */
    public static final String URL_GETTASKTYPE = BaseUrl + "/API/ApiTasIssued/getTaskType";
    /**
     * 获取优先级类型
     */
    public static final String URL_GETTASKPRIORITY = BaseUrl + "/API/ApiTasIssued/GetTaskPriorityXml";
    /**
     * 获取人员部门树状结构数据
     */
    public static final String URL_GETPERSONINFOBYDEPARTMENT = BaseUrl + "/API/ApiTasIssued/GetPersonInfoByDepartment";
}
