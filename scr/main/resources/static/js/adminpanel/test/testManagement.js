$(document).ready(function() {
	
		var header = document.getElementById("test_details");
		var btns = header.querySelectorAll(".details");
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				var userid= "testdetail"+this.value;
				window.location.href=userid;
				
		  });	    
	    };
	});