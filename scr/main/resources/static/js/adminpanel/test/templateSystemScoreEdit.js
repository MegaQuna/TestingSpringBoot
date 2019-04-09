$(document).ready(function() {
	
	
		if (document.getElementById('only').checked){
			$('.onlycorectPointCorrect').show();
			$('.onlycorectPointCorrectlable').show();
			$('.notonlycorectPointCorrect').hide();
			$('.notonlycorectPointCorrectlable').hide();
			
		}
		
		if (document.getElementById('single').checked){
			$('.onlycorectPointCorrect').show();
			$('.onlycorectPointCorrectlable').show();
			$('.notonlycorectPointCorrect').hide();
			$('.notonlycorectPointCorrectlable').hide();
			$('.notonlycorect').hide();
			
		}
	
		var header = document.getElementById("corect");
		var btns = header.querySelectorAll(".onlycorect");
		var btns2 = header.querySelectorAll(".notonlycorect");
		var btns3 = header.querySelectorAll(".single");
		var btns4 = header.querySelectorAll(".notsingle");
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				$('.onlycorectPointCorrect').show();
				$('.onlycorectPointCorrectlable').show();
				$('.notonlycorectPointCorrect').hide();
				$('.notonlycorectPointCorrectlable').hide();
				
		  });	    
	    };
	    
	    for (var i = 0; i < btns2.length; i++) {
			btns2[i].addEventListener("click", function() {
				
				$('.onlycorectPointCorrect').show();
				$('.onlycorectPointCorrectlable').show();
				$('.notonlycorectPointCorrect').val(1);
				$('.notonlycorectPointCorrect').show();
				$('.notonlycorectPointCorrectlable').show();
				
		  });	    
	    };
	    
	    for (var i = 0; i < btns3.length; i++) {
			btns3[i].addEventListener("click", function() {
				
				$('.onlycorectPointCorrect').show();
				$('.onlycorectPointCorrectlable').show();
				$('.notonlycorectPointCorrect').hide();
				$('.notonlycorectPointCorrectlable').hide();
				$(".onlycorect").prop("checked", true);
				$('.notonlycorect').hide();
				
		  });	    
	    };
	    
	    for (var i = 0; i < btns4.length; i++) {
			btns4[i].addEventListener("click", function() {
				
				$('.onlycorectPointCorrect').show();
				$('.onlycorectPointCorrectlable').show();
				$('.notonlycorectPointCorrect').val(1);
				$('.notonlycorectPointCorrect').show();
				$('.notonlycorectPointCorrectlable').show();
				$('.notonlycorect').show();
				
		  });	    
	    };
	});