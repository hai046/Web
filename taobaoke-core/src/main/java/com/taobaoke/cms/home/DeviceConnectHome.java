package com.taobaoke.cms.home;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.taobaoke.cms.dao.ConnectIDGeneratorDAO;
import com.taobaoke.cms.dao.DeviceConnectDAO;
import com.taobaoke.cms.model.DeviceConnect;

@Component
public class DeviceConnectHome {

	@Autowired
	private DeviceConnectDAO deviceConnectDAO;
	
	@Autowired
    private ConnectIDGeneratorDAO connectIDGeneratorDAO;
	
    private static ApplicationContext context;

    private static DeviceConnectHome instance;
    
    public static final int STATUS_OK = 0;
	
	public static DeviceConnectHome getInstance() {
        if (instance != null) {
            return instance;
        }
        if (context == null) {
            throw new RuntimeException("Initial Error");
        }
        instance = (DeviceConnectHome) BeanFactoryUtils.beanOfType(context, DeviceConnectHome.class);
        return instance;
    }

    @Autowired
    public void setContext(ApplicationContext rContext) {
        context = rContext;
    }
	
	public boolean insert(String deviceNo, long connectId, int status) throws SQLException {
		try {
			return deviceConnectDAO.insert(deviceNo, connectId, status) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException("插入数据失败");
		}

	}

	public DeviceConnect getById(int id) throws SQLException {
		try {
			return deviceConnectDAO.getById(id);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public List<DeviceConnect> getByDeviceNo(String deviceNo) throws SQLException {
		try {
			return deviceConnectDAO.getByDeviceNo(deviceNo);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}
	
	public String getDeviceNoByConnectId(long connectId) throws SQLException {
        try {
            return deviceConnectDAO.getDeviceNoByConnectId(connectId);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

	public boolean update(String deviceNo, long connectId, int status) throws SQLException {
		try {
			return deviceConnectDAO.update(deviceNo, connectId, status) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}


	public boolean updateByConnectId(long connectId, int status) throws SQLException {
		try {
			return deviceConnectDAO.updateStatusByConnectId(connectId, status) > 0 ? true : false;
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		
	}
	
	public long generateNewConnectId() throws SQLException {
        try {
            return connectIDGeneratorDAO.generateId();
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
        
    }

}
