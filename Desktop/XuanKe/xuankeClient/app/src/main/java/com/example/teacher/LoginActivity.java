package com.example.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Ip;
import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.S;
import com.example.util.T;

/**
 * 登录界面
 *
 * @author Administrator
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    EditText et_user;
    EditText et_password;
    TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


      //  Ip.ips = "http://" + S.getString(LoginActivity.this, "ip", "ip", Ip.ip) + ":8080/XuankeService/Do?action=";

        // 初始化控件
        et_user = (EditText) findViewById(R.id.edit_denglu_user);
        et_password = (EditText) findViewById(R.id.edit_denglu_pwd);
        tv_login = (TextView) findViewById(R.id.tv_denglu_btn);
        tv_login.setOnClickListener(this);
        findViewById(R.id.option).setOnClickListener(this);


        if (S.getBoolean(this, "login", "login", false)) {

            Users user = S.loadU();
            if (user.getUser() == null || user.getPswd() == null) {
                return;
            }
            String str_user = user.getUser();
            String str_password = user.getPswd();
            login(str_user, str_password);

        }




    }

    /**
     * 监听各种按钮
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option:

                final EditText et = new EditText(LoginActivity.this);
                et.setText(S.getString(LoginActivity.this, "ip", "ip", Ip.ip));
                new AlertDialog.Builder(LoginActivity.this).setMessage("修改服务器地址").setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String ip = et.getText().toString();
                                if (ip.equals("")) {
                                    T.Show("ip地址不能为空");
                                } else {
                                    S.setString(LoginActivity.this, "ip", "ip", ip);
                                    Ip.ips = "http://" + S.getString(LoginActivity.this, "ip", "ip", Ip.ip) + ":8080/XuankeService/Do?action=";


                                }

                            }
                        }).setNegativeButton("取消", null).show();

                break;
            case R.id.tv_denglu_btn:

                String str_user = et_user.getText().toString();
                String str_password = et_password.getText().toString();
                login(str_user, str_password);

                break;


        }

    }

    void login(String username, String pswd) {
        if (username == null || pswd == null || username.equals("")
                || pswd.equals("")) { //判断输入的账号和密码是否为空
            T.Show("账号或密码不能为空");

            return;
        }
        Users u = UserDBService.getInstence().search(username, pswd);
        if (u == null) {
            T.Show("账号或密码输入不正确");
        } else {
            S.setBoolean(LoginActivity.this, "login", "login", true);//缓存登录状态，下次自动登录
            S.saveU(u);//缓存用户登录信息
            if (u.getType() == 1) {//学生登录跳转学生主界面
                startActivity(new Intent(LoginActivity.this,
                        StudentMainActivity.class));
            } else {//老师登录跳转老师主界面
                startActivity(new Intent(LoginActivity.this,
                        TeacherMainActivity.class));
            }

            finish();//销毁当前界面
        }

    }

    long newTime;

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - newTime > 2000) {
            newTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

}
