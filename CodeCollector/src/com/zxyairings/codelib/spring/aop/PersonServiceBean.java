package com.zxyairings.codelib.spring.aop;
/*


*/
public class PersonServiceBean implements PersonService{

	public void save(String name) {
		//throw new RuntimeException("我爱例外");
		System.out.println("this is save() method");
		
	}

	public void update(String name, Integer personid) {
		System.out.println("this is update() method");
		
	}

	public String getPersonName(Integer personid) {
		System.out.println("this is getPersonName() method");
		return "xxx";
	}

}
