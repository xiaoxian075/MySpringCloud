<template>
  <div class="panel">

    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">

      <el-form :model="dialogForm">
        <el-form-item label="商铺名称" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.name" auto-complete="off"></el-input>
        </el-form-item>

      <el-form-item label="轮播图" :label-width="formLabelWidth">
        <el-upload
          class="upload-demo"
          name="mulFile"
          limit="2"
          :action="fileAction"
          :on-remove="fileRemove"
          :on-success="filesSuccess"
          :before-upload="filesBefore"
          :file-list="showPicsUrl"
          list-type="picture-card">
          <el-button size="small" type="primary">点击上传</el-button>
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
        :data="table_data"
        v-loading="load_data"
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
          prop="name"
          label="商铺名称"
          width="300">
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
          :disabled="batch_select.length === 0"
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

        formLabelWidth: '120px',
        dialogType: 1,
        dialogName:'',
        dialogForm: {
          id: 0,
          name: '',
          listPic:[],
        },
        dialogFormVisible: false,

        fileAction: '',
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

    computed: {
      baseUrl: function () {
        return window.localStorage.getItem('baseUrl');
      },
      showPicUrl: function () {
        return this.baseUrl + this.dialogForm.showPic;
      },
      showPicsUrl: function () {
        let arrPics = [];
        //arrPics = [];
        let index = 0;
        let len = this.dialogForm.listPic.length;
        for(index = 0; index < len; index++) {
          let name = this.dialogForm.listPic[index];
          let url = this.baseUrl + this.dialogForm.listPic[index];
          arrPics.push({name:name,url:url});
        }
        return arrPics;
      }


    },
    methods: {
      //获取数据
      getTableData(){
        this.load_data = true;
        communication.httpPost(communication.SHOP_LIST, {page: this.page, size: this.size})
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

      //单个删除
      deleteData(item){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            communication.httpPost(communication.SHOP_DEL, {id: item.id})
              .then(({data}) => {
                this.get_table_data();
                this.$message.success("删除成功");
              })
              .catch(({code, msg}) => {

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
        this.batch_select = val
      },
      //批量删除
      onBatchDel(){
        this.$confirm('此操作将批量删除选择数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            var arrId = [];
            for (var i = 0; i < this.batch_select.length; i++) {
              arrId[i] = this.batch_select[i].id;
            }

            communication.httpPost(communication.SHOP_BATCH_DEL, {idList: arrId})
              .then(({data}) => {
                this.get_table_data();
                this.$message.success("删除成功");
              })
              .catch(({code, msg}) => {
                this.$message.success(msg);
              })
          })
          .catch(() => {
          })
      },

      dialogShow(row) {
        if (row == undefined) {
          this.dialogType = 1;
          this.dialogName = '添加店铺';
          this.dialogForm.id = 0;
          this.dialogForm.name = '';
          this.dialogForm.listPic = [];
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '编辑店铺';
          this.dialogForm.id = row.id;
          this.dialogForm.name = row.name;
          this.dialogForm.listPic = row.listPic;
          this.dialogFormVisible = true;
        }
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.SHOP_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.SHOP_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      },



      filesSuccess(res, file) {
        if (res.code === 0) {
          let resInfo = '';
          if (res.info == '' || res.info == undefined || res.info == null) {
            resInfo = '';
          } else {
            resInfo = JSON.parse(res.info);
          }
          this.dialogForm.listPic.push(resInfo.url);
        } else {
          this.$message.error(res.msg);
        }
      },

      fileRemove(file, fileList) {
        let index = 0;
        let len = this.dialogForm.listPic.length;
        for(index = 0; index < len; index++) {
          if (file.name == this.dialogForm.listPic[index]) {
            break;
          }
        }

        if (index > 0) {
          this.dialogForm.listPic.splice(index,1);
        }
      },


      filesBefore(file) {
        if (this.dialogForm.listPic.length >= 3) {
          this.$message.error('最多只能上传3张图片');
          return false;
        }
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
        return true;
      },
    }
  }
</script>
