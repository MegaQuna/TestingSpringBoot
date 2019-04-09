$(document).ready(function() {
	
		var header = document.getElementById("right-part");
		var btns = header.querySelectorAll(".removeUser");
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				var url= "/testsettings/removeStudent"+this.getAttribute("data-testid")+"/"+this.value;
				window.location.href=url;
				
		  });	    
	    };
	});