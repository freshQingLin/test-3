package com.example.teacher;

import android.content.Intent;
import android.os.Bundle;

import com.example.db.UserDBService;

//欢迎界面
public class LogoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);


		if (!UserDBService.getInstence().isExit("student")) {
			UserDBService.getInstence().save(1,"teacher1", "123456", "女", "李老师",2);
			UserDBService.getInstence().save(2,"teacher2", "123456", "女", "陈老师",2);
			UserDBService.getInstence().save(3,"teacher3", "123456", "女", "吕老师",2);
			UserDBService.getInstence().save(4,"teacher4", "123456", "女", "江老师",2);
			UserDBService.getInstence().save(5,"teacher5", "123456", "女", "赵老师",2);
			UserDBService.getInstence().save(6,"teacher6", "123456", "女", "唐老师",2);
			UserDBService.getInstence().save(7,"teacher7", "123456", "女", "主管老师",3);
			UserDBService.getInstence().save(8,"student", "123456", "男", "学生",1);
		}










		new Thread() {
			public void run() {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(new Intent(LogoActivity.this, LoginActivity.class));
				finish();
			};
		}.start();
	}

}
