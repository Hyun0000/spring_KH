package com.mycompany.myapp05.person.model.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.myapp05.job.model.vo.Job;

// 여기에 어노테이션을 붙이면 객체 생성이 된다.
// 4교시 이 부분 영상있으면 보기 45분쯤
// id에 해당하는 객체 이름은 따로 지정하지 않는다.
// @Component = Bean
/*
 * 		
 */

// @Autowired private PersonAnn person3; 따라서 person3의 이름을 매번 다르게 해도 된다.
@Component("personAnn")
public class PersonAnn {
	private String name;
	private Job myjob;
	
	public PersonAnn() {
		System.out.println("PersonAnn : 0개 arg 생성자");
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

	public PersonAnn(String name, Job myjob) {
		this.name = name;
		this.myjob = myjob;
		System.out.println("2개 arg 생성자");
	}

	public PersonAnn(String name) {
		this.name = name;
		System.out.println("1개 arg 생성자");
	}
}
