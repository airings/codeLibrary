package com.zxyairings.codelib.hibernate.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;


public class CreateSchema {

	// 根据配置生成表结构
	@Test
	public void test() throws Exception {
		Configuration cfg = new Configuration().configure();
		SchemaExport schemaExport = new SchemaExport(cfg);
		// 第一个参数script的作用： print the DDL to the console 打印
		// 第二个参数export的作用： export the script to the database 数据库运行该script
		schemaExport.create(true, true);
	}
}