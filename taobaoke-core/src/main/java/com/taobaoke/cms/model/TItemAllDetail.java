package com.taobaoke.cms.model;

import java.util.ArrayList;
import java.util.List;

public class TItemAllDetail {

	private TItemDetail mItemDetail;
	private String desc;
	private List<TItemImg> imgs;

	public TItemDetail getmItemDetail() {
		return mItemDetail;
	}

	public String getDesc() {
		return desc;
	}

	public List<TItemImg> getImgs() {
		return imgs;
	}

	public void setmItemDetail(TItemDetail mItemDetail) {
		this.mItemDetail = mItemDetail;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setImgs(List<TItemImg> imgs) {
		this.imgs = imgs;
	}

	public boolean addImg(TItemImg itemImg) {
		if (itemImg == null)
			return false;
		if (imgs == null)
			imgs = new ArrayList<TItemImg>();
		imgs.add(itemImg);
		return true;
	}

}
