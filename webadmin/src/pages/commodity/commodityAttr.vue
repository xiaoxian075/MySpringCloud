<template>

  <div class="panel">

    <panel-title :title="$route.meta.title">
      <el-button @click.stop="getTableData" size="small"> <i class="fa fa-refresh"></i>  </el-button>
      <el-button type="primary" icon="plus" size="small" @click="dialogShow()">添加数据</el-button>
      <el-button @click="$router.back()">返回商品</el-button>
    </panel-title>


    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="商品ID" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.commodityId" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="属性ID" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.attrId" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="属性名称" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.attrName" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="价格" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.price" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="库存" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.stockNum" auto-complete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogData()">确 定</el-button>
      </div>
    </el-dialog>


    <div class="panel-body">
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
          prop="commodityId"
          label="商品ID"
          width="80">
        </el-table-column>
        <el-table-column
          prop="attrId"
          label="属性ID"
          width="80">
        </el-table-column>
        <el-table-column
          prop="attrName"
          label="属性名称"
          width="150">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="80">
        </el-table-column>
        <el-table-column
          prop="stockNum"
          label="库存"
          width="80">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template scope="props">
            <el-button type="info" icon="edit" size="small" @click="dialogShow(props.row)">修改</el-button>
            <el-button type="danger" size="small" icon="delete" @click="deleteData(props.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <el-button
          type="danger"
          icon="delete"
          size="small"
          :disabled="batchSelect.length === 0"
          @click="onBatchDel"
          slot="handler">
          <span>批量删除</span>
        </el-button>
        <div slot="page">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="page"
            :page-size="5"
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
        size: 5,
        //请求时的loading效果
        loadData: true,
        //批量选择数组
        batchSelect: [],

        commodityId: this.$route.params.id,

        dialogType: 1,  // 1:添加 2:编辑
        dialogName: '',
        formLabelWidth: '120px',
        dialogForm: {
          id: 0,
          commodityId: 0,
          attrId: 0,
          attrName: '',
          price: '0.00',
          stockNum: 0
        },
        dialogFormVisible: false,

      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    methods: {
      //获取数据
      getTableData(){
        this.loadData = true;
        communication.httpPost(communication.COMMUDITY_ATTR_LIST, {id: this.commodityId, page: this.page, size: this.size})
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

      //单个删除
      deleteData(item){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loadData = true;
          communication.httpPost(communication.COMMUDITY_ATTR_DEL, {id: item.id})
            .then(({data}) => {
              this.getTableData();
              this.$message.success("删除成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        })
          .catch(() => {
          })
      },
      //页码选择
      handleCurrentChange(val) {
        this.page = val;
        this.getTableData();
      },
      //批量选择
      onBatchSelect(val){
        this.batchSelect = val
      },
      //批量删除
      onBatchDel(){
        this.$confirm('此操作将批量删除选择数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loadData = true;
          var arrId = [];
          for (var i = 0; i < this.batchSelect.length; i++) {
            arrId[i] = this.batchSelect[i].id;
          }

          communication.httpPost(communication.COMMUDITY_ATTR_BATCH_DEL, {idList: arrId})
            .then(({data}) => {
              this.getTableData();
              this.$message.success("删除成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }).catch(() => {
        })
      },

      dialogShow(row) {
        if (row == undefined) {
          this.dialogType = 1;
          this.dialogName = '添加商品属性';
            this.dialogForm.id = 0;
          this.dialogForm.commodityId = this.commodityId;
          this.dialogForm.attrId = 0;
          this.dialogForm.attrName = '';
          this.dialogForm.price = '0.00';
          this.dialogForm.stockNum = 0;
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '修改商品属性',
            this.dialogForm.id = row.id;
          this.dialogForm.commodityId = row.commodityId;
          this.dialogForm.attrId = row.attrId;
          this.dialogForm.attrName = row.attrName;
          this.dialogForm.price = row.price;
          this.dialogForm.stockNum = row.stockNum;
          this.dialogFormVisible = true;
        }
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.COMMUDITY_ATTR_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.COMMUDITY_ATTR_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      }
    },
    computed: {
      baseUrl: function () {
        return window.localStorage.getItem('baseUrl');
      },
      addBaseUrl: function () {
        return this.baseUrl + this.dialogForm.url;
      }
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
