<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">

    </panel-title>
    <div class="panel-body">
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input v-model="selectParam.account" placeholder="账号" clearable></el-input>
        </el-col>
        <el-col :span="3">
          <el-button type="info" size="big" icon="small" @click="get_table_data">查询</el-button>
        </el-col>
      </el-row>

      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="get_table_data"
        style="width: 100%;">
        <el-table-column
          prop="id"
          label="id"
          width="120">
        </el-table-column>
        <el-table-column
          prop="account"
          label="帐号"
          width="200">
        </el-table-column>

        <el-table-column
          prop="nickName"
          label="昵称"
          width="120">
        </el-table-column>

        <!--<el-table-column-->
          <!--prop="headUrl"-->
          <!--label="头像"-->
          <!--width="300">-->
        <!--</el-table-column>-->

        <el-table-column
          prop="referee"
          label="推荐人"
          width="200">
        </el-table-column>

        <el-table-column
          prop="sex"
          label="性别"
          width="80">
          <template scope="props">
            <span v-text="showSex(props.row.sex)"></span>
          </template>
        </el-table-column>

        <el-table-column
          prop="level"
          label="等级"
          width="150">
          <template scope="props">
            <span v-text="showLev(props.row.level)"></span>
          </template>
        </el-table-column>

        <el-table-column
          prop="balance"
          label="余额"
          width="100">
        </el-table-column>

        <el-table-column
          prop="payState"
          label="支付状态"
          width="100">
          <template scope="props">
            <span v-text="showPaySta(props.row.payState)"></span>
          </template>
        </el-table-column>

        <el-table-column
          prop="accountState"
          label="帐号状态"
          width="100">
          <template scope="props">
            <span v-text="showSta(props.row.accountState)"></span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          width="300">
          <template scope="props">
            <el-button v-if="props.row.accountState==0" type="info" size="small" icon="small" @click="setState(props.row, 1)">帐号启用</el-button>
            <el-button v-else type="danger" size="small" icon="small" @click="setState(props.row, 2)">帐号禁用</el-button>

            <el-button v-if="props.row.payState==0" type="info" size="small" icon="small" @click="setState(props.row, 3)">支付启用</el-button>
            <el-button v-else type="danger" size="small" icon="small" @click="setState(props.row, 4)">支付禁用</el-button>
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

  export default {
    data() {
      return {
        table_data: null,
        //当前页码
        page: 1,
        //数据总条目
        total: 0,
        //每页显示多少条数据
        size: 5,
        //请求时的loading效果
        load_data: true,

        formLabelWidth: '120px',
        selectParam: {
            account: ''
        },

        showPara: 0,
        type: 0
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created() {
      this.get_table_data()
    },
    methods: {
      //刷新
      on_refresh() {
        this.get_table_data()
      },
      //获取数据
      get_table_data() {
        this.load_data = true;
        communication.httpPost(communication.ACCOUNT_LIST, {page: this.page, size: this.size, account: this.selectParam.account})
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
        this.page = val
        this.get_table_data()
      },
      showSta(showPara){
        if (showPara==0) return '禁用'; else return '正常';
      },
      showPaySta(showPara){
        if (showPara==1) return '可支付'; else return '不可支付';
      },
      showLev(showPara){
        if (showPara==1) return '普通会员'; else return 'VIP会员';
      },
      showSex(showPara){
        if (showPara==1) return '男'; else return '女';
      },
      setState(row, type){
        this.$confirm('是否确定此操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          communication.httpPost(communication.ACCOUNT_STATE, {id: row.id, state: type})
            .then(({data}) => {
              this.get_table_data();
              this.$message.success("操作成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        }).catch(() => {

        })
      }
    }
  }
</script>
