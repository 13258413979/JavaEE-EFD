package com.woniu.dao.entity;

import java.io.Serializable;

public class City implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String cityid;
	private String city;
	private String provinceid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
}
