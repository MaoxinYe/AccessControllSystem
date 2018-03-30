<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
		$(document).ready(function(){
		    var curPage = $("#nowPage");//当前页面
			var allPage = parseInt("${pageMap.page.totalPages}");//页面总数
			var formSearch = document.searchresult;
			formSearch.action = formSearch.pageUrl.value;
			$(".pagepre01").click(function(){
				if(parseInt(curPage.val()) > 1) {
					curPage.val(parseInt(curPage.val())+parseInt('-1'));
					formSearch.submit();
				}
			});
			$(".pagenxt").click(function(){
				if(parseInt(curPage.val()) < allPage) {
					curPage.val(parseInt(curPage.val()) + parseInt('1'));
					formSearch.submit();
				}
			});		
		});
		function flip(num){
		    var curPage = $("#nowPage");
			var allPage = parseInt("${pageMap.page.totalPages}");
			var formSearch = document.searchresult;
            //--输入指定页数跳转到指定页面
			var page = document.getElementById("page").value;
			if(parseInt(page) >= 1 && parseInt(page) <= allPage && parseInt(page) != curPage.val()) {
				curPage.val(parseInt(page));
				formSearch.submit();
			}	
			//--//
			formSearch.action = formSearch.pageUrl.value;			
			if(parseInt(num) >= 1 && parseInt(num) <= allPage && parseInt(num) != curPage.val()) {
				curPage.val(parseInt(num));
				formSearch.submit();
			}			
		}		
</script>
<c:set var = "page" value="${pageMap.page}" />
<c:set var = "totalPages" value="${page.totalPages}" />
<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">共 ${page.totalRows}条数据,当前第${page.currentPage}页
	<input type="hidden" id = "page" type="text" value="">
</div>
<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
	<span class="<c:choose><c:when test='${page.currentPage==1}'>pagepre</c:when><c:otherwise>pagepre01</c:otherwise></c:choose>"><a tabindex="0" class="paginate_button previous disabled" id="DataTables_Table_0_previous" aria-controls="DataTables_Table_0" data-dt-idx="0">上一页</a></span>
	<c:choose>
		<c:when test="${totalPages<10}">
			<c:forEach var="pagenum" begin="1" end="${totalPages}" step="1">
				<span><a tabindex="0" class="paginate_button <c:if test='${page.currentPage==pagenum}'>current</c:if>" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${pagenum});">${pagenum}</a></span>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${page.currentPage<=10}">
					<c:forEach var="pagenum" begin="1" end="${10}" step="1">
						<span><a tabindex="0" class="paginate_button <c:if test='${page.currentPage==pagenum}'>current</c:if>" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${pagenum});">${pagenum}</a></span>
					</c:forEach>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:;">...</a></span>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${totalPages});">${totalPages}</a></span>
				</c:when>
				<c:when test="${page.currentPage>10 && totalPages-page.currentPage>2}">
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:javascript:flip(1);">1</a></span>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:;">...</a></span>
					<c:forEach var="pagenum" begin="${page.currentPage-2}" end="${page.currentPage+2}" step="1">
						<span><a tabindex="0" class="paginate_button <c:if test='${page.currentPage==pagenum}'>current</c:if>" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${pagenum});">${pagenum}</a></span>
					</c:forEach>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:;">...</a></span>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${totalPages});">${totalPages}</a></span>
				</c:when>
				<c:when test="${page.currentPage>10 && totalPages-page.currentPage<=2}">
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:javascript:flip(1);">1</a></span>
					<span><a tabindex="0" class="paginate_button" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:;">...</a></span>
					<c:forEach var="pagenum" begin="${page.currentPage-2}" end="${totalPages}" step="1">
						<span><a tabindex="0" class="paginate_button <c:if test='${page.currentPage==pagenum}'>current</c:if>" aria-controls="DataTables_Table_0" data-dt-idx="${index}" href="javascript:flip(${pagenum});">${pagenum}</a></span>
					</c:forEach>
				</c:when>		
			</c:choose>
		</c:otherwise>
	</c:choose>
	<span class="<c:choose><c:when test='${page.currentPage==totalPages}'>pagenxt01</c:when><c:otherwise>pagenxt</c:otherwise></c:choose>">
		<a tabindex="0" class="paginate_button next disabled" id="DataTables_Table_0_next" aria-controls="DataTables_Table_0" data-dt-idx="2" href="javascript:;">下一页</a>
	</span>
	<input type="hidden" name="nowPage" id="nowPage" value="${page.currentPage}"/>
</div>