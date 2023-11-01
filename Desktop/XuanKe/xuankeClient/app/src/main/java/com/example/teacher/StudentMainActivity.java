package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.util.S;
//学生主界面
public class StudentMainActivity extends BaseActivity implements OnClickListener {
	TextView tv1, tv2, tv3, tv4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_student);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);

		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {


		case R.id.tv1://选课列表
			startActivity(new Intent(StudentMainActivity.this, 	CourseActivityForStudent.class));
			break;
		case R.id.tv2://我的课程
			startActivity(new Intent(StudentMainActivity.this, ApplyListActivityForStudent.class));
			break;

		case R.id.tv3://个人中心
			startActivity(new Intent(StudentMainActivity.this, StudentMsgActivity.class));
			break;
		case R.id.tv4:
			S.setBoolean(StudentMainActivity.this, "login", "login", false);
			startActivity(new Intent(StudentMainActivity.this, LoginActivity.class));
			finish();
			break;
		}

	}

}
