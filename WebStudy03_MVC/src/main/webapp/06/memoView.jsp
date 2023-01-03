<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="/includee/preScript.jsp" />
<script src="<%=request.getContextPath() %>/resources/js/custom.js"></script>
</head>
<body>
<!-- Restful : -->
<h4>Restful 기반의 메모 관리</h4>
<!-- 
1. 동기 형태
2. submit
	- button, input의 type="submit"의 차이
	- 클릭하면 이벤트는 클릭이벤트다.
	- type이 submit은 그 클릭 이벤트가 끝나면 그 이후에 submit을 실행한다.
 -->
 
<form name="memoForm" action="${pageContext.request.contextPath}/memo" method="post">
	<input type="text" name="writer" placeholder="작성자"/>
	<input type="date" name="date" placeholder="작성일"/>
	<textarea name="content"></textarea>
	<input type="submit" value="등록"/>
</form>

<table class="table-bordered">
	<thead>
		<tr>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	</thead>
	<tbody id="listBody"></tbody>
</table>




<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div id="modalContent" class="modal-body">
       ... 
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">

	// let memoForm =  <-이렇게 하자.
	let memoForm = $(document.memoForm).on("submit", function(event){
		// this, event.target이랑 같다.
		event.preventDefault(); // 동기 형태를 막는 방법 2 : 1,2 두개다 안정장치로 쓰자.
		console.log(this);
		console.log($(this));
		console.log($(this)[0][0].value);
		console.log($(this)[0][1].value);
		console.log($(this)[0][2].value);
		console.log($(this).serialize());

		// 이렇게 만들어주는 플러그인들도 이미 만들어져 있다.
		let url = this.action;
		let method = this.method;
		// 파라미터는 원래 객체로 표현하지 않는다.
		// let data = $(this).serialize(); // writer=작성자&data=작성일&content=내용 (QueryString)
		// 이렇게 보내면 jquery가 serialize을 자동으로 해주기 때문에 밑에처럼 보내도 쿼리문으로 보낸다.
		let data = $(this).serializeObject();
		// let memoFrom = this; <- 이걸 사용하면 memoForm.reset()이 실행된다.
		$.ajax({
			// url : "${pageContext.request.contextPath}/memo",
			// method : "post",
			url : url,
			method : method,
			contentType:"application/json;charset=UTF-8", // 보내는 편지의 request content-type을 결정한다.
			// 일단 data는 객체일뿐 JSOn.stringify()로 마샬링을 해줘야한다.
			data : JSON.stringify(data),
			dataType : "json", // request Accept, response content-type을 결정한다.
			success : function(resp) {
				makeListBody(resp.listMemo);
				// this는 function의 소유자다. 그래서 아래처럼 쓴다.
				// memoForm을 reset이란 이벤트를 발생
				memoForm[0].reset();
			},
			error : function(jqXHR, status, error) {
				console.log(jqXHR);
				console.log(status);
				console.log(error);
			}
		});
		// 동기 형태를 막는 방법 1
		return false;
	});
	


	// EDD(Event Driven Development)
	// TDD(Test Driven Development)
	$("#exampleModal").on("show.bs.modal", function(event){
		// this==event.target : modal 창 // this는 떠 있는 모달창
		let memo = $(event.relatedTarget).data("memo"); // modal을 오픈할때 사용한 클릭 대상, tr
		$(this).find(".modal-body").html(memo.content);
	}).on("hidden.bs.modal", function(event){
		$(event.target).find(".modal-body").empty();
	});
	
	let listBody = $("#listBody");
	let makeListBody = function(memoList){
		listBody.empty(); // 초기화
		let trTags = [];
		if(makeListBody.length>0){
			$.each(memoList, function(index, memo){
				// data-bs-toggle="modal" data-bs-target="#exampleModal"
				let tr = $("<tr>").append(
					$("<td>").html(memo.writer)
					,$("<td>").html(this.date)
				).attr({
					"data-bs-toggle":"modal",
					"data-bs-target":"#exampleModal"
				}).data("memo",memo);
				trTags.push(tr);
			});
		}else{
			// $("tr") // 찾는다라는 뜻
			// $("<tr>") // 만든다라는 뜻
			let tr = $("<tr>").html(
				// 함수 체인 형식
				$("<td>")
					.attr("colspan", "2")
					.html("작성된 메모 없음.")
			);
			trTags.push(tr);
		}
		listBody.append(trTags);
	}
	
	$.ajax({
		url : "${pageContext.request.contextPath}/memo",
		// 아래같은 경우를 Rest라고 한다.
		// url : "${pageContext.request.contextPath}/memo/1",
		// 1번 메모를 삭제하겠어
		// method : "delete",
		method : "get", // 전체 메모를 가져와
		/// data : {}, // 이건 필요없어지게 된다.
 		dataType : "json",
		success : function(resp) {
			console.log(resp);
			makeListBody(resp.listMemo);
		},
		error : function(jqXHR, status, error) {
			console.log(jqXHR);
			console.log(status);
			console.log(error);
		}
	});


	
</script>
<jsp:include page="/includee/postScript.jsp"></jsp:include>
</body>
</html>