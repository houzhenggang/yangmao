<#include "../../common/_layout.ftl" />

<@layoutHead title="修改用户信息">

</@layoutHead>
<@layoutBody>
<form id="form1" method="post" action="${path}/admin/user/update_user.html" role="form" class="form-horizontal form-bordered">
    <input name="userId"  type="hidden"  class="form-control validate[required]" value="${data.userId!''}" />
    <input name="password" id="password" type="hidden" class="form-control" value="${data.password!''}"  />

    <section class="content-wrapper" role="main">
        <div class="content">
            <div class="content-bar">
                <ul class="breadcrumb breadcrumb-angle">
                    <li><a href="#" aria-label="home"><i class="fa fa-home"></i></a></li>
                    <li class="active">用户管理</li>
                    <li class="active">添加用户</li>
                </ul>
            </div><!-- /.content-bar -->

            <div class="col-md-12">
                <div class="panel" data-fill-color="true">
                    <div class="panel-heading">
                        <h3 class="panel-title">用户基本信息</h3>
                    </div>
                    <div class="panel-body">
                        <!--正文内容 开始-->
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                用户昵称<span class="text-danger">*</span>
                            </label>
                            <div class="col-md-5">
                                <input name="name" id="name" type="text"  class="form-control validate[required]" value="${data.name!''}" />
                                </br>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                用户密码<span class="text-danger">*</span>
                            </label>
                            <div class="col-md-5">
                                <input name="passwords" id="passwords" onblur="passwordBlur();" type="password" class="form-control "  />
                                </br>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                用户邮箱<span class="text-danger">*</span>
                            </label>
                            <div class="col-md-5">
                                <input name="email" id="email" type="text"  class="form-control validate[required]" value="${data.email!''}" />
                                </br>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">
                                是否是管理员<span class="text-danger">*</span>
                            </label>
                            <div class="col-md-5">
                                <input type="checkbox" name="isNotAdmin" class="js-switch" data-class-name="switchery switchery-alt" <#if data.isAdmin==1> checked="checked"</#if> >
                                </br>
                            </div>
                        </div>
                        <br/>
                        <div class="col-md-offset-3 col-md-9">
                            <button class="btn btn-info" type="submit" style="left: 35%;">
                                修改
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
    <script src="${path}/wrap/base64/base64.js"></script>
    <script src="${path}/wrap/sha1/sha1.js"></script>
    <script>
        function encodePass(value){
            var basePassword = Base64.encode(value);
            var encodeSha1 = sha1(basePassword);
            return encodeSha1;
        }
        function passwordBlur(){
            var password = $("#passwords").val();
            var encode = encodePass(password);
            $("#password").val(encode);
        }
    </script>
</@layoutFooter>
