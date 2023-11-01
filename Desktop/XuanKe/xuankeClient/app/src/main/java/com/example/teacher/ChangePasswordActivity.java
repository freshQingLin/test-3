package com.example.teacher;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Users;
import com.example.db.UserDBService;
import com.example.util.S;
import com.example.util.T;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 修改密码界面
 *
 * @author Administrator
 */
public class ChangePasswordActivity extends BaseActivity {

    LinearLayout ll_back;
    EditText et_yuan_password;
    EditText et_new_password;
    EditText et_new_password_again;
    TextView tv_xg;
    String yuan_password;
    Users user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        user = S.loadU();

        yuan_password = user.getPswd();
        et_yuan_password = (EditText) findViewById(R.id.change_password_editText_yuan_password);
        et_new_password = (EditText) findViewById(R.id.change_password_editText_new_password);
        et_new_password_again = (EditText) findViewById(R.id.change_password_editText_new_password_again);
        ll_back = (LinearLayout) findViewById(R.id.layout_back);
        ll_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });
        tv_xg = (TextView) findViewById(R.id.tv_xg);
        tv_xg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String new_password = et_new_password.getText().toString();// 获取输入框输入的内容
                String new_password_again = et_new_password_again.getText().toString();
                String yuan = et_yuan_password.getText().toString();
                if (!yuan_password.equals(yuan)) {// 各种判断
                    Toast.makeText(ChangePasswordActivity.this, "原密码输入不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (new_password.length() < 1 || new_password_again.length() < 1) {
                    Toast.makeText(ChangePasswordActivity.this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!new_password.equals(new_password_again)) {
                    Toast.makeText(ChangePasswordActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                user.setPswd(new_password);

                String json = new Gson().toJson(user);
                try {
                    UserDBService.getInstence().update(new JSONObject(json));
                    S.saveU(user);
                    T.Show("修改成功");
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
