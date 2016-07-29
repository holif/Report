<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>职位分析统计</title>
<meta name="Keywords" content="">
<meta name="description" content="">

<!-- js,css -->
<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}

body {
	background: url("images/bg.jpg") no-repeat top center;
	font-size: 12px;
	font-family: "微软雅黑";
	color: #666;
}
/*report start*/
.report {
	width: 630px;
	height: 100px;
	margin: 30px auto;
}

.report h1 {
	text-align: center;
	color: #fff;
	line-height: 60px;
}

.report .r_box .r_select {
	width: 70px;
	height: 37px;
	padding-left: 10px;
	outline: none;
}

.report .r_box .r_txt {
	width: 440px;
	height: 36px;
	border: none;
	line-height: 36px;
	padding-left: 10px;
	outline: none;
}

.report .r_box .r_btn {
	width: 110px;
	height: 36px;
	background: #3CBD38;
	border: none;
	font-size: 14px;
	font-family: "微软雅黑";
	color: #fff;
	font-weight: bold;
	cursor: pointer;
	border-radius: 0px 3px 3px 0px;
	outline: none;
}

.report .r_box .r_btn:hover {
	background: #34A431
}
/*end report*/

/*chart start*/
.chart {
	text-align: center;
	font-size: 16px;
	color: #fff;
}

.chart #text {
	font-size: 18px;
	text-align: center;
}

.chart #main1 {
	width: 1050px;
	height: 400px;
	text-align: center;
	margin-left: 150px;
}

.chart #main2 {
	width: 400px;
	height: 400px;
	text-align: center;
	margin-left: 150px;
	float: left;
}

.chart #main3 {
	width: 400px;
	height: 400px;
	text-align: center;
	margin-left: 150px;
	float: left;
}
/*end chart*/
</style>
<script type="text/javascript" src="js/jquery-1.9.0.min.js"></script>
<script src="js/echarts.common.min.js"></script>

