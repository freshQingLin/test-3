package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.S;
import com.example.util.T;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

//老师信息界面
public class TeacherMsgActivity extends BaseActivity implements OnClickListener {

    Users user;
    TextView tv_user;
    TextView tv_sex;
    TextView tv_name;
    TextView etPswd;


    RelativeLayout rl_sex;
    RelativeLayout rl_name;

    RelativeLayout rl_pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_msg);

        tv_user = (TextView) findViewById(R.id.regster_editText_user);
        tv_sex = (TextView) findViewById(R.id.regster_editText_sex);
        tv_name = (TextView) findViewById(R.id.regster_editText_name);
        etPswd = (TextView) findViewById(R.id.etPswd);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        rl_name = (RelativeLayout) findViewById(R.id.rl_name);

        rl_pswd = (RelativeLayout) findViewById(R.id.rl_pswd);
        rl_sex.setOnClickListener(this);
        rl_name.setOnClickListener(this);
        rl_pswd.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    protected void onResume() {
        super.onResume();
        user = S.loadU();
        String u = user.getUser();
        String sex = user.getSex();
        String name = user.getName();

        tv_user.setText(u);
        tv_sex.setText(sex);
        tv_name.setText(name);


    }

    ;

    AlertDialog.Builder mMaterialDialog;
    EditText contentView;
    int isex = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_sex:
                mMaterialDialog = new AlertDialog.Builder(TeacherMsgActivity.this);
                mMaterialDialog.setTitle("修改性别");
                NumberPicker picker = new NumberPicker(TeacherMsgActivity.this);
                picker.setDisplayedValues(new String[]{"男", "女"});
                picker.setMinValue(0);
                picker.setMaxValue(1);
                if (user.getSex().equals("男")) {
                    picker.setValue(0);
                    isex = 0;
                } else {
                    picker.setValue(1);
                    isex = 1;
                }
                picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

                    @Override
                    public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
                        // TODO Auto-generated method stub
                        isex = arg2;
                        // watertanktemp.setText(String.format("%02d",
                        // arg2));
                    }
                });
                mMaterialDialog.setView(picker);
                mMaterialDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isex == 0) {
                            user.setSex("男");
                            tv_sex.setText("男");
                            dialog.dismiss();
                        } else {
                            user.setSex("女");
                            tv_sex.setText("女");
                            dialog.dismiss();
                        }
                        updateMsg();

                    }
                });
                mMaterialDialog.setNegativeButton("取消", null);
                mMaterialDialog.show();

                break;
            case R.id.rl_name:
                contentView = new EditText(TeacherMsgActivity.this);
                contentView.setGravity(Gravity.CENTER);

                mMaterialDialog = new AlertDialog.Builder(TeacherMsgActivity.this).setTitle("修改名字").setView(contentView);
                mMaterialDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickname = contentView.getText().toString();
                        if (nickname.length() > 10 || nickname.length() < 2) {
                            Toast.makeText(TeacherMsgActivity.this, "名字的长度为3~10之间", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            user.setName(nickname);
                            tv_name.setText(nickname);
                            dialog.dismiss();
                            updateMsg();

                        }
                    }
                });
                mMaterialDialog.setNegativeButton("取消", null);
                mMaterialDialog.show();
                break;


            case R.id.rl_pswd:
                Intent i2 = new Intent(TeacherMsgActivity.this, ChangePasswordActivity.class);
                startActivity(i2);
                break;

        }

    }

    public void updateMsg() {
        String json = new Gson().toJson(user);
        try {
            UserDBService.getInstence().update(new JSONObject(json));
            S.saveU(user);
            T.Show("修改成功");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
