package cn.com.problem.bean;

/**
 * 文件名：ProblemTypeRight
 * 描    述：获取二级节点类型的实体类
 * 作    者：stt
 * 时    间：2017.3.1
 * 版    本：V1.0.2
 */

public class ProblemTypeRight {


    /**
     * ID : 3
     * Name : 消防上报
     * Pcode : 1
     * Code : 3
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
        return "ProblemTypeRight{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Pcode='" + Pcode + '\'' +
                ", Code='" + Code + '\'' +
                '}';
    }
}
