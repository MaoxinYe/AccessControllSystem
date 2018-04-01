package cn.edu.zjut.acs.util;

import java.io.File;
import java.util.Calendar;



public class DirsUtils {
	
	public DirsUtils() {
		super();
	}
	
	public static String  DirsString() {
		Calendar calendar = Calendar.getInstance();
		String year = "" + calendar.get(Calendar.YEAR);
		String month = "" + (calendar.get(Calendar.MONTH) + 1);
		if (month.length() < 2) {
			month = 0 + month;
		}
		String day = "" + calendar.get(Calendar.DATE);
		String hour = "" + calendar.get(Calendar.HOUR_OF_DAY);
		String dirs = year + File.separator + month + File.separator + day + File.separator + hour;
		return dirs;
	}
	
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
