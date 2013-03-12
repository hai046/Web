<!-- <script type="text/javascript" src="../js/jquery-ui.js"></script>
<link href="../css/ui-lightness/jquery-ui.css" rel="stylesheet"
        type="text/css" /> -->

<link rel="stylesheet" href="../css/jquery.ui.all.css">
<script src="../js/jquery.js"></script>
<script src="../js/jquery.bgiframe-2.1.2.js"></script>
<script src="../js/jquery.ui.core.js"></script>
<script src="../js/jquery.ui.widget.js"></script>
<script src="../js/jquery.ui.mouse.js"></script>
<script src="../js/jquery.ui.button.js"></script>
<script src="../js/jquery.ui.draggable.js"></script>
<script src="../js/jquery.ui.position.js"></script>
<script src="../js/jquery.ui.resizable.js"></script>
<script src="../js/jquery.ui.dialog.js"></script>
<script src="../js/jquery.effects.core.js"></script>
<script src="../js/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="../js/jquery-ui-timepicker-zh-CN.js"></script>

<link href="../css/jquery-ui-timepicker-addon.css" rel="stylesheet"
	type="text/css" />
<link href="../css/bootstrap-responsive.css" rel="stylesheet"
	type="text/css" />
<link href="../css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bootstrap-dropdown.js"></script>
<script type="text/javascript" src="../js/bootstrap.js"></script>



<script>
	$(function() {
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		$("#dialog:ui-dialog").dialog("destroy");
		var pickerOpts = {
			changeMonth : true,
			changeYear : true,
			dateFormat : "yy-mm-dd"
		};

		$(".datepicker").datepicker(pickerOpts);

		$(".timepicker").timepicker({});

		$('.dropdown-toggle').dropdown();

		$("select")
				.each(
						function() {
							var initVal = $(this).attr("initVal");
							if (initVal == null || initVal === undefined
									|| typeof (initVal) == 'undefined'
									|| initVal == "") {
								return;
							}
							$(this).val(initVal);
						});
	});
</script>



<div id="navbar-example" class="navbar navbar-static">
	<div class="navbar-inner">
		<div class="container" style="width: auto;">
			<a class="brand" href="#">CMS </a>
			<ul class="nav">
				<li class="active"><a href="/">首页</a></li>
				<li class="dropdown">
					<a href="#nogo" class="dropdown-toggle" data-toggle="dropdown"> 类别 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/category/list">分类列表</a></li>
						<li><a href="/category/add">添加分类</a></li>
						<li><a href="/crawler/status">爬取状态</a></li>
					</ul>
				</li>
				<li><a href="/joke/list">每日段子</a></li>
				<li class="dropdown">
					<a href="#nogo" class="dropdown-toggle" data-toggle="dropdown"> 专题 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/topic/topicList">专题列表</a></li>
						<li><a href="/topic/topicAdd">添加专题</a></li>
						<li><a href="/topic/itemList?topicId=">添加推荐</a></li>
						<li><a href="/topic/recommendList">延迟推荐</a></li>
						<li><a href="/topic/planningTopicList">延迟专题</a></li>
					</ul>
				</li>
				<li class="dropdown">
					<a href="/bbs/articleList">帖子管理<b class="caret"></b></a>
				</li>
				<li class="dropdown"><a href="#nogo" class="dropdown-toggle" data-toggle="dropdown"> 配置<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/system/list">SharedPreferences</a></li>
					</ul>
				</li>
				<li class="dropdown"><a href="#nogo" class="dropdown-toggle" data-toggle="dropdown">程序<font color="red">${currentApp}</font><b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="/system/app?app_id=0">淘色</a></li>
						<li><a href="/system/app?app_id=1">宠爱</a></li>
						<li><a href="/system/app?app_id=-1">全部程序</a></li>
					</ul>
				</li>
			</ul>


			<form action="/search" method="post" class="navbar-search "
				id="search_text">
				<input type="text" name="query" class="search-query span2"
					placeholder="搜索" /> <input type="hidden" name="currentPage"
					value="${currentPage}" /> <a href="javascript:;" id="searchBtnAC"
					style="background-position: -157px -48px;"
					onclick="$('#search_text').submit()">搜索</a>
			</form>
			
			

			<ul class="nav pull-right ">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">账号 <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#" id="create-user">登陆</a></li>
						<li><a href="/feedback/">退出</a></li>
					</ul></li>
				<li><a href="/feedBack/list">意见建议库</a></li>
			</ul>
		</div>
	</div>
</div>



<!-- <div id="dialog-form" title="切换用户">

        <p class="validateTips"></p>
        <form>
                <fieldset>
                        <label for="name">用户名：</label> <input type="text" name="name"
                                id="name" class="text ui-widget-content ui-corner-all" /> <label
                                for="password">密码：</label> <input type="password" name="password"
                                id="password" value="" class="text ui-widget-content ui-corner-all" />
                </fieldset>
        </form>
</div> -->
