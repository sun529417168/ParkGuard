package cn.com.parkguard.bean;

import java.io.Serializable;

/**
 * 文件名：HomeBean
 * 描    述：首页功能分类的实体类
 * 作    者：stt
 * 时    间：2017.4.6
 * 版    本：V1.1.2
 */

public class HomeBean implements Serializable {


    /**
     * id : 101
     * name : 任务
     * isTrue : true
     * imageView : 0
     */

    private int id;
    private String name;
    private boolean isTrue;
    private int imageView;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsTrue() {
        return isTrue;
    }

    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isTrue=" + isTrue +
                ", imageView=" + imageView +
                '}';
    }
}
