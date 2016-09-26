<#include "../../common/_layout.ftl" />

<@layoutHead title="创建实例">

</@layoutHead>
<@layoutBody>
    <form id="form1" method="post" action="${path}/admin/instance/insert_instance_email.html" role="form" class="form-horizontal form-bordered">
        <section class="content-wrapper" role="main">
            <div class="content">
                <div class="content-bar">
                    <ul class="breadcrumb breadcrumb-angle">
                        <li><a href="#" aria-label="home"><i class="fa fa-home"></i></a></li>
                        <li class="active">实例管理</li>
                        <li class="active">创建实例</li>
                    </ul>
                </div><!-- /.content-bar -->

                <div class="col-md-12">
                    <div class="panel" data-fill-color="true">
                        <div class="panel-heading">
                            <h3 class="panel-title">实例基本信息</h3>
                        </div>
                        <div class="panel-body">
                            <input type="hidden" id="instanceId" name="mailInstanceId" value="${instanceId}">
                            <!--正文内容 开始-->

                            <#list template.sectionModelList as section>
                                <div class="row">
                                    <input type="hidden" name="sectionName" value="${section.section}">
                                    <input type="hidden" name="favoritesId" value="${section.favoritesId}">
                                    <#list 1..section.sectionAmount as index>
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <#--class="col-sm-3 control-label"-->
                                                <label >
                                                    ${section.section} 商品${index}<span class="text-danger">*</span>
                                                </label>
                                                <#--<div class="col-md-5">-->
                                                    <#--<label class="select select-sm">-->
                                                        <select class="form-control" name="instanceItemId" >
                                                            <#list section.favoritesItemsModels as favorites>
                                                                <option value="${favorites.itemId}-${section.sectionId}">${favorites.title}--优惠${favorites.originalPrice-favorites.finalPrice}元</option>
                                                            </#list>
                                                        </select>
                                                    <#--</label>-->
                                                    <#--</br>-->
                                                <#--</div>-->
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            </#list>
                            <br>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">
                                    邮件标题<span class="text-danger">*</span>
                                </label>
                                <div class="col-md-5">
                                    <input name="title" type="text" class="form-control validate[required]"  value="${template.title!""}" />
                                    </br>
                                </div>
                            </div>
                            <label for="commodity-detail-description-summernote">
                                文章正文
                            </label>
                            <div id="summernote-panel" class="panel" data-fill-color="true">
                                <div class="panel-body">
                                    <div id="commodity-detail-description-summernote"
                                         style="font-family:'Open Sans'"></div>
                                </div><!-- /.panel-body -->
                            </div><!-- /.panel -->
                            <input id="content" name="content" hidden></input>

                            <div class="col-md-offset-3 col-md-9">
                                <a href="#" class="btn btn-info" onclick="instanceEmail()" style="left: 35%;">
                                    装载
                                </a>
                                <button class="btn btn-info" onclick="saveTemplate()" style="left: 35%;">
                                    提交
                                </button>
                                <button class="btn" type="reset" onclick="javascript:history.go(-1)">
                                    返回
                                </button>
                            </div>
                        </div><!-- /.content-body -->
                    </div>
                </div>

                <!-- Template Setups -->
                <div class="modal fade" id="templateSetup">
                    <div class="modal-dialog">
                        <!-- modal-content -->
                        <div class="modal-content"></div>
                    </div><!-- /.modal-dialog -->
                </div><!-- /.templateSetup -->

            </div><!-- /.content -->
        </section><!-- /MAIN -->
    <form>

</@layoutBody>

