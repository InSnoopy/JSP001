<%@ page import="java.util.Calendar" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!doctype html>
<html lang="ko">
<head>
  <meta charset="utf-8">
  <meta name="robots" content="noindex, nofollow">
  <title>Inline editor with floating UI</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
  <script src="https://cdn.ckeditor.com/4.16.1/standard-all/ckeditor.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.min.js" integrity="sha512-2hPuJOZB0q6Eu4RlRRL2/8/MZ+IoSSxgDUu+eIUNzHOoHLUwf2xvrMFN4se9mu0qCgxIjHum6jdGk/uMiQoMpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
<h4>${today } 회의실 사용 현황</h4>
<h4>(매일 아침 리셋 : alt+c), (일정 수정 후 저장 : ctrl+s)</h4>
  <div id="editor1" contenteditable="true">
  	<c:choose>
  		<c:when test="${not empty tableSource }">${tableSource }</c:when>
  		<c:otherwise>
		    <table border="1" cellpadding="1" cellspacing="1" style="width:1000px">
		    	<thead  contenteditable="false">
		    		<tr>
		    			<th scope="col">팀번호</th>
		    			<th scope="col">회의실</th>
		    			<th scope="col">회의실 이용 시간</th>
		    		</tr>
		    		<tr>
		    			<th scope="col">ex) TEAM1</th>
		    			<th scope="col">ex) 분임토의2실</th>
		    			<th scope="col">ex) 14:00~15:00</th>
		    		</tr>
		    	</thead>
		    	<tbody>
		    		<c:forEach begin="1" end="10" varStatus="vs">
		    			<tr>
		    				<th scope="col">${vs.first?"TEAM1":"" }</th>
		    				<td></td>
		    				<td></td>
		    			</tr>
		    		</c:forEach>
		    	</tbody>
		    </table>
  		
  		</c:otherwise>
  	</c:choose>
  </div>
  <script>
  	let ws = null;
  	
    $(function(){
    	function consoleLog(){
    		console.log(arguments);
    	}
    	ws = new SockJS("${cPath}/wsConnect");
		ws.onopen=consoleLog;    	
		ws.onclose=consoleLog;    	
		ws.onerror=consoleLog;    	
		ws.onmessage=function(event){
			if(event.data=='RELOAD'){
				location.reload(true);
			}
		};
    	let saveFunction = function(event){
    		event.preventDefault();
    		let data = ckeditor.getData();
    		$.ajax({
    			method:"post",
    			data:data,
    			dataType:"text",
    			contentType:"text/plain",
    			success:function(resp){
    				if(resp=="FAIL"){
						alert("저장 실패");	    					
    				}else{
	    				location.href=resp;
    				}
    			}
    		});
    		return false;
    	}
    	let clearFunction = function(event){
    		event.preventDefault();
    		let data = ckeditor.getData();
    		$.ajax({
    			method:"put",
    			dataType:"text",
    			success:function(resp){
    				location.href=resp;
    			}
    		});
    		return false;
    	}
    	$(document).on("keydown", function(event){
    		let retValue = true;
    		if(event.keyCode==83 && event.ctrlKey){
				retValue = saveFunction(event);
    		}else if(event.keyCode==67 && event.altKey){
				retValue = clearFunction(event);
    		}
    		return retValue;
        });
    });
    window.addEventListener("beforeunload", function(){
    	if(ws){
    		ws.close();
    		ws = null;
    	}
    });
    try{
	    CKEDITOR.disableAutoInline = true;
	    ckeditor = CKEDITOR.inline('editor1', {
	      extraPlugins: 'sourcedialog'
	    });
    }catch(e){}
  </script>
</body>
</html>