<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
</head>
<body>
<table>
	<thead>
		<tr>
			<th>일련번호</th>
			<th>상품분류</th>
			<th>상품명</th>
			<th>거래처명</th>
			<th>구매가</th>
			<th>판매가</th>
			<th>상품구매자수</th>
		</tr>
	</thead>
	<tbody id="listBody">
<%-- 		<c:set var="list" value="${pagingVO.dataList }" /> --%>
<%-- 		<c:choose> --%>
<%-- 			<c:when test="${not empty list }"> --%>
<%-- 				<c:forEach items="${list }" var="ProdVO"> --%>
<!-- 					<tr> -->
<%-- 						<td>${ProdVO.rnum }</td> --%>
<%-- 						<td>${ProdVO.lprodNm }</td> --%>
<%-- 						<td>${ProdVO.prodName }</td> --%>
<%-- 						<td>${ProdVO.buyer.buyerName }</td> --%>
<%-- 						<td>${ProdVO.prodCost }</td> --%>
<%-- 						<td>${ProdVO.prodSale }</td> --%>
<%-- 						<td>${ProdVO.cartCount }</td> --%>
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
<%-- 			</c:when> --%>
<%-- 			<c:otherwise> --%>
<!-- 				<tr> -->
<!-- 					<td colspan="7">조건에 맞는 상품이 없음.</td> -->
<!-- 				</tr> -->
<%-- 			</c:otherwise> --%>
<%-- 		</c:choose> --%>
	</tbody>
	<tfoot>
		<tr>
			<td colspan="7">
				<div class="pagingArea">
<%-- 					${pagingVO.pagingHTML } --%>
				</div>
				<div id="searchUI">
					<select name="prodLgu">
						<option value>분류</option>
						<c:forEach items="${lprodList }" var="lprod">
							<option value="${lprod.lprodGu }">${lprod.lprodNm }</option>
						</c:forEach>
					</select>
					<select name="prodBuyer">
						<option value>거래처</option>
						<c:forEach items="${buyerList }" var="buyer">
							<option value="${buyer.buyerId }" class="${buyer.buyerLgu }">
								${buyer.buyerName }
							</option>							
						</c:forEach>
					</select>
					<input type="text" name="prodName" placeholder="상품명"/>
					<input type="button" id="searchBtn" value="검색" />
				</div>
				<c:url value='/prod/prodInsert.do' var="insertURL"/>
				<a href="${insertURL }" class="btn btn-primary">등록</a>
			</td>
		</tr>
	</tfoot>
</table>

<h4>Hidden Form</h4>
<form id="searchForm">
	<input type="text" name="page" />
	<input type="text" name="prodLgu" placeholder="분류코드"/>
	<input type="text" name="prodBuyer" placeholder="거래처코드"/>
	<input type="text" name="prodName" placeholder="상품명"/>
</form>

<script type="text/javascript">

	let prodBuyerTag = $("[name=prodBuyer]").val("${detailCondition.prodBuyer}")
	$("[name=prodLgu]").on("change", function(){
		let prodLgu = $(this).val();
		console.log(prodLgu);
		// 마지막 선택자
		prodBuyerTag.find("option:gt(0)").hide();
		prodBuyerTag.find("option."+prodLgu).show();
	}).val("${detailCondition.prodLgu}");
	
	$("[name=prodLgu]").val("${detailCondition.prodLgu}");
	$("[name=prodName]").val("${detailCondition.prodName}");
	
	let listBody = $("#listBody");
	
	let pagingArea = $(".pagingArea").on("click", "a.paging", function(event){
		event.preventDefault();
		let page = $(this).data("page");
		if(!page) return false;
		searchForm.find("[name=page]").val(page);
		searchForm.submit();
		return false;
	});
	
	let makeTrTag = function(prod){
		let aTag = $("<a>")
					.attr("href", "${pageContext.request.contextPath}/prod/prodView.do?what="+prod.prodId)
					.html(prod.prodName);
		return $("<tr>").append(
			$("<td>").html(prod.rnum),		
			$("<td>").html(aTag),		
			$("<td>").html(prod.lprodNm),		
			$("<td>").html(prod.buyerName),		
			$("<td>").html(prod.prodCost),		
			$("<td>").html(prod.prodSale),		
			$("<td>").html(prod.cartCount)	
		);
	};
	
	let searchForm = $("#searchForm").on("submit", function(event){
		event.preventDefault();
		
		let url = this.action;
		let method = this.method;
		let data = $(this).serialize();
		$.ajax({
			url : url,
			method : method,
			data : data,
			dataType : "json",
			success : function(resp) {
				listBody.empty();
				pagingArea.empty();
				searchForm[0].page.value="";
				
				let pagingVO = resp.pagingVO;
				let dataList = pagingVO.dataList;
				let trTags = [];
				if(dataList){
					$.each(dataList, function(index, prod){
						trTags.push(makeTrTag(prod));
					});
				}else{
					let tr = $("<tr>").html($("<td>").attr("colspan", "7").html("조건에 맞는 상품 없음."));
					trTags.push(tr);
				}
				listBody.html(trTags);
				pagingArea.html(pagingVO.pagingHTML);
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
		
		return false;
	}).submit();
	
	let searchUI = $("#searchUI").on("click", "#searchBtn", function(){
		let inputs = searchUI.find(":input[name]");
		$.each(inputs, function(index, input){
			let name = this.name;
			let value = $(this).val();
			searchForm.find("[name="+name+"]").val(value);
// 			searchForm[0][name].value = value;
		});
		searchForm.submit();
	});
	

</script>

<jsp:include page="/includee/postScript.jsp" />
</body>
</html>