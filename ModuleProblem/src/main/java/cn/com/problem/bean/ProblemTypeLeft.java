package cn.com.problem.bean;

/**
 * 文件名：ProblemTypeLeft
 * 描    述：获取一级节点类型的实体类
 * 作    者：stt
 * 时    间：2017.2.28
 * 版    本：V1.0.0
 */

public class ProblemTypeLeft {

    /**
     * ID : 1
     * Name : 部件上报
     * Pcode : 0
     * Code : 1
     */

    private int ID;
    private String Name;
    private String Pcode;
    private String Code;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPcode() {
        return Pcode;
    }

    public void setPcode(String Pcode) {
        this.Pcode = Pcode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    @Override
    public String toString() {
        return "ProblemTypeLeft{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Pcode='" + Pcode + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }
}
