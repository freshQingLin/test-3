package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.Api;
import com.example.util.S;
import com.example.util.T;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 选课界面列表
 *
 * @author Administrator
 */
public class CourseActivityForStudent extends FragmentActivity {
    ListView lv;

    JSONArray array;
    MyAdapter adapter;

    AlertDialog.Builder mMaterialDialog;
    EditText contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_course_student);
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int arg2, long arg3) {
                JSONObject object = array.optJSONObject(arg2);
                final int cid = object.optInt("cid");

                new AlertDialog.Builder(CourseActivityForStudent.this)
                        .setTitle("操作")
                        .setItems(new String[]{"选这门课程", "取消"},
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        switch (arg1) {

                                            case 0:

                                                contentView = new EditText(CourseActivityForStudent.this);
                                                contentView.setGravity(Gravity.CENTER);

                                                mMaterialDialog = new AlertDialog.Builder(CourseActivityForStudent.this)
                                                        .setTitle("请输入选择理由").setView(contentView);
                                                mMaterialDialog.setPositiveButton("确定",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                String content1 = contentView.getText().toString();
                                                                if (content1.equals("")) {
                                                                    Toast.makeText(CourseActivityForStudent.this,
                                                                            "理由不能为空", Toast.LENGTH_SHORT)
                                                                            .show();
                                                                } else {
                                                                    HttpParams params = new HttpParams();
                                                                    params.put("uid", S.loadU().getUid());
                                                                    params.put("cid", cid);
                                                                    params.put("content1", content1);
                                                                    Api.post("saveApply", params, new Api.CallBack() {
                                                                        @Override
                                                                        public void success(JSONObject result) {
                                                                            T.Show(result.optString("msg"));
                                                                        }

                                                                        @Override
                                                                        public void faile() {

                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        });
                                                mMaterialDialog.setNegativeButton("取消", null);
                                                mMaterialDialog.show();


                                                break;

                                        }

                                    }
                                }).show();

            }
        });
        findViewById(R.id.ivBack).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });


        getData();

    }

    private void getData() {

        Api.get("searchCourse", new Api.CallBack() {
            @Override
            public void success(JSONObject result) {
                array = result.optJSONArray("data");
                if (adapter == null) {
                    adapter = new MyAdapter();
                    lv.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void faile() {

            }
        });

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return array.length();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return array.opt(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup arg2) {

            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(CourseActivityForStudent.this).inflate(
                        R.layout.item1, null);
                holder.tv_title = (TextView) convertView
                        .findViewById(R.id.tv_title);
                holder.tv_time = (TextView) convertView
                        .findViewById(R.id.tv_time);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            JSONObject object = array.optJSONObject(position);
            Users users = UserDBService.getInstence().search(object.optInt("uid"));
            holder.tv_time.setText("计划学时：" + object.optString("time")
                    + "\n上课总人数：" + object.optInt("num") + "人");

            holder.tv_title.setText("课程名称：" + object.optString("name")
                    + "\n主讲老师：" + users.getName());

            return convertView;

        }

    }

    class Holder {
        TextView tv_title, zhaiyao, tv_time;
        ImageView iv;
    }

}
