package cn.com.watchman.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Marker;
import com.linked.erfli.library.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.bean.DinatesBean;
import cn.com.watchman.utils.PoiOverlay;

/**
 * 文件名：MarkerActivity
 * 描    述：地图点位标记
 * 作    者：stt
 * 时    间：2017.6.15
 * 版    本：V1.1.2
 */

public class MarkerActivity extends MoveShowActivity implements AMap.OnMarkerClickListener {
    private List<DinatesBean> markerList = new ArrayList<>();
    private ViewPoiOverlay poiOverlay;

    @Override
    protected void initMarker() {
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        if (dinatesList.size() > 0) {
            markerList.add(dinatesList.get(0));
            markerList.add(dinatesList.get(dinatesList.size() - 1));
            poiOverlay = new ViewPoiOverlay(aMap, markerList);
            poiOverlay.removeFromMap();
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();

//            DinatesBean bean5 = new DinatesBean(116.34797509821901, 39.980656820423505, "第五个");
//            DinatesBean bean = new DinatesBean(116.3499049793749, 39.97617053371078, "第一个");
//            DinatesBean bean3 = new DinatesBean(116.34825631960626, 39.976869087779995, "第三个");
//            DinatesBean bean2 = new DinatesBean(116.34918981582413, 39.976260803938594, "第二个");
//            DinatesBean bean4 = new DinatesBean(116.3482098441266, 39.978170063673524, "第四个");
//            markerList.add(bean);
//            markerList.add(bean2);
//            markerList.add(bean3);
//            markerList.add(bean4);
//            markerList.add(bean5);
//
//            aMap.clear();// 清理之前的图标
//            poiOverlay = new ViewPoiOverlay(aMap, markerList);
//            poiOverlay.removeFromMap();
//            poiOverlay.addToMap();
//            poiOverlay.zoomToSpan();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        String markId = marker.getId();
        String subId = markId.substring(markId.length() - 1);
//        ToastUtil.show(this, "第" + markerList.get(Integer.parseInt(marker.getId().substring(marker.getId().length() - 1))).getTime() + "个框框");
        Log.i("点击事件", "markId:" + markId + "subId:" + subId + "===");
        ToastUtil.show(this, markerList.get(Integer.parseInt(subId)-1).getInfo());
        return false;
    }


    public class ViewPoiOverlay extends PoiOverlay {

        public ViewPoiOverlay(AMap aMap, List<DinatesBean> list) {
            super(aMap, list);
        }

        @Override
        protected BitmapDescriptor getBitmapDescriptor(final int index) {
            View view = null;
            view = View.inflate(MarkerActivity.this, R.layout.custom_view, null);
            TextView textView = ((TextView) view.findViewById(R.id.title));
            textView.setText(getTime(index));

            return BitmapDescriptorFactory.fromView(view);
        }
    }
}
