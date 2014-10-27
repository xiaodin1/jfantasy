<#assign s=JspTaglibs["/WEB-INF/tlds/struts-tags.tld"]/>
<@override name="pageTitle">
属性转换器
<small>
    converter
</small>
</@override>
<@override name="head">
<script type="text/javascript">
    $(function(){
        $(window).resize(function () {
            var _$gridPanel = $('.grid-panel');
            if(!!_$gridPanel.length){
                _$gridPanel.css('minHeight', $(window).height() - (_$gridPanel.offset().top + 15));
                _$gridPanel.triggerHandler('resize');
            }
        });
        //列表初始化
        var pager=<@s.property value="@com.fantasy.framework.util.jackson.JSON@serialize(pager)" escapeHtml="false"/>;
        var $grid = $('#view').dataGrid($('#searchFormPanel'),$('.batch'));
        $grid.data('grid').view().on('add',function(data){
            this.target.find('.delete').click(function(e){
                deleteMethod([data.id]);
                return stopDefault(e);
            });
        });
        $grid.setJSON(pager);

        var deleteMethod = $('.batchDelete').batchExecute($("#allChecked"),$grid.data('grid').pager(),'id','是否确认删除{name}？',function(){
            $.msgbox({
                msg : "删除成功!",
                type : "success"
            });
        });
    });
</script>
</@override>
<@override name="pageContent">
<div id="searchFormPanel" class="button-panel pad5A">
    <@s.form id="searchForm" namespace="/attr" action="converter_search" method="post">
        <a title="添加" class="btn medium primary-bg dd-add" href="<@s.url namespace="/attr" action="converter_add"/>" target="after:closest('#page-content')">
            <span class="button-content">
                <i class="glyph-icon icon-plus float:left"></i>
                <@s.text name= '添加' />
            </span>
        </a>
        <div class="propertyFilter">
        </div>
        <div class="form-search">
            <input type="text" name="LIKES_title" title="" data-placement="bottom" class="input tooltip-button ac_input" placeholder="Search..." autocomplete="off" style="display: inline-block; width: 200px;">
            <i class="glyph-icon icon-search"></i>
        </div>
    </@s.form>
</div>
<div class="batch">
    <a title="批量删除" class="btn small primary-bg batchDelete" href="<@s.url namespace="/attr" action="converter_delete"/>">
        <span class="button-content">
            <i class="glyph-icon icon-trash float-left"></i>
            批量删除
        </span>
    </a>
</div>
<div class="grid-panel">
    <table id="view" class="table table-hover mrg5B text-center">
        <thead>
        <tr>
            <th class="pad15L" style="width:20px;">
                <input id="allChecked" class="custom-checkbox bg-white" checkAll=".id" type="checkbox" <#--checktip="{message:'您选中了{num}条记录',tip:'#config_check_info'}"--> />
            </th>
            <th>名称</th>
            <th>转换器</th>
            <th>创建时间</th>
            <th>描述</th>
            <th class="text-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr class="template" name="default">
            <td><input class="id custom-checkbox" type="checkbox" value="{id}"/></td>
            <td class="font-bold">{name} </td>
            <td>{typeConverter}</td>
            <td>{createTime:date('yyyy-MM-dd')}</td>
            <td>{description}</td>
            <td class="pad0T pad0B text-center">
                <div class="dropdown actions">
                    <a href="javascript:;" title="" class="btn medium bg-blue" data-toggle="dropdown">
                        <span class="button-content">
                            <i class="glyph-icon font-size-11 icon-cog"></i>
                            <i class="glyph-icon font-size-11 icon-chevron-down"></i>
                        </span>
                    </a>
                    <ul class="dropdown-menu float-right">
                        <li>
                            <a title="<@s.text name= '详情' />" class="view" href="<@s.url namespace="/attr" action="converter_view?id={id}"/>" target="after:closest('#page-content')" >
                                <i class="glyph-icon icon-external-link-sign mrg5R"></i>
                                详情
                            </a>
                        </li>
                        <li>
                            <a title="<@s.text name= '修改' />" class="edit" href="<@s.url namespace="/attr" action="converter_edit?id={id}"/>" target="after:closest('#page-content')" >
                                <i class="glyph-icon icon-edit mrg5R"></i>
                                修改
                            </a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a title="<@s.text name= '删除' />" href="<@s.url namespace="/attr" action="converter_delete?id={id}"/>" class="font-red delete">
                                <i class="glyph-icon icon-remove mrg5R"></i>
                                删除
                            </a>
                        </li>
                    </ul>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<div class="divider mrg0T"></div>
</@override>
<@extends name="../wrapper.ftl"/>