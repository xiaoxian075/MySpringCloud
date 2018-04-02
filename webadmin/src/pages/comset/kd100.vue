<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <!--<router-link :to="{name: 'tableAdd'}" tag="span">-->
        <!--<el-button type="primary" icon="plus" size="small">添加数据</el-button>-->
      <!--</router-link>-->
      <!--<el-button type="text" @click="dialogAddFormVisible = true">添加数据</el-button>-->
      <el-button type="primary" icon="plus" size="small" @click="add_data_show()">添加数据</el-button>

      <el-dialog title="添加快递公司" :visible.sync="dialogAddFormVisible">
        <el-form :model="addForm">
          <el-form-item label="快递公司名称" :label-width="formLabelWidth">
            <el-input v-model="addForm.name" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="快递公司简码" :label-width="formLabelWidth">
            <el-input v-model="addForm.code" auto-complete="off"></el-input>
          </el-form-item>
          <!--<el-form-item label="活动区域" :label-width="formLabelWidth">-->
            <!--<el-select v-model="addForm.region" placeholder="请选择活动区域">-->
              <!--<el-option label="区域一" value="shanghai"></el-option>-->
              <!--<el-option label="区域二" value="beijing"></el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
<!--          <el-form-item label="详情" :label-width="formLabelWidth">
          <input type="button" value="切换裁切" @click="canCrop=!canCrop">
          <editor ref="myTextEditor"
                  :fileName="'myFile'"
                  :canCrop="canCrop"
                  :uploadUrl="uploadUrl"
                  v-model="content"></editor>
          <div v-html="content"></div>
        </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogAddFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="add_data()">确 定</el-button>
        </div>
      </el-dialog>

    </panel-title>
    <div class="panel-body">
      <el-table
        :data="table_data"
        v-loading="load_data"
        element-loading-text="拼命加载中"
        border
        @selection-change="on_batch_select"
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
          label="快递公司名称"
          width="300">
        </el-table-column>
        <el-table-column
          prop="code"
          label="快递公司简码"
          width="200">
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template scope="props">
            <!-- <router-link :to="{name: 'tableUpdate', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small" icon="edit">修改</el-button>
            </router-link> -->
            <el-button type="info" icon="edit" size="small" @click="edit_data_show(props.row)">修改</el-button>
            <el-button type="danger" size="small" icon="delete" @click="delete_data(props.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <bottom-tool-bar>
        <el-button
          type="danger"
          icon="delete"
          size="small"
          :disabled="batch_select.length === 0"
          @click="on_batch_del"
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

            <el-dialog title="修改快递公司" :visible.sync="dialogEditFormVisible">
              <el-form :model="editForm">
                <el-form-item label="编号" :label-width="formLabelWidth">
                  <el-input v-model="editForm.id" auto-complete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="快递公司名称" :label-width="formLabelWidth">
                  <el-input v-model="editForm.name" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="快递公司简码" :label-width="formLabelWidth">
                  <el-input v-model="editForm.code" auto-complete="off"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogEditFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="edit_data()">确 定</el-button>
              </div>
            </el-dialog>

    </div>
  </div>


</template>
<script type="text/javascript">
  import {panelTitle, bottomToolBar} from 'components'
  import * as communication from '../../common/service/communication'

  //import editor from '../../components/editor/Quilleditor.vue'

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
        addForm: {
          name: '',
          code: '',
        },
        dialogAddFormVisible: false,

        editForm: {
          id: 0,
          name: '',
          code: ''
        },
        dialogEditFormVisible: false,

        canCrop:false,
        /*测试上传图片的接口，返回结构为{url:''}*/
        uploadUrl:'http://localhost:4000/api/upload',
        content:''
      }
    },
    components: {
      panelTitle,
      bottomToolBar,
      //editor
    },
    created(){
      this.get_table_data()
    },
    methods: {
      //刷新
      on_refresh(){
        this.get_table_data()
      },
      //获取数据
      get_table_data(){
        this.load_data = true;
        communication.httpPost(communication.KD100_LIST, {page: this.page, size: this.size})
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
      delete_data(item){
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            communication.httpPost(communication.KD100_DEL, {id: item.id})
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
        this.page = val
        this.get_table_data()
      },
      //批量选择
      on_batch_select(val){
        this.batch_select = val
      },
      //批量删除
      on_batch_del(){
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

            communication.httpPost(communication.KD100_BATCH_DEL, {idList: arrId})
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

      add_data_show() {
        this.addForm.name = '';
        this.addForm.code = '';
        this.dialogAddFormVisible = true;
      },

      add_data() {
        communication.httpPost(communication.KD100_ADD, this.addForm)
          .then(({data}) => {
            this.dialogAddFormVisible = false;
            this.get_table_data();
            this.$message.success("添加成功");
          })
          .catch(({code, msg}) => {
            this.$message.success(msg);
          })
      },

      edit_data_show(row) {
        this.editForm.id = row.id;
        this.editForm.name = row.name;
        this.editForm.code = row.code;
        this.dialogEditFormVisible = true;
      },

      edit_data() {
        communication.httpPost(communication.KD100_EDIT, this.editForm)
          .then(({data}) => {
            this.dialogEditFormVisible = false;
            this.get_table_data();
            this.$message.success("修改成功");
          })
          .catch(({code, msg}) => {
            this.$message.success(msg);
          })
      }
    }
  }
</script>
