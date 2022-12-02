<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jpeg-red, png-green, gif-blue -->
<style type="text/css">

	.red{
		background-color:red;
	}
	.green{
		background-color:green;
	}
	.blue{
		background-color:blue;
	}

</style>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
</head>
<body>

   <form name="imgForm" action='<%=request.getContextPath() %>/imageStreaming.do'>
   <select name='imgChoice'>
	
   </select>
   <input type='submit' value='전송'/> 
   </form>
   <div id="imgArea">

   </div>
   

	<script type="text/javascript">
		const DIVTAG = $("#imgArea");
		const SELECTTAG = $("[name='imgChoice']").on("change", function(event){
			let option = $(this).find("option:selected");
			let mime = option.attr("class");
			let clzName = matchedClass(mime);
			console.log($(this).value)
			$(this).removeClass(colors);
			$(this).addClass(clzName);
			
			// form에 id값이 아니라 name으로 설정하면 document의 속성으로 만들어진다.
			let srcURL = document.imgForm.action;
			let queryString = $(document.imgForm).serialize();
			let src = "%U?%P".replace("%U", srcURL).replace("%P", queryString);
			
			let img = $("<img>").attr("src", src);
			DIVTAG.html(img);

		});
		const changeCondition = {
			jpeg:"red",
			png:"green",
			gif:"blue"
		}
		const colors = [];
		$.each(changeCondition, function(prop, propValue){
			console.log(prop+","+propValue);
			colors.push(propValue);
		});
		let matchedClass = function(mime){
			let clzName = "";
			for(let prop in changeCondition){
				let idx = mime.indexOf(prop);
				if(idx>=0){
					clzName = changeCondition[prop];
					break;
				}
			}
			return clzName;
		}
	   $.ajax({
			dataType : "json",
			success : function(resp) {
				// console.log(resp.length);
				// console.log(resp[0].name);
				let options = [];
				$.each(resp, function(index, file){
					// option태그 생성
					// document에는 생성 전
					let option = $("<option>").addClass(file.mime).html(file.name);
					options.push(option);
				});
				SELECTTAG.append(options);				
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
	   


   </script>
</body>
</html>