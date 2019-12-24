package com.example.sampleappcollection.sqlitetable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：Sqlite数据库
 *
 * @author zhangqin
 * @date 2017/2/28
 */
public class SqliteTableActivity extends AppCompatActivity {

    private MySqlite mMySqlite;
    private Button mBtn_add;
    private SQLiteDatabase db;
    private TableLayout mTable1;
    private List<List> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_table);
        mTable1 = findViewById(R.id.table1);

        mMySqlite = new MySqlite(this);
        db = mMySqlite.getWritableDatabase();

        mBtn_add = findViewById(R.id.btn_add);
        mBtn_add.setOnClickListener(view -> showMyDialog());

        showData();
    }

    private void showData() {

        mList.clear();

        Cursor cursor = db.query("usertable", null, null, null, null, null, null);

        //判断游标是否为空
        if (cursor.moveToFirst()) {

            int x = cursor.getCount();
            //遍历游标
            do {
                List<String> list = new ArrayList<>();
                //获得ID
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String red = cursor.getString(2);
                String green = cursor.getString(3);
                String yellow = cursor.getString(4);

                Log.e("test", "showData: " + id);
                Log.e("test", "x: " + x);

                list.add(id + "");
                list.add(name);
                list.add(red);
                list.add(green);
                list.add(yellow);

                mList.add(list);
            } while (cursor.moveToNext());
            cursor.close();
            Log.e("test", mList.toString());

            //清除表格所有行
            mTable1.removeAllViews();
            //全部列自动填充空白处
            mTable1.setStretchAllColumns(true);
            // 4列x行
            for (int i = 0; i < x; i++) {
                TableRow tableRow = new TableRow(SqliteTableActivity.this);
                for (int j = 0; j < 4; j++) {
                    TextView tv = new TextView(SqliteTableActivity.this);
                    tv.setText(mList.get(i).get(j + 1) + "");
                    tv.setBackgroundResource(R.drawable.table_bg);
                    tv.setPadding(0, 5, 0, 5);
                    tv.setGravity(Gravity.CENTER);
                    tv.setWidth(1);
                    tableRow.addView(tv);
                }
                mTable1.addView(tableRow, new TableLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            }

        }


    }

    /**
     * 显示增加弹窗
     */
    private void showMyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.dialog_add, null);
        final EditText add_name = view.findViewById(R.id.add_name);
        final EditText add_red = view.findViewById(R.id.add_red);
        final EditText add_green = view.findViewById(R.id.add_green);
        final EditText add_yellow = view.findViewById(R.id.add_yellow);

        builder.setTitle("增加数据")
                .setView(view)
                .setCancelable(false);

        builder.setPositiveButton("确认", (dialogInterface, i) -> {
            ContentValues values = new ContentValues();
            values.put("name", add_name.getText().toString().trim() + "");
            values.put("red", add_red.getText().toString().trim() + "");
            values.put("green", add_green.getText().toString().trim() + "");
            values.put("yellow", add_yellow.getText().toString().trim() + "");
            db.insert("usertable", null, values);

            showData();
        })
                .setNegativeButton("取消", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
