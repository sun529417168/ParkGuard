package cn.com.watchman.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.SharedUtil;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.com.watchman.R;
import cn.com.watchman.bean.DinatesBean;
import cn.com.watchman.bean.DinatesDaoImpl;
import cn.com.watchman.bean.GPSBean;
import cn.com.watchman.utils.Distance;
import cn.com.watchman.utils.MyRequest;
import cn.com.watchman.utils.PointsUtil;
import cn.com.watchman.utils.SensorEventHelper;
import cn.com.watchman.utils.WMyUtils;

/**
 * 文件名：MoveShowActivity
 * 描    述：平滑移动轨迹
 * 作    者：stt
 * 时    间：2017.6.8
 * 版    本：V1.1.2
 */

public abstract class MoveShowActivity extends BaseActivity implements LocationSource, AMapLocationListener {
    protected MapView mapView;
    protected AMap aMap;
    private DinatesDaoImpl dinatesDao;
    private SensorEventHelper mSensorHelper;
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private Circle mCircle;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    public static final String LOCATION_MARKER_FLAG = "mylocation";
    protected List<DinatesBean> dinatesList = new ArrayList<>();
    private boolean isSend = true;

    @Override
    protected void setView() {
        setContentView(R.layout.activity_move);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.move_map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        dinatesDao = new DinatesDaoImpl(this);
    }

    @Override
    protected void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //定位方法
        locate();
    }

    public void clearInfo(View view) {
        dinatesList = dinatesDao.find();
        if (dinatesList.size() > 0) {
            for (DinatesBean bean : dinatesList) {
                dinatesDao.delete(bean.getId());
            }
        }
    }

    protected void addPolylineInPlayGround() {
        List<LatLng> list = readLatLngs();
        List<Integer> colorList = new ArrayList<Integer>();
        List<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();

        int[] colors = new int[]{Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0), Color.argb(255, 255, 0, 0)};

        //用一个数组来存放纹理
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.custtexture));

        List<Integer> texIndexList = new ArrayList<Integer>();
        texIndexList.add(0);//对应上面的第0个纹理
        texIndexList.add(1);
        texIndexList.add(2);

        Random random = new Random();
        for (int i = 0; i < list.size(); i++) {
            colorList.add(colors[random.nextInt(3)]);
            bitmapDescriptors.add(textureList.get(0));

        }

        aMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture)) //setCustomTextureList(bitmapDescriptors)
//				.setCustomTextureIndex(texIndexList)
                .addAll(list)
                .useGradient(true)
                .width(18));
    }

    /**
     * 取到Sqlite的数据，把点位add到mark上
     *
     * @return
     */
    protected List<LatLng> readLatLngs() {
        List<LatLng> points = new ArrayList<>();

        for (DinatesBean bean : dinatesList) {
            if ((int) bean.getLongitude() != 0 || (int) bean.getLatitude() != 0)
                points.add(new LatLng(bean.getLatitude(), bean.getLongitude()));
        }
        return points;
    }

    public void onBackClick(View view) {//定位点击事件
        locate();
    }

    /**
     * 定位方法
     */
    protected void locate() {
        setUpMap();
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }

    }

    public void onTrajectory(View view) {//轨迹点击事件
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        trajectory();
    }

    /**
     * 轨迹方法
     */
    protected void trajectory() {
        dinatesList = dinatesDao.rawQuery("select * from t_gps where time > ?", new String[]{WMyUtils.getTimesmorning()});
        if (dinatesList.size() > 0) {
            addPolylineInPlayGround();
            List<LatLng> points = readLatLngs();
            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < points.size(); i++) {
                b.include(points.get(i));
            }
            LatLngBounds bounds = b.build();
            aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            LatLng drivePoint = points.get(0);
            Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(points, drivePoint);
            points.set(pair.first, drivePoint);
            if (isSend) {
                isSend = false;
                initMarker();
            }
        } else {
            findViewById(R.id.move_trajectory).setVisibility(View.GONE);
        }
    }

    /**
     * 设置一些amap的属性
     */
    protected void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        mapView.onPause();
        deactivate();
        mFirstFix = false;
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mLocMarker != null) {
            mLocMarker.destroy();
        }
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                if (!mFirstFix) {
                    mFirstFix = true;
                    addCircle(location, aMapLocation.getAccuracy());//添加定位精度圆
                    addMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(aMapLocation.getAccuracy());
                    mLocMarker.setPosition(location);
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(location));
                }
                if (isSend) {
                    trajectory();
                }

                /**
                 * 下边的逻辑是用来写当在地图界面的时候还在行走状态，路线要实时画出来
                 */
                GPSBean gpsBean = new GPSBean(aMapLocation.getLongitude(), aMapLocation.getLatitude());
                if ((int) aMapLocation.getLongitude() != 0 || (int) aMapLocation.getLatitude() != 0) {
                    if (Double.parseDouble(String.valueOf(aMapLocation.getAccuracy())) > 0 && Double.parseDouble(String.valueOf(aMapLocation.getAccuracy())) < 25) {
                        if (Distance.isCompare(this, gpsBean)) {
                            MyRequest.gpsRequest(this, gpsBean);
                            dinatesDao.insert(new DinatesBean(aMapLocation.getLongitude(), aMapLocation.getLatitude(), System.currentTimeMillis() / 1000));
                            SharedUtil.setString(this, "longitude", String.valueOf(aMapLocation.getLongitude()));
                            SharedUtil.setString(this, "latitude", String.valueOf(aMapLocation.getLatitude()));
                            trajectory();
                        } 
                    }
                }
            }
        } else {
            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
            Log.e("AmapErr", errText);
        }
    }


    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    protected void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        MarkerOptions options = new MarkerOptions();
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.navi_map_gps_locked)));
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
        mLocMarker.setTitle(LOCATION_MARKER_FLAG);
    }

    protected abstract void initMarker();
}