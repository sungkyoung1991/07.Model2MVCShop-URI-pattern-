<%@ page language="java" contentType="text/html; charset=euc-kr"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetList(currentPage) { // user > purchase 로 변경
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${ resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">상품번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">물품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">배송주소</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
		
		<td class="ct_line02"></td>
		<td class="ct_list_b">상품수령</td>
		
		
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	

	
<c:set var="i" value="0" />
	<c:forEach var="purchase" items="${list }">
		<c:set var="i" value="${i+1 }"/>
	
	
	<tr class="ct_list_pop">
		<td align="center">${purchase.purchaseProd.prodNo}</td>
		<td align="left"></td>
		<td>${purchase.purchaseProd.prodName }</td>
		<td align="left"></td>
		<td>${purchase.divyAddr }</td>
		<td align="left"></td>
		<td>${purchase.receiverPhone}</td>
		<td align="left"></td>
		<td>
		
		<!-- 배송현황 -->
		<c:if test="${purchase.tranCode=='1  '}">
		배송준비중
		</c:if>
		<c:if test="${purchase.tranCode=='2  '}">
		배송중
		</c:if>
		<c:if test="${purchase.tranCode=='3  '}">
		배송완료
		</c:if>
		
		
		
		</td>
		<td align="left"><td align="left">
		<c:if test="${purchase.tranCode=='1  '}">
		<a href="/getPurchase.do?tranNo=${purchase.tranNo}&prodNo=${purchase.purchaseProd.prodNo}">정보수정하기</a></td>
		</c:if>
		<c:if test="${purchase.tranCode!='1  '}">
		수정불가.
		</c:if>
		
		
		<td align="left"></td>
		<td>
		
		<c:if test="${purchase.tranCode=='1  '}">
 			대기
		</c:if>
		<c:if test="${purchase.tranCode=='2  '}">
<a href="/updateTranCode.do?tranNo=${purchase.tranNo}&prodNo=${purchase.purchaseProd.prodNo}&tranCode=${purchase.tranCode}&menu=${param.menu}"> 상품수령 </a></td>
		</c:if>
		<c:if test="${purchase.tranCode=='3  '}">
		상품수령완료
		</c:if>
		
		
		</td>
		
		
		
			
	</tr>
	
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	
	</c:forEach>


</table>

<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="1"/>	
			
			<jsp:include page="../common/pageNavigator.jsp"/>
		
    	</td>
	</tr>
</table>
<!-- PageNavigation End... -->

</form>

</div>

</body>
</html> 