package cn.com.watchman.chatui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.linked.erfli.library.config.UrlConfig;
import com.linked.erfli.library.function.wheelview.OnWheelChangedListener;
import com.linked.erfli.library.function.wheelview.OnWheelScrollListener;
import com.linked.erfli.library.function.wheelview.adapter.AbstractWheelTextAdapter1;
import com.linked.erfli.library.okhttps.OkHttpUtils;
import com.linked.erfli.library.okhttps.callback.GenericsCallback;
import com.linked.erfli.library.okhttps.utils.JsonGenericsSerializator;
import com.linked.erfli.library.utils.ToastUtil;
import com.linked.erfli.library.weight.WheelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.watchman.R;
import cn.com.watchman.chatui.enity.ChatProblemTypeLeftEntity;
import cn.com.watchman.chatui.enity.ChatProblemTypeRightEntity;
import okhttp3.Call;

/**
 * zzq
 * 2017年6月14日14:48:24
 */

public class ChatProblemTypePopwindow extends PopupWindow implements View.OnClickListener {
    private WheelView wvProvince;
    private WheelView wvCitys;
    private View lyChangeAddressChild;
    private TextView btnSure;
    private TextView btnCancel;

    private Activity context;
    private AddressTextAdapter provinceAdapter;
    private AddressTextAdapter cityAdapter;

    private String strProvince = "";
    private String strCity = "";
    private String code = "";
    private OnAddressCListener onAddressCListener;

    private int maxsize = 14;
    private int minsize = 12;
    private ArrayList<ChatProblemTypeLeftEntity> problemTypeLeftList;
    private ArrayList<ChatProblemTypeRightEntity> problemTypeRightList;

    public ChatProblemTypePopwindow(final Activity context, ArrayList<ChatProblemTypeLeftEntity> problemTypeLeftLists) {
        super(context);
        this.context = context;
        this.problemTypeLeftList = problemTypeLeftLists;
        View view = View.inflate(context, R.layout.chat_pop_problem_detail_type, null);

        wvProvince = (WheelView) view.findViewById(R.id.wv_address_province);
        wvCitys = (WheelView) view.findViewById(R.id.wv_address_city);
        lyChangeAddressChild = view.findViewById(R.id.ly_myinfo_changeaddress_child);
        btnSure = (TextView) view.findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) view.findViewById(R.id.btn_myinfo_cancel);


        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        lyChangeAddressChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


        provinceAdapter = new AddressTextAdapter(context, problemTypeLeftList, getProvinceItem(problemTypeLeftList.get(0).getName()), maxsize, minsize);
        wvProvince.setVisibleItems(5);
        wvProvince.setViewAdapter(provinceAdapter);
        wvProvince.setCurrentItem(getProvinceItem(problemTypeLeftList.get(0).getName()));
        getTypeRightByCodeRequest(problemTypeLeftList.get(0).getCode());

        wvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        wvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
                getTypeRightByCodeRequest(problemTypeLeftList.get(wheel.getCurrentItem()).getCode());
            }
        });

        wvCitys.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                setTextviewSize(currentText, cityAdapter);
            }
        });
        wvCitys.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                // TODO Auto-generated method stub
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                code = problemTypeRightList.get(wheel.getCurrentItem()).getCode();
                setTextviewSize(currentText, cityAdapter);
            }
        });


    }


    private class AddressTextAdapter extends AbstractWheelTextAdapter1 {
        ArrayList<ChatProblemTypeLeftEntity> listLeft;
        ArrayList<ChatProblemTypeRightEntity> listRight;
        int type = -1;

        protected AddressTextAdapter(Context context, ArrayList<ChatProblemTypeLeftEntity> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.chat_item_wheelview, NO_RESOURCE, currentItem, maxsize, minsize);
            this.listLeft = list;
            setItemTextResource(R.id.item_wheelView);
        }

        protected AddressTextAdapter(Context context, int type, ArrayList<ChatProblemTypeRightEntity> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.chat_item_wheelview, NO_RESOURCE, currentItem, maxsize, minsize);
            this.listRight = list;
            this.type = type;
            setItemTextResource(R.id.item_wheelView);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return type == 1 ? listRight.size() : listLeft.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return type == 1 ? listRight.get(index).getName() : listLeft.get(index).getName();
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(14);
            } else {
                textvew.setTextSize(12);
            }
        }
    }

    public void setAddresskListener(OnAddressCListener onAddressCListener) {
        this.onAddressCListener = onAddressCListener;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == btnSure) {
            if (onAddressCListener != null) {
                onAddressCListener.onClick(strProvince, strCity, code);
            }
        } else if (v == btnCancel) {

        } else if (v == lyChangeAddressChild) {
            return;
        } else {
//			dismiss();
        }
        dismiss();
    }

    /**
     * 回调接口
     *
     * @author Administrator
     */
    public interface OnAddressCListener {
        void onClick(String left, String right, String code);
    }


    /**
     * 返回省会索引，没有就返回默认“部件上报”
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = problemTypeLeftList.size();
        int provinceIndex = 0;
        boolean noprovince = true;
        for (int i = 0; i < size; i++) {
            if (province.equals(problemTypeLeftList.get(i))) {
                noprovince = false;
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        if (noprovince) {
            strProvince = problemTypeLeftList.get(0).getName();
            return 0;
        }
        return provinceIndex;
    }

    /**
     * 得到城市索引，没有返回默认第一个
     *
     * @param city
     * @return
     */
    public int getCityItem(String city) {
        int size = problemTypeRightList.size();
        int cityIndex = 0;
        boolean nocity = true;
        for (int i = 0; i < size; i++) {
            System.out.println(problemTypeRightList.get(i));
            if (city.equals(problemTypeRightList.get(i))) {
                nocity = false;
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        if (nocity) {
            strCity = problemTypeRightList.get(0).getName();
            return 0;
        }
        return cityIndex;
    }

    /**
     * 方法名：getTypeListByCodeRequest
     * 功    能：获取二级节点信息（根据code）
     * 参    数：Activity activity final String username, final String password
     * 返回值：无
     */
    public void getTypeRightByCodeRequest(String codes) {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("SearchProblemType", codes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpUtils.post().url(UrlConfig.URL_GETTYPELISTBYCODE).params(params).build().execute(new GenericsCallback<String>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(String response, int id) {
                ArrayList<ChatProblemTypeRightEntity> problemTypeRight = (ArrayList<ChatProblemTypeRightEntity>) JSON.parseArray(response, ChatProblemTypeRightEntity.class);
                problemTypeRightList = problemTypeRight;
                cityAdapter = new AddressTextAdapter(context, 1, problemTypeRightList, getCityItem(problemTypeRightList.get(0).getName()), maxsize, minsize);
                wvCitys.setVisibleItems(5);
                wvCitys.setViewAdapter(cityAdapter);
                wvCitys.setCurrentItem(getCityItem(problemTypeRightList.get(0).getName()));
                code = problemTypeRight.get(0).getCode();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.show(context, "服务器有错误，请稍候再试");
            }
        });
    }
}
