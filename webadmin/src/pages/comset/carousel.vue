<template>

  <div class="panel">
    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="类型" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.type" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

<!--        <el-form-item label="链接地址" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.url" auto-complete="off" disabled></el-input>
        </el-form-item>-->

        <el-form-item label="图片" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            :action="fileAction"
            name="mulFile"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="dialogForm.url" :src="addBaseUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogData()">确 定</el-button>
      </div>
    </el-dialog>

    <panel-title :title="$route.meta.title">
      <el-button @click.stop="getTableData" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <el-button type="primary" icon="plus" size="small" @click="dialogShow()">添加数据</el-button>
    </panel-title>
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
          prop="type"
          label="类型"
          width="100">
          <template scope="props">
            <span v-text="showType(props.row.type)"></span>
            <!--<span v-text="props.row.type == 1 ? '云社区' : '零距离'"></span>-->
          </template>
        </el-table-column>
<!--        <el-table-column
          prop="url"
          label="链接地址"
          width="200">
        </el-table-column>-->
        <el-table-column
          prop="url"
          label="图片"
          width="100">
          <template scope="props">
            <img :src="showUrl(props.row.url)" alt="">
          </template>
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
        //批量选择数组
        batchSelect: [],

        dialogType: 1,  // 1:添加 2:编辑
        dialogName: '',
        formLabelWidth: '120px',
        dialogForm: {
          id: 0,
          type: 0,
          url: ''
        },
        dialogFormVisible: false,

        //fileAction: './api/file/upload',
        fileAction: '',
        options: [{
          value: 1,
          label: '云社区'
        }, {
          value: 2,
          label: '零距离'
        }],
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created(){
      this.fileAction = communication.UPLOAD_FILE;
      this.getTableData();
    },
    methods: {
      //获取数据
      getTableData(){
        this.loadData = true;
        communication.httpPost(communication.CAROUSEL_LIST, {page: this.page, size: this.size})
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
          communication.httpPost(communication.CAROUSEL_DEL, {id: item.id})
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

          communication.httpPost(communication.CAROUSEL_BATCH_DEL, {idList: arrId})
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
          this.dialogName = '添加轮播图',
            this.dialogForm.id = 0;
          this.dialogForm.type = '';
          this.dialogForm.url = '';
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '修改轮播图',
            this.dialogForm.id = row.id;
          this.dialogForm.type = row.type;
          this.dialogForm.url = row.url;
          this.dialogFormVisible = true;
        }
      },

      showType(type) {
        let index = 0;
        let len = this.options.length;
        for (index = 0; index < len; index++) {
          let vv = parseInt(this.options[index].value);
          if (vv == type) {
            return this.options[index].label;
          }
        }
        return '';
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.CAROUSEL_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.CAROUSEL_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      },

      handleAvatarSuccess(res, file) {
        if (res.code === 0) {
          let resInfo = '';
          if (res.info == '' || res.info == undefined || res.info == null) {
            resInfo = '';
          } else {
            resInfo = JSON.parse(res.info);
          }

//          let baseUrl = window.localStorage.getItem('baseUrl');
//          this.dialogForm.url = baseUrl + resInfo.url;
          this.dialogForm.url = resInfo.url;
        } else {
          this.$message.error(res.msg);
        }
        //this.addForm.url = URL.createObjectURL(file.raw);
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isLt2M = file.size < 2 * 1024 * 1024;

        //let isFormat = false;
        if (!isJPG && !isPNG) {
          this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
          return false;
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
          return false;
        }
        //return isFormat && isLt2M;
        return true;
      },
      showUrl(url) {
        return this.baseUrl + url;
      }
    },
    computed: {
      baseUrl: function () {
        return window.localStorage.getItem('baseUrl');
      },
      addBaseUrl: function () {
        return this.baseUrl + this.dialogForm.url;
      }
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
