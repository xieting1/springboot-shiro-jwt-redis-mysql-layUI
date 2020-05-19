function getParamJosn() {
    var param = getFormJson($('.searchForm').serializeArray());
    console.log(totalCount)
    param["limit"] = totalCount;
    param["page"] = 1;
    return param;
}

function resetDefaultLayuiExport(config) {
    resetLayuiExport(config.url,config.title);
}


function resetLayuiExport(url, excelName, paramJson = {}) {
    $('.layui-table-tool div[title="导出"]').click(function () {
        $(this).attr('lay-event', 'exportExcel');
        var load = showLoad("数据导出中");
        var paramJson = paramJson || getParamJosn();
        ajax(url,paramJson , function (data) {
            outExcel(data["records"], excelName + dateFormat("yyyy-MM-dd", new Date()));
            layer.msg('导出成功');
            closeLoad(load);
        }, function () {
            closeLoad(load);
            layer.msg('网络异常');
        });
    });
}

function outExcel(dataDto, name) {
    layui.config({
        base: 'static/manage/layuiadmin/modules/',
    }).extend({
        excel: 'excel',
    });
    layui.use(['excel'], function () {
        var excel = layui.excel;
        var arrFieldName = new Array();
        var arrFieldValue = new Array();
        var arryTh = $('th');
        var reg = /^[a-z_A-Z]+$/;
        for (var i = 0; i < arryTh.length; i++) {
            var field = arryTh.eq(i).attr("data-field");
            if (reg.test(field)) {
                var title = arryTh[i].innerText.replace(/[\r\n]/g, "");
                if (title) {
                    arrFieldName.push(title);
                    arrFieldValue.push(field);
                }
            }
        }
        var arrDto = new Array();
        arrDto.push(arrFieldName);
        $.each(dataDto, function (i, dto) {
            var oneData = new Array();
            for (var i = 0; i < arrFieldValue.length; i++) {
                var f = arrFieldValue[i];
                var d = dto[f];
                // customerRecordList.html 特殊处理卡申请状态
                if(f == 'APPLY_TYPE'){
                    d =  DISTRIBUTOR.applyDesc[d]
                }
                if (d === undefined || d === null){d = "";}
                oneData.push(d);
            }
            arrDto.push(oneData);
        });
        excel.exportExcel({ sheet1: arrDto }, name + '.xlsx', 'xlsx');
    });
    return false;
}

function showLoad(msg) {

    return layer.msg(msg, {
        icon: 16,
        shade: [0.5, '#f5f5f5'],
        scrollbar: false,
        offset: 'auto',
        time: 100000
    });

}

function closeLoad(index) {
    layer.close(index);
}