</head>
<body>	
	<!--report start-->
	<div class="report">
		<h1>职位分析统计</h1>
		<div class="r_box">
			<select class="r_select">
				<option value="bj">北京</option>
				<option value="sh">上海</option>
				<option value="gz">广州</option>
				<option value="sz">深圳</option>
				<option value="nj">南京</option>
				<option value="hz">杭州</option>
				<option value="hf">合肥</option>
				<option value="tj">天津</option>
				<option value="cd">成都</option>
				<option value="su">苏州</option>
				<option value="wh">武汉</option>
				<option value="fz">福州</option>
				<option value="xa">西安</option>
				<option value="wuhu">芜湖</option>
				<option value="zz">郑州</option>
				<option value="nb">宁波</option>
				<option value="xm">厦门</option>
			</select><input type="text" class="r_txt" placeholder="如:Java工程师" /><input type="button" value="开始统计"
				class="r_btn btn-default btn-white btn-active" onclick="start()" />
		</div>
	</div>
	<!--end report-->

	<!--chart start-->
	<div class="chart">
		<div id="text">
			共计：47个公司136个职位<br>
		</div>
		<div id="main1"></div>
		<div id="main2"></div>
		<div id="main3"></div>
	</div>
	<!--end chart-->

	<script type="text/javascript">
		function ready(){
                
            }
		function start() {
			var city = $(".r_select").val();
			var key = $(".r_txt").val();
			var posturl = "";
			if ('*' !== key && key !== null && key !== undefined && key !== '') {				
				posturl = "http://" + city + ".58.com/job/?key=" + key+"&filter=free";
				posturl = encodeURI(posturl);
			} else {
				alert("请输入正确的职位！");
				return;
			}
			getdata(posturl);
			
		}

		var myChart = echarts.init(document.getElementById('main1'));
		var lbs = new Array();
		lbs[0] = '面议';
		lbs[1] = '2000-3000';
		lbs[2] = '3000-5000';
		lbs[3] = '5000-8000';
		lbs[4] = '8000-12000';
		lbs[5] = '12000-20000';
		lbs[6] = '20000-25000';
		var datas = new Array();
		datas[0] = 21;
		datas[1] = 3;
		datas[2] = 10;
		datas[3] = 9;
		datas[4] = 3;
		datas[5] = 2;
		var option = {
			title : {
				text : '薪资分布'
			},
			tooltip : {},
			legend : {
				data : [ '薪水' ]
			},
			xAxis : {
				data : lbs
			},
			yAxis : {},
			series : [ {
				name : '薪水',
				type : 'bar',
				data : datas
			} ]
		};
		myChart.setOption(option);

		var eduObj = [ {
			value : 13,
			name : '不限'
		}, {
			value : 2,
			name : '中专'
		}, {
			value : 5,
			name : '高中'
		}, {
			value : 19,
			name : '大专'
		}, {
			value : 8,
			name : '本科'
		} ]
		var eduArray = [ '不限', '中专', '高中', '大专', '本科' ];
		var myChart2 = echarts.init(document.getElementById('main3'));
		var option1 = {
			title : {
				text : '最低学历要求',
				subtext : '数据来自58同城',
				x : 'center'
			},
			tooltip : {
				trigger : 'item',
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				data : eduArray
			},
			series : [ {
				name : '学历',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : eduObj,
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					}
				}
			} ]
		};
		myChart2.setOption(option1);

		var workExp = [ '不限', '1-2年', '3-5年', '6-7年' ];
		var workExpNum = [ 23, 12, 11, 1 ];
		var myChart3 = echarts.init(document.getElementById('main2'));
		var option2 = {
			title : {
				text : '工作经验要求',
				subtext : '数据来自58同城'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '2011年' ]
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			xAxis : [ {
				type : 'value',
				boundaryGap : [ 0, 0.01 ]
			} ],
			yAxis : [ {
				type : 'category',
				data : workExp
			} ],
			series : [ {
				name : '工作经验',
				type : 'bar',
				data : workExpNum
			} ]
		};
		myChart3.setOption(option2);

		function getdata(posturl) {

			$.ajax({
				//客户端向服务器发送请求时采取的方式
				type : "post",

				cache : false,

				//服务器返回的数据类型，可选 XML, json, jsonp, script, html, text。
				dataType : 'json',

				//指明客户端要向哪个页面里面的哪个方法发送请求
				url : "getdata.do",

				data : {
					url : posturl
				},
				complete:function(){},
				//客户端调用服务器端方法成功后执行的回调函数
				success : function(data) {
					//alert(data);
					if(data === null || data === undefined || data === ""){
						alert("未找到该工作的招聘信息。。。");
						return;
					}
					var lbs = new Array();
					var datas = new Array();
					data = eval(data);
					var companyNum = data[0]["companyNum"];
					var workerNum = data[0]["workerNum"];

					lbs[0] = '面议';
					lbs[1] = '2000-3000';
					lbs[2] = '3000-5000';
					lbs[3] = '5000-8000';
					lbs[4] = '8000-12000';
					lbs[5] = '12000-20000';
					lbs[6] = '20000-25000';

					if (data[0]["salary"]["my"] == null)
						datas[0] = 0;
					else
						datas[0] = parseInt(data[0]["salary"]["my"]);
					if (data[0]["salary"]["2000-3000"] == null)
						datas[1] = 0;
					else
						datas[1] = parseInt(data[0]["salary"]["2000-3000"]);
					if (data[0]["salary"]["3000-5000"] == null)
						datas[2] = 0;
					else
						datas[2] = parseInt(data[0]["salary"]["3000-5000"]);
					if (data[0]["salary"]["5000-8000"] == null)
						datas[3] = 0;
					else
						datas[3] = parseInt(data[0]["salary"]["5000-8000"]);
					if (data[0]["salary"]["8000-12000"] == null)
						datas[4] = 0;
					else
						datas[4] = parseInt(data[0]["salary"]["8000-12000"]);
					if (data[0]["salary"]["12000-20000"] == null)
						datas[5] = 0;
					else
						datas[5] = parseInt(data[0]["salary"]["12000-20000"]);
					if (data[0]["salary"]["20000-25000"] == null)
						datas[6] = 0;
					else
						datas[6] = parseInt(data[0]["salary"]["20000-25000"]);
					$("#text").html(
							"共计：" + companyNum + "个公司" + workerNum + "个职位");

					var myChart = echarts
							.init(document.getElementById('main1'));
					var option = {
						title : {
							text : '薪资分布'
						},
						tooltip : {},
						legend : {
							data : [ '薪水' ]
						},
						xAxis : {
							data : lbs
						},
						yAxis : {},
						series : [ {
							name : '薪水',
							type : 'bar',
							data : datas
						} ]
					};
					myChart.setOption(option);
					var edunum = new Array();
					if (data[0]["educa"]["bx"] != null)
						edunum[0] = parseInt(data[0]["educa"]["bx"]);
					else
						edunum[0] = 0;

					if (data[0]["educa"]["zz"] != null)
						edunum[1] = parseInt(data[0]["educa"]["zz"]);
					else
						edunum[1] = 0;

					if (data[0]["educa"]["gz"] != null)
						edunum[2] = parseInt(data[0]["educa"]["gz"]);
					else
						edunum[2] = 0;

					if (data[0]["educa"]["dz"] != null)
						edunum[3] = parseInt(data[0]["educa"]["dz"]);
					else
						edunum[3] = 0;

					if (data[0]["educa"]["bk"] != null)
						edunum[4] = parseInt(data[0]["educa"]["bk"]);
					else
						edunum[4] = 0;
					if (data[0]["educa"]["ss"] != null)
						edunum[5] = parseInt(data[0]["educa"]["ss"]);
					else
						edunum[5] = 0;
					var eduObj = [ {
						value : edunum[0],
						name : '不限'
					}, {
						value : edunum[1],
						name : '中专'
					}, {
						value : edunum[2],
						name : '高中'
					}, {
						value : edunum[3],
						name : '大专'
					}, {
						value : edunum[4],
						name : '本科'
					}, {
						value : edunum[5],
						name : '硕士'
					} ]
					var eduArray = [ '不限', '中专', '高中', '大专', '本科', '硕士' ];
					var myChart2 = echarts.init(document
							.getElementById('main3'));
					var option1 = {
						title : {
							text : '最低学历要求',
							subtext : '数据来自58同城',
							x : 'center'
						},
						tooltip : {
							trigger : 'item',
							formatter : "{a} <br/>{b} : {c} ({d}%)"
						},
						legend : {
							orient : 'vertical',
							left : 'left',
							data : eduArray
						},
						series : [ {
							name : '学历',
							type : 'pie',
							radius : '55%',
							center : [ '50%', '60%' ],
							data : eduObj,
							itemStyle : {
								emphasis : {
									shadowBlur : 10,
									shadowOffsetX : 0,
									shadowColor : 'rgba(0, 0, 0, 0.5)'
								}
							}
						} ]
					};
					myChart2.setOption(option1);

					var workExp = [ '不限', '1-2年', '3-5年', '6-7年' ];
					var work = new Array();
					if (data[0]["workExp"]["bx"] != null)
						work[0] = parseInt(data[0]["workExp"]["bx"]);
					else
						work[0] = 0;
					if (data[0]["workExp"]["1-2"] != null)
						work[1] = parseInt(data[0]["workExp"]["1-2"]);
					else
						work[1] = 0;
					if (data[0]["workExp"]["3-5"] != null)
						work[2] = parseInt(data[0]["workExp"]["3-5"]);
					else
						work[2] = 0;
					if (data[0]["workExp"]["6-7"] != null)
						work[3] = parseInt(data[0]["workExp"]["6-7"]);
					else
						work[3] = 0;
					var workExpNum = [ work[0], work[1], work[2], work[3] ];
					var myChart3 = echarts.init(document
							.getElementById('main2'));
					var option2 = {
						title : {
							text : '工作经验要求',
							subtext : '数据来自58同城'
						},
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : [ '2011年', '2012年' ]
						},
						grid : {
							left : '3%',
							right : '4%',
							bottom : '3%',
							containLabel : true
						},
						xAxis : [ {
							type : 'value',
							boundaryGap : [ 0, 0.01 ]
						} ],
						yAxis : [ {
							type : 'category',
							data : workExp
						} ],
						series : [ {
							name : '工作经验',
							type : 'bar',
							data : workExpNum
						} ]
					};
					myChart3.setOption(option2);
				}
			})
		}
		
	</script>
</body>
</html>