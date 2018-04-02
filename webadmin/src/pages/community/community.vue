<template>

  <div class="panel">
    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.title" auto-complete="off"></el-input>
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

        <!--<el-form-item label="链接地址" :label-width="formLabelWidth">-->
          <!--<el-input v-model="this.dialogForm.url" auto-complete="off" disabled></el-input>-->
        <!--</el-form-item>-->

        <el-form-item label="图片" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            :action="fileAction"
            name="mulFile"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="addBaseUrl" :src="addBaseUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogData()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="html5文本编辑" :visible.sync="dialogHtml5Visible" width="400">
      <el-form :model="dialogHtml5">
        <el-form-item label="关联商品ID" :label-width="formLabelWidth">
          <el-input v-model="dialogHtml5.linkId" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth">
          <quill-editor ref="myTextEditor"
                        :content="dialogHtml5.data"
                        :config="editorOption"
                        @change="onEditorChange($event)">
          </quill-editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogHtml5Visible = false">取 消</el-button>
        <el-button type="primary" @click="dialogHtml5Submit()">确 定</el-button>
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
          prop="title"
          label="标题"
          width="200">
        </el-table-column>
        <el-table-column
          prop="type"
          label="类型"
          width="100">
          <template scope="props">
            <span v-text="showType(props.row.type)"></span>
          </template>
        </el-table-column>
        <!--<el-table-column-->
          <!--prop="url"-->
          <!--label="链接地址"-->
          <!--width="200">-->
        <!--</el-table-column>-->
        <el-table-column
          prop="hitNum"
          label="点击量"
          width="200">
        </el-table-column>
        <el-table-column
          prop="praiseNum"
          label="点赞量"
          width="200">
        </el-table-column>
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
          width="250">
          <template scope="props">
            <el-button type="info" icon="edit" size="small" @click="dialogShow(props.row)">修改</el-button>
            <el-button type="danger" size="small" icon="delete" @click="deleteData(props.row)">删除</el-button>
            <el-button type="info" icon="edit" size="small" @click="dialogHtml5Show(props.row)">H5</el-button>
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
  import { quillEditor } from 'vue-quill-editor'

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
          title: '',
          type: 0,
          url: ''
        },
        dialogFormVisible: false,

        canCrop: false,
        dialogHtml5Visible: false,
        dialogHtml5: {
            type: 1,
            id: '',
            linkId: '',
            data: ''
        },
        editorOption: {
          // something config
        },

        fileAction: '',
        options: [{
          value: 1,
          label: '学习教育'
        },{
          value: 2,
          label: '美食分享'
        },{
          value: 3,
          label: '生活健康'
        },{
          value: 4,
          label: '健身健美'
        }],


      }
    },
    components: {
      panelTitle,
      bottomToolBar,
      quillEditor
    },
    methods: {
      //获取数据
      getTableData(){
        this.loadData = true;
        communication.httpPost(communication.COMMUNITY_LIST, {page: this.page, size: this.size})
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
          communication.httpPost(communication.COMMUNITY_DEL, {id: item.id})
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

          communication.httpPost(communication.COMMUNITY_BATCH_DEL, {idList: arrId})
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
          this.dialogName = '添加云社区资讯',
            this.dialogForm.id = 0;
          this.dialogForm.title = '';
          this.dialogForm.type = '';
          this.dialogForm.url = '';
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '修改云社区资讯',
            this.dialogForm.id = row.id;
          this.dialogForm.title = row.title;
          this.dialogForm.type = row.type;
          this.dialogForm.url = row.url;
          this.dialogFormVisible = true;
        }
      },

      dialogHtml5Show(row) {
          this.dialogHtml5.type = 1;
          this.dialogHtml5.id = row.id;
          this.dialogHtml5.data = '';
          this.dialogHtml5.linkId = '';
          this.dialogHtml5Visible = true;

          let param = {
              type: this.dialogHtml5.type,
              foreignId: this.dialogHtml5.id
          };
        communication.httpPost(communication.RICH_SELECT, param)
          .then(({data}) => {
            this.dialogHtml5.data = data.data;
            //let link = JSON.parse(data.link);
            this.dialogHtml5.id = data.foreignId;
            this.dialogHtml5.linkId = data.id;
          })
          .catch(({code, msg}) => {
            this.$message.error(msg);
          })
      },

      dialogHtml5Submit() {
        communication.httpPost(communication.RICH_SUBMIT, this.dialogHtml5)
          .then(({data}) => {
            this.dialogHtml5Visible = false;
            this.getTableData();
            this.$message.success("保存成功");
          })
          .catch(({code, msg}) => {
            this.$message.error(msg);
          })
      },

      showType(type) {
        let index = 0;
        let len = this.options.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.options[index].value);
          if (vv == type) {
            return this.options[index].label;
          }
        }
        return '';
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.COMMUNITY_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.COMMUNITY_EDIT, this.dialogForm)
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

          this.dialogForm.url = resInfo.url;
        } else {
          this.$message.error(res.msg);
        }
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isPNG = file.type === 'image/png';
        const isLt2M = file.size < 2*1024*1024;

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
      },
      onEditorChange({ editor, html, text }) {
        this.dialogHtml5.data = html
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
      this.fileAction = communication.UPLOAD_FILE;
      this.getTableData();
    }
  }
</script>

<style>
  /*.ql-blank {*/
    /*min-height: 600px;*/
  /*}*/
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
