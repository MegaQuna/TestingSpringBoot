$(document).ready(function() {
	
		var header = document.getElementById("user_details");
		var btns = header.querySelectorAll(".deactivationAccount");
		var btns2 = header.querySelectorAll(".activationAccount");
		var btns3 = header.querySelectorAll(".unlockAccount");
		var btns4 = header.querySelectorAll(".removegroup");
		var btns5 = header.querySelectorAll(".TemplateAddGroup");
		var btns6 = header.querySelectorAll(".templateSetTestToUser");
		
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				var userid= "../account/deactivationAccount"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	    
	    for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {
				
				var userid= "../account/activationAccount"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {
				
				var userid= "../account/unlockAccount"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	    
	    for (var i = 0; i < btns4.length; i++) {
			btns4[i].addEventListener("click", function() {
				
				
				if (confirm('Czy napewno wypisać studenta z grupy? ')) {
					var userid= "../account/removeGroup"+this.value+"/"+this.getAttribute("data-userid");
					window.location.href=userid;
			       } 
											
		  });	    
	    };
	    
	    for (var i = 0; i < btns5.length; i++) {
			btns5[i].addEventListener("click", function() {
				
				var userid= "../account/TemplateAddGroup"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	    
	    for (var i = 0; i < btns6.length; i++) {
			btns6[i].addEventListener("click", function() {
				
				var userid= "../account/templateSetTestToUser"+this.value;
				window.location.href=userid;							
		  });	    
	    };
	    
	});