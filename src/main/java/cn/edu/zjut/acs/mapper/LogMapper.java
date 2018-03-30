package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_LOG;


/** 
 * @ClassName: FaceidMapper
 * @author  <a href="mailto:945922054@qq.com">余其刚</a> 
 * @date 创建时间：2017年12月01日 下午3:16:42 
 * @version V1.0  
 * @since  JDK 1.7 
 */

public interface LogMapper {
	
	/**
     * 根据主键查询模块信息
     * @param id
     * @return XT_LOG
     */
	XT_LOG getLogByPK (Integer id);
	/**
     * 保存日志
     * @param log
     */
    void saveLog(XT_LOG log);
    
    
    
    void deleteLogByPk(Integer id);
    /**
	 * 根据map查询日志信息
	 * 带分页
	 * @param map
	 * @return List<XT_LOG>
	 */
	List<XT_LOG> getLogList(Map<String, Object> map);
	
	/**
	 * 根据map查询日志信息总数
	 * 带分页
	 * @param map
	 * @return Integer
	 */
	Integer getTotalNumber(Map<String, Object> map);

}
