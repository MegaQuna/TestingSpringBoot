$(document).ready(function() {
	
		var header = document.getElementById("questions_details");
		var btns = header.querySelectorAll(".deleteQuestionImg");
		var btns2 = header.querySelectorAll(".deleteAnswereImg");
		var btns3 = header.querySelectorAll(".changeSingle");
		var btns4 = header.querySelectorAll(".changeDificulty");
		var btns5 = header.querySelectorAll(".changeContentQuestion");
		var btns6 = header.querySelectorAll(".changeContentAnswere");
		var btns7 = header.querySelectorAll(".removeAnswere");
		var btns8 = header.querySelectorAll(".changeArchive");
		var btns9 = header.querySelectorAll(".addAnswere");
		var btns10 = header.querySelectorAll(".removeQuestionFromCourse");
		var btns11 = header.querySelectorAll(".relocateQuestion");
		
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {		
				var idquestion=this.value;
				var url= "/admin/deleteImgQuestion"+idquestion;
				window.location.href=url;					
		  });	    
	    };
	    
	    for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {		
				var idanswere=this.value;
				var url= "/admin/deleteImgAnswere"+this.getAttribute("data-questionid")+"/"+idanswere;
				window.location.href=url;					
		  });	    
	    };  
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {		
				var url= "/question/changeSingle"+this.value;
				window.location.href=url;					
		  });	    
	    };  
	    
	    for (var i = 0; i < btns4.length; i++) {
			btns4[i].addEventListener("click", function() {		
				var url= "/question/changeDificulty"+this.value;
				window.location.href=url;					
		  });	    
	    };  
	    
	    for (var i = 0; i < btns5.length; i++) {
			btns5[i].addEventListener("click", function() {		
				var url= "/question/templatechangeContentQuestion"+this.value;
				window.location.href=url;					
		  });	    
	    }; 
	    
	    for (var i = 0; i < btns6.length; i++) {
			btns6[i].addEventListener("click", function() {		
				var url= "/question/templateChangeContentAnswere"+this.value;
				window.location.href=url;					
		  });	    
	    };
	    
	    for (var i = 0; i < btns7.length; i++) {
			btns7[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz usunąć odpowiedz? ')) {
					var url= "/question/removeAnswere"+this.value;
					window.location.href=url;
			       } else {
			           
			       }

		  });	    
	    };
	    
	    for (var i = 0; i < btns8.length; i++) {
			btns8[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz zmienić archiwizacje pytania? ')) {
					var url= "/question/changeArchive"+this.value;
					window.location.href=url;
			       } else {
			           
			       }

		  });	    
	    };
	    
	    for (var i = 0; i < btns9.length; i++) {
			btns9[i].addEventListener("click", function() {		
				var url= "/question/templateAddAnswere"+this.value;
				window.location.href=url;					
		  });	    
	    }; 
	    
	    for (var i = 0; i < btns10.length; i++) {
			btns10[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno chcesz całkowicie usunąć pytanie? ')) {
					var url= "/question/removeQuestionFromCourse"+this.value;
					window.location.href=url;
			       } else {
			           
			       }

		  });	    
	    };
	    
	    
	    for (var i = 0; i < btns11.length; i++) {
			btns11[i].addEventListener("click", function() {
				
				if (confirm('Czy napewno przenieść pytanie do innego kursu? ')) {
					var url= "/question/templateRelocateQuestion"+this.value;
					window.location.href=url;
			       } else {
			           
			       }

		  });	    
	    };
	    
	});