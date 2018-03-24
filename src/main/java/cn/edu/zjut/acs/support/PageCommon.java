package cn.edu.zjut.acs.support;


public class PageCommon {
	private int totalRows; // 总记录数
	 
	private int pageSize = 20; // 每页显示的行数  
	 
	private int currentPage; // 当前页号  
	 
	private int startRow; // 当前页在数据库中的起始行  
	
	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0)
			this.currentPage = 1;
		else
			this.currentPage = currentPage;
	}

	public int getTotalPages() {
		int totalPages = (this.totalRows / this.pageSize) + 1;
		if (this.totalRows <= 0) {
			totalPages = 0;
		} else if (this.totalRows % this.pageSize == 0) {
			totalPages = totalPages - 1;
		}
		return totalPages;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	 
   /**  
	* 设定mybatis查询的起始行数  
	* 
	*/ 	 
	public int getStartRow() {
		startRow = (currentPage - 1) * pageSize;
		return startRow;
	}
}
