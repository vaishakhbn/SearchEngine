/**
 * Created by swanand on 2/27/2015.
 */
$(document).ready(function() {


$("#srch-btn").click(function(){
    //Check if it's not empty
    $.ajax({
        url: "ResultServlet",
        type: "GET", //send it through get method
        dataType:"json",
        data:{qry:$('#txt-query').val()},
        success: function(response) {
            console.log("Success");
            for(var resp in response.data){
                $("#results").append("<a href="+response.data[resp]+">" + response.data[resp] + "<\a>");
                $("#results").append("<br>");
            }
        },
        error: function(xhr,err) {
            //Do Something to handle error
            alert('Ajax readyState: '+xhr.readyState+'\nstatus: '+xhr.status + ' ' + err);
            console.log("Error");
        }
    });
});
});