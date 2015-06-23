package com.zxyairings.codelib.spring.jdbc;

import java.util.List;

public interface PersonService {
	/**
	 * 保存person
	 * @param person
	 */
	public void save(Person person);
	/**
	 * 更新person
	 * @param person
	 */
	public void update(Person person);
	/**
	 * 获取person
	 * @param personid
	 * @return
	 */
	public Person getPerson(Integer personid);
	/**
	 * 获取所有person
	 * @return
	 */
	public List<Person> getPersons();
	/**
	 * 删除指定id的person
	 * @param personid
	 */
	public void delete(Integer personid);
}
