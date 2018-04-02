<template>

  <div class="panel">
    <el-dialog :title="dialogName" :visible.sync="dialogFormVisible" :before-close="handleClose">
      <el-form :model="dialogForm">
        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="简洁标题" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.shortTitle" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="详细标题" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.fullTitle" auto-complete="off"></el-input>
        </el-form-item>

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

        <el-form-item label="商品分类" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.shopProductType" placeholder="请选择">
            <el-option
              v-for="item in optShopProductType"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="服务项目" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.serviceItem" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="销售额" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.allSaleNum" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="月售额" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.monthSaleNum" auto-complete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="是否免邮" :label-width="formLabelWidth">
          <el-select v-model="dialogForm.isFreeExp" placeholder="请选择">
            <el-option
              v-for="item in optFreeExp"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地区" :label-width="formLabelWidth">
          <el-input v-model="dialogForm.addrName" auto-complete="off" disabled></el-input>
          <el-select v-model="dialogForm.provice" placeholder="请选择省份" @change="selectProvice">
            <el-option
              v-for="item in optAreaProvice"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="dialogForm.city" placeholder="请选择市">
            <el-option
              v-for="item in optAreaCity"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <el-button @click="submitArea">确定</el-button>
        </el-form-item>

        <el-form-item label="默认商品图片" :label-width="formLabelWidth">
          <el-upload
            class="avatar-uploader"
            name="mulFile"
            :action="fileAction"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="dialogForm.showPic" :src="showPicUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品图片集" :label-width="formLabelWidth">
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
        <el-button type="primary" @click="dialogSubmit()">确 定</el-button>
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

      <el-select v-model="selectParam.shopProductType" clearable placeholder="商品分类">
        <el-option
          v-for="item in optShopProductType"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>

        <el-select v-model="selectParam.state" clearable placeholder="是否上架">
          <el-option
            v-for="item in optState"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

        <!--<el-button @click.stop="getTableData" size="small">-->
          <!--<i class="fa fa-refresh"></i>-->
        <!--</el-button>-->
        <el-button type="info" size="big" icon="small" @click="getTableData">查询</el-button>
      </div>

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
          prop="shortTitle"
          label="简洁标题"
          width="200">
        </el-table-column>
        <el-table-column
          prop="fullTitle"
          label="详细标题"
          width="200">
        </el-table-column>
        <el-table-column
          prop="shopName"
          label="店铺名称"
          width="100">
        </el-table-column>
        <el-table-column
          prop="addrName"
          label="地区"
          width="100">
        </el-table-column>
        <el-table-column
          prop="serviceItem"
          label="服务项目"
          width="100">
        </el-table-column>
        <el-table-column
          prop="allSaleNum"
          label="销售额"
          width="80">
        </el-table-column>
        <el-table-column
          prop="monthSaleNum"
          label="月销额"
          width="80">
        </el-table-column>
        <el-table-column
          prop="isFreeExp"
          label="是否免邮"
          width="100">
          <template scope="props">
            <span v-text="showFreeExp(props.row.isFreeExp)"></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="showPic"
          label="默认商品图片"
          width="100">
          <template scope="props">
            <img :src="showUrl(props.row.showPic)" alt="">
          </template>
        </el-table-column>
        <el-table-column
          prop="state"
          label="是否上架"
          width="100">
          <template scope="props">
            <span v-text="showState(props.row.state)"></span>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="350">
          <template scope="props">
            <el-button type="info" icon="edit" size="small" @click="dialogShow(props.row)">修改</el-button>
            <router-link :to="{name: 'commodityAttr', params: {id: props.row.id}}" tag="span">
              <el-button type="info" size="small">属性</el-button>
            </router-link>
            <el-button v-if="props.row.state==0" type="info" size="small" icon="small" @click="setUp(props.row)">上架</el-button>
            <el-button v-else type="danger" size="small" icon="small" @click="setDown(props.row)">下架</el-button>
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
        selectParam: {
          shopId: '',
          shopProductType: '',
          state: ''
        },

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
          shortTitle: '',
          fullTitle: '',
          shopId: 0,
          shopProductType: '',
          showPic: '',
          listPic: [],
          addrId: 0,
          addrName: '',
          serviceItem: '',
          allSaleNum: 0,
          monthSaleNum: 0,
          isFreeExp: 0,

          provice: '',
          city: '',
        },
        dialogFormVisible: false,

        fileAction: '',
        optFreeExp: [{
          value: 0,
          label: '不免邮'
        },{
          value: 1,
          label: '免邮'
        }],
        optState: [{
          value: 0,
          label: '下架'
        },{
          value: 1,
          label: '上架'
        }],
        optShop: [],
        optAreaProvice: [],
        optAreaCity: [],
        optShopProductType: [
          {
            id: 1,
            name: '学习教育'
          },{
            id: 2,
            name: '美食分享'
          },{
            id: 3,
            name: '生活健康'
          },{
            id: 4,
            name: '健身健美'
          }
        ],

        canCrop: false,
        dialogHtml5Visible: false,
        dialogHtml5: {
          type: 2,
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
    methods: {
        showState(row) {
          if (props.row.state==0) return '上架'; else return '下架';
        },
      //获取数据
      getTableData(){
            let state = -1;
            if (this.selectParam.state != '') {
              state = Number(this.selectParam.state);
            }
            let param = {
              shopId: Number(this.selectParam.shopId),
              shopProductType: Number(this.selectParam.shopProductType),
              state: state,
              page: this.page,
              size: this.size
            };
        this.loadData = true;
        communication.httpPost(communication.COMMUDITY_LIST, param)
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
          communication.httpPost(communication.COMMUDITY_DEL, {id: item.id})
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

          communication.httpPost(communication.COMMUDITY_BATCH_DEL, {idList: arrId})
            .then(({data}) => {
              this.getTableData();
              this.$message.success("删除成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }).catch(() => {
        })
      },


      setState(id, state, msg) {
        communication.httpPost(communication.COMMUDITY_SET_STATE, {id: id, state: state})
          .then(({data}) => {
            this.getTableData();
            this.$message.success(msg);
          }).catch(({code, msg}) => {
          this.$message.error(msg);
        })
      },
      setUp(row) {
        this.$confirm('是否上架该商品?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.setState(row.id, 1, '上架成功');
        }).catch(() => {
        })
      },
      setDown(row) {
        this.$confirm('是否下架该商品?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.setState(row.id, 0, '下架成功');
        }).catch(() => {
        })
      },


      dialogShow(row) {
        if (row == undefined) {
          this.dialogType = 1;
          this.dialogName = '添加商品';

          this.dialogForm.id = 0;
          this.dialogForm.shortTitle = '';
          this.dialogForm.fullTitle = '';
          this.dialogForm.shopId = '';
          this.dialogForm.shopProductType = '';
          this.dialogForm.showPic = 0;
          this.dialogForm.listPic = [];
          this.dialogForm.addrId = 0;
          this.dialogForm.serviceItem = 0;
          this.dialogForm.allSaleNum = 0;
          this.dialogForm.monthSaleNum = 0;
          this.dialogForm.isFreeExp = 0;

          this.dialogFormVisible = true;
        } else {
          this.dialogType = 2;
          this.dialogName = '修改商品';

          this.dialogForm.id = row.id;
          this.dialogForm.shortTitle = row.shortTitle;
          this.dialogForm.fullTitle = row.fullTitle;
          this.dialogForm.shopId = row.shopId;
          this.dialogForm.shopProductType = row.shopProductType;
          this.dialogForm.showPic = row.showPic;

          // 深拷贝
          this.dialogForm.listPic = [];
          for (var i = 0; i < row.listPic.length; i++) {
            this.dialogForm.listPic[i] = row.listPic[i];
          }
          this.dialogForm.addrId = row.addrId;
          this.dialogForm.serviceItem = row.serviceItem;
          this.dialogForm.allSaleNum = row.allSaleNum;
          this.dialogForm.monthSaleNum = row.monthSaleNum;
          this.dialogForm.isFreeExp = row.isFreeExp;

          this.dialogFormVisible = true;
        }

        this.dialogForm.addrName='';
        this.dialogForm.provice = '';
        this.dialogForm.city = '';
      },

      showFreeExp(type) {
        let index = 0;
        let len = this.optFreeExp.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.optFreeExp[index].value);
          if (vv == type) {
            return this.optFreeExp[index].label;
          }
        }
        return '';
      },
      showState(type) {
        let index = 0;
        let len = this.optState.length;
        for(index = 0; index < len; index++) {
          let vv = parseInt(this.optState[index].value);
          if (vv == type) {
            return this.optState[index].label;
          }
        }
        return '';
      },


      dialogSubmit() {
        let param = {
          id: this.dialogForm.id,
          shortTitle: this.dialogForm.shortTitle,
          fullTitle: this.dialogForm.fullTitle,
          shopId: this.dialogForm.shopId,
          shopProductType: this.dialogForm.shopProductType,
          showPic: this.dialogForm.showPic,
          listPic: this.dialogForm.listPic,
          addrId: this.dialogForm.addrId,
          serviceItem: this.dialogForm.serviceItem,
          isFreeExp: this.dialogForm.isFreeExp
        };
        if (this.dialogType == 1) {
          communication.httpPost(communication.COMMUDITY_ADD, param)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.dialogFormVisible = false;
              this.$message.success("添加成功");
            })
            .catch(({code, msg}) => {
              this.$message.error(msg);
            })
        } else {
          communication.httpPost(communication.COMMUDITY_EDIT, param)
            .then(({data}) => {
              this.dialogFormVisible = false;
              this.getTableData();
              this.dialogFormVisible = false;
              this.$message.success("修改成功");
            }).catch(({code, msg}) => {
            this.$message.error(msg);
          })
        }
      },
      handleClose(done) {
        this.$confirm('确认关闭对话框', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            done();
        }).catch(() => {});
      },
      handleAvatarSuccess(res, file) {
        if (res.code === 0) {
          let resInfo = '';
          if (res.info == '' || res.info == undefined || res.info == null) {
            resInfo = '';
          } else {
            resInfo = JSON.parse(res.info);
          }

          this.dialogForm.showPic = resInfo.url;
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

      showUrl(url) {
        return this.baseUrl + url;
      },

      selectProvice() {
        this.dialogForm.city = '';
        this.getArea(2, this.dialogForm.provice);
      },
      getArea(type, id) {
        communication.httpPost(communication.AREA_GET_CHILD, {id: id})
          .then(({data}) => {
            if (type == 1) {
              this.optAreaProvice = data;
            } else if (type == 2) {
              this.optAreaCity = data;
            }
          })
          .catch(({code, msg}) => {
            //this.loadData = false;
          })
      },
      submitArea() {
        this.dialogForm.addrId = this.dialogForm.district;
        this.dialogForm.addrName = '';

        let index = 0;
        let addrId = 0;
        let name = '';
        let len = 0;

        len = this.optAreaProvice.length;
        for(index = 0; index < len; index++) {
          addrId = parseInt(this.optAreaProvice[index].id);
          if (addrId == this.dialogForm.provice) {
            name = this.optAreaProvice[index].name;
            break;
          }
        }
        this.dialogForm.addrId = addrId;
        this.dialogForm.addrName += name;


        if (this.dialogForm.city != '') {
          name = '';
          len = this.optAreaCity.length;
          for (index = 0; index < len; index++) {
            addrId = parseInt(this.optAreaCity[index].id);
            if (addrId == this.dialogForm.city) {
              name = this.optAreaCity[index].name;
              break;
            }
          }
          this.dialogForm.addrId = addrId;
          this.dialogForm.addrName += name;
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

      dialogHtml5Show(row) {
        this.dialogHtml5.type = 2;
        this.dialogHtml5.id = row.id;
        this.dialogHtml5.data = '';
        this.dialogHtml5.link = '';
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

      onEditorChange({ editor, html, text }) {
        this.dialogHtml5.data = html
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
    created(){
      this.fileAction = communication.UPLOAD_FILE;
      this.getTableData();
      this.getArea(1, 1111111111);
      this.getShop();
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
