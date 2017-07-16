package com.lp.activiti;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class HelloWorld {
	ProcessEngine pe=ProcessEngines.getDefaultProcessEngine();
	/**
	 *
	 */
	@Test
	public void loadDatabase(){
		ProcessEngine pe=ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault()
				.buildProcessEngine();
		System.out.println("pe="+pe);
	}
	
	/**
	 * 部署流程定义
	 */
	@Test
	public void deployedProcess(){
		InputStream is=this.getClass().getClassLoader().getResourceAsStream("diagrams/leaveProcess.zip");
		ZipInputStream zipInputStream = new ZipInputStream(is);
		Deployment deploy=pe.getRepositoryService()//获取与流程定义与部署相关的service
				.createDeployment()//创建部署对象
				.addZipInputStream(zipInputStream)//添加部署内容
				.deploy();//部署完成
		System.out.println("部署定义id:"+deploy.getId());
	}
	
	/**
	 * 启动流程实例
	 * 
	 */
	@Test
	public void starProcess(){
		String processInstanceKey="leaveProcess";
		ProcessInstance pi=pe.getRuntimeService()//获取正在执行的或流程实例相关的service
				.startProcessInstanceByKey(processInstanceKey);//通过流程定义的key启动流程实例，key对应leaveProcess.bpm中id属性的值
		System.out.println("流程实例id:"+pi.getId());
		System.out.println("流程定义id:"+pi.getProcessDefinitionId());
	}
}
