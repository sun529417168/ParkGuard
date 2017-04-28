package cn.com.task.bean;

import java.io.Serializable;

/**
 * 文件名：TaskChoosePersonBean
 * 描    述：下发人员的实体类
 * 作    者：stt
 * 时    间：2017.3.17
 * 版    本：V1.0.7
 */

public class TaskChoosePersonBean implements Serializable {

    /**
     * Id : 14
     * Pid : XBGD_BaoWeiChu
     * name : 刘新航
     * Code :
     * open : false
     * isParent : false
     */

    private String Id;
    private String Pid;
    private String name;
    private String Code;
    private boolean open;
    private boolean isParent;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String Pid) {
        this.Pid = Pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    @Override
    public String toString() {
        return "TaskChoosePersonBean{" +
                "Id='" + Id + '\'' +
                ", Pid='" + Pid + '\'' +
                ", name='" + name + '\'' +
                ", Code='" + Code + '\'' +
                ", open=" + open +
                ", isParent=" + isParent +
                '}';
    }
}
