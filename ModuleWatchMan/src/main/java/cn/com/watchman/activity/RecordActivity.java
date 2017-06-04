package cn.com.watchman.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.watchman.R;
import cn.com.watchman.adapter.RecordAdapter;
import cn.com.watchman.bean.PathRecord;
import cn.com.watchman.database.DbAdapter;


/**
 * 所有轨迹list展示activity
 */
public class RecordActivity extends Activity implements OnItemClickListener {

    private RecordAdapter mAdapter;
    private ListView mAllRecordListView;
    private DbAdapter mDataBaseHelper;
    private List<PathRecord> mAllRecord = new ArrayList<PathRecord>();
    public static final String RECORD_ID = "record_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordlist);
        mAllRecordListView = (ListView) findViewById(R.id.recordlist);
        mDataBaseHelper = new DbAdapter(this);
        mDataBaseHelper.open();
        searchAllRecordFromDB();
        mAdapter = new RecordAdapter(this, mAllRecord);
        mAllRecordListView.setAdapter(mAdapter);
        mAllRecordListView.setOnItemClickListener(this);
    }

    private void searchAllRecordFromDB() {
        mAllRecord = mDataBaseHelper.queryRecordAll();
    }

    public void onBackClick(View view) {
        this.finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PathRecord recorditem = (PathRecord) parent.getAdapter().getItem(position);
        Intent intent = new Intent(RecordActivity.this, RecordShowActivity.class);
//        intent.putExtra(RECORD_ID, mAllRecord.get(position).getId());
//        startActivity(intent);
        mDataBaseHelper.delete(recorditem.getId());
    }


}
