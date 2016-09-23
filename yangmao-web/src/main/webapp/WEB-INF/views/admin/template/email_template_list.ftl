<#include "../../common/_layout.ftl" />

<@layoutHead title="模板列表">

</@layoutHead>
<@layoutBody>
<form id="form1" method="post" >
    <section class="content-wrapper" role="main">
        <div class="content">
            <div class="content-bar">
                <ul class="breadcrumb breadcrumb-angle">
                    <li><a href="#" aria-label="home"><i class="fa fa-home"></i></a></li>
                    <li class="active">模板管理</li>
                    <li class="active">模板列表</li>
                </ul>
            </div><!-- /.content-bar -->


            <div class="content-body">
                <!--正文内容 开始-->
                <div class="col-md-12">
                    <h3>模板列表</h3>
                    <div class="row">
                        <div class="col-md-12">
                            <a class="btn btn-success mb-1x mr-1x" href="${path}/admin/template/add_email_template.html">添加模板</a>
                        </div>
                    </div>
                    <div class="panel fade in panel-default panel-fill" data-fill-color="true" data-init-panel="true">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-md-2 control-label">模板名称:</label>
                                        <div class="col-md-4">
                                            <div class="input-group input-group-in">
                                                <input name="name" class="form-control" value = "${name!''}"/>                                        </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-md-2 control-label"></label>
                                        <div class="col-md-4">
                                            <button class="btn btn-success mb-1x mr-1x">搜索</button>
                                        </div>
                                    </div>
                                </div><!-- /panel-body -->
                            </div>
                            </br>
                        </div>
                    </div>
                    <div class="table-responsive">

                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>模板名称</th>
                                <th>邮件标题</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list data as d >
                                <tr class="odd" role="row">
                                    <td>${d.name!''}</td>
                                    <td>${d.title!''}</td>
                                    <td>${d.createTime?date}</td>
                                    <td>
                                        <a class="btn btn-info mb-1x mr-1x" href="${path}/admin/template/modify_email_template.html?templateId=${d.templateId}">修改</a>
                                        <a class="btn btn-success mb-1x mr-1x" href="${path}/admin/template/add_template_favorites.html?templateId=${d.templateId}">添加品类</a>
                                        <a class="btn btn-danger mb-1x mr-1x" href="${path}/admin/template/delete_email_template.html?templateId=${d.templateId}">删除</a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div><!-- /.table-responsive -->
                </div><!-- /.cols -->
                <!--正文内容 结束-->
                <#include "../../common/page.ftl">
            </div><!-- /.panel -->
        </div><!-- /.content-body -->


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

</@layoutFooter>
