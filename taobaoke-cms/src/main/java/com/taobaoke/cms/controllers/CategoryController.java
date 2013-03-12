package com.taobaoke.cms.controllers;

import java.net.URLEncoder;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.api.internal.util.StringUtils;
import com.taobaoke.cms.fileupload.FileSystemBCS;
import com.taobaoke.cms.home.TCategoryHome;
import com.taobaoke.cms.model.TCategory;
import com.taobaoke.cms.utils.CookieUtil;

@Component
@Path("/category")
public class CategoryController {
	@Autowired
	TCategoryHome tCategoryHome;

	private static final int DEFAULT_SIZE = 20;

	@Get("/list")
	public String categoryList(Invocation inv, @Param("currentPage") int page) {
		page = page < 1 ? 1 : page;
		int offset = (page - 1) * DEFAULT_SIZE;

		try {
			List<TCategory> categoryList = null;
			int count = 0;
			if (CookieUtil.getApp_id() < 0) {
				categoryList = tCategoryHome.getAll(TCategoryHome.ALL,
						TCategoryHome.STATUS_OK, offset, DEFAULT_SIZE);
				count = tCategoryHome.getCount(TCategoryHome.ALL,
						TCategoryHome.STATUS_OK);

			} else {
				categoryList = tCategoryHome.getAll(TCategoryHome.ALL,
						CookieUtil.getApp_id(), TCategoryHome.STATUS_OK, offset,
						DEFAULT_SIZE);
				count = tCategoryHome.getCount(TCategoryHome.ALL,
						CookieUtil.getApp_id(), TCategoryHome.STATUS_OK);

			}

			long pageCount = count / (long) DEFAULT_SIZE;
			pageCount = pageCount * DEFAULT_SIZE == count ? pageCount
					: pageCount + 1;

			inv.addModel("categoryList", categoryList);
			inv.addModel("count", count);
			inv.addModel("pageCount", pageCount);
			inv.addModel("currentPage", page);
			inv.addModel("callBackForNotSameRoot", URLEncoder.encode(
					"/category/list?currentPage=" + page, "utf-8"));
			inv.addModel("callBack",
					URLEncoder.encode("./list?currentPage=" + page, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "category/list";
	}

	@Post("/add")
	public String add(Invocation inv, TCategory tCategory,
			@Param("backUrl") String backUrl,
			@Param("iconFile") MultipartFile photo) {

		int id = tCategory.getId();
		String picPath = "";
		try {

			if (photo != null && photo.getBytes().length > 0) {
				picPath = FileSystemBCS.getInstance().storeFile(
						photo.getBytes(), photo.getName(),
						photo.getContentType());
			}
			tCategory.setIconUrl(picPath);
			tCategory.setType(TCategoryHome.TYPE_MOBILE);
			if (tCategory.getId() > 0) {
				if (StringUtils.isEmpty(picPath)) {
					tCategory.setIconUrl(tCategoryHome.getById(
							tCategory.getId(), tCategory.getApp_id())
							.getIconUrl());
				}
				tCategoryHome.update(tCategory);
			} else {
				tCategoryHome.insert(tCategory);
			}

			inv.addModel("tCategory",
					tCategoryHome.getById(id, tCategory.getApp_id()));
			inv.addModel("backUrl", backUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:./add?id=" + id + "&backUrl=" + backUrl;
	}

	@Get("/add")
	public String add(Invocation inv, @Param("id") int id,
			@Param("backUrl") String backUrl) {

		try {
			inv.addModel("tCategory", tCategoryHome.getById(id));
			inv.addModel("backUrl", backUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "category/add";
	}

	@Post("/del")
	public String del(Invocation inv, @Param("id") int id,
			@Param("backUrl") String backUrl) {

		try {
			tCategoryHome.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "r:" + backUrl;
	}
}
