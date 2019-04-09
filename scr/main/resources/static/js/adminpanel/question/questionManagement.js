$(document).ready(function() {
	
		var header = document.getElementById("right-part");
		var btns = header.querySelectorAll(".details");
		
		
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {		
				
				var url= "/question/coursequestionsManagement/"+this.getAttribute("data-courseid")+"/"+this.getAttribute("data-single")+"/"+this.value;
				window.location.href=url;					
		  });	    
	    };
	    
	   
	});