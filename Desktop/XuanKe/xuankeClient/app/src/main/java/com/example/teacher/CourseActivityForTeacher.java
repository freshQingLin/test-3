package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.Api;
import com.example.util.S;

import org.json.JSONArray;
import org.json.JSONObject;


public class CourseActivityForTeacher extends FragmentActivity {
    ListView lv;

    JSONArray array;
    MyAdapter adapter;
    int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_course_teacher);
        lv = (ListView) findViewById(R.id.lv);

        findViewById(R.id.ivBack).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        uid = S.loadU().getUid();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                if(S.loadU().getType()==3){
                    startActivity(new Intent(CourseActivityForTeacher.this, ApplyListActivityForTeacher2.class).putExtra("cid", array.optJSONObject(position).optInt("cid")));

                }else{
                    startActivity(new Intent(CourseActivityForTeacher.this, ApplyListActivityForTeacher.class).putExtra("cid", array.optJSONObject(position).optInt("cid")));

                }


            }
        });
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getList();

    }

    private void getList() {
        if(S.loadU().getType()==3){
            Api.get("searchCourse3&uid=" + S.loadU().getUid(), new Api.CallBack() {
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
        }else{
            Api.get("searchCourse2&uid=" + S.loadU().getUid(), new Api.CallBack() {
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


    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return array.length();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return array.opt(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(CourseActivityForTeacher.this).inflate(
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
            Users users2 = UserDBService.getInstence().search(object.optInt("uid2"));
            holder.tv_time.setText("计划学时：" + object.optString("time")
                    + "\n上课总人数：" + object.optInt("num") + "人");


            if(S.loadU().getType()==3){
                holder.tv_title.setText("课程名称：" + object.optString("name")
                        + "\n主讲老师：" + users.getName()

                );
            }else{
                holder.tv_title.setText("课程名称：" + object.optString("name")
                       + "\n主管老师：" + users2.getName()

                );
            }



            return convertView;
        }

    }

    class Holder {
        TextView tv_title, tv_time;
    }

}
