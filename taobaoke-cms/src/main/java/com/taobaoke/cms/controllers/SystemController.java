package com.taobaoke.cms.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.taobaoke.cms.fileupload.FileSystemBCS;
import com.taobaoke.cms.home.ExtraHome;
import com.taobaoke.cms.model.ExtraItem;
import com.taobaoke.cms.redis.RedisPoolFactory;
import com.taobaoke.cms.utils.CookieUtil;
import com.taobaoke.cms.utils.PathUtils;

@Path("system")
public class SystemController {
	// @Autowired
	// private ExtraHome mExtraHome;
	private final int DEFAULT_SIZE = 40;

	@Get("app")
	public String selectApp(Invocation inv, @Param("app_id") int app_id) {
		CookieUtil.setApp_id(app_id);
		return "r:/category/list";
	}

	@Get("list")
	public String getList(Invocation inv, @Param("currentPage") int page) {
		try {
			page = page < 1 ? 1 : page;
			int offset = (page - 1) * DEFAULT_SIZE;
			int count = ExtraHome.getInstance().getCount();
			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;
			List<ExtraItem> list = ExtraHome.getInstance().getAll(offset,
					DEFAULT_SIZE);
			inv.addModel("list", list);
			inv.addModel("count", count);
			inv.addModel("pageCount", pageCount);
			inv.addModel("currentPage", page);
			inv.addModel("callBack",
					URLEncoder.encode("./list?currentPage=" + page, "utf-8"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "system/list";
	}

	@Post("update")
	public String update(Invocation inv, ExtraItem mExtraItem,
			@Param("currentPage") int page) {

		try {
			if (!ExtraHome.getInstance().updateById(mExtraItem.getId(),
					mExtraItem.getName(), mExtraItem.getValue())) {
				return "@ update Error " + mExtraItem;
			}
		} catch (SQLException e) {

			e.printStackTrace();
			return "@Error " + mExtraItem.toString();
		}
		inv.addModel("currentPage", page);
		return "r:/system/list";
	}

	@Get("delete")
	public String delete(Invocation inv, @Param("id") int id,
			@Param("currentPage") int page) {
		try {
			ExtraHome.getInstance().deleteById(id);
			inv.addModel("currentPage", page);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "r:/system/list";
	}

	@Post("setKeyValue")
	public String setKeyValue(Invocation inv, ExtraItem mExtraItem) {
		if (StringUtils.isNullOrEmpty(mExtraItem.getKey())
				|| StringUtils.isNullOrEmpty(mExtraItem.getValue())) {
			return "@传入参数不能为空";
		}
		try {
			ExtraHome.getInstance().insert(mExtraItem);
		} catch (SQLException e) {
			e.printStackTrace();
			return "@" + e.getMessage();
		}
		return "r:/system/list";
	}

	@Get("deleteCache")
	public String deleteCache(Invocation inv, @Param("key") String key) {
		RedisPoolFactory.delete(key);
		return "";
	}

	@Post("addFile")
	public String add(Invocation inv, @Param("file") MultipartFile file) {
		String picPath = uploadFile(file);
		if (picPath == null) {
			return "@上传失败";
		}

		return "@" + picPath;
	}

	private String uploadFile(MultipartFile file) {

		String picPath = null;
		try {
			if (file != null && file.getBytes().length > 0) {

				picPath = FileSystemBCS.getInstance().storeFile(
						file.getBytes(), file.getName(), file.getContentType());
				if (picPath != null)
					picPath = PathUtils.getFileUrl(picPath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picPath;
	}

}
