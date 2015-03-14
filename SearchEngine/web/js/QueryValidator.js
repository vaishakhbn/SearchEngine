/**
 * Created by swanand on 2/27/2015.
 */
$(document).ready(function() {

var presentResults = function(resp){

    $.ajax({
        type: 'GET',
        url: resp.url + '&callback=?',
        success: function(data){
            var title = data.responseText.match(/<title|TITLE>(.*?)<\/title|\/TITLE>/);
            if(title === null){

                title = resp.url;
            }
            $("#results").append("<a href="+resp.url+">" + title + "<\a>");
            $("#results").append("<br>");
            $("#results").append("<p>"+ resp.snippet + "<\p>");
            $("#results").append("<br>");
        },
        error: function(xhr,err) {
            return resp.url;
        }
   });
}

$("#srch-btn").click(function(){
    //Check if it's not empty
    $.ajax({
        url: "ResultServlet",
        type: "GET", //send it through get method
        dataType:"json",
        data:{qry:$('#txt-query').val()},
        success: function(response) {
            console.log("Success");
            response.data.forEach(function(resp){
                presentResults(resp);
            });
        },
        error: function(xhr,err) {
            //Do Something to handle error
            alert('Ajax readyState: '+xhr.readyState+'\nstatus: '+xhr.status + ' ' + err);
            console.log("Error");
        }
    });
});
});