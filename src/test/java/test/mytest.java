package test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.model.XT_LOG;


public class mytest {
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		LogMapper p=context.getBean(LogMapper.class);
		/*for(int i=1;i<=30;i++)
			p.deleteLogByPk(i);*/
		for(int i=0;i<30;i++)
		{	
			XT_LOG t=new XT_LOG();
			t.setAddtime(new Date());
			t.setContent("测试的"+i);
			t.setUsername("admin");
			t.setClientip(null);
			p.saveLog(t);
		}
		//testController t=context.getBean(testController.class);
		//t.login();
	}
}
