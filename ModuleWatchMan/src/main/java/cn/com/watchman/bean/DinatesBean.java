package cn.com.watchman.bean;

import java.io.Serializable;

import cn.com.watchman.database.annotation.Column;
import cn.com.watchman.database.annotation.Id;
import cn.com.watchman.database.annotation.Table;

/**
 * 文件名：DinatesBean
 * 描    述：后台收集轨迹点位的实体类
 * 作    者：stt
 * 时    间：2017.6.8
 * 版    本：V1.1.2
 */
@Table(name = "t_gps")
public class DinatesBean implements Serializable {
    @Id
    @Column(name = "id")
    private int id; // 主键,int类型,数据库建表时此字段会设为自增长
    @Column(name = "longitude", type = "LONG")
    private double longitude;
    @Column(name = "latitude", type = "LONG")
    private double latitude;
    @Column(name = "time", type = "LONG")
    private long time;
    private String info;


    public DinatesBean() {
        super();
    }

    public DinatesBean(double longitude, double latitude, long time) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public DinatesBean(double longitude, double latitude, String info) {
        super();
        this.longitude = longitude;
        this.latitude = latitude;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "DinatesBean{" +
                "id=" + id +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", time=" + time +
                ", info='" + info + '\'' +
                '}';
    }
}
