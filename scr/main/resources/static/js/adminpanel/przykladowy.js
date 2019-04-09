function loadrightpart(url) {
	  $.ajax({
	     type: "GET",
	     url: url,
		 success: function(response) 
	            {	
			 		
	                console.log("sucess!");
					document.getElementById("right-part").innerHTML =response;
					
					switch(url) {
				    case "groupManagement":{
				    	if(!$('#adminpanel_right_groupManagement').length > 0){
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_groupManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_groupManagement.js";
						    document.head.appendChild(newjs);
						}else{
							var oldjs = document.getElementById("adminpanel_right_groupManagement");
							oldjs.remove();
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_groupManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_groupManagement.js";
						    document.head.appendChild(newjs);
						}				    	
				        break;
				    }  
				    case "courseManagement":{
				    	if(!$('#adminpanel_right_courseManagement').length > 0){
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_courseManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_courseManagement.js";
						    document.head.appendChild(newjs);
						}else{
							var oldjs = document.getElementById("adminpanel_right_courseManagement");
							oldjs.remove();
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_courseManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_courseManagement.js";
						    document.head.appendChild(newjs);
						}
				        break;
				    }
				    case "accountManagement":{
				    	
				    	
				    	if(!$('#adminpanel_right_accountManagement').length > 0){
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_accountManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_accountManagement.js";
						    document.head.appendChild(newjs);
						}else{
							var oldjs = document.getElementById("adminpanel_right_accountManagement");
							oldjs.remove();
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_accountManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_accountManagement.js";
						    document.head.appendChild(newjs);
						}
				        break;
				    }
				    case "testManagement":{
				    	
				    	if(!$('#adminpanel_right_testManagement').length > 0){
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_testManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_testManagement.js";
						    document.head.appendChild(newjs);
						}else{
							var oldjs = document.getElementById("adminpanel_right_testManagement");
							oldjs.remove();
							var newjs = document.createElement("script");
							newjs.setAttribute("id", "adminpanel_right_testManagement");
							newjs.src = "/js/adpaneljs/adminpanel_right_testManagement.js";
						    document.head.appendChild(newjs);
						}
				        break;
				    } 
				    default:{
				    	
				    }
				} 
	            },
	     error: function(e){
	                console.log("ERROR: ", e);
	                 
	            }
				
	});
	}

$(document).ready(function() {
	
		var header = document.getElementById("subnavi");
		var btns = header.querySelectorAll(".btn, .active");
		console.log(btns.length);
	
		for (var i = 0; i < btns.length; i++) {
			btns[i].addEventListener("click", function() {
				
				    console.log("klikniety: "+this.id);
				    	
				    	var current = header.getElementsByClassName("active");
				    	var idname=current[0].getAttribute( 'id' )
				    	console.log("aktywny: "+idname);
					    current[0].className = current[0].className.replace("active", "btn");
					    this.className = "active";
					    loadrightpart(this.id);
					    			
		  });	    
	    };
	});