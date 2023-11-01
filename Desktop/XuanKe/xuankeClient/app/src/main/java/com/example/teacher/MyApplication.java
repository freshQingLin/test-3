package com.example.teacher;

import android.app.Application;

import com.example.util.S;
import com.example.util.T;

public class MyApplication extends Application{
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		T.context = this;
		S.context = this;
	}
}
