(function () {
    window.upload_hostname = "http://upload-z2.qiniup.com/";
    window.upload_access_hostname = "http://static.fengniaochuangxin.com/";
    $(document).on('mouseenter', '.layui-table td > div', function () {

        var domType = $(this)[0].firstChild; //.firstChild.nodeType;
        if (domType == '' || domType == null || domType == undefined) {
            return;
        }
        if (domType.nodeType == 3) {
            if (isEllipsis($(this)[0])) {
                showHint = layer.tips($(this).html(), $(this), {tips: 1, time: 1850});
            }
        }

    });

})();


function getDoubleNumber(price){
    var p1 = price+"";
    var p = p1.split(".");
    if(p.length==1){
        return price+".00";
    }else {
        if(p[1].length==1){
            return price+"0";
        }else {
            return price;
        }
    }

}

function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return '';
}

function isBlank(data) {
    if (data === undefined || data === null) return true;
    return typeof data === 'string' && data.replace(/(^\s*)|(\s*$)/g, "").length === 0;
}

function unique(arr) {
    var hash = [];
    for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
            if (arr[i] === arr[j]) {
                ++i;
            }
        }
        hash.push(arr[i]);
    }
    return hash;
}

window.ajaxMap = {};

function resetForm(form) {
    $(form)[0].reset();
    $(form).find('[type="file"]').each(function (index, element) {
        $(element).siblings('img').attr('src', 'static/manage/image/upload_bg.png');
    });
}


function ajax(url, data, success_callback, fail_callback) {
    var key = url + '_' + JSON.stringify(data);
    if (isBlank(ajaxMap[key]) || Date.now() - ajaxMap[key] > 1000) {
        ajaxMap[key] = Date.now();
        $.ajax({
            type: "POST",
            url: url,
            headers: {
                Authentication: localStorage.authentication
            },
            data: data,
            success: function (result) {
                if (result.code === 200) {
                    if (typeof success_callback === 'function') success_callback(result.data);
                } else if (result.code === 202) {
                    toLoginPage();
                } else if (result.code === 600) {
                    layer.msg(result.message);
                    if (typeof success_callback === 'function') success_callback(result.data);
                } else {
                    layer.msg(result.message);
                    if (typeof fail_callback === 'function') fail_callback();
                }
            },
            error: function (xhr, status, error) {
                layer.msg("请求失败");
            }
        });
    }
}

function ajaxAsync(url, data,async, success_callback, fail_callback) {
    var key = url + '_' + JSON.stringify(data);
    if (isBlank(ajaxMap[key]) || Date.now() - ajaxMap[key] > 1000) {
        ajaxMap[key] = Date.now();
        $.ajax({
            type: "POST",
            url: url,
            async:async,
            headers: {
                Authentication: localStorage.authentication
            },
            data: data,
            success: function (result) {
                if (result.code === 200) {
                    if (typeof success_callback === 'function') success_callback(result.data);
                } else if (result.code === 202) {
                    toLoginPage();
                } else if (result.code === 600) {
                    layer.msg(result.message);
                    if (typeof success_callback === 'function') success_callback(result.data);
                } else {
                    layer.msg(result.message);
                    if (typeof fail_callback === 'function') fail_callback();
                }
            },
            error: function (xhr, status, error) {
                layer.msg("请求失败");
            }
        });
    }
}
function parseDataForTable(res) {
    if (res.code === 200) {
        return {
            "code": res.code,
            "msg": res.message,
            "count": res.data['total'],
            "data": res.data['records']
        };
    } else if (res.code === 202) {
        toLoginPage();
    } else {
        layer.msg(res.message);
    }
}

function encrypt_rsa(password, publicKey) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey);
    return encrypt.encrypt(sha256_digest(password));
}

function toLoginPage() {
    var companyId = localStorage.companyId;
    localStorage.removeItem("authentication");
    localStorage.removeItem("userId");
    localStorage.removeItem("userName");
    localStorage.removeItem("userType");
    localStorage.removeItem("companyId");
    localStorage.removeItem("companyName");
    var domain = document.domain;

    if(domain=='admin.cloudsvc.cn'){
        top.location.href = 'static/manage/html/entrysys.html?companyId=' + (isBlank(companyId) ? '' : companyId);
    }else if(companyId==1164452118213902338){ //农行
        top.location.href = 'static/manage/html/login_.html?companyId=' + (isBlank(companyId) ? '' : companyId);
    }else if(companyId==1139370856773091329){//中信
        top.location.href = 'static/manage/html/login_citic.html?companyId=' + (isBlank(companyId) ? '' : companyId);
    }else{ //  蜂鸟创新
        top.location.href = 'static/manage/html/login.html?companyId=' + (isBlank(companyId) ? '' : companyId);
    }
}

function openTab(name, url) {
    if (top === window) {
        window.open("/xmall/" + url);
    } else {
        top.layui.index.openTabsPage(url, name);
    }
}

/**
 * 单图片上传
 */
(function ($) {
    $.fn.extend({
        singleImageUploader: function (callback, option) {
            if (option === undefined) {
                option = {};
            }
            this.each(function () {
                $(this).on("change", function () {
                    upload(this);
                });
                var upload = function (fileInput) {
                    autoCrop(fileInput.files[0], option, function (data) {
                        callback(data);
                    });
                }
            });
        }
    });
})(jQuery);

