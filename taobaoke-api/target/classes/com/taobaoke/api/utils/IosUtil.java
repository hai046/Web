package com.taobaoke.api.utils;

/**
 * 开启审核数据通道工具
 * 
 * @author 邓海柱<br>
 *         E-mail:denghaizhu@brunjoy.com
 */
public class IosUtil {
	public static boolean isAndroidPassed(String app_key, int version) {
		do {
			// 如果googlePlay通过了审核请把下面2行代码注释掉

			 if(true)
			 break;

			if (app_key == null)
				break;
			if (version == 3) {
				return app_key.equalsIgnoreCase("android_mobile");
			}
		} while (false);
		return false;
	}

	/**
	 * ==========================================<BR>
	 * 功能：如果IPad正在审核，请让当前版本（1）返回true ,如果审核通过返回false<BR>
	 * 时间：2013-2-5 下午6:58:05 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param app_key
	 * @param version
	 * @return
	 */
	public static boolean isIosIPadDev(String app_key, int version) {
		do {
			// 如果IPad通过了审核请把下面2行代码注释掉

			if (true)
				break;

			if (app_key == null)
				break;
			if (version == 1 || version == 2) {// 当前提交的版本号为1 随后还有2 越狱为0
				return app_key.equalsIgnoreCase("ios_ipad");
			}
		} while (false);
		return false;
	}

	/**
	 * ==========================================<BR>
	 * 功能：如果IPhone正在审核，请让当前版本（2）返回true ,如果审核通过返回false<BR>
	 * 时间：2013-2-5 下午6:59:38 <BR>
	 * ========================================== <BR>
	 * 参数：
	 * 
	 * @param app_key
	 * @param version
	 * @return
	 */
	public static boolean isIosIPhoneDev(String app_key, int version) {
		do {
			// 如果IPhone通过了审核请把下面2行代码注释掉

			if (true)
				break;

			if (app_key == null)
				break;
			if (version == 2) {// IPhone 当前提交的版本号为2
				return app_key.equalsIgnoreCase("ios_iphone");
			}
		} while (false);
		return false;
	}
}
