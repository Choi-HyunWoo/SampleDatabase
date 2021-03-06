package com.hw.corcow.sampledatabase;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
//    ArrayAdapter<AddressItem> mAdapter;
    SimpleCursorAdapter mAdapter;

    int nameColumnIndex = -1;        // name들만 변경할 것이므로 name column index 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView)findViewById(R.id.listView);
//        mAdapter = new ArrayAdapter<AddressItem>(this, android.R.layout.simple_list_item_1);
        String[] from = { AddressDB.AddressTable.COLUMN_NAME , AddressDB.AddressTable.COLUMN_ADDRESS };
        int[] to = {R.id.text_name , R.id.text_address};
        mAdapter = new SimpleCursorAdapter(this, R.layout.view_item, null, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {        // 가져올 뷰, 커서, 몇번째 인덱스(column)?
                if (columnIndex == nameColumnIndex) {
                    TextView tv = (TextView) view;
                    String name = cursor.getString(columnIndex);
                    tv.setText(Html.fromHtml("<b>" + name + "</b>"));
                    return true;            // true return -> Override
                }
                return false;               // false return -> Default action
            }
        });

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) listView.getItemAtPosition(position);           // 리스트뷰에 있는 AddressItem은 커서임!!
                AddressItem item = new AddressItem();
                item._id = id;
                item.name = c.getString(c.getColumnIndex(AddressDB.AddressTable.COLUMN_NAME));
                item.address = c.getString(c.getColumnIndex(AddressDB.AddressTable.COLUMN_ADDRESS));
                item.phone = c.getString(c.getColumnIndex(AddressDB.AddressTable.COLUMN_PHONE));
                item.office = c.getString(c.getColumnIndex(AddressDB.AddressTable.COLUMN_OFFICE));
                item.lastMessageId = c.getLong(c.getColumnIndex(AddressDB.AddressTable.COLUMN_LAST_MESSAGE_ID));
                item.lastMessage = c.getString(c.getColumnIndex(AddressDB.MessageTable.COLUMN_MESSAGE));
                item.timestamp = c.getLong(c.getColumnIndex(AddressDB.MessageTable.COLUMN_CREATED));

                Intent intent = new Intent(MainActivity.this, ChattingActivity.class);
                intent.putExtra(ChattingActivity.EXTRA_ITEM, item);
                startActivity(intent);
            }
        });

        Button btn = (Button)findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DBAddActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c = DataManager.getInstance().getAddressCursor(null);
        nameColumnIndex = c.getColumnIndex("name");             // name의 columnIndex를 받아온다.
        mAdapter.changeCursor(c);               // 지금 가지고있는 커서를 close하고 이 커서를 설정해라.
//        List<AddressItem> list = DataManager.getInstance().getAddressList(null);
//        mAdapter.clear();
//        for (AddressItem item : list) {
//            mAdapter.add(item);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.changeCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
