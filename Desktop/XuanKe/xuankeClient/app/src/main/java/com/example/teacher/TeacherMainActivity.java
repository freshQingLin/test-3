package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.util.S;

//主界面
public class TeacherMainActivity extends BaseActivity implements OnClickListener {
    TextView  tv2, tv3, tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_teacher);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {



            case R.id.tv2:
                startActivity(new Intent(TeacherMainActivity.this, CourseActivityForTeacher.class));
                break;

            case R.id.tv3:
                startActivity(new Intent(TeacherMainActivity.this, TeacherMsgActivity.class));
                break;
            case R.id.tv4:
                S.setBoolean(TeacherMainActivity.this, "login", "login", false);
                startActivity(new Intent(TeacherMainActivity.this, LoginActivity.class));
                finish();
                break;
        }

    }

}
