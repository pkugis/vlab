$(function () {
	$(document).ready(function() {
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
