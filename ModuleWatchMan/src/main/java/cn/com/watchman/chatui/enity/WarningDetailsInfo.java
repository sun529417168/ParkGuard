package cn.com.watchman.chatui.enity;

import java.util.List;

/**
 * Created by 志强 on 2017.6.20.
 */

public class WarningDetailsInfo {

    /**
     * alarm : {"id":164,"region_id":0,"user_id":0,"relate_id":1,"alarm_type":3,"alarm_time":"2017-06-20T15:32:46","alarm_text":"测试数据","deviceguid":"c4ecda50-aa16-356b-939e-3abfbf09e619","device_name":null,"alarm_related_info":1,"Longitude":"116.33077833333333","Latitude":"39.944145000000006","file_type":1,"ext1":null,"ext2":null,"ext3":null,"ext4":null,"ext5":null}
     * file : [{"id":102,"relevancy_id":164,"file_name":"1497943941986","file_address":"http://123.56.96.237:10080/2017_06_20/164/8abeea4722d3421f8dd57b5a9446fa8a.jpg","file_extname":"jpg","person_id":-1,"create_time":"2017-06-20T15:32:46.963","ext1":null,"ext2":null,"ext3":null,"ext4":null,"ext5":null},{"id":103,"relevancy_id":164,"file_name":"1497943953369","file_address":"http://123.56.96.237:10080/2017_06_20/164/cb133234373e4ed0af719786feb7c5ea.jpg","file_extname":"jpg","person_id":-1,"create_time":"2017-06-20T15:32:46.97","ext1":null,"ext2":null,"ext3":null,"ext4":null,"ext5":null}]
     * filenum : 2
     */

    private AlarmBean alarm;
    private int filenum;
    private List<FileBean> file;

    public AlarmBean getAlarm() {
        return alarm;
    }

    public void setAlarm(AlarmBean alarm) {
        this.alarm = alarm;
    }

    public int getFilenum() {
        return filenum;
    }

    public void setFilenum(int filenum) {
        this.filenum = filenum;
    }

    public List<FileBean> getFile() {
        return file;
    }

    public void setFile(List<FileBean> file) {
        this.file = file;
    }

    public static class AlarmBean {
        /**
         * id : 164
         * region_id : 0
         * user_id : 0
         * relate_id : 1
         * alarm_type : 3
         * alarm_time : 2017-06-20T15:32:46
         * alarm_text : 测试数据
         * deviceguid : c4ecda50-aa16-356b-939e-3abfbf09e619
         * device_name : null
         * alarm_related_info : 1
         * Longitude : 116.33077833333333
         * Latitude : 39.944145000000006
         * file_type : 1
         * ext1 : null
         * ext2 : null
         * ext3 : null
         * ext4 : null
         * ext5 : null
         */

        private int id;
        private int region_id;
        private int user_id;
        private int relate_id;
        private int alarm_type;
        private String alarm_time;
        private String alarm_text;
        private String deviceguid;
        private Object device_name;
        private int alarm_related_info;
        private String Longitude;
        private String Latitude;
        private int file_type;
        private Object ext1;
        private Object ext2;
        private Object ext3;
        private Object ext4;
        private Object ext5;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getRelate_id() {
            return relate_id;
        }

        public void setRelate_id(int relate_id) {
            this.relate_id = relate_id;
        }

        public int getAlarm_type() {
            return alarm_type;
        }

        public void setAlarm_type(int alarm_type) {
            this.alarm_type = alarm_type;
        }

        public String getAlarm_time() {
            return alarm_time;
        }

        public void setAlarm_time(String alarm_time) {
            this.alarm_time = alarm_time;
        }

        public String getAlarm_text() {
            return alarm_text;
        }

        public void setAlarm_text(String alarm_text) {
            this.alarm_text = alarm_text;
        }

        public String getDeviceguid() {
            return deviceguid;
        }

        public void setDeviceguid(String deviceguid) {
            this.deviceguid = deviceguid;
        }

        public Object getDevice_name() {
            return device_name;
        }

        public void setDevice_name(Object device_name) {
            this.device_name = device_name;
        }

        public int getAlarm_related_info() {
            return alarm_related_info;
        }

        public void setAlarm_related_info(int alarm_related_info) {
            this.alarm_related_info = alarm_related_info;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public int getFile_type() {
            return file_type;
        }

        public void setFile_type(int file_type) {
            this.file_type = file_type;
        }

        public Object getExt1() {
            return ext1;
        }

        public void setExt1(Object ext1) {
            this.ext1 = ext1;
        }

        public Object getExt2() {
            return ext2;
        }

        public void setExt2(Object ext2) {
            this.ext2 = ext2;
        }

        public Object getExt3() {
            return ext3;
        }

        public void setExt3(Object ext3) {
            this.ext3 = ext3;
        }

        public Object getExt4() {
            return ext4;
        }

        public void setExt4(Object ext4) {
            this.ext4 = ext4;
        }

        public Object getExt5() {
            return ext5;
        }

        public void setExt5(Object ext5) {
            this.ext5 = ext5;
        }
    }

    public static class FileBean {
        /**
         * id : 102
         * relevancy_id : 164
         * file_name : 1497943941986
         * file_address : http://123.56.96.237:10080/2017_06_20/164/8abeea4722d3421f8dd57b5a9446fa8a.jpg
         * file_extname : jpg
         * person_id : -1
         * create_time : 2017-06-20T15:32:46.963
         * ext1 : null
         * ext2 : null
         * ext3 : null
         * ext4 : null
         * ext5 : null
         */

        private int id;
        private int relevancy_id;
        private String file_name;
        private String file_address;
        private String file_extname;
        private int person_id;
        private String create_time;
        private Object ext1;
        private Object ext2;
        private Object ext3;
        private Object ext4;
        private Object ext5;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRelevancy_id() {
            return relevancy_id;
        }

        public void setRelevancy_id(int relevancy_id) {
            this.relevancy_id = relevancy_id;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public String getFile_address() {
            return file_address;
        }

        public void setFile_address(String file_address) {
            this.file_address = file_address;
        }

        public String getFile_extname() {
            return file_extname;
        }

        public void setFile_extname(String file_extname) {
            this.file_extname = file_extname;
        }

        public int getPerson_id() {
            return person_id;
        }

        public void setPerson_id(int person_id) {
            this.person_id = person_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getExt1() {
            return ext1;
        }

        public void setExt1(Object ext1) {
            this.ext1 = ext1;
        }

        public Object getExt2() {
            return ext2;
        }

        public void setExt2(Object ext2) {
            this.ext2 = ext2;
        }

        public Object getExt3() {
            return ext3;
        }

        public void setExt3(Object ext3) {
            this.ext3 = ext3;
        }

        public Object getExt4() {
            return ext4;
        }

        public void setExt4(Object ext4) {
            this.ext4 = ext4;
        }

        public Object getExt5() {
            return ext5;
        }

        public void setExt5(Object ext5) {
            this.ext5 = ext5;
        }
    }
}
