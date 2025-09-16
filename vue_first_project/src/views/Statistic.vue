<template>
  <el-row>
    <el-col :span="12">
      <div id="pie"  class="card" style="width: auto;height:500px;"></div>
    </el-col>

    <el-col :span="12">
      <div id="col"  class="card" style="width: auto;height:500px;"></div>
    </el-col>

    <el-col :span="12">
      <div id="line"  class="card" style="width: auto;height:500px;"></div>
    </el-col>

    <el-col :span="12">
      <div id="ageBar"  class="card" style="width: auto;height:500px;"></div>
    </el-col>

  </el-row>
</template>


<style>

.card{
  border-radius: 5px;
  color: white;
  background-color: #ffffff;
  border: 2px solid ; /* 透明边框占位 */
  align-items: center;
  padding: 10px;
  box-shadow:0 0 10px rgba(0,0,0,0.25);
  margin: 20px;
  margin-top: 10px;
}
/*

.editor-content-view {
  border: 3px solid #ccc;
  border-radius: 5px;
  padding: 0 10px;
  margin-top: 20px;
  overflow-x: auto;
}

*/
.editor-content-view {
  border-radius: 5px;
  padding: 0 10px;
  margin-top: 20px;
  overflow-x: auto;
}

.editor-content-view p,
.editor-content-view li {
  white-space: pre-wrap; /* 保留空格 */
}

.editor-content-view blockquote {
  border-left: 8px solid #d0e5f2;
  padding: 10px 10px;
  margin: 10px 0;
  background-color: #f1f1f1;
}

.editor-content-view code {
  font-family: monospace;
  background-color: #eee;
  padding: 3px;
  border-radius: 3px;
}
.editor-content-view pre>code {
  display: block;
  padding: 10px;
}

.editor-content-view table {
  border-collapse: collapse;
}
.editor-content-view td,
.editor-content-view th {
  border: 1px solid #ccc;
  min-width: 50px;
  height: 20px;
}
.editor-content-view th {
  background-color: #f1f1f1;
}

.editor-content-view ul,
.editor-content-view ol {
  padding-left: 20px;
}

.editor-content-view input[type="checkbox"] {
  margin-right: 5px;
}

</style>


<script setup>


import '@wangeditor/editor/dist/css/style.css' // 引入 css
import '../assets/wangEditor.css'
import request, {batchDel, get} from "@/utils/reques.js";
import * as echarts from 'echarts';


import {onBeforeUnmount, ref, shallowRef, onMounted, reactive} from 'vue'



// // 模拟 ajax 异步获取内容
// onMounted(() => {
//   reqData()
// })


const data=reactive({
  xData:[],
  seriesData:[],
  pieData:[],
  colData:[],
  colDataRatio:[],
  lineData:{
    years:[],
    counts:[]
  },
  ageBarData:{
    groupName:[],
    counts:[]
  }
})
// get("api/usrCount"),
// get("api/prjCount")
const reqData=()=> {

  const requests = [
    get("api/dpt-count"),
    get("api/salary-distribution"),
    get("api/hire-stats-by-year"),
    get("api/age-statistic")
  ];

  Promise.all(requests).then(([detCountRes,salaryDistriRes,hireRes,ageRes]) => {
    data.pieData = transformData(detCountRes, [
      {oldKey: 'departmentName', newKey: 'name'},
      {oldKey: 'count', newKey: 'value'}
    ]);
    const {
      above_2w_count,
      below_1w_count,
      between_1_2w_count,
      above_2w_ratio,
      between_1_2w_ratio,
      below_1w_ratio
    } = salaryDistriRes;

    data.colData.push(above_2w_count, below_1w_count, between_1_2w_count);
    data.colDataRatio.push(above_2w_ratio,between_1_2w_ratio,below_1w_ratio)

    // console.log(data.colData)
    // console.log(ageRes)
    hireRes.forEach(item=>{
      data.lineData.years.push(item.year)
      data.lineData.counts.push(item.hire_count)
    })


    ageRes.forEach(item=>{
      data.ageBarData.groupName.push(item.age_group)
      data.ageBarData.counts.push(item.counts)
    })



  }).catch(res => {
    console.log("---",res)
  }).finally(() => {
    initEChart()
  })
}

  // get("api/dptCount").then(res => {
  //
  //   // 使用示例
  //   data.pieData = transformData(res, [
  //     {oldKey: 'departmentName', newKey: 'name'},
  //     {oldKey: 'count', newKey: 'value'}
  //   ]);
  //
  // }).catch(res => {
  //   console.log(res)
  // }).finally(
  //     () => {
  //       initEChart()
  //     }