<@layoutFooter>


    <!-- COMPONENTS -->
    <script src="${path}/wrap/scripts/epiceditor.js"></script>
    <script src="${path}/wrap/scripts/summernote.js"></script>
    <script>

        $(window).load(function () {

            //富文本编辑器
            var $summernote = $("#commodity-detail-description-summernote");
            $summernote.summernote({
                height: 300,
                toolbar: [
                    ['style', ['style']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
                    ['fontname', ['fontname']],
                    ['fontsize', ['fontsize']],
                    ['color', ['color']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['table', ['table']],
                    ['insert', ['link', 'picture', 'video', 'hr', 'readmore']],
                    ['genixcms', ['elfinder']],
                    ['view', ['fullscreen', 'codeview']],
                    ['help', ['help']]
                ]
            });
            $summernote.code('${template.content!""}');
        })

        window.saveTemplate = function () {
            var content = $("#commodity-detail-description-summernote").code();
            $("#content").val(content);
            $("#form1").submit();


        };

        <#--function selectCommodity(context,favoritesId){-->
            <#--$.ajax({-->
                <#--url:"${path}/admin/instance/get_commodity_list",-->
                <#--type:"get",-->
                <#--data:{"favoritesId":favoritesId},-->
                <#--dataType:'json',-->
                <#--success:function(data){-->
                    <#--var value ={-->
                        <#--list:data-->
                    <#--}-->
                    <#--var html = template('commodityList', value);-->
                    <#--context.innerHTML = html;-->
                <#--},-->
                <#--error:function (xhr, type, exception) {-->
                    <#--alert(type, "Failed");-->
                <#--}-->
            <#--});-->
        <#--}-->

        function instanceEmail(){
            var $summernote = $("#commodity-detail-description-summernote");
            var content = $summernote.code();
//            var sectionNameArray = $('input[name="sectionName"]').val();
            $.ajax({
                url: "${path}/admin/instance/get_replace_key_value_list",
                type: "get",
                dataType: 'json',
                success: function (data) {
                    var keyValue = data;
                    var sectionNameArray =[];
                    var favoritesId = [];
                    $('input[name="sectionName"]').each(function(){
                        var section = {"section" : $(this).val()};
                        sectionNameArray.push(section);
                    });
                    $('input[name="favoritesId"]').each(function(){
                        favoritesId.push($(this).val());
                    });
                    for(var i = 0;i < keyValue.length;i++){
                        if(keyValue[i].level == 1){
                            content = repalceArray(content,keyValue[i].templateReplaceName,sectionNameArray,keyValue[i].databaseField);
                        }
                    }
                    var itemsId = [];
                    var itemsArray = $('select[name="instanceItemId"] option:selected');
                    var formDate = new FormData();
                    for(var i = 0 ; i<itemsArray.length;i++){
                        var idArray = itemsArray[i].value.split("-");
                        //获取 item id
//                        formDate.append("itemsId",idArray[0]);
                        itemsId.push(idArray[0]);
                    }
                    var instanceId = $("#instanceId").val();
                    $.ajax({
                        url: "${path}/admin/instance/get_commodity_list_by_item_id",
                        type: "post",
                        data: {itemsId:itemsId,instanceId:instanceId,favoritesId:favoritesId},
                        dataType: 'json',
                        success: function (data) {
                            var list = data;
                            for(var i = 0;i < keyValue.length;i++){
                                if(keyValue[i].level == 2){
                                    content = repalceArray(content,keyValue[i].templateReplaceName,list,keyValue[i].databaseField);
                                }
                            }
                            $summernote.code(content);
                        },
                        error: function (xhr, type, exception) {
                            alert(type, "Failed");
                        }
                    });
//                    var list = instanceEmailTemplateUpload(itemIdArray);

                },
                error: function (xhr, type, exception) {
                    alert(type, "Failed");
                }
            });


        }
        function getReplaceKeyValue(){
            $.ajax({
                url: "${path}/admin/instance/get_replace_key_value_list",
                type: "get",
                dataType: 'json',
                async: false,
                success: function (data) {
                    return data;
                },
                error: function (xhr, type, exception) {
                    alert(type, "Failed");
                }
            });
        }

        function instanceEmailTemplateUpload(itemIdArray){
            $.ajax({
                url: "${path}/admin/instance/get_commodity_list_by_item_id",
                type: "get",
                data: {"itemsId": itemIdArray},
                dataType: 'json',
                async: false,
                success: function (data) {
                   return data;
                },
                error: function (xhr, type, exception) {
                    alert(type, "Failed");
                }
            });
        }


    </script>
</@layoutFooter>



