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




