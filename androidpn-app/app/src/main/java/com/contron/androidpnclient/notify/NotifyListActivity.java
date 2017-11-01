package com.contron.androidpnclient.notify;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.contron.androidpn.apnbb.Constants;
import com.contron.androidpnclient.R;
import com.contron.androidpnclient.db.DBConsts;
import com.contron.androidpnclient.db.DBIQOperator;

import org.androidpn.client.LogUtil;
import org.androidpn.client.NotificationIQ;


public class NotifyListActivity extends ActionBarActivity {
    private static final String LOG_TAG = LogUtil.makeLogTag(NotifyListActivity.class);

    private DBIQOperator operator;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);
        ListView listView = (ListView) findViewById(R.id.notify_list);
        operator = new DBIQOperator(this);
        cursor = operator.queryAll();
        CursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_notify_item, cursor,
                new String[]{DBConsts.IQ_TITLE, DBConsts.IQ_MSG, DBConsts.IQ_TIME},
                new int[]{R.id.notify_list_title, R.id.notify_list_msg, R.id.notify_list_time});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                NotificationIQ iq = operator.queryIQBy_Id(id);
                startActivity(new Intent(NotifyListActivity.this, NotifyDetailActivity.class).putExtra(Constants.INTENT_EXTRA_IQ, iq));
            }
        });
    }

    @Override
    protected void onDestroy() {
        cursor.close();
        operator.closeDB();
        super.onDestroy();
    }
}
