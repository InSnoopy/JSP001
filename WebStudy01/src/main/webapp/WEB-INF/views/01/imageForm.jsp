<html>
<head>
   <script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
</head>
   <body>  
      <form action='${cPath}/imageStreaming.do'>
         <select name='imgChoice' >  
         ${options}
         </select> 
         <input type='submit' value='전송' />
      </form>
      <div id="box">
      </div>
      <script type="text/javascript">

         $("[name=imgChoice]").on("change",function(event){
        	event.target === this
            console.log(event.target.value);
            let x = "${cPath}/imageStreaming.do?imgChoice="+event.target.value;
 			const element = document.getElementById('box');
            element.innerHTML = "<img src='"+x+"'>";
            console.log(x);
         });
         
      </script>
   </body>       
</html>           
      