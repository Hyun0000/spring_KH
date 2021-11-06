package com.mycompany.myapp05.job.model.vo;

public class Developer implements Job {

	@Override
	public void printInfo(String compName) {
		System.out.println("직장 명 : " + compName);
	}
	
}
