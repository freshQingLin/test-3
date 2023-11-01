package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.Api;
import com.example.util.T;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONObject;

//已选课学生界面
public class ApplyListActivityForTeacher extends BaseActivity {
    ListView lv;
    JSONArray array;
    int cid;
    AlertDialog.Builder mMaterialDialog;
    EditText contentView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list2);
        cid = getIntent().getIntExtra("cid", 0);

        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final JSONObject object = array.optJSONObject(position);
                int statu = object.optInt("statu");
                final int aid = object.optInt("aid");
                if (statu == 1) {
                    new AlertDialog.Builder(ApplyListActivityForTeacher.this)
                            .setTitle("选项")
                            .setItems(new String[]{"同意申请", "拒绝申请"},
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                            switch (which) {
                                                case 0:
                                                    update(object.optInt("aid"), 2);
                                                    break;

                                                case 1:


                                                    contentView = new EditText(ApplyListActivityForTeacher.this);
                                                    contentView.setGravity(Gravity.CENTER);

                                                    mMaterialDialog = new AlertDialog.Builder(ApplyListActivityForTeacher.this)
                                                            .setTitle("请输入拒绝理由").setView(contentView);
                                                    mMaterialDialog.setPositiveButton("确定",
                                                            new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    String content = contentView.getText().toString();
                                                                    if (content.equals("")) {
                                                                        Toast.makeText(ApplyListActivityForTeacher.this,
                                                                                "理由不能为空", Toast.LENGTH_SHORT)
                                                                                .show();
                                                                    } else {
                                                                        HttpParams params = new HttpParams();
                                                                        params.put("aid", aid);
                                                                        params.put("content2", content);
                                                                        Api.post("updateApply", params, new Api.CallBack() {
                                                                            @Override
                                                                            public void success(JSONObject result) {
                                                                                T.Show(result.optString("msg"));
                                                                                getList();
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
                                    }).setNegativeButton("取消", null).show();
                } else if (statu == 2) {
                    T.Show("等待主管老师审核");
                }  else if (statu == 3) {
                    T.Show("已通过申请");
                } else if (statu == 4) {
                    T.Show("已驳回，驳回理由："+object.optString("content2"));
                } else if (statu == 5) {
                    T.Show("已驳回，驳回理由："+object.optString("content3"));
                }

                else {
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

    /**
     * 获取已报名学生列表
     */
    private void getList() {

        Api.get("searchApply&cid=" + cid, new Api.CallBack() {
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
            TextView tView = new TextView(ApplyListActivityForTeacher.this);
            JSONObject object = array.optJSONObject(position);
            Users users = UserDBService.getInstence().search(object.optInt("uid"));

            int statu = object.optInt("statu");
            String statuRes = "";
            if (statu == 1) {
                statuRes = "申请中";
            } else if (statu == 2) {
                statuRes = "已同意，等待主管老师审批";
            } else if (statu == 3) {
                statuRes = "已通过申请";
            } else if (statu == 4) {
                statuRes = "已驳回，驳回理由：" + object.optString("content2");
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

            tView.setText("姓名：" + users.getName()
                    + "\n性别：" + users.getSex()
                    + "\n申请理由："
                    + object.optString("content1")
                    + "\n申请状态："
                    + statuRes

            );
            tView.setPadding(20, 20, 20, 20);
            return tView;
        }

    }

    class Holder {
        TextView tv_title, tv_time;
    }

}
