package com.zxyairings.codelib.spring.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@Transactional//加上后，当调用下面的增删改查各个方法时，就会在每个方法开始时开启事务，在每个方法结束时关闭事务
public class PersonServiceBean implements PersonService {
//	private DataSource dataSource; // 建议不要直接对DataSource进行操作
	private JdbcTemplate jdbcTemplate; //建议对spring提供的JdbcTemplate进行操作
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// unchecked: runtime exception 默认spring会回滚
	// checked: normal exception 默认spring不会回滚
	// 如果想要改变spring这种默认事务的回滚行为，需要使用 @Transactional 注解
	@Transactional(rollbackFor=Exception.class)//这样就会改变默认事务行为，使得checked exception 回滚
	public void delete(Integer personid)  {
		jdbcTemplate.update("delete from person where id=?", new Object[]{personid},
				new int[]{java.sql.Types.INTEGER});
		throw new RuntimeException("runtime exception");//默认会回滚
//		throw new Exception("checked exception"); //默认不会回滚
	}

	public Person getPerson(Integer personid) {		
		return (Person)jdbcTemplate.queryForObject("select * from person where id=?", new Object[]{personid}, 
				new int[]{java.sql.Types.INTEGER}, new PersonRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Person> getPersons() {
		return (List<Person>)jdbcTemplate.query("select * from person", new PersonRowMapper());
	}

	public void save(Person person) {
		jdbcTemplate.update("insert into person(name) values(?)", new Object[]{person.getName()},
				new int[]{java.sql.Types.VARCHAR});
	}

	public void update(Person person) {
		jdbcTemplate.update("update person set name=? where id=?", new Object[]{person.getName(), person.getId()},
				new int[]{java.sql.Types.VARCHAR, java.sql.Types.INTEGER});
	}
}
