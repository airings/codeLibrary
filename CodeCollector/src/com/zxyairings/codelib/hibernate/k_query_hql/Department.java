package com.zxyairings.codelib.hibernate.k_query_hql;

import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 * 
 * @author tyg
 * 
 */
public class Department {
	private Integer id;
	private String name;
	private Set<Employee> employees = new HashSet<Employee>(); // 关联的很多员工

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "[Department: id=" + id + ", name=" + name + "]";
	}

}
