package cn.com.parkguard.bean;

/**
 * zzq
 * 2017年6月28日10:14:45
 * 登录返回实体类
 */

public class MyLoginBean {

    /**
     * userName : 600056
     * passWord : 123
     * personId : 91
     * loginName : 600056
     * personName : 孙腾腾
     * resultNum : 3
     */

    private String userName;
    private String passWord;
    private int personId;
    private String loginName;
    private String personName;
    private int resultNum;
    /**
     * userType : 1
     */

    private int userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getResultNum() {
        return resultNum;
    }

    public void setResultNum(int resultNum) {
        this.resultNum = resultNum;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
