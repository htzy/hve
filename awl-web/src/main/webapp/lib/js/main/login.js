/**
 * Created by 黄诗鹤 on 2015/3/21.
 */
$(document).ready(function(){
    $("#username").focusout(function(e){
        if(!checkUsername()){
            return false;
        }
    });
    $("#password").focusout(function(e){
       if(!checkPassword()){
            return false;
       }
    });

    $("#loginForm").submit(function(){
        if(!checkUsername()){
           return false;
        }
        if(!checkPassword()){
            return false;
        }
        if(document.getElementById("info").innerHTML == "此用户不存在！"){
            $("#username_css").addClass("has-error");
            return false;
        }
    });

});

/**
 * check username
 * @returns {boolean} right return true.
 */
function checkUsername(){
    if(!(isEmail($("#username").val()) || isLegal($("#username").val()))){
        document.getElementById("info").innerHTML = "请填入正确格式！";
        //$("#username_css").removeClass();
        $("#username_css").addClass("has-error");
        //$("#username_css").addClass("input-group col-md-6 has-error");
        return false;
    }else{
        //密码提示
        $.post($("#basePath").val()+"/user/getLoginInfo",
            {
                "username" : $("#username").val()
            },
            function(result){
                document.getElementById("info").innerHTML = result.info;
                if(result.info == "此用户不存在！"){
                    $("#username_css").addClass("has-error");
                }else{
                    $("#username_css").removeClass("has-error");
                }
            }
        )

    }
    return true;
}

/**
 * check password
 * @returns {boolean} right return true.
 */
function checkPassword(){
    if(isLegal($("#password").val())){
        document.getElementById("pwdInfo").innerHTML = "";
        $("#password_css").removeClass("has-error");
        $("#enPass").val($.md5($("#password").val()));
        return true;
        //alert($("#enPass").val());
    }else{
        document.getElementById("pwdInfo").innerHTML = "请输入正确格式！";
        $("#password_css").addClass("has-error");
        return false;
    }
}