//通过id设置值
function setValueById(id,value){
    if(id != null && id != ""){
        if(value != null && value != ""){
            $("#"+id).val(value);
        }else{
            return "error: value is null";
        }
    }else{
        return "error: id is null";
    }
}
//通过id获取值
function getValueById(id){
    if(id != null && id != ""){
        return $("#"+id).val();
    }else{
        return "error: id is null";
    }
}

/**
 * 通过名字
 */
function getValueByName(name){
    var value = $("input[name='"+name+"']").val();
    if(value != undefined && value != null && value != ""){
        return value;
    }else{
        return "";
    }
}
/**
 * 通过名字设置值
 */
function setValueByName(name,value){
    if(value != undefined && value != null && value != ""){
        $("input[name='"+name+"']").val(value);
    }
}
//消息提示
function messageBox(messageTitle,messageText){
    $.gritter.add({
        title: messageTitle,
        text: messageText,
        class_name: 'gritter-info gritter-center',
    });
}

/**
 * 替换字符串
 */
function replaceStr(targe,original,final){
    return targe.replace(original,final);
}

/**
 * 通过id获取内容
 */
function getTextValueById(id){
    var value = $("#" + id).text();
    if (value != null && value != "") {
        return value;
    } else {
        return "";
    }
}

/**
 * 通过id设置内容
 * @param id
 * @param text
 */
function setTextValueById(id,value){
    var amount = $("#" + id);
    amount.empty();
    if (value != undefined && value != null && value != "") {
        amount.append(value);
    } else {
        amount.append("");
    }
}

/**
 * 通过id获取class
 * @param id
 */
function getClassById(id){
    if(id!= null && id != ""){
        return $("#"+id).prop("class");
    }
    return "";
}

/**
 * 通过id设置class
 * @param id
 * @param attClass
 */
function setClassById(id,atrClass){
    if(id!= null && id != ""){
        $("#"+id).prop("class",atrClass);
    }
}

/**
 * 错误提示
 */
function alertMessage(code) {
    var key = {
        "106": "请填写账号密码",
        "104": "请重新登录账户",
        "112": "账户已存在",
        "unknow": "#" + code
    }
    if (key[code]) message = key[code];
    else message = key.unknow;
    if(code != 0){
        messageBox("错误提示",message);
    }
}

function repalceArray(content,findStr,replaceStrArray,repalceName){
    for(var i=0;i<replaceStrArray.length;i++){
        var repObject = replaceStrArray[i];
        content = replace(content,findStr,repObject[repalceName]);
    }
    return content;
}

function replace(content,findStr,replaceStr){
    var index = content.indexOf(findStr);

    if(index < 0){
        return content;
    }

    var length = findStr.length;

    return content.substring(0,index) + replaceStr + content.substring(index+length);

}