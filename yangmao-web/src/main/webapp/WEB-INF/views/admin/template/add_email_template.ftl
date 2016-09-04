<#include "../../common/_layout.ftl" />

<@layoutHead title="添加模板">

</@layoutHead>
<@layoutBody>
    <form id="form1" method="post" action="${path}/admin/template/insert_email_template.html" role="form" class="form-horizontal form-bordered">
        <section class="content-wrapper" role="main">
            <div class="content">
                <div class="content-bar">
                    <ul class="breadcrumb breadcrumb-angle">
                        <li><a href="#" aria-label="home"><i class="fa fa-home"></i></a></li>
                        <li class="active">模板管理</li>
                        <li class="active">添加模板</li>
                    </ul>
                </div><!-- /.content-bar -->

                <div class="col-md-12">
                    <div class="panel" data-fill-color="true">
                        <div class="panel-heading">
                            <h3 class="panel-title">模板基本信息</h3>
                        </div>
                        <div class="panel-body">
                            <!--正文内容 开始-->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">
                                    模板名称<span class="text-danger">*</span>
                                </label>
                                <div class="col-md-5">
                                    <input name="name" type="text"  class="form-control validate[required]"  />
                                    </br>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">
                                    邮件标题<span class="text-danger">*</span>
                                </label>
                                <div class="col-md-5">
                                    <input name="title" type="text" class="form-control validate[required]"  />
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
                            <div class="modal-dialog modal-full">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-model-dialog data-dismiss="modal" aria-hidden="true"><i class="icon_close fa-lg"></i></button>
                                        <h4 class="modal-title" id="fullWidthLabel">添加选品组</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                选品组名称<span class="text-danger">*</span>
                                            </label>
                                            <div class="col-md-5">
                                                <select name="favoritesId">
                                                    <#list favorites as f>
                                                        <option value="${f.yangmaoFavoritesId}-${f.title}">${f.title}</option>
                                                    </#list>
                                                </select>
                                                </br>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                商品个数<span class="text-danger">*</span>
                                            </label>
                                            <div class="col-md-5">
                                                <input name="amount" type="text" class="form-control validate[required]"  />
                                                </br>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">

                                    </div>
                                </div><!-- /.modal-content -->
                            </div>
                            <div id="app">

                            </div>
                            <div class="col-md-offset-3 col-md-9">
                                <a id="addModel" class="btn btn-danger" type="button" style="left: 35%;">
                                    添加一行
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
    <script id="emailTemplate" type="text/html">
        <div class="modal-dialog modal-full">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-model-dialog data-dismiss="modal" aria-hidden="true"><i class="icon_close fa-lg"></i></button>
                    <h4 class="modal-title" id="fullWidthLabel">添加选品组</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            选品组名称<span class="text-danger">*</span>
                        </label>
                        <div class="col-md-5">
                            <select name="favoritesId">
                                {{each list as one}}
                                    <option value="{{one.yangmaoFavoritesId}}-{{one.title}}">{{one.title}}</option>
                                {{/each}}
                            </select>
                            </br>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            商品个数<span class="text-danger">*</span>
                        </label>
                        <div class="col-md-5">
                            <input name="amount" type="text" class="form-control validate[required]"  />
                            </br>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                </div>
            </div><!-- /.modal-content -->
        </div>
    </script>
    <!-- COMPONENTS -->
    <script src="${path}/wrap/scripts/epiceditor.js"></script>
    <script src="${path}/wrap/scripts/summernote.js"></script>
    <script>
        $("button[class='close']").on("click",function(){
            this.parentNode.parentNode.parentNode.remove()
        });

        $("#addModel").on('click',function(){
            $.ajax({
                url:"${path}/admin/template/get_favorites",
                type:"get",
                dataType:'json',
                success:function(data){
                    var value ={
                        list:data
                    }
                    var html = template('emailTemplate', value);
                    $("#app").append(html);
                    $("button[class='close']").on("click",function(){
                        this.parentNode.parentNode.parentNode.remove()
                    });
                },
                error:function (xhr, type, exception) {
                    alert(type, "Failed");
                }
            });
        });
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
        })

        window.saveTemplate = function () {
            var content = $("#commodity-detail-description-summernote").code();
            $("#content").val(content);
            $("#form1").submit();


        };
    </script>
</@layoutFooter>


