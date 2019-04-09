$(document).ready(function() {
	
		var header = document.getElementById("group_details");
		var btns = header.querySelectorAll(".groupNameChange");
		var btns2 = header.querySelectorAll(".groupDescriptionChange");
		var btns3 = header.querySelectorAll(".groupAddStudent");
		var btns4 = header.querySelectorAll(".removeuser");
		var btns5 = header.querySelectorAll(".groupAddCourse");
		var btns6 = header.querySelectorAll(".removecourse");
		var btns7 = header.querySelectorAll(".removeGroup");
		
			
		console.log(btns.length);
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				var groupnamechange= "../group/templateChangeNameGroup"+this.value;
				window.location.href=groupnamechange;
				
								
		  });	    
	    };
	    
		for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {
				
				var groupnamechange= "../group/templateChangeDescriptionGroup"+this.value;
				window.location.href=groupnamechange;
				
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {
				
				var addusers= "../group/templateGroupAddStudent"+this.value;
				window.location.href=addusers;
				
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns4.length; i++) {
			btns4[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz usunuć studenta? ')) {
					var userremove= "../group/groupRemoveStudent"+this.getAttribute("data-groupid")+"/"+this.value;
					window.location.href=userremove;
			       } else {
			           
			       }
				
					
		  });	    
	    };
	    
	    for (var i = 0; i < btns5.length; i++) {
			btns5[i].addEventListener("click", function() {
				
					var addcourse= "../group/templateGroupAddCourse"+this.value;
					window.location.href=addcourse;
			      				
		  });	    
	    };
	    
	    for (var i = 0; i < btns6.length; i++) {
			btns6[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz usunuć kurs? ')) {
					var courseremove= "../group/groupRemoveCourse"+this.getAttribute("data-groupid")+"/"+this.value;
					window.location.href=courseremove;
			       } else {
			           
			       }
				
					
		  });	    
	    };
	    
	    for (var i = 0; i < btns7.length; i++) {
			btns7[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz rozwiązać Grupę? ')) {
					var groupremove= "../group/removeGroup"+this.value;
					window.location.href=groupremove;
			       } else {
			           
			       }
				
					
		  });	    
	    };
	    
	});