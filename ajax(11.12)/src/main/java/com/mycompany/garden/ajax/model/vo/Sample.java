package com.mycompany.garden.ajax.model.vo;

public class Sample {
	
	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Sample() {}
	
	@Override
	public String toString() {
		return "Sample [name=" + name + ", age=" + age + "]";
	}
}