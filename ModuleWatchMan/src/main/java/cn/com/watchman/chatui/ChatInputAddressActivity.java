package cn.com.watchman.chatui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.linked.erfli.library.base.BaseActivity;

import cn.com.watchman.R;

/**
 * Created by 志强 on 2017.6.14.
 */

public class ChatInputAddressActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout back, save;
    private EditText editText;
    private String text, oldText;


    @Override
    protected void setView() {
        setContentView(R.layout.activity_chat_inputaddress);
    }

    @Override
    protected void setDate(Bundle savedInstanceState) {
        oldText = "";
        if (getIntent().getStringExtra("value") != null) {
            oldText = getIntent().getStringExtra("value");
        }
    }

    @Override
    protected void init() {
        back = (LinearLayout) findViewById(R.id.title_back);
        save = (LinearLayout) findViewById(R.id.title_save);
        back.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        editText = (EditText) findViewById(R.id.chat_input_address_text);
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
