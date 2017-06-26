package cn.com.watchman.chatui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 志强 on 2017.6.14.
 */

public class ChatWarningGridView extends GridView {

    private OnTouchInvalidPositionListener onTouchInvalidPositionListener;

    public ChatWarningGridView(Context context) {
        super(context);
    }

    public ChatWarningGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatWarningGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChatWarningGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
