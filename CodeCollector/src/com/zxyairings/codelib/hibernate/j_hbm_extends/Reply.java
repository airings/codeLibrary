package com.zxyairings.codelib.hibernate.j_hbm_extends;

public class Reply extends Article {
	private int floor; // 楼层

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

}
