(function($) {
	$.fn.extend({
		// crud(表单选项,查询参数,
		crud : function(opt) {
			var table = $(this);
			var url = opt.url;
			var columns = opt.columns[0];
			var _PANEL_NAME = "dataTable_editPanel";
			

			// 建立编辑面板
			var createEditPanel = function() {
				var div = $('<div class="container" id="' + _PANEL_NAME + '" style="padding:10px"></div>');
				var form = $('<form class="form-horizontal" role="form" id="form" method="post"></form>');
				
				// 每个编辑项目
				var itemTemplate = '<div class="form-group">' + 
			    	'<label for="#id#" class="col-sm-3 control-label">#title#</label>' + 
			    	'<div class="col-md-6">' + 
			    	'<input type="text" class="form-control #input#" id="#id#" name="#id#" placeholder="#title#" data-options="#data-options#"></div></div>';
				
				for(var i = 0; i < columns.length; ++i) {
					if(!columns[i].igorn) {
						form.append(itemTemplate.replace(/#id#/g,columns[i].field)
							.replace(/#title#/g, columns[i].title)
							.replace(/#input#/g, columns[i].input)
							.replace(/#data-options#/g, columns[i]['data-options']));
					}
				}
				
				// 加入隐藏域
				form.append('<input type="hidden" id="_method" name="_method" />');
				form.append('<input type="hidden" id="id" name="id" />');
				// 加入提交按钮
				form.append('<div class="form-group">' +
					    '<div class="col-md-offset-3 col-md-4">' +
					    '<button type="submit" class="btn btn-default btn-block">提交</button>' +
					    '</div></div>');
				// 加入div中
				div.append(form);
				
				table.parent().parent().append(div);
				
				// 建立easyui事件
				$('.easyui-validatebox').validatebox();
				$('.easyui-numberbox').validatebox();
				
				// 将表单作为面板
				$('#' + _PANEL_NAME).dialog({
					title : '编辑表单',
					height : $(document).height() - 200,
					width : $(document).width() / 2,
					closed : true,
					modal : true
				});
			};
			
			var btn_add = function() {
				$('#form').form('clear');

				$('#' + _PANEL_NAME).dialog('open');
				$('#' + _PANEL_NAME).find('input:first').focus();
				$('#form').find('#id').val('0');
			    $('#form').find('#_method').val('put');
			};
			var btn_update = function() {
				var row = table.datagrid('getSelected');console.info(row)
				if (row){
					$('#' + _PANEL_NAME).dialog('open');
					$('#' + _PANEL_NAME).find('input:first').focus();
				    $('#form').form('load',row);
				    $('#form').find('#_method').val('post');
				};
			};
			var btn_delete = function() {
				var row = table.datagrid('getSelected');  		
			    if (row){
			        $.messager.confirm('确认','是否确定删除此条记录,此操作不可恢复?',function(r){
			            if (r){ 
			            	$('#form').form('load',row);
			            	$('#form').find('#_method').val('delete');
			            	$('#form').form('submit');
			            }  
			        }); 
			    }
			};
			var btn_reload = function() {
				table.datagrid('reload');
			};
			
			var formBuild = function() {
				$('#form').form({
					url : url,
					onSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						result = JSON.parse(data);
			            if (!result.success){  
			                $.messager.alert('Error', result.errorMsg);  
			            } else {  
			            	$('#' + _PANEL_NAME).dialog('close');      // close the dialog  
			                table.datagrid('reload');    // reload the user data  
			            }  
					}
				});
			};
			
			var dgOptions = $.extend(opt,{
				toolbar : [
					{iconCls : 'icon-add',handler : btn_add,text:'添加'},
					{iconCls : 'icon-edit',handler : btn_update,text:'编辑'},
					{iconCls : 'icon-remove',handler : btn_delete,text:'删除'},
					{iconCls : 'icon-reload',handler : btn_reload,text:'刷新'}
				],
				onLoadSuccess : function() {
					if(opt.onSuccess != null)
						opt.onSuccess();
					createEditPanel();
					formBuild();
				}
			});
			// 添加按钮条
			table.datagrid(dgOptions);
		},
		
		// --------------------------------------------------------------------------------------//
		
		// 格式化Date
		dateFormat : function(d,format) {
			var o = {    
	            "M+": d.getMonth() + 1,    
	            "d+": d.getDate(),    
	            "h+": d.getHours(),    
	            "m+": d.getMinutes(),    
	            "s+": d.getSeconds(),    
	            "q+": Math.floor((d.getMonth() + 3) / 3),    
	            "S": d.getMilliseconds()    
	        };
	        if (/(y+)/.test(format)) {    
	            format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));    
	        }    
	        for (var k in o) {    
	            if (new RegExp("(" + k + ")").test(format)) {    
	                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));    
	            }    
	        }    
	        return format;    
		},
		
		// --------------------------------------------------------------------------------------//
		
		date : function(value,format) {
			var formatDate = function(d,format) {
				var o = {    
			            "M+": d.getMonth() + 1,    
			            "d+": d.getDate(),    
			            "h+": d.getHours(),    
			            "m+": d.getMinutes(),    
			            "s+": d.getSeconds(),    
			            "q+": Math.floor((d.getMonth() + 3) / 3),    
			            "S": d.getMilliseconds()    
			        };
			        if (/(y+)/.test(format)) {    
			            format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));    
			        }    
			        for (var k in o) {    
			            if (new RegExp("(" + k + ")").test(format)) {    
			                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));    
			            }    
			        }    
			        return format;
			};
			
			var defaultPattern = {
					'Full' : 'yyyy-MM-dd hh:mm:ss',
					'Short' : 'yyyy-MM-dd'
			};
			
			var pattern = format == null ? defaultPattern.Full : defaultPattern[format];
			if(pattern == null) pattern = format;
			
			var d;
			console.info(value.constructor);
			if(value.constructor == Number)
				d = new Date(value);
			else
				d = value;
			return formatDate(d, pattern);
		}
	});
})(jQuery);