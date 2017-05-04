package cn.com.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.linked.erfli.library.base.BaseActivity;
import com.linked.erfli.library.utils.StatusBarUtils;

/**
 * Created by 志强 on 2017.4.27.
 */

public class InputTextActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout back, save;
    private EditText editText;
    private String text, oldText;

    @Override
    protected void setView() {
        setContentView(R.layout.task_activity_inputtext);
        StatusBarUtils.ff(InputTextActivity.this, R.color.blue);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        oldText = "";
        String getStr = getIntent().getStringExtra("value");
        if (getStr != null) {
            oldText = getIntent().getStringExtra("value");
        }
    }

    @Override
    protected void init() {
        back = (LinearLayout) findViewById(R.id.title_back);
        save = (LinearLayout) findViewById(R.id.title_save);
        back.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        editText = (EditText) findViewById(R.id.input_text);
        editText.setText(oldText);
        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int i = v.getId();
        if (i == R.id.title_back) {
            intent.putExtra("return_value", oldText);
            setResult(RESULT_CANCELED, intent);
            finish();

        } else if (i == R.id.title_save) {
            text = editText.getText().toString();
            intent.putExtra("return_value", text);
            setResult(RESULT_OK, intent);
            finish();

        }
    }
}
