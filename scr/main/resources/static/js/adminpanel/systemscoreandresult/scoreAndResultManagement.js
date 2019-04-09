$(document).ready(function() {
	
		var header = document.getElementById("right-part");
		var btns = header.querySelectorAll(".systemScoreEdit");
		var btns2 = header.querySelectorAll(".systemScoreArchive");
		var btns3 = header.querySelectorAll(".systemResultArchive");
		
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {		
				var url= "/testsettings/templateSystemScoreEdit"+this.value;
				window.location.href=url;					
		  });	    
	    };
	    
	    for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {	
				
				
				if (confirm('Napewno Usunąć?')) {
					var url= "/testsettings/systemScoreArchive"+this.value;
					window.location.href=url;
			       }					
		  });	    
	    };
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {	
				
				
				if (confirm('Napewno Usunąć?')) {
					var url= "/testsettings/systemResultArchive"+this.value;
					window.location.href=url;
			       }					
		  });	    
	    };
	    
	   
	});