package cn.com.problem.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 文件名：ReportedGridview
 * 描    述：自定义gridview
 * 作    者：stt
 * 时    间：2017.01.05
 * 版    本：V1.0.0
 */
public class ReportedGridview extends GridView {
    private OnTouchInvalidPositionListener onTouchInvalidPositionListener;

    public ReportedGridview(Context context) {
        super(context);
    }

    public ReportedGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReportedGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ReportedGridview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setOnTouchInvalidPositionListener(
            OnTouchInvalidPositionListener onTouchInvalidPositionListener) {
        this.onTouchInvalidPositionListener = onTouchInvalidPositionListener;
    }

    public interface OnTouchInvalidPositionListener {
        public boolean onTouchInvalidPosition(int motionEvent);
    }
}
