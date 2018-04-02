<template>
  <div class="panel">
    <panel-title :title="$route.meta.title">
      <el-button @click.stop="on_refresh" size="small">
        <i class="fa fa-refresh"></i>
      </el-button>
      <el-button type="primary" icon="plus" size="small" @click="add_data_show()">添加数据</el-button>

      <el-dialog title="添加消息" :visible.sync="dialogAddFormVisible">
        <el-form :model="addForm">
          <el-form-item label="消息类型" :label-width="formLabelWidth">
            <el-select v-model="addForm.msgType" placeholder="请选择">
              <el-option
                v-for="item in optMsgType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="消息标题" :label-width="formLabelWidth">
            <el-input v-model="addForm.msgTitle" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="消息简介" :label-width="formLabelWidth">
            <el-input v-model="addForm.introduction" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="消息内容" :label-width="formLabelWidth">
            <el-input v-model="addForm.msgContent" auto-complete="off"></el-input>
          </el-form-item>
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
          prop="msgType"
          label="消息类型"
          width="100">
          <template scope="props">
            <span v-text="showMsgType(props.row.msgType)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="msgTitle"
          label="消息标题"
          width="200">
        </el-table-column>
        <el-table-column
          prop="introduction"
          label="消息简介"
          width="200">
        </el-table-column>
        <el-table-column
          prop="msgContent"
          label="消息内容"
          width="300">
        </el-table-column>
        <el-table-column
          prop="push"
          label="是否推送"
          width="100">
          <template scope="props">
            <span v-text="showPush(props.row.push)"></span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="180">
          <template scope="props">
            <!-- <router-link :to="{name: 'tableUpdate', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small" icon="edit">修改</el-button>
            </router-link> -->
            <el-button type="info" icon="edit" size="small" @click="push_data(props.row)">推送</el-button>
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
        size: 15,
        //请求时的loading效果
        load_data: true,
        //批量选择数组
        batch_select: [],

        formLabelWidth: '120px',

        optMsgType: [{
          value: 1,
          label: '系统消息'
        }],
        addForm: {
          msgType: 0,
          msgTitle: '',
          introduction: '',
          msgContent: '',
          push:0
        },
        dialogAddFormVisible: false
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
        communication.httpPost(communication.MESSAGE_LIST, {page: this.page, size: this.size})
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
      push_data(item) {
        this.$confirm('确认推送此消息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            communication.httpPost(communication.MESSAGE_PUSH, {id: item.id})
              .then(({data}) => {
                this.get_table_data();
                this.$message.success("推送成功");
              })
              .catch(({code, msg}) => {

              })
          })
          .catch(() => {
          })
      },
      //单个删除
      delete_data(item) {
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            communication.httpPost(communication.MESSAGE_DEL, {id: item.id})
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
      on_batch_select(val) {
        this.batch_select = val
      },
      //批量删除
      on_batch_del() {
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

            communication.httpPost(communication.MESSAGE_BATCH_DEL, {idList: arrId})
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
        this.addForm.msgType = '';
        this.addForm.msgTitle = '';
        this.addForm.introduction = '';
        this.addForm.msgContent = '';
        this.dialogAddFormVisible = true;
      },

      add_data() {
        communication.httpPost(communication.MESSAGE_ADD, this.addForm)
          .then(({data}) => {
            this.dialogAddFormVisible = false;
            this.get_table_data();
            this.$message.success("添加成功");
          })
          .catch(({code, msg}) => {
            this.$message.success(msg);
          })
      },

      showMsgType(type) {
        if (type == 1) return '系统消息';
      },

      showPush(pu) {
        if (pu==0) return '否'; else return '是';
      }
    }
  }
</script>
