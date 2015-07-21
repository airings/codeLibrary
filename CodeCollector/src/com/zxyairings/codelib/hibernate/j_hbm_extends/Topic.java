package com.zxyairings.codelib.hibernate.j_hbm_extends;;

public class Topic extends Article {
	private int type; // 精华，置顶...

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
