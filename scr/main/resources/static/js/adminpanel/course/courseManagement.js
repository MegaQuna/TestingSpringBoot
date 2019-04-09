$(document).ready(function() {
	
		var header = document.getElementById("course_details");
		var btns = header.querySelectorAll(".details");
		console.log(btns.length);
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {				
				
				var userid= "coursedetail"+this.value;
				window.location.href=userid;
								
		  });	    
	    };
	});