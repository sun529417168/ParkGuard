package cn.com.watchman.bean;

import java.io.Serializable;

/**
 * 文件名：GPSBean
 * 描    述：GPS实体类
 * 作    者：stt
 * 时    间：2017.5.10
 * 版    本：V1.0.0
 */

public class GPSBean implements Serializable {
    private double longitude;//经度
    private double latitude;//纬度
    private String address;//地址
    private float accuracy;//精度
    private double altitude;//海拔
    private String describe;//定位描述
    private int satellite;//连接卫星
    private String findSatellite;//发现卫星
    private float speed;//速度

    public GPSBean() {
    }

    public GPSBean(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public GPSBean(double longitude, double latitude, String address, float accuracy, double altitude, String describe, int satellite, String findSatellite, float speed) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.accuracy = accuracy;
        this.altitude = altitude;
        this.describe = describe;
        this.satellite = satellite;
        this.findSatellite = findSatellite;
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSatellite() {
        return satellite;
    }

    public void setSatellite(int satellite) {
        this.satellite = satellite;
    }

    public String getFindSatellite() {
        return findSatellite;
    }

    public void setFindSatellite(String findSatellite) {
        this.findSatellite = findSatellite;
    }

    @Override
    public String toString() {
        return "GPSBean{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", address='" + address + '\'' +
                ", accuracy=" + accuracy +
                ", altitude=" + altitude +
                ", describe='" + describe + '\'' +
                ", satellite=" + satellite +
                ", findSatellite='" + findSatellite + '\'' +
                ", speed=" + speed +
                '}';
    }
}
