package com.fx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fx.dao.DaoImpl;

public class Do extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();
		DaoImpl ndi = new DaoImpl();
		if (action.equals("searchCourse")) {

			JSONArray array = ndi.searchCourse();
			object.put("code", "success");
			object.put("msg", "查询成功");
			object.put("data", array);

		} else if (action.equals("searchCourse2")) {

			JSONArray array = ndi.searchCourse2(Integer.parseInt(request
					.getParameter("uid")));
			object.put("code", "success");
			object.put("msg", "查询成功");
			object.put("data", array);

		} else if (action.equals("searchCourse3")) {

			JSONArray array = ndi.searchCourse3(Integer.parseInt(request
					.getParameter("uid")));
			object.put("code", "success");
			object.put("msg", "查询成功");
			object.put("data", array);

		} else if (action.equals("saveApply")) {

			String content1 = request.getParameter("content1");
			int uid = Integer.parseInt(request.getParameter("uid"));
			int cid = Integer.parseInt(request.getParameter("cid"));

			if (!ndi.isApply(uid) && !ndi.isApply(uid, cid)) {

				ndi.saveApply(uid, cid, content1);

				object.put("code", "success");
				object.put("msg", "申请提交成功，等待审核");

			} else {
				object.put("code", "fail");
				object.put("msg", "一次只能申请一门课程");
			}

		} else if (action.equals("searchApply")) {

			int cid = Integer.parseInt(request.getParameter("cid"));
			JSONArray array = ndi.searchApply(cid);
			object.put("code", "success");
			object.put("msg", "查询成功");
			object.put("data", array);

		} else if (action.equals("searchApply2")) {

			int uid = Integer.parseInt(request.getParameter("uid"));
			JSONArray array = ndi.searchApply2(uid);
			object.put("code", "success");
			object.put("msg", "查询成功");
			object.put("data", array);

		}

		else if (action.equals("updateApply")) {

			int aid = Integer.parseInt(request.getParameter("aid"));
			String content2 = request.getParameter("content2");
			ndi.updateApply(aid, content2);
			object.put("code", "success");
			object.put("msg", "操作成功");

		} else if (action.equals("updateApply2")) {

			int aid = Integer.parseInt(request.getParameter("aid"));
			String content3 = request.getParameter("content3");
			ndi.updateApply2(aid, content3);
			object.put("code", "success");
			object.put("msg", "操作成功");

		} else if (action.equals("updateApply3")) {

			int aid = Integer.parseInt(request.getParameter("aid"));
			int statu = Integer.parseInt(request.getParameter("statu"));
			ndi.updateApply3(aid, statu);
			object.put("code", "success");
			object.put("msg", "操作成功");

		}

		System.out.println(object.toString());
		out.print(object);
		out.flush();
		out.close();
	}
}
