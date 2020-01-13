$(function () {

})
function check () {
    var userName = $("#userName").val()
    var password = $("#password").val()
    var truePassword
    $.ajax({
        async:false,
        dataType:"json",
        url:"truePassword",
        type:"POST",
        data:{"userName":userName,"password":password},
        success:function (data) {
            truePssword = data.truePassword
            var tips = $("#tips")
            if(data.truePassword){
                tips.html("")
            }else{
                tips.css("color","red")
                tips.html(data.msg)
            }
        }
    })
    if(truePssword){
        return true;
    }else {
        return  false
    }
}

$(function () {
    alert("2222");
})
