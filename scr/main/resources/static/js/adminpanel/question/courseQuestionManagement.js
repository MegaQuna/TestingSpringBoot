$(document).ready(function() {
	
		var header = document.getElementById("questions_details");
		var btns = header.querySelectorAll(".editQuestion");
		
		
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {		
				var idquestion=this.value;
				var url= "/question/singleQuestion"+idquestion;
				window.location.href=url;					
		  });	    
	    };
	    
	   
	});