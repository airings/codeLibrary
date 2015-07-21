package com.zxyairings.codelib.hibernate.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User_collection {
	private Integer id;
	private String name;

	private Set<String> addressSet = new HashSet<String>(); // Set集合
	private List<String> addressList = new ArrayList<String>(); // List集合
	private String[] addressArray; // 数组
	private Map<String, String> addressMap = new HashMap<String, String>();
	private List<String> addressBag = new ArrayList<String>();

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

	public Set<String> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<String> addressSet) {
		this.addressSet = addressSet;
	}

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public String[] getAddressArray() {
		return addressArray;
	}

	public void setAddressArray(String[] addressArray) {
		this.addressArray = addressArray;
	}

	public Map<String, String> getAddressMap() {
		return addressMap;
	}

	public void setAddressMap(Map<String, String> addressMap) {
		this.addressMap = addressMap;
	}

	public List<String> getAddressBag() {
		return addressBag;
	}

	public void setAddressBag(List<String> addressBag) {
		this.addressBag = addressBag;
	}
}
