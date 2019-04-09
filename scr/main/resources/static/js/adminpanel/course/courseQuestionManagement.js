$(document).ready(function() {
	
		var header = document.getElementById("course_details");
		var btns = header.querySelectorAll(".details");
		var btns2 = header.querySelectorAll(".courseNameChange");
		var btns3 = header.querySelectorAll(".courseDescriptionChange");
		var btns4 = header.querySelectorAll(".courseAddGroup");
		var btns5 = header.querySelectorAll(".removegroup");
		var btns6 = header.querySelectorAll(".moveToArchive");
		var btns7 = header.querySelectorAll(".moveFromArchive");
		var btns8 = header.querySelectorAll(".removeCourse");
		var btns9 = header.querySelectorAll(".addQuestionToCourse");
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {				
				var idcouse=this.getAttribute("data-courseid");
				var single=this.getAttribute("data-single");
				var dificulty=this.getAttribute("data-dificulty");
				var userid= "../../question/coursequestionsManagement/"+idcouse+"/"+single+"/"+dificulty;
				window.location.href=userid;
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {				
				
				var courseid= "../course/templateChangeNameCourse"+this.value;
				window.location.href=courseid;
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {				
				
				var coursenamechange= "../course/templateChangeDescriptionCourse"+this.value;
				window.location.href=coursenamechange;
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns4.length; i++) {
			btns4[i].addEventListener("click", function() {				
				
				var courseAddGroup= "../course/templateCourseAddGroup"+this.value;
				window.location.href=courseAddGroup;
								
		  });	    
	    };
	    
	    for (var i = 0; i < btns5.length; i++) {
			btns5[i].addEventListener("click", function() {	
				
				
				if (confirm('Czy napewno chcesz usunuć Grupę? ')) {
					
					var removeGroup= "../course/courseRemoveGroup"+this.getAttribute("data-courseId")+"/"+this.value;
					window.location.href=removeGroup;
			       }
						
		  });	    
	    };
	    
	    for (var i = 0; i < btns6.length; i++) {
			btns6[i].addEventListener("click", function() {	
				
				
				if (confirm('Czy napewno chcesz Przenieść kurs do archiwum? ')) {
					
					var moveToArchive= "../course/moveToArchive"+this.value;
					window.location.href=moveToArchive;
			       }
						
		  });	    
	    };
	    
	    for (var i = 0; i < btns7.length; i++) {
			btns7[i].addEventListener("click", function() {	
				
				
				if (confirm('Czy napewno ustawić kurs jako dostępny? ')) {
					
					var moveFromArchive= "../course/moveFromArchive"+this.value;
					window.location.href=moveFromArchive;
			       }
						
		  });	    
	    };
	    
	    for (var i = 0; i < btns8.length; i++) {
			btns8[i].addEventListener("click", function() {	
				
				
				if (confirm('Czy napewno chcesz usunąć kurs? ')) {
					
					var removeCourse= "../course/removeCourse"+this.value;
					window.location.href=removeCourse;
			       }
						
		  });	    
	    };
	    
	    for (var i = 0; i < btns9.length; i++) {
			btns9[i].addEventListener("click", function() {	
				
					var addQuestion= "../course/addQuestionToCourse"+this.value;
					window.location.href=addQuestion;
			      	
		  });	    
	    };
	    
	    
	    
	});