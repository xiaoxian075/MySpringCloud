<template>

  <div class="panel">

    <panel-title :title="$route.meta.title">
      <el-button @click.stop="getTableData" size="small"> <i class="fa fa-refresh"></i>  </el-button>
<!--      <el-button type="primary" icon="plus" size="small" @click="dialogShow()">添加数据</el-button>-->
<!--      <el-button @click="$router.back()">返回商品</el-button>-->
    </panel-title>


    <el-dialog title="版本修改" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.type" placeholder="请选择" disabled>
            <el-option
              v-for="item in optType"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="版本" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.version" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.state" placeholder="请选择">
            <el-option
              v-for="item in optState"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
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
        style="width: 100%">
        <el-table-column
          prop="id"
          label="id"
          width="200">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          width="200">
          <template scope="props">
            <span v-text="showType(props.row.type)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="version"
          label="版本"
          width="300">
        </el-table-column>
        <el-table-column
          prop="state"
          label="状态"
          width="200">
          <template scope="props">
            <span v-text="showState(props.row.state)"></span>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          width="350">
          <template scope="props">
            <el-button type="info" icon="edit" size="small" @click="dialogShow(props.row)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
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
        //请求时的loading效果
        //loadData: true,

        optType: [{
          id: 1,
          name: 'IOS'
        },{
          id: 2,
          name: 'ANDRIOD'
        }],

        optState: [{
          id: 1,
          name: '末通过'
        },{
          id: 2,
          name: '通过'
        }],

        formLabelWidth: '120px',
        dialogForm: {
          id: 0,
          type: 0,
          version: '',
          state: 0
        },
        dialogFormVisible: false
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
        communication.httpPost(communication.VERSION_GETALL, {page: this.page, size: this.size})
          .then(({data}) => {
            this.tableData = data;
          })
          .catch(({code, msg}) => {
          })
      },
      showType(type) {
        let index = 0;
        let len = this.optType.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.optType[index].id);
          if (vv == type) {
            return this.optType[index].name;
          }
        }
        return '';
      },

      showState(state) {
        let index = 0;
        let len = this.optState.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.optState[index].id);
          if (vv == state) {
            return this.optState[index].name;
          }
        }
        return '';
      },

      dialogShow(row) {
          this.dialogForm.id = row.id;
          this.dialogForm.type = Number(row.type);
          this.dialogForm.version = row.version;
          this.dialogForm.state = Number(row.state);
          this.dialogFormVisible = true;
      },

      dialogData() {
          communication.httpPost(communication.VERSION_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
      }
    },
    computed: {
//      baseUrl: function () {
//        return window.localStorage.getItem('baseUrl');
//      },
//      addBaseUrl: function () {
//        return this.baseUrl + this.dialogForm.url;
//      }
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
