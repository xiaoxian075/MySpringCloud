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
          <el-input v-model="selectParam.tradeOdd" placeholder="流水号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="selectParam.orderOdd" placeholder="订单号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="selectParam.payAccount" placeholder="支付账号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-input v-model="selectParam.receiveAccount" placeholder="收款账号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-select v-model="selectParam.payWay" clearable placeholder="支付方式">
            <el-option
              v-for="item in paywayOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="selectParam.act" clearable placeholder="账单类目">
            <el-option
              v-for="item in actOptions"
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
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="on_batch_select"
        style="width: 100%;">
        <el-table-column
          prop="tradeOdd"
          label="流水号"
          width="250">
        </el-table-column>
        <el-table-column
          prop="orderOdd"
          label="订单号"
          width="200">
        </el-table-column>
        <el-table-column
          prop="payWay"
          label="支付方式"
          width="100">
          <template scope="props">
            <span v-text="showWay(props.row.payWay)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="act"
          label="账单类目"
          width="100">
          <template scope="props">
            <span v-text="showAct(props.row.act)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="orderType"
          label="订单类型"
          width="100">
          <template scope="props">
            <span v-text="showOrderType(props.row.orderType)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="payAccount"
          label="支付账号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="receiveAccount"
          label="收款账号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="amount"
          label="金额"
          width="100">
        </el-table-column>
        <el-table-column
          prop="tradeTime"
          label="交易时间"
          width="150">
        </el-table-column>
        <!--<el-table-column-->
          <!--label="操作"-->
          <!--width="180">-->
          <!--<template scope="props">-->
            <!--<el-button type="info" icon="edit" size="small" @click="edit_data_show(props.row)">修改</el-button>-->
            <!--<el-button type="danger" size="small" icon="delete" @click="delete_data(props.row)">删除</el-button>-->
          <!--</template>-->
        <!--</el-table-column>-->
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
        table_data: null,
        //当前页码
        page: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        size: 15,
        //请求时的loading效果
        load_data: true,
        //批量选择数组
        batch_select: [],

        selectParam: {
          tradeOdd: '',
          orderOdd: '',
          payAccount: '',
          receiveAccount: '',
          payWay: '',
          act: ''
        },

        paywayOptions: [{
          id: 1,
          name: '余额'
        },{
          id: 2,
          name: '云付通'
        }],

        actOptions: [{
          id: 6,
          name: '购物'
        },{
          id: 3,
          name: '充值'
        }],
        ordertypeOptions: [{
          id: 1,
          name: '单笔支付'
        },{
          id: 2,
          name: '多笔合购'
        }],

      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.getTableData();
    },
    methods: {
      //获取数据
      getTableData(){
        let param = {
          tradeOdd: this.selectParam.tradeOdd,
          orderOdd: this.selectParam.orderOdd,
          payAccount: this.selectParam.payAccount,
          receiveAccount: this.selectParam.receiveAccount,
          payWay: Number(this.selectParam.payWay),
          act: Number(this.selectParam.act),
          page: this.page,
          size: this.size
        }
        this.load_data = true;
        communication.httpPost(communication.PAY_RECORD_LIST, param)
          .then(({data}) => {
            this.table_data = data.list
            this.page = data.page
            this.total = data.total
            this.load_data = false
          })
          .catch(({code, msg}) => {
            this.load_data = false;
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.page = val;
        this.getTableData();
      },

      showWay(state) {
        let index = 0;
        let len = this.paywayOptions.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.paywayOptions[index].id);
          if (vv == state) {
            return this.paywayOptions[index].name;
          }
        }
        return '';
      },

      showAct(state) {
        let index = 0;
        let len = this.actOptions.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.actOptions[index].id);
          if (vv == state) {
            return this.actOptions[index].name;
          }
        }
        return '';
      },


      showOrderType(state) {
        let index = 0;
        let len = this.ordertypeOptions.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.ordertypeOptions[index].id);
          if (vv == state) {
            return this.ordertypeOptions[index].name;
          }
        }
        return '';
      },
    }
  }
</script>
