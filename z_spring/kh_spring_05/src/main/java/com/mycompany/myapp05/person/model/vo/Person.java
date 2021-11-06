package com.mycompany.myapp05.person.model.vo;

import com.mycompany.myapp05.job.model.vo.Job;

public class Person {
	private String name;
	private Job myjob;
	
	public Person() {
		System.out.println("0개 arg 생성자");
	}

	public String getName() {
		System.out.println("getName 호출");
		return name;
	}

	public void setName(String name) {
		System.out.println("setName 호출");
		this.name = name;
	}

	public Job getMyjob() {
		System.out.println("getMyjob 호출");
		return myjob;
	}

	public void setMyjob(Job myjob) {
		System.out.println("setMyjob 호출");
		this.myjob = myjob;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", myjob=" + myjob + "]";
	}

	public Person(String name, Job myjob) {
		this.name = name;
		this.myjob = myjob;
		System.out.println("2개 arg 생성자");
	}

	public Person(String name) {
		this.name = name;
		System.out.println("1개 arg 생성자");
	}
	
	
}
