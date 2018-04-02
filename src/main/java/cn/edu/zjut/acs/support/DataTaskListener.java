package cn.edu.zjut.acs.support;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.edu.zjut.acs.mapper.AreaMapper;
import cn.edu.zjut.acs.mapper.AttendanceMapper;
import cn.edu.zjut.acs.mapper.PersonnelMapper;
import cn.edu.zjut.acs.mapper.RecordMapper;
import cn.edu.zjut.acs.mapper.VisitorMapper;
import cn.edu.zjut.acs.mapper.WorktimeMapper;


public class DataTaskListener implements ServletContextListener {

	 //时间间隔
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public void contextInitialized(ServletContextEvent sce)  {
    	
    	ServletContext servletContext = sce.getServletContext();
    	try {
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
			
			PersonnelMapper personnelMapper =context.getBean(PersonnelMapper.class);
			RecordMapper recordMapper =  context.getBean(RecordMapper.class);
			VisitorMapper visitorMapper = context.getBean(VisitorMapper.class);
			AreaMapper areaMapper =  context.getBean(AreaMapper.class);
			WorktimeMapper worktimeMapper =  context.getBean(WorktimeMapper.class);
			AttendanceMapper attendanceMapper =  context.getBean(AttendanceMapper.class);
			Calendar calendar = Calendar.getInstance();
	           
		     /*** 定制每日6:00执行方法 ***/
		     calendar.set(Calendar.HOUR_OF_DAY, 23);
		     calendar.set(Calendar.MINUTE, 0);
		     calendar.set(Calendar.SECOND, 0);
		     Date date=calendar.getTime(); //第一次执行定时任务的时间
		     //如果第一次执行定时任务的时间 小于 当前的时间
		     //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		     if (date.before(new Date())) {
		         date = this.addDay(date, 1);
		     }
		     
		     Timer timer1 = new Timer();
		     GainWorkAttendanceTimerTask task1 = new GainWorkAttendanceTimerTask(attendanceMapper,personnelMapper,recordMapper,worktimeMapper);
		     //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		     timer1.schedule(task1,date,PERIOD_DAY);
		     /*Timer timer2 = new Timer();
		     DeletingExpiredFaceFeatureTimerTask task2 = new DeletingExpiredFaceFeatureTimerTask(personnelMapper, visitorMapper, areaMapper, servletContext);
		     //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		     timer2.schedule(task2,date,PERIOD_DAY);*/
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

    public void contextDestroyed(ServletContextEvent sce)  {}
    
    // 增加或减少天数
    public Date addDay(Date date, int num) {
     Calendar startDT = Calendar.getInstance();
     startDT.setTime(date);
     startDT.add(Calendar.DAY_OF_MONTH, num);
     return startDT.getTime();
    }
	
}
