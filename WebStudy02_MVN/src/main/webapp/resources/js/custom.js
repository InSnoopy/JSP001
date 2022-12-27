/**
 * 모든 html은 태그 name이라는 프로퍼티를 가지고 있다.
 * - attr, prop의 차이
 */
//폼태그의 모든 입력 데이터의 이름과 값을 콘솔에 로그로 출력할 수 있는 함수.
//ex) $("form").log().seralizeObject(); 
//+ 함수의 체인 구조가 가능해야한다.

$.fn.serializeObject=function(){
	if(this.prop('tagName')!='FORM')
		throw Error("form 태그 외에는 사용 불가.");
	// this[0]으로 선택한 이유는?
	let fd = new FormData(this[0]);
	let nameSet = new Set();
	for(let key of fd.keys()){
		nameSet.add(key);
	}
	let data = {}
	for(let name of nameSet){
		// getParameter, getParameters? 이 차이랑 똒같다.
		let values = fd.getAll(name)
		if(values.length>1){
			data[name] = values;
		}else{
			data[name] = values[0];
		}
	}
	return data;
}

$.fn.log=function(){
	let data = this.serializeObject();
	// of는 우측에 배열이나 리터럴이나 셋일때 반복으로 꺼낼 대상이 있는경우
	// in는 객체인 경우다. 프로퍼티 하나하나에 접근한다.
	for(let prop in data){
		console.log(prop + " : " + data[prop]);
	} 
	console.log(data);
	return this;
}

$.fn.sessionTimer=function(timeout, msgObj){
	if(!timeout)
		throw Error("세션 타임아웃 값이 없음."); // 에러 처리
	
// $(".controlBtn").on("click", ) -> event 선택법
// 자식 이벤트가 발생되면 부모가 전파된다. 이걸 버블링이라고 한다. 
// 결국 맨 마지막은 body(document)이다.

// event propagation : bubbling 방식
// $(document).on("click", ".controlBtn", function(){ // document 전체에서 찾고 .controlBtn을 클릭했을 때	
// });
	
	const SPEED = 100;
	const TIMEOUT = timeout;
	const timerArea = this; // $("#timerArea")
	
	let msgArea = null;
	
	if(msgObj){
		msgArea = $(msgObj.msgAreaSelector).on("click", msgObj.btnSelector ,function(event){
			// console.log(this.id + ", " + $(this).prop("id"));
			if(this.id=="YES"){
				jobClear();
				timerInit();
				$.ajax({ // 서버 session을 유지하기 위해 요청만 한다.
					method : "head",
				});
			}
			msgArea.hide();
		}).hide();		
	}
	// 자바스크립트에서는 
	// 1. 주기적인 함수 - setInterval
	// 2. 지연 함수 - setTimeout
	
	// 전역변수로 timerJob 이런걸 사용 가능하지만 data로도 사용가능하다.
	let jobClear = function(){
		let timerJob = timerArea.data("timerJob");
		if(timerJob)
			clearInterval(timerJob);
		let msgJob = msgArea.data('msgJob');
		if(msgJob)
			clearTimeout(msgJob);
	}
	
	let timerInit = function(){
		if(msgObj){
			let msgJob = setTimeout(() => {
				msgArea.show();
			}, (TIMEOUT-60)*SPEED); // 단위가 밀리센컨드이기 때문에 천을 곱함
			msgArea.data('msgJob', msgArea); // data 숨겨놓기 기능			
		}
		
		let timer = TIMEOUT;
		let timerJob = setInterval(() => {
			if(timer==1){				
				clearInterval(timerJob);
				location.reload(); // location 객체
			}else
				timerArea.html( timeFormat(--timer) ); // 이렇게 timerArea을 선언하고 쓰면 스크립트 과부하를 줄인다.
		}, SPEED);
		timerArea.data("timerJob", timerJob);
	}
	
	timerInit();
	
	let timeFormat = function(time){
		let min = Math.trunc( time / 60 ); // 동적 타입 언어에서는 연산자에 요소에 따라 타입이 정해진다.
										   // 정적 타입 언어에서는 연산 전에 타입을 지정해줘야 한다.
		let sec = time % 60;
		return min + ":" + sec;
	}
	
	return this;
}



