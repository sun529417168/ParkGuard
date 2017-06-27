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

public class MarkerActivity extends MoveShowActivity {
    private List<DinatesBean> markerList = new ArrayList<>();
    private ViewPoiOverlay poiOverlay;

    @Override
    protected void initMarker() {
        if (dinatesList.size() > 0) {
            markerList.add(dinatesList.get(0));
            markerList.add(dinatesList.get(dinatesList.size() - 1));
            poiOverlay = new ViewPoiOverlay(aMap, markerList);
            poiOverlay.removeFromMap();
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();
        }
    }


    public class ViewPoiOverlay extends PoiOverlay {

        public ViewPoiOverlay(AMap aMap, List<DinatesBean> list) {
            super(aMap, list);
        }

        @Override
        protected BitmapDescriptor getBitmapDescriptor(final int index) {
            View view = null;
            view = View.inflate(MarkerActivity.this, R.layout.custom_view, null);
            TextView textView = ((TextView) view.findViewById(R.id.marker_title));
            textView.setText(getTime(index));

            return BitmapDescriptorFactory.fromView(view);
        }
    }
}
