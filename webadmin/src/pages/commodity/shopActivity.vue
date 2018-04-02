<template>
  <div class="panel">

    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
      <el-form-item label="店铺" :label-width="formLabelWidth">
        <el-select v-model="dialogForm.shopId" placeholder="请选择">
          <el-option
            v-for="item in optShop"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>

        <el-form-item label="活动名称" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.title" auto-complete="off"></el-input>
        </el-form-item>
        </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogData()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="html5文本编辑" :visible.sync="dialogHtml5Visible" width="400">
      <el-form :model="dialogHtml5">
        <!--        <el-form-item label="内容" :label-width="formLabelWidth">-->
        <quill-editor ref="myTextEditor"
                      :content="dialogHtml5.data"
                      :config="editorOption"
                      @change="onEditorChange($event)">
        </quill-editor>
        <!--        </el-form-item>-->
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
      <div>
        <el-select v-model="selectParam.shopId" clearable placeholder="商店">
          <el-option
            v-for="item in optShop"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>

        <el-button type="info" size="big" icon="small" @click="getTableData">查询</el-button>
      </div>

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
          prop="shopId"
          label="商店"
          width="100">
          <template scope="props">
            <span v-text="showShop(props.row.shopId)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="title"
          label="活动名称"
          width="300">
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
  import { quillEditor } from 'vue-quill-editor'

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
          shopId:'',
          title: '',
        },
        dialogFormVisible: false,

        selectParam: {
            shopId: ''
        },
        optShop: [],

        canCrop: false,
        dialogHtml5Visible: false,
        dialogHtml5: {
          type: 3,
          id: '',
          data: ''
        },
        editorOption: {
          // something config
        },
      }
    },
    components: {
      panelTitle,
      bottomToolBar,
      quillEditor
    },
    created(){
      //this.fileAction = communication.UPLOAD_FILE;
      this.getTableData();
      this.getShop();
    },

    computed: {
//      baseUrl: function () {
//        return window.localStorage.getItem('baseUrl');
//      },
//      showPicUrl: function () {
//        return this.baseUrl + this.dialogForm.showPic;
//      },
//      showPicsUrl: function () {
//        let arrPics = [];
//        //arrPics = [];
//        let index = 0;
//        let len = this.dialogForm.listPic.length;
//        for(index = 0; index < len; index++) {
//          let name = this.dialogForm.listPic[index];
//          let url = this.baseUrl + this.dialogForm.listPic[index];
//          arrPics.push({name:name,url:url});
//        }
//        return arrPics;
//      }
    },
    methods: {
      //获取数据
      getTableData(){
          let shopId = Number(this.selectParam.shopId);
        this.load_data = true;
        communication.httpPost(communication.SHOP_ACTIVITY_LIST, {page: this.page, size: this.size, id: shopId})
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
            communication.httpPost(communication.SHOP_ACTIVITY_DEL, {id: item.id})
              .then(({data}) => {
                this.load_data = false;
                this.getTableData();
                this.$message.success("删除成功");

              })
              .catch(({code, msg}) => {
                this.load_data = false;
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

            communication.httpPost(communication.SHOP_ACTIVITY_BATCH_DEL, {idList: arrId})
              .then(({data}) => {
                this.load_data = false;
                this.getTableData();
                this.$message.success("删除成功");
              })
              .catch(({code, msg}) => {
                this.load_data = false;
                this.$message.success(msg);
              })
          })
          .catch(() => {
          })
      },



      dialogShow(row) {
        if (row == undefined) {
          this.dialogType = 1;
          this.dialogName = '添加店铺活动';
          this.dialogForm.id = 0;
          this.dialogForm.shopId = '';
          this.dialogForm.title = '';
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '编辑店铺活动';
          this.dialogForm.id = row.id;
          this.dialogForm.shopId = row.shopId;
          this.dialogForm.title = row.title;
          this.dialogFormVisible = true;
        }
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.SHOP_ACTIVITY_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.SHOP_ACTIVITY_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      },

      getShop() {
        communication.httpPost(communication.SHOP_GET_ALL_SHOP, {})
          .then(({data}) => {
            this.optShop = data;
          })
          .catch(({code, msg}) => {
            //this.loadData = false;
          })
      },

      showShop(shopId) {
        let index = 0;
        let len = this.optShop.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.optShop[index].id);
          if (vv == shopId) {
            return this.optShop[index].name;
          }
        }
        return '';
      },

      onEditorChange({ editor, html, text }) {
        this.dialogHtml5.data = html
      },

      dialogHtml5Show(row) {
        this.dialogHtml5.type = 3;
        this.dialogHtml5.id = row.id;
        this.dialogHtml5.data = '';
        this.dialogHtml5Visible = true;

        let param = {
          type: this.dialogHtml5.type,
          foreignId: this.dialogHtml5.id
        };
        communication.httpPost(communication.RICH_SELECT, param)
          .then(({data}) => {
            this.dialogHtml5.data = data.data;
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
      }
    }
  }
</script>
