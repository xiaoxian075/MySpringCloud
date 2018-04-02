<template>

  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="getTableData" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <div class="panel-body">
      <el-row :gutter="20">
        <el-col :span="3">
          <el-select v-model="selectParam.state" clearable placeholder="提醒状态">
            <el-option
              v-for="item in remindState"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="info" size="big" icon="small" @click="getTableData">查询</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="tableData"
        v-loading="loadData"
        element-loading-text="拼命加载中"
        border
        @selection-change="onBatchSelect"
        style="width: 100%;">
        <el-table-column
          prop="id"
          label="编号"
          width="80">
        </el-table-column>
        <el-table-column
          prop="account"
          label="账号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="odd"
          label="订单号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="orderState"
          label="订单状态"
          width="100">
          <template scope="props">
            <span v-text="showState(props.row.orderState)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsPrice"
          label="商品价格"
          width="100">
        </el-table-column>
        <el-table-column
          prop="expPrice"
          label="运费"
          width="100">
        </el-table-column>
        <el-table-column
          prop="orderTime"
          label="订单产生时间"
          width="200">
          <template scope="props">
            <span v-text="showTime(props.row.orderTime)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="200">
          <template scope="props">
            <span v-text="showTime(props.row.createTime)"></span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="250">
          <template scope="props">
<!--            <el-button type="info" icon="edit" size="small" @click="detailShow(props.row)">详情</el-button>-->
            <el-button type="danger" size="small" icon="delete" @click="ignoreShow(props.row)">忽略</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <div slot="page">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="page"
            :page-size="size"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </bottom-tool-bar>
    </div>
  </div>


</template>
<script type="text/javascript">
  import {panelTitle, bottomToolBar} from 'components'
  import * as communication from '../../common/service/communication'

  export default{
    data(){
      return {
        tableData: null,
        //当前页码
        page: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        size: 15,
        //请求时的loading效果
        loadData: true,

        selectParam: {
          state: 1
        },

        formLabelWidth: '120px',
        dialogForm: {
          odd: '',
          code: '',
          expOdd: ''
        },
        dialogFormVisible: false,

        remindState:[{
          id: 0,
          name: '全部'
        },{
          id: 1,
          name: '提醒发货'
        },{
          id: 2,
          name: '已处理发货'
        }],
        orderState: [{
          id: 1,
          name: '待付款'
        },{
          id: 2,
          name: '待发货'
        },{
          id: 3,
          name: '待收货'
        },{
          id: 4,
          name: '待评价'
        },{
          id: 5,
          name: '完成'
        },{
          id: 6,
          name: '取消订单'
        },{
          id: 7,
          name: '失效'
        },{
          id: 8,
          name: '异常'
        }]
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    methods: {
      //获取数据
      getTableData(){
        let param = {
          state: Number(this.selectParam.state),
          page: this.page,
          size: this.size
        };
        this.loadData = true;
        communication.httpPost(communication.REMIND_LIST, param)
          .then(({data}) => {
            this.tableData = data.list;
            this.page = data.page;
            this.total = data.total;
            this.loadData = false;
          })
          .catch(({code, msg}) => {
            this.loadData = false;
          })
      },

      //页码选择
      handleCurrentChange(val) {
        this.page = val;
        this.getTableData();
      },

      ignoreShow(row) {
        this.$confirm('忽略客户的发货提醒, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loadData = true;
          communication.httpPost(communication.REMIND_IGNORE, {id: row.id})
            .then(({data}) => {
              this.getTableData();
              this.$message.success("忽略成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        })
          .catch(() => {
          })
      },

      showState(state) {
        let index = 0;
        let len = this.orderState.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.orderState[index].id);
          if (vv == state) {
            return this.orderState[index].name;
          }
        }
        return '';
      },

      showTime(data) {
        if (data == 0) {
          return '';
        } else {
          return new Date(data).toLocaleString();
        }
        //return formatDate(date, "yyyy-MM-dd hh:mm");
      },

//      detailShow(row) {
//        this.commodityTableData = row.goodsInfo;
//        this.dialogCommodityVisible = true;
//      }
    },
    computed: {

    },
    created(){
      this.getTableData();
    }
  }
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
