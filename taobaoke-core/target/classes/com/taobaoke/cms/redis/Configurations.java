package com.taobaoke.cms.redis;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * redis 配置
 * 
 * @author Mars zhou
 * 
 */
public class Configurations {

    private static Configurations instance = new Configurations();
    private Properties properties = new Properties();
    private boolean hasConfigFile = true;
    // 连接配置服务器的超时
    public static final int CONNECT_TIMEOUT = 10000;

    // 读取配置服务器的超时
    public static final int READ_TIMEOUT = 10000;

    private Configurations() {
        Properties urlConfig = new Properties();
        try {
            urlConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.propertise"));
            String configUrl = (String) urlConfig.get("jedisconfigUrl");
            loadURL(new URL(configUrl));
        } catch (IOException e) {
            e.printStackTrace();
            hasConfigFile = false;
        }
    }
    
    public String getConfig(String name){
        try{
            return properties.getProperty(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static Configurations getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        // Configurations config = Configurations.getInstance();
        // System.out.println(config.getConfigMap());
    }

    public void loadURL(URL url) {

        try {
            // 打开网络连接
            URLConnection connect = url.openConnection();
            connect.setConnectTimeout(CONNECT_TIMEOUT);
            // 设置读取超时
            connect.setReadTimeout(READ_TIMEOUT);
            properties.load(connect.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            hasConfigFile = false;
        }
        // 设置连接超时
        catch (Exception e) {
            e.printStackTrace();
            hasConfigFile = false;
        }

    }

    public boolean hasConfigFile() {
        return hasConfigFile;
    }

}
