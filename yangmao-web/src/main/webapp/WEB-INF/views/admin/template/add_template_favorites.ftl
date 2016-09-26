<#include "../../common/_layout.ftl" />

<@layoutHead title="添加模板">

</@layoutHead>
<@layoutBody>
    <form id="form1" method="post" action="${path}/admin/instance/add_instance_email.html" role="form" class="form-horizontal form-bordered">
        <input type="hidden" name="templateId" value="${templateId!""}">
        <section class="content-wrapper" role="main">
            <div class="content">
                <div class="content-bar">
                    <ul class="breadcrumb breadcrumb-angle">
                        <li><a href="#" aria-label="home"><i class="fa fa-home"></i></a></li>
                        <li class="active">模板管理</li>
                        <li class="active">添加品类</li>
                    </ul>
                </div><!-- /.content-bar -->

                <div class="col-md-12">
                    <div class="panel" data-fill-color="true">
                        <div class="panel-heading">
                            <h3 class="panel-title">品类列表信息</h3>
                        </div>
                        <div class="panel-body">
                            <#list section as s>
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
                                                    <input name="amount" type="text" class="form-control validate[required]" value="${s.sectionAmount}" disabled />
                                                    </br>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">

                                        </div>
                                    </div><!-- /.modal-content -->
                                </div>
                            </#list>
                            <div class="col-md-offset-3 col-md-9">
                                <a class="btn btn-info" onclick="generateInstance()" style="left: 35%;">
                                    提交
                                </a>
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

    <script>


        window.generateInstance = function () {
            $("#form1").submit();
        };
    </script>
</@layoutFooter>



