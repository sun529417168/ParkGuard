package cn.com.watchman.bean;

import java.util.List;

/**
 * 文件名：UserBean
 * 描    述:
 * 作    者：stt
 * 时    间：2017.2.13
 * 版    本：V1.0.0
 */

public class UserBean {

    /**
     * Permissions : [{"PermissionsID":1,"Pid":"0","Href":"","URL":"../DeviceManagement/Index","SortID":1,"ImageUrl":"/Content/Administrator/images/Master/终端管理.png","Remark":"","NavigationID":1,"Name":"终端设备管理","PermissionsType":1,"Permissions_Code":"","ControlID":"","MenuID":1,"MenuName":"终端设备管理","Role_Code":"","Module":1,"ModuleName":"","GroupRoleID":"","MenuCode":"Device","IsAdministrator":2,"UserName":"aa"},{"PermissionsID":2,"Pid":"0  ","Href":null,"URL":"../TasIssued/index","SortID":2,"ImageUrl":"/Content/Administrator/images/Master/任务下发.png","Remark":null,"NavigationID":3,"Name":"任务下发","PermissionsType":1,"Permissions_Code":null,"ControlID":null,"MenuID":3,"MenuName":"任务下发","Role_Code":null,"Module":1,"ModuleName":null,"GroupRoleID":null,"MenuCode":"Task","IsAdministrator":2,"UserName":"aa"},{"PermissionsID":4,"Pid":"0  ","Href":null,"URL":"","SortID":1,"ImageUrl":"/Content/Administrator/images/Master/问题管理.png","Remark":null,"NavigationID":6,"Name":"问题公告","PermissionsType":1,"Permissions_Code":null,"ControlID":null,"MenuID":6,"MenuName":"问题管理","Role_Code":null,"Module":1,"ModuleName":null,"GroupRoleID":null,"MenuCode":"Problem","IsAdministrator":2,"UserName":"aa"},{"PermissionsID":5,"Pid":"6  ","Href":null,"URL":"../ProblemReport/Index","SortID":2,"ImageUrl":null,"Remark":null,"NavigationID":7,"Name":"已上报","PermissionsType":1,"Permissions_Code":null,"ControlID":null,"MenuID":7,"MenuName":"已上报","Role_Code":null,"Module":1,"ModuleName":null,"GroupRoleID":null,"MenuCode":"ProblemNews","IsAdministrator":2,"UserName":"aa"},{"PermissionsID":6,"Pid":"6  ","Href":null,"URL":"../ProblemReport/RepliedIndex","SortID":3,"ImageUrl":null,"Remark":null,"NavigationID":8,"Name":"已处理","PermissionsType":1,"Permissions_Code":null,"ControlID":null,"MenuID":8,"MenuName":"已处理","Role_Code":null,"Module":1,"ModuleName":null,"GroupRoleID":null,"MenuCode":"ProblemReplied","IsAdministrator":2,"UserName":"aa"}]
     * PersonId : 91
     * LoginName : 600056
     * UserType : 1
     */

    private int PersonId;
    private String LoginName;
    private int UserType;
    private List<PermissionsBean> Permissions;

    public int getPersonId() {
        return PersonId;
    }

    public void setPersonId(int PersonId) {
        this.PersonId = PersonId;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int UserType) {
        this.UserType = UserType;
    }

    public List<PermissionsBean> getPermissions() {
        return Permissions;
    }

    public void setPermissions(List<PermissionsBean> Permissions) {
        this.Permissions = Permissions;
    }

    public static class PermissionsBean {
        /**
         * PermissionsID : 1
         * Pid : 0
         * Href :
         * URL : ../DeviceManagement/Index
         * SortID : 1
         * ImageUrl : /Content/Administrator/images/Master/终端管理.png
         * Remark :
         * NavigationID : 1
         * Name : 终端设备管理
         * PermissionsType : 1
         * Permissions_Code :
         * ControlID :
         * MenuID : 1
         * MenuName : 终端设备管理
         * Role_Code :
         * Module : 1
         * ModuleName :
         * GroupRoleID :
         * MenuCode : Device
         * IsAdministrator : 2
         * UserName : aa
         */

        private int PermissionsID;
        private String Pid;
        private String Href;
        private String URL;
        private int SortID;
        private String ImageUrl;
        private String Remark;
        private int NavigationID;
        private String Name;
        private int PermissionsType;
        private String Permissions_Code;
        private String ControlID;
        private int MenuID;
        private String MenuName;
        private String Role_Code;
        private int Module;
        private String ModuleName;
        private String GroupRoleID;
        private String MenuCode;
        private int IsAdministrator;
        private String UserName;

        public int getPermissionsID() {
            return PermissionsID;
        }

        public void setPermissionsID(int PermissionsID) {
            this.PermissionsID = PermissionsID;
        }

        public String getPid() {
            return Pid;
        }

        public void setPid(String Pid) {
            this.Pid = Pid;
        }

        public String getHref() {
            return Href;
        }

        public void setHref(String Href) {
            this.Href = Href;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public int getSortID() {
            return SortID;
        }

        public void setSortID(int SortID) {
            this.SortID = SortID;
        }

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String ImageUrl) {
            this.ImageUrl = ImageUrl;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public int getNavigationID() {
            return NavigationID;
        }

        public void setNavigationID(int NavigationID) {
            this.NavigationID = NavigationID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getPermissionsType() {
            return PermissionsType;
        }

        public void setPermissionsType(int PermissionsType) {
            this.PermissionsType = PermissionsType;
        }

        public String getPermissions_Code() {
            return Permissions_Code;
        }

        public void setPermissions_Code(String Permissions_Code) {
            this.Permissions_Code = Permissions_Code;
        }

        public String getControlID() {
            return ControlID;
        }

        public void setControlID(String ControlID) {
            this.ControlID = ControlID;
        }

        public int getMenuID() {
            return MenuID;
        }

        public void setMenuID(int MenuID) {
            this.MenuID = MenuID;
        }

        public String getMenuName() {
            return MenuName;
        }

        public void setMenuName(String MenuName) {
            this.MenuName = MenuName;
        }

        public String getRole_Code() {
            return Role_Code;
        }

        public void setRole_Code(String Role_Code) {
            this.Role_Code = Role_Code;
        }

        public int getModule() {
            return Module;
        }

        public void setModule(int Module) {
            this.Module = Module;
        }

        public String getModuleName() {
            return ModuleName;
        }

        public void setModuleName(String ModuleName) {
            this.ModuleName = ModuleName;
        }

        public String getGroupRoleID() {
            return GroupRoleID;
        }

        public void setGroupRoleID(String GroupRoleID) {
            this.GroupRoleID = GroupRoleID;
        }

        public String getMenuCode() {
            return MenuCode;
        }

        public void setMenuCode(String MenuCode) {
            this.MenuCode = MenuCode;
        }

        public int getIsAdministrator() {
            return IsAdministrator;
        }

        public void setIsAdministrator(int IsAdministrator) {
            this.IsAdministrator = IsAdministrator;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }
    }
}
