/**
 * Created by swanand on 2/27/2015.
 */
$(document).ready(function() {
    $('#spinner').hide();
    $('.container').hide();
    $("#srch-btn").click(function(){
        //Check if it's not empty
        $('#spinner').show();
        $.ajax({
            url: "ResultServlet",
            type: "GET", //send it through get method
            dataType:"json",
            data:{qry:$('#txt-query').val()},
            success: function(response) {
                $('#spinner').hide();
                $('.container').show();
                console.log("Success");
                $(".jumbotron").empty();
                response.data.forEach(function(resp){
                    $(".jumbotron").append("<a href="+resp.url+">" + resp.title + "<\a>");
                    $(".jumbotron").append("<br>");
                    $(".jumbotron").append("<p>"+ resp.snippet + "<\p>");
                    $(".jumbotron").append("<br>");
                });
            },
            error: function(xhr,err) {
                //Do Something to handle error
                $('#spinner').hide();
                alert('Ajax readyState: '+xhr.readyState+'\nstatus: '+xhr.status + ' ' + err);
                console.log("Error");
            }
        });
    });
});