function autoCrop(file, option, callback) {
    if (!file) {
        return;
    }
    var reader = new FileReader();
    var image = new Image();
    var canvas = document.createElement("canvas");
    var ctx = canvas.getContext('2d');
    reader.onload = function () {
        image.src = reader.result;
    };
    image.onload = function () {
        var width = image.naturalWidth;
        var height = image.naturalHeight;
        if (option.width !== undefined && option.height !== undefined) {
            width = option.width;
            height = option.height
        } else if (option.width !== undefined && option.width < image.naturalWidth) {
            width = option.width;
            height = width * image.naturalHeight / image.naturalWidth
        } else if (option.height !== undefined && option.height < image.naturalHeight) {
            height = option.height;
            width = height * image.naturalWidth / image.naturalHeight
        }
        canvas.width = width;
        canvas.height = height;
        ctx.drawImage(image, 0, 0, width, height);
        $.upload(dataURLtoBlob(canvas.toDataURL(isBlank(option.type) ? 'image/jpeg' : option.type)), callback);
    };
    reader.readAsDataURL(file);
}

function dataURLtoBlob(dataURL) {
    var array = dataURL.split(',');
    var mime = array[0].match(/:(.*?);/)[1];
    var byteString = atob(array[1]), n = byteString.length, u8array = new Uint8Array(n);
    while (n--) {
        u8array[n] = byteString.charCodeAt(n);
    }
    return new Blob([u8array], {type: mime});
}

function clearInputFile(input) {
    if (input.value) {
        try {
            input.value = '';
        } catch (err) {
        }
        if (input.value) {
            var form = document.createElement('form'), ref = input.nextSibling, p = input.parentNode;
            form.appendChild(input);
            form.reset();
            p.insertBefore(input, ref);
        }
    }
}

//用于七牛上传
window.upload_retry = 0;
$.upload = function (file, callback) {
    getUploadToken(function (token) {
        var fd = new FormData();
        fd.append('file', file);
        fd.append('token', token);
        $.ajax({
            type: 'POST',
            url: window.upload_hostname,
            dataType: "json",
            processData: false,
            contentType: false,
            data: fd,
            success: function (result) {
                window.upload_retry = 0;
                callback(window.upload_access_hostname + result.key);
            },
            error: function (xhr, status, error) {
                if (window.upload_retry < 3) {
                    window.upload_retry++;
                    sessionStorage.removeItem('upload_token');
                    $.upload(file, callback);
                    return;
                }
                layer.msg('上传失败');
            }
        });
    });
};

function getUploadToken(callback) {
    if (!isBlank(sessionStorage.upload_token)) {
        callback(sessionStorage.upload_token);
        return;
    }
    ajax('manage/common/getUploadToken', null, function (token) {
        sessionStorage.upload_token = token;
        callback(sessionStorage.upload_token);
    });
}

function showPositionPicker(callback) {
    layer.open({
        type: 2,
        title: '选取位置',
        shadeClose: true,
        shade: 0.8,
        area: ['800px', '80%'],
        content: 'https://apis.map.qq.com/tools/locpicker?search=1&type=1&key=MGNBZ-DULYD-ZR44N-HNJVN-K3REK-Y3F2C&referer=myapp'
    });
    window.addEventListener('message', function (event) {
        var loc = event.data;
        if (loc && loc.module == 'locationPicker') {
            console.log('location', loc);
            callback(loc.latlng.lat, loc.latlng.lng);
        }
    }, false);
}

//将form中的值转换为键值对。
function getFormJson(a) {
    var o = {};
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });

    return o;
}

function isEllipsis(dom) {
    var checkDom = dom.cloneNode(), parent, flag;
    checkDom.style.width = dom.offsetWidth + 'px';
    checkDom.style.height = dom.offsetHeight + 'px';
    checkDom.style.overflow = 'auto';
    checkDom.style.position = 'absolute';
    checkDom.style.zIndex = -1;
    checkDom.style.opacity = 0;
    checkDom.style.whiteSpace = "nowrap";
    checkDom.innerHTML = dom.innerHTML;

    parent = dom.parentNode;
    parent.appendChild(checkDom);
    flag = checkDom.scrollWidth > checkDom.offsetWidth;
    parent.removeChild(checkDom);
    return flag;
};

//2019-03-26 15:58:17
function formatToTimestamp(dateStr) {
    dateStr = dateStr.substring(0, 19);
    dateStr = dateStr.replace(/-/g, '/');
    return new Date(dateStr).getTime();
}

/**************************************时间格式化处理************************************/
function dateFormat(fmt, date) {
    var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 公用字符串拼接对象
 */
function StringBuffer(str){
    var arr = [];
    str = str || "";
    arr.push(str);
    this.append = function(str1)
    {
        arr.push(str1);
        return this;
    };
    this.toString = function()
    {
        return arr.join("");
    };
}


function moneyFormat(number, decimals, dec_point, thousands_sep) {
　　/*
　　 * 参数说明：
　　 * number：要格式化的数字
　　 * decimals：保留几位小数
　　 * dec_point：小数点符号
　　 * thousands_sep：千分位符号
　　 * */
　　 number = (number + '').replace(/[^0-9+-Ee.]/g, '');
　　 var n = !isFinite(+number) ? 0 : +number,
　　 prec = !isFinite(+decimals) ? 2 : Math.abs(decimals),
 　　sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
 　　dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
 　　s = '',
　　 toFixedFix = function(n, prec) {
 　　　　var k = Math.pow(10, prec);
 　　　　return '' + Math.ceil(n * k) / k;
 　　};

 　　s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
　　 var re = /(-?\d+)(\d{3})/;
　　 while(re.test(s[0])) {
 　　　　s[0] = s[0].replace(re, "$1" + sep + "$2");
 　　}

 　　if((s[1] || '').length < prec) {
    　　 s[1] = s[1] || '';
     　　s[1] += new Array(prec - s[1].length + 1).join('0');
 　　}
  　　　　return s.join(dec);
 }

 function restoreMoney(e) {  
    return parseFloat(e.replace(/[^\d\.-]/g, ""));  
} 