reqData()


const transformData = (data, keyMap) => {
  return data.map(item => {
    const newItem = {};
    keyMap.forEach(({ oldKey, newKey }) => {
      newItem[newKey] = item[oldKey];
    });
    return newItem;
  });
};



const initEChart=()=>{
  // 基于准备好的dom，初始化echarts实例
  let pieChart = echarts.init(document.getElementById('pie'));

// 绘制图表
  pieChart.setOption({
    title: {
      text: '员工人数饼状图',
      subtext: 'Fake Data',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '部门',
        type: 'pie',
        radius: '50%',
        data: data.pieData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  });

  let colChart=echarts.init(document.getElementById('col'));


  colChart.setOption({
    title: {
      text: '工资直方图'
    },
    tooltip: {},
    xAxis: {
      data: ['低于10000', '10000~20000', '高于20000']
    },
    yAxis: {},
    series: [
      {
        name: '工资',
        type: 'bar',
        data: data.colData,
        itemStyle: {
          color: function(params) { // 支持动态颜色（可选）
            // 方式1：固定颜色数组（推荐）
            const colors = ['#5861AC', '#FEA040', '#6AB4C1']; // 自定义颜色（粉色、青绿色、蓝绿色）
            return colors[params.dataIndex]; // 按索引取色
          }
        },
      }
    ],
    label: {
      show: true, // 显示标签
      position: 'top', // 标签位置（顶部，可根据需求调整）
      formatter: function(params) {
        return data.colDataRatio[params.dataIndex] ; // 直接拼接 % 符号
      }
    }
  });

  const lineChart=echarts.init(document.getElementById('line'));

  lineChart.setOption({
    title: {
      text: '年入职人数折线图'
    },
    tooltip: {},
    xAxis: {
      data: data.lineData.years
    },
    yAxis: {},
    series: [
      {
        smooth: true, // 启用平滑
        name: '人数',
        type: 'line',
        data: data.lineData.counts
      }
    ]
  });





  const ageBarChart=echarts.init(document.getElementById('ageBar'));



  // 假设原始数据
  const total = data.ageBarData.counts.reduce((sum, count) => sum + count, 0); // 计算总人数

// 生成百分比数据（保留两位小数，如 "25.00%"）
  const percentageData = data.ageBarData.counts.map(count => {
    const percent = ((count / total) * 100).toFixed(2); // 转换为百分比并保留两位小数
    return `${percent}%`; // 格式化为 "xx.xx%"
  });


  ageBarChart.setOption({
    title: {
      text: '员工年龄直方图'
    },
    tooltip: {},
    xAxis: {
      data: data.ageBarData.groupName
    },
    yAxis: {},
    series: [
      {
        smooth: true, // 启用平滑
        name: '人数',
        type: 'bar',
        data: data.ageBarData.counts,
        itemStyle: {
          color: function(params) { // 支持动态颜色（可选）
            // 方式1：固定颜色数组（推荐）
            const colors = ['#5861AC', '#FEA040', '#6AB4C1','#F28080']; // 自定义颜色（粉色、青绿色、蓝绿色）
            return colors[params.dataIndex]; // 按索引取色
          }
        },
        label: {
          show: true,
          position: 'top',
          formatter: function(params) {
            return percentageData[params.dataIndex]; // 通过 this.dataIndex 获取当前索引
          },
          color: '#333',
          fontSize: 12
        }
      }
    ]
  });

}


</script>