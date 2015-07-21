package com.zxyairings.codelib.hibernate.domain;

import java.util.List;

public class QueryResult {
	private int count; // 总记录数
	private List list; // 一页的数据

	public QueryResult(int count, List list) {
		this.count = count;
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}
