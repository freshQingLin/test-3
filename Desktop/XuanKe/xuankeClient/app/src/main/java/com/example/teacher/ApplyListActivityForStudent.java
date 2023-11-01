package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.Api;
import com.example.util.S;
import com.example.util.T;

import org.json.JSONArray;
import org.json.JSONObject;

//学生申请的课程界面
public class ApplyListActivityForStudent extends BaseActivity {
    ListView lv;
    JSONArray array;
    int uid;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        uid = S.loadU().getUid();
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                final JSONObject object = array.optJSONObject(position);
                int statu = object.optInt("statu");

                if (statu == 4 || statu == 5) {
                    new AlertDialog.Builder(ApplyListActivityForStudent.this)
                            .setTitle("选项")
                            .setItems(new String[]{"完成申请", "取消"},
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                            switch (which) {
                                                case 0:
                                                    update(object.optInt("aid"), 6);
                                                    break;
                                            }

                                        }
                                    }).show();
                } else if (statu == 1 || statu == 2 || statu == 3) {
                    T.Show("等待老师审核");
                } else {
                    T.Show("申请已完成");
                }


            }
        });
        findViewById(R.id.ivBack).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        getList();
    }


    private void getList() {

        Api.get("searchApply2&uid=" + S.loadU().getUid(), new Api.CallBack() {
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


    private void update(int aid, int statu) {
        Api.get("updateApply3&aid=" + aid + "&statu=" + statu, new Api.CallBack() {
            @Override
            public void success(JSONObject result) {
                T.Show("操作成功");
                getList();
            }

            @Override
            public void faile() {

            }
        });
    }


    class MyAdapter extends BaseAdapter {
        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            TextView tView = new TextView(ApplyListActivityForStudent.this);
            JSONObject object = array.optJSONObject(position);
            Users users = UserDBService.getInstence().search(object.optInt("cuid"));
            int statu = object.optInt("statu");
            String statuRes = "";
            if (statu == 1) {
                statuRes = "申请中";
            } else if (statu == 2) {
                statuRes = "主讲老师已同意，等待主管老师审批";
            } else if (statu == 3) {
                statuRes = "已通过申请";
            } else if (statu == 4) {
                statuRes = "主讲老师驳回，驳回理由：" + object.optString("content2");
            } else if (statu == 5) {
                statuRes = "主管老师驳回，驳回理由：" + object.optString("content3");
            } else if (statu == 6) {


                if(!object.optString("content2").equals("")){
                    statuRes = "申请已被驳回，本次申请已完成";
                }else if(!object.optString("content3").equals("")){
                    statuRes = "申请已被驳回，本次申请已完成";
                }else{
                    statuRes = "课程申请通过，本次申请已完成";
                }

            }


            tView.setText("课程名称："
                    + object.optString("name")
                    + "\n任课老师："
                    + users.getName()
                    + "\n申请理由："
                    + object.optString("content1")
                    + "\n申请状态："
                    + statuRes

            );
            tView.setPadding(20, 20, 20, 20);
            return tView;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return array.opt(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return array.length();
        }
    }

}
