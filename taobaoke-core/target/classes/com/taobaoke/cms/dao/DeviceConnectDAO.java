package com.taobaoke.cms.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.taobaoke.cms.model.DeviceConnect;

@DAO(catalog = "cms")
public interface DeviceConnectDAO {

	String TABLE = "device_connect";

	String INSERT_FIELD = "device_no, connect_id, status, create_time, update_time";

	String SELECT_FIELD = "id, " + INSERT_FIELD;

	@SQL("insert into " + TABLE + "(" + INSERT_FIELD + ") values " + 
	"(:1, :2, :3, now(), now()) ON DUPLICATE KEY UPDATE device_no=:1, connect_id=:2, status=:3, update_time=now()")
	int insert(String deviceNo, long connectId, int status);

	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where id=:1")
	DeviceConnect getById(int id);
	
	@SQL("select " + SELECT_FIELD + " from " + TABLE + " where device_no=:1")
    List<DeviceConnect> getByDeviceNo(String deviceNo);
	
	@SQL("select device_no from " + TABLE + " where connect_id=:1")
    String getDeviceNoByConnectId(long connectId);

	@SQL("update " + TABLE + " set connect_id=:2, status=:3  where device_no=:1")
	public int update(String deviceNo, long connectId, int status);

	@SQL("update " + TABLE + " set status=:2  where connect_id=:1")
	public int updateStatusByConnectId(long connectId, int status);

}
