<template>
  <div class="panel">
    <el-dialog title="dialogName" :visible.sync="dialogFormVisible">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="买家地区" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.bullAddrName" auto-complete="off" disabled></el-input>
          <el-select v-model="dialogForm.bullAddrId" placeholder="省" @change="selectBullProvice">
            <el-option
              v-for="item in optAreaProvice"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
<!--          <el-select v-model="dialogForm.bullCity" placeholder="市">
            <el-option
              v-for="item in optBullAreaCity"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>-->
<!--          <el-button @click="submitArea3">确定</el-button>-->
        </el-form-item>

        <el-form-item label="卖家地区" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.commodityAddrName" auto-complete="off" disabled></el-input>
          <el-select v-model="dialogForm.commodityAddrId" placeholder="省" @change="selectCommodityProvice">
            <el-option
              v-for="item in optAreaProvice"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
<!--          <el-select v-model="dialogForm.commodityCity" placeholder="市">
            <el-option
              v-for="item in optCommodityAreaCity"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>-->
<!--          <el-button @click="submitCommodityArea">确定</el-button>-->
        </el-form-item>

        <el-form-item label="运费" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.price" auto-complete="off"></el-input>
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
          prop="bullAddrName"
          label="买家地区"
          width="300">
        </el-table-column>
        <el-table-column
          prop="commodityAddrName"
          label="卖家地区"
          width="300">
        </el-table-column>

        <el-table-column
          prop="price"
          label="运费"
          width="100">
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

        dialogType: 1,
        dialogName:'',
        dialogForm: {
          id: 0,
          bullAddrId: '',
          bullAddrName: '',
          commodityAddrId: '',
          commodityAddrName: '',
          price: ''
        },
        dialogFormVisible: false,

        optAreaProvice: [],
      }
    },
    components: {
      panelTitle,
      bottomToolBar
    },
    created() {
      this.getTableData();
      this.getArea(1, 1111111111);
    },
    methods: {
      //获取数据
      getTableData() {
        this.load_data = true;
        communication.httpPost(communication.EXPENSES_LIST, {page: this.page, size: this.size})
          .then(({data}) => {
            this.table_data = data.list;
            this.page = data.page;
            this.total = data.total;
            this.load_data = false;
          })
          .catch(({code, msg}) => {
            this.load_data = false;
          })
      },

      //单个删除
      deleteData(item) {
        this.$confirm('此操作将删除该数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.load_data = true;
            communication.httpPost(communication.EXPENSES_DEL, {id: item.id})
              .then(({data}) => {
                this.getTableData();
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
        this.getTableData()
      },
      //批量选择
      onBatchSelect(val) {
        this.batch_select = val
      },
      //批量删除
      onBatchDel() {
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

            communication.httpPost(communication.EXPENSES_BATCH_DEL, {idList: arrId})
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

      getArea(type, id) {
        communication.httpPost(communication.AREA_GET_CHILD, {id: id})
          .then(({data}) => {
            if (type == 1) {
              this.optAreaProvice = data;
            } else if (type == 2) {
              this.optAreaCity = data;
            } else if (type == 3) {
              this.optAreaDistrict = data;
            }
          })
          .catch(({code, msg}) => {
            //this.loadData = false;
          })
      },

      selectBullProvice() {
        //this.dialogForm.bullAddrId = this.dialogForm.district;
        this.dialogForm.bullAddrName = '';

        let index = 0;
        let addrId = 0;
        let name = '';
        let len = 0;

        len = this.optAreaProvice.length;
        for (index = 0; index < len; index++) {
          addrId = parseInt(this.optAreaProvice[index].id);
          if (addrId == this.dialogForm.bullAddrId) {
            name = this.optAreaProvice[index].name;
            break;
          }
        }
        //this.dialogForm.addrId = addrId;
        this.dialogForm.bullAddrName += name;
      },

      selectCommodityProvice() {
        //this.dialogForm.bullAddrId = this.dialogForm.district;
        this.dialogForm.commodityAddrName = '';

        let index = 0;
        let addrId = 0;
        let name = '';
        let len = 0;

        len = this.optAreaProvice.length;
        for (index = 0; index < len; index++) {
          addrId = parseInt(this.optAreaProvice[index].id);
          if (addrId == this.dialogForm.commodityAddrId) {
            name = this.optAreaProvice[index].name;
            break;
          }
        }
        //this.dialogForm.addrId = addrId;
        this.dialogForm.commodityAddrName += name;
      },

      dialogShow(row) {
        if (row == undefined) {
          this.dialogType = 1;
          this.dialogName = '添加地区运费';
          this.dialogForm.id = 0;
          this.dialogForm.bullAddrId = '';
          this.dialogForm.bullAddrName = '';
          this.dialogForm.commodityAddrId = '';
          this.dialogForm.commodityAddrName = '';
          this.dialogForm.price = '0.00';
          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '编辑地区运费';
          this.dialogForm.id = row.id;
          this.dialogForm.bullAddrId = row.bullAddrId;
          this.dialogForm.bullAddrName = row.bullAddrName;
          this.dialogForm.commodityAddrId = row.commodityAddrId;
          this.dialogForm.commodityAddrName = row.commodityAddrName;
          this.dialogForm.price = row.price;
          this.dialogFormVisible = true;
        }
      },

      dialogData() {
        if (this.dialogType == 1) {
          communication.httpPost(communication.EXPENSES_ADD, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.EXPENSES_EDIT, this.dialogForm)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      },
    }
  }
</script>
