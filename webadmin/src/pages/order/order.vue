<template>

  <div class="panel">
    <el-dialog :title="添加快递信息" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="订单号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.odd" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="快递公司" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.code" placeholder="请选择">
            <el-option
              v-for="item in kd100"
              :key="item.code"
              :label="item.name"
              :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="物流单号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.expOdd" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="sendGoods()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="购买详情" :visible.sync="dialogCommodityVisible">
      <el-table
        :data="commodityTableData"
        style="width: 100%">
        <el-table-column
          prop="commudityId"
          label="商品ID"
          width="180">
        </el-table-column>
        <el-table-column
          prop="title"
          label="名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="attrName"
          label="属性">
        </el-table-column>
        <el-table-column
          prop="shopName"
          label="店铺">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格">
        </el-table-column>
        <el-table-column
          prop="num"
          label="数量">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCommodityVisible = false">返 回</el-button>
      </div>
    </el-dialog>

    <panel-title :title="$route.meta.title">
      <el-button @click.stop="getTableData" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
    </panel-title>
    <div class="panel-body">
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input v-model="selectParam.account" placeholder="账号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="selectParam.odd" placeholder="订单号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="selectParam.state" clearable placeholder="订单状态">
            <el-option
              v-for="item in orderState"
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
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="id"
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
          prop="state"
          label="订单状态"
          width="100">
          <template scope="props">
            <span v-text="showState(props.row.state)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsPrice"
          label="商品价格"
          width="80">
        </el-table-column>
        <el-table-column
          prop="expPrice"
          label="运费"
          width="80">
        </el-table-column>
        <el-table-column
          prop="lastPay"
          label="截止付款时间"
          width="200">
          <template scope="props">
            <span v-text="showTime(props.row.lastPay)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="payTime"
          label="支付时间"
          width="200">
          <template scope="props">
            <span v-text="showTime(props.row.payTime)"></span>
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
            <el-button type="info" icon="edit" size="small" @click="detailShow(props.row)">详情</el-button>
            <el-button v-if="props.row.state==2" type="info" icon="edit" size="small" @click="dialogShow(props.row)">发货</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <!--<el-button-->
          <!--type="danger"-->
          <!--icon="delete"-->
          <!--size="small"-->
          <!--:disabled="batchSelect.length === 0"-->
          <!--@click="onBatchDel"-->
          <!--slot="handler">-->
          <!--<span>批量删除</span>-->
        <!--</el-button>-->
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
            account: '',
            odd: '',
            state: ''
        },

        formLabelWidth: '120px',
        dialogForm: {
          odd: '',
          code: '',
          expOdd: ''
        },
        dialogFormVisible: false,

        dialogCommodityVisible: false,
        commodityTableData: [],


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
        }],
        kd100:[]
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
          account: this.selectParam.account,
          odd: this.selectParam.odd,
          state: Number(this.selectParam.state),
          page: this.page,
          size: this.size
        };
        this.loadData = true;
        communication.httpPost(communication.ORDER_LIST, param)
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

      dialogShow(row) {
        if (row == undefined) {
          this.dialogForm.odd = '';
          this.dialogForm.code = '';
          this.dialogForm.expOdd = '';
          this.dialogFormVisible = true;
        } else {
          this.dialogForm.odd = row.odd;
          this.dialogForm.code = '';
          this.dialogForm.expOdd = '';
          this.dialogFormVisible = true;
        }
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


      sendGoods() {
        let param = {
            orderOdd: this.dialogForm.odd,
            code: this.dialogForm.code,
            expOdd: this.dialogForm.expOdd
        }
        communication.httpPost(communication.ORDER_SEND_GOODS, param)
          .then(({data}) => {
            this.getTableData();
            this.dialogFormVisible = false;
            this.$message.success("发货成功");
          }).catch(({code, msg}) => {
          this.$message.error(msg);
          });
      },

      getKd100() {
        communication.httpPost(communication.KD100_GET_ALL, {})
          .then(({data}) => {
            this.kd100 = data;
          })
          .catch(({code, msg}) => {
            //this.loadData = false;
          })
      },

      detailShow(row) {
          this.commodityTableData = row.goodsInfo;
          this.dialogCommodityVisible = true;
      }
    },
    computed: {

    },
    created(){
      this.getTableData();
      this.getKd100();
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
