package com.zxyairings.codelib.jdbc.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zxyairings.codelib.jdbc.domain.User;
import com.zxyairings.codelib.jdbc.util.JdbcUtilsDBCP;


/*
 * Spring 提供了一个JdbcTemplate工具类，他对JDBC API进行了很好的封装，原理类似自己写的 UserDaoImplStrategy.java，只是代码更健壮，功能更强大。
 * 这与直接使用jdbc API没有太大的性能区别。
 * 
 * 还有一个问题：设置参数时，使用Object[] args = new Object[] { id }; 必须要与问号一一对应。如何改进？NamedJdbcTemplateDemo.java
 */

public class JdbcTemplateDemo {
	static JdbcTemplate jdbc = new JdbcTemplate(JdbcUtilsDBCP.getDataSource());

	public static void main(String[] args) {
//		User user = findUser("zhangsan");
//		 System.out.println("user:" + user);

//		 System.out.println("users:" + findUsers(3));

		 System.out.println("user count:" + getUserCount());

		 System.out.println("user name:" + getUserName(1));

		System.out.println("data:" + getData(1));
	}

	//插入用户之后，可以拿到新出入用户的主键
	static int addUser(final User user) {
		//连接回调，把连接给程序员，那么就可以做一些更细致的工作
		jdbc.execute(new ConnectionCallback() {

			public Object doInConnection(Connection con) throws SQLException,
					DataAccessException {
				String sql = "insert into user(name,birthday, money) values (?,?,?) ";
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setDate(2, new java.sql.Date(user.getBirthday().getTime()));
				ps.setFloat(3, user.getMoney());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next())
					user.setId(rs.getInt(1));
				return null;
			}
		});
		return 0;
	}

//	当没有User这个类时，那么还可以将结果放到map中
	static Map<String, Object> getData(int id) {
		String sql = "select id as userId, name, money, birthday  from user where id<"
				+ id;
//		jdbc.queryForList(sql);//多条返回结果时使用，返回一个list，其中每个元素是一个map
		return jdbc.queryForMap(sql);
	}

	static String getUserName(int id) {
		String sql = "select name from user where id=" + id;
		Object name = jdbc.queryForObject(sql, String.class);
		return (String) name;
	}

	static int getUserCount() {
		String sql = "select count(*) from user";
		return jdbc.queryForInt(sql);
	}

	//返回多个纪录行
	static List findUsers(int id) {
		String sql = "select id, name, money, birthday  from user where id<?";
		Object[] args = new Object[] { id };
		int[] argTypes = new int[] { Types.INTEGER };
		List users = jdbc.query(sql, args, argTypes, new BeanPropertyRowMapper(
				User.class));
		return users;
	}

//	返回一个javabean类型
	static User findUser(String name) {
		String sql = "select id, name, money, birthday, birthday as regist_date from user where name=?";//只要数据库中的字段符合javaBean命名规则或者是用下划线分隔，Spring的行映射器都可以识别。如果不匹配时，可以通过使用别名as，就像以前一样
		Object[] args = new Object[] { name };
//		使用spring，我们就不需要再实现行映射器了，他会通过反射来构造出目标对象，原理类似ORMDemo.java
		Object user = jdbc.queryForObject(sql, args, new BeanPropertyRowMapper(
				User.class));
		return (User) user;
	}

	//跟之前的UserDaoImplStrategy.java一样。返回一个javabean类型
	static User findUser1(String name) {
		String sql = "select id, name, money, birthday  from user where name=?";
		Object[] args = new Object[] { name };
		Object user = jdbc.queryForObject(sql, args, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setMoney(rs.getFloat("money"));
				user.setBirthday(rs.getDate("birthday"));
				return user;
			}
		});
		return (User) user;
	}
}
