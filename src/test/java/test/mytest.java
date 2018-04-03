package test;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.zjut.acs.mapper.AuthorityMapper;
import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.ModuleMapper;
import cn.edu.zjut.acs.model.Authority;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.ModuleService;


public class mytest {
	public static void main(String[] args)
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		AuthorityMapper p=(AuthorityMapper) context.getBean(AuthorityMapper.class);
		ModuleMapper service=context.getBean(ModuleMapper.class);
		List<Module> list=service.getModuleList(null);
		for(Module module:list)
		{
			Authority t=new Authority();
			t.setModuleid(module.getModuleid());
			t.setRoleid(1);
			p.saveAuthority(t);
		}
		/*for(int i=1;i<=30;i++)
			p.deleteLogByPk(i);*/
		/*for(int i=0;i<30;i++)
		{	
			
		}*/
		//testController t=context.getBean(testController.class);
		//t.login();
	}
}
