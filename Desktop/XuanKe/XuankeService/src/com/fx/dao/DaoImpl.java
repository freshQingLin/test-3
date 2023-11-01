package com.fx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DaoImpl extends BaseDaoImpl {

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	

	/**
	 * 查询所有课程
	 * 
	 * @return
	 */
	public JSONArray searchCourse() {
		conn = this.getConnection();
		JSONArray list = new JSONArray();
		try {

			pstmt = conn.prepareStatement("select * from course");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JSONObject content = new JSONObject();
				content.put("cid",rs.getInt("cid"));
				content.put("uid",rs.getInt("uid"));
				content.put("uid2",rs.getInt("uid2"));
				content.put("name",rs.getString("name"));
				content.put("num",rs.getString("num"));
				content.put("time",rs.getString("time"));

				list.add(content);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;

	}
	
	/**
	 * 老师查询自己的课程
	 * @return
	 */
	public JSONArray searchCourse2(int uid) {
		conn = this.getConnection();
		JSONArray list = new JSONArray();
		try {
			
			pstmt = conn.prepareStatement("select * from course where uid="+uid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JSONObject content = new JSONObject();
				content.put("cid",rs.getInt("cid"));
				content.put("uid",rs.getInt("uid"));
				content.put("uid2",rs.getInt("uid2"));
				content.put("name",rs.getString("name"));
				content.put("num",rs.getString("num"));
				content.put("time",rs.getString("time"));
				
				list.add(content);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
			
		}
		return list;
		
	}
	
	/**
	 * 主管老师查询课程
	 * @return
	 */
	public JSONArray searchCourse3(int uid) {
		conn = this.getConnection();
		JSONArray list = new JSONArray();
		try {
			
			pstmt = conn.prepareStatement("select * from course where uid2="+uid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JSONObject content = new JSONObject();
				content.put("cid",rs.getInt("cid"));
				content.put("uid",rs.getInt("uid"));
				content.put("uid2",rs.getInt("uid2"));
				content.put("name",rs.getString("name"));
				content.put("num",rs.getString("num"));
				content.put("time",rs.getString("time"));
				
				list.add(content);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
			
		}
		return list;
		
	}

	

	/**
	 * 保存学生选课信息
	 * @param uid
	 * @param cid
	 * @param content1
	 * @return
	 */
	public boolean saveApply(int uid,int cid,String content1) {
		conn = this.getConnection();
		try {
			pstmt = conn
					.prepareStatement("insert into apply(uid,cid,statu,content1)values(?,?,?,?)");
			pstmt.setInt(1, uid);
			pstmt.setInt(2, cid);
			pstmt.setInt(3, 1);
			pstmt.setString(4, content1);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}

	}
	
	/**
	 * 查询学生是否已经选择了此课程
	 * @param uid
	 * @param cid
	 * @return
	 */
	public boolean isApply(int uid,int cid) {
		conn = this.getConnection();
		try {

			pstmt = conn.prepareStatement("select * from apply where uid="
					+ uid+" and cid="+cid+" and statu !=6");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return false;

	}
	
	/**
	 * 查询学生是否正在申请课程
	 * @param uid
	 * @return
	 */
	public boolean isApply(int uid) {
		conn = this.getConnection();
		try {
			
			pstmt = conn.prepareStatement("select * from apply where uid="
					+ uid+" and ( statu=1 or statu=2 or  statu=4 or  statu=5)");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				return true;
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
			
		}
		return false;
		
	}
	
	/**
	 * 学生查询课程申请记录
	 * @param uid
	 * @return
	 */
	public JSONArray searchApply2(int uid) {
		conn = this.getConnection();
		JSONArray list = new JSONArray();
		try {

			pstmt = conn.prepareStatement("select a.*,c.name,c.num,c.time,c.uid cuid,c.uid2 cuid2 from apply a left join course c on c.cid=a.cid where a.uid="+uid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JSONObject content = new JSONObject();
				content.put("aid",rs.getInt("aid"));
				content.put("uid",rs.getInt("uid"));
				content.put("cid",rs.getInt("cid"));
				content.put("statu",rs.getInt("statu"));
				content.put("content1",rs.getString("content1"));
				content.put("content2",rs.getString("content2"));
				content.put("content3",rs.getString("content3"));
				
				
				content.put("name",rs.getString("name"));
				content.put("num",rs.getString("num"));
				content.put("time",rs.getString("time"));
				content.put("cuid",rs.getInt("cuid"));
				content.put("cuid2",rs.getInt("cuid2"));

				list.add(content);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;

	}
	
	
	
	
	/**
	 * 老师查询课程申请记录
	 * @param cid
	 * @return
	 */
	public JSONArray searchApply(int cid) {
		conn = this.getConnection();
		JSONArray list = new JSONArray();
		try {

			pstmt = conn.prepareStatement("select * from apply where cid="+cid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JSONObject content = new JSONObject();
				content.put("aid",rs.getInt("aid"));
				content.put("uid",rs.getInt("uid"));
				content.put("cid",rs.getInt("cid"));
				content.put("statu",rs.getInt("statu"));
				content.put("content1",rs.getString("content1"));
				content.put("content2",rs.getString("content2"));
				content.put("content3",rs.getString("content3"));

				list.add(content);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;

	}
	
	
	
	

	
	/**
	 * 主讲老师驳回
	 * @param aid
	 * @param content2
	 * @return
	 */
	public boolean updateApply(int aid,String content2) {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update apply set statu=?,content2=? where aid='"
					+ aid + "'");
			pstmt.setInt(1, 4);
			pstmt.setString(2, content2);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	
	/**
	 * 主管老师驳回
	 * @param aid
	 * @param content3
	 * @return
	 */
	public boolean updateApply2(int aid,String content3) {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update apply set statu=?,content3=? where aid='"
					+ aid + "'");
			pstmt.setInt(1, 5);
			pstmt.setString(2, content3);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	
	/**
	 * 主讲/主管老师同意
	 * @param aid
	 * @param statu
	 * @return
	 */
	public boolean updateApply3(int aid,int statu) {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update apply set statu=? where aid='"
					+ aid + "'");
			pstmt.setInt(1, statu);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	
	
	
	
	
	
	
	

}
