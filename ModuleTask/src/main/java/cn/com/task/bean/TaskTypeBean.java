package cn.com.task.bean;

import java.io.Serializable;

/**
 * 文件名：TaskTypeBean
 * 描    述：任务类型的实体类
 * 作    者：stt
 * 时    间：2017.3.17
 * 版    本：V1.0.7
 */

public class TaskTypeBean implements Serializable {

    /**
     * Name : 巡逻
     * Value : 1
     * Code :
     */

    private String Name;
    private int Value;
    private String Code;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int Value) {
        this.Value = Value;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    @Override
    public String toString() {
        return "TaskTypeBean{" +
                "Name='" + Name + '\'' +
                ", Value=" + Value +
                ", Code='" + Code + '\'' +
                '}';
    }
}
