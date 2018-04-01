package cn.edu.zjut.acs.support;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "ResourceBean")
public class ResourceBean{
	
	@Value("${setting.photo.virtual.path}")
	private String virtualPath;
	@Value("${setting.photo.physical.path}")
	private String physicalPath;
	@Value("${setting.area_num}")
	private Integer areaNum;	
	@Value("${setting.area_1}")
	private String area_1;
	@Value("${setting.area_2}")
	private String area_2;
	@Value("${setting.area_3}")
	private String area_3;
	@Value("${setting.area_4}")
	private String area_4;
	@Value("${setting.area_5}")
	private String area_5;
	@Value("${setting.area_6}")
	private String area_6;
	
	

	
	public String getVirtualPath() {
		return virtualPath;
	}

	public String getPhysicalPath() {
		return physicalPath;
	}

	public Integer getAreaNum() {
		return areaNum;
	}

	public String getArea_1() {
		return area_1;
	}

	public String getArea_2() {
		return area_2;
	}

	public String getArea_3() {
		return area_3;
	}

	public String getArea_4() {
		return area_4;
	}

	public String getArea_5() {
		return area_5;
	}

	public String getArea_6() {
		return area_6;
	}

	
	
}
