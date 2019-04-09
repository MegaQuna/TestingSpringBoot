$(document).ready(function() {
	
		var header = document.getElementById("user_details");
		var btns = header.querySelectorAll(".details");
		console.log(btns.length);
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				var userid= "../accountdetails"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	});