$(function () {
	$(document).ready(function() {
		$("#listcourses").click(function()
          	{
                	$.getJSON(
		                "listcourses/",
		                function(result){
		                        var tb_body = "";
		                        $.each(result, function(){
		                                var tb_row = "";
		                                $.each(this, function(k,v){
							tb_row +="<td>"+v+"</td>";
		                                })
		                                tb_row += "<td><button class='btn btn'>delete</button><button class='btn btn'>modify</button></td>";
		                                tb_body += "<tr>"+tb_row+"</tr>";
		                        })
		                        $("#coursesbody").html(tb_body);
		                }
                	);      
          	});
		$("#listteachers").click(function()
		{
			//var query4teacher = $("#teacherquery").val();
			url4teacher = "listteachers/";//+$("#teacherquery").val();
			$.getJSON(
				url4teacher,
 	                       	function(result){
                                	var tb_body = "";
					var link = "";
                                	$.each(result, function(){
                                        	var tb_row = "";
                                        	$.each(this, function(k,v){
                                               		if(k=="teacherID") {
		                                                link = "<a href=listteacherbyID/"+v+">"+v+"</a>";
								tb_row += "<td>"+link+"</td>";
							}
							else
								tb_row += "<td>"+v+"</td>";
                                        	})
                                        	tb_row += "<td><button class='btn btn'>delete</button><button class='btn btn'>modify</button></td>";
                                        	tb_body += "<tr>"+tb_row+"</tr>";
                                	})
                                	$("#teachersbody").html(tb_body);
                        	}
                	);
		});
		$("#liststudents").click(function()
          	{
                	$.getJSON(
		                "liststudents/",
		                function(result){
		                        var tb_body = "";
		                        var link = "";
		                        $.each(result, function(){
		                                var tb_row = "";
		                                $.each(this, function(k,v){
		                                        if(k=="studentID"){
		                                                link = "<a href=liststudentbyID/"+v+">"+v+"</a>";
		                                                tb_row += "<td>"+link+"</td>";
		                                        }
		                                        else
		                                                tb_row +="<td>"+v+"</td>";
		                                })
		                                tb_row += "<td><button class='btn btn'>delete</button><button class='btn btn'>modify</button></td>";
		                                tb_body += "<tr>"+tb_row+"</tr>";
		                        })
		                        $("#studentsbody").html(tb_body);
		                }
                	);      
          	});
		$("#listhosts").click(function()
          	{
                	$.getJSON(
		                "listhosts/",
		                function(result){
		                        var tb_body = "";
		                        var link = "";
		                        $.each(result, function(){
		                                var tb_row = "";
		                                $.each(this, function(k,v){
		                                        if(k=="hostIP"){
		                                                link = "<a href=listdomainsbyIp/"+v+">"+v+"</a>";
		                                                tb_row += "<td>"+link+"</td>";
		                                        }
		                                        else
		                                                tb_row +="<td>"+v+"</td>";
		                                })
		                                tb_row += "<td><button class='btn btn'>delete</button><button class='btn btn'>modify</button></td>";
		                                tb_body += "<tr>"+tb_row+"</tr>";
		                        })
		                        $("#hostsbody").html(tb_body);
		                }
                	);      
          	});
		/*
		$("#addteacherbutton").click(function()
		{
			var o = {name:"insert!"};
			var o_str = JSON.stringify(o);
			alert(o_str);
			$.getJSON(
				"addteacher/",
				o_str,
				function(result){
					$.each(result,function()
					{
						$.each(this,function(k,v){
							alert(k);
						})
					})
					alert(result);
				},
				"json"
			);
		});*/
		$("#listselectives").click(function()
          	{	
			alert("1");
			//alert($("#userid").attr("href"));
                	/*$.getJSON(
		                "listSelectiveByID/"+$("#userid").text(),
		                function(result){
		                        var tb_body = "";
		                        $.each(result, function(){
		                                var tb_row = "";
		                                $.each(this, function(k,v){
							tb_row +="<td>"+v+"</td>";
		                                })
		                                tb_row += "<td><button class='btn btn'>delete</button><button class='btn btn'>modify</button></td>";
		                                tb_body += "<tr>"+tb_row+"</tr>";
		                        })
		                        $("#coursesbody").html(tb_body);
		                }
                	);*/      
          	});
		
	});
});
