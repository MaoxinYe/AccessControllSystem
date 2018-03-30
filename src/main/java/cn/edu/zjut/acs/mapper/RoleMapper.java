package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_ROLE;



public interface RoleMapper {

	   /**
     * 保存角色类型
     * @param Role
     */
    void saveRole(XT_ROLE Role);
    
    /**
     * 修改角色类型
     * @param Role
     */
    void updateRole(XT_ROLE Role);
    
    /**
     * 通过key查询角色类型
     * @param key
     * @return XT_ROLE
     */
    XT_ROLE getRoleByPK(Integer key);
    
    /**
     * 查询角色类型信息列表
     * @param map 
     * @return List<XT_ROLE>
     */
    List<XT_ROLE> getRoleList(Map<String, Object> map);
    
    /**
     * 删除角色类型信息
     * @param Role
     */
    void deleteRole(XT_ROLE Role);

    
    
}
