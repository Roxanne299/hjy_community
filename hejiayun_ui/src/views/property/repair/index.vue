<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报修状态" prop="repairState">
              <el-select v-model="queryParams.repairState" placeholder="请选择报修状态" clearable size="small">
                <el-option
                    v-for="item in repairOption"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
      <el-form-item label="业主姓名" prop="ownerRealName">
           <el-input
              v-model="queryParams.ownerRealName"
              placeholder="请输入业主姓名"
              clearable
              size="small"
               @keyup.enter.native="handleQuery"
              />
      </el-form-item>
      <el-form-item label="业主电话" prop="ownerPhoneNumber">
           <el-input
              v-model="queryParams.ownerPhoneNumber"
              placeholder="请输入业主姓名"
              clearable
              size="small"
               @keyup.enter.native="handleQuery"
              />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:repair:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="repairList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
       <el-table-column
           label="序号"
           type="index"
           width="50"
           align="center">
      </el-table-column>
      <el-table-column label="id" v-if="show" align="center" prop="repairId" />
      <el-table-column label="报修编号" align="center" prop="repairNum" width="180"/>
      <el-table-column label="报修状态" align="center" prop="repairState" :formatter="repairStatusFormat"/>
      <el-table-column label="业主姓名" align="center" prop="ownerRealName" />
      <el-table-column label="业主电话" align="center" prop="ownerPhoneNumber" />
      <el-table-column label="报修内容" align="center" prop="repairContent" />
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="创建时间" align="center" prop="completeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="期待上门时间" align="center" prop="doorTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.doorTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
          </template>
      </el-table-column>
      <el-table-column label="处理人姓名" align="center" prop="completeName" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:repair:edit']"
          >查看详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改报修信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules">
          <el-row class="mb8">
              <el-col :span="1.5">
                  <el-form-item label="报修编号" prop="repairNum" label-width="98px">
                      <el-input v-model="form.repairNum" style="width: 200px"/>
                  </el-form-item>
              </el-col>
              <el-col :span="1.5">
                 <el-form-item label="报修状态" prop="repairState" label-width="98px">
                     <el-input v-model="form.repairState" style="width: 200px" :formatter="repairStatusFormat"/>
                 </el-form-item>
              </el-col>
          </el-row>
          <el-row class="mb8">
              <el-col :span="1.5">
                  <el-form-item label="业主姓名" prop="userId" label-width="98px">
                      <el-input v-model="form.userId" style="width: 200px"/>
                  </el-form-item>
              </el-col>
              <el-col :span="1.5">
                 <el-form-item label="创建时间" prop="createTime" label-width="98px">
                     <el-date-picker clearable size="small" style="width: 200px"
                         v-model="form.createTime"
                         type="datetime"
                         value-format="yyyy-MM-dd HH:mm:ss"
                         >
                     </el-date-picker>
               </el-form-item>
              </el-col>
          </el-row>
          <el-row class="mb8">
                <el-col :span="1.5">
                   <el-form-item label="派单时间" prop="assignmentTime" label-width="98px">
                       <el-date-picker clearable size="small" style="width: 200px"
                           v-model="form.assignmentTime"
                           type="datetime"
                           value-format="yyyy-MM-dd HH:mm:ss"
                           >
                        </el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="1.5">
                    <el-form-item label="接单时间" prop="receivingOrdersTime" label-width="98px">
                        <el-date-picker clearable size="small" style="width: 200px"
                            v-model="form.receivingOrdersTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            >
                        </el-date-picker>
                     </el-form-item>
                </el-col>
              </el-row>
          <el-row>
                <el-col :span="1.5">
                   <el-form-item label="处理完成时间" prop="completeTime" label-width="98px">
                        <el-date-picker clearable size="small" style="width: 200px"
                            v-model="form.completeTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            >
                            </el-date-picker>
                        </el-form-item>
                </el-col>
                <el-col :span="1.5">
                   <el-form-item label="取消时间" prop="cancelTime" label-width="98px">
                        <el-date-picker clearable size="small" style="width: 200px"
                             v-model="form.cancelTime"
                             type="datetime"
                             value-format="yyyy-MM-dd HH:mm:ss"
                             >
                        </el-date-picker>
                   </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                   <el-form-item label="期待上门时间" prop="doorTime" label-width="98px">
                       <el-date-picker clearable size="small" style="width: 200px"
                           v-model="form.doorTime"
                           type="datetime"
                           value-format="yyyy-MM-dd HH:mm:ss"
                           >
                       </el-date-picker>
                   </el-form-item>
                </el-col>
                <el-col :span="1.5">
                   <el-form-item label="分派人id" prop="assignmentId" label-width="98px">
                         <el-input v-model="form.assignmentId" style="width: 200px"/>
                   </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                    <el-form-item label="处理人姓名" prop="completeName" label-width="98px">
                          <el-input v-model="form.completeName" style="width: 200px"/>
                    </el-form-item>
                </el-col>
                <el-col :span="1.5">
                   <el-form-item label="处理人电话" prop="completePhone" label-width="98px">
                          <el-input v-model="form.completePhone" style="width: 200px"/>
                   </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                    <el-form-item label="报修内容" label-width="98px">
                        <el-input v-model="form.repairContent" :min-height="192" style="width: 200px"/>
                    </el-form-item>
                </el-col>
                <el-col :span="1.5">
                   <el-form-item label="详细地址" prop="address" label-width="98px">
                         <el-input v-model="form.address" style="width: 200px"/>
                   </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="1.5">
                    <el-form-item label="备注" prop="remark" label-width="98px">
                        <el-input v-model="form.remark" style="width: 200px"/>
                    </el-form-item>
                </el-col>
                <el-col :span="1.5">
                   <el-form-item label="小区ID" prop="communityId" label-width="98px">
                       <el-input v-model="form.communityId" style="width: 200px"/>
                   </el-form-item>
                </el-col>
              </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRepair, getRepair, delRepair, addRepair, updateRepair, exportRepair } from "@/api/property/repair";
import Editor from '@/components/Editor';

export default {
  name: "Repair",
  components: { Editor },
  data() {
    return {
      show: false,
      //绑定状态
       repairOption: [{
          value: 'Pending',
          label: '待处理'
       },{
          value: 'Allocated',
          label: '已分派'
      },{
          value: 'Processing',
          label: '处理中'
      },{
          value: 'Processed',
          label: '已处理'
      },{
          value: 'No_Processed',
          label: '不处理'
      },{
          value: 'Cancelled',
          label: '已取消'
      }],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 报修信息表格数据
      repairList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repairState: null,
        assignmentTime: null,
        receivingOrdersTime: null,
        completeTime: null,
        cancelTime: null,
        doorTime: null,
        completePhone: null,
        completeName: null,
        remark: null,
        repairContent: null,
        ownerRealName: null,
        ownerPhoneNumber: null,
        address: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 类型翻译
    repairStatusFormat(row, column) {
      if(row.repairState == 'Pending'){
         return '待处理';
      }else if(row.repairState == 'Allocated'){
         return '已分派';
      }else if(row.repairState == 'Processing'){
         return '处理中';
      }else if(row.repairState == 'Processed'){
         return '已处理';
      }else if(row.repairState == 'No_Processed'){
         return '不处理';
      }else if(row.repairState == 'Cancelled'){
         return '已取消';
      }
    },

    /** 查询报修信息列表 */
    getList() {
      this.loading = true;
      listRepair(this.queryParams).then(response => {
        this.repairList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        repairId: null,
        repairNum: null,
        repairState: null,
        assignmentTime: null,
        receivingOrdersTime: null,
        completeTime: null,
        cancelTime: null,
        doorTime: null,
        assignmentId: null,
        completeId: null,
        completePhone: null,
        completeName: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        userId: null,
        delFlag: null,
        repairContent: null,
        communityId: null,
        address: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报修信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repairId = row.repairId || this.ids
      getRepair(repairId).then(response => {
        this.form = response.data;
        if(this.form.repairState == 'Pending'){
             this.form.repairState = '待处理';
         }else if(this.form.repairState == 'Allocated'){
             this.form.repairState = '已分派';
         }else if(this.form.repairState == 'Processing'){
             this.form.repairState = '处理中';
         }else if(this.form.repairState == 'Processed'){
             this.form.repairState = '已处理';
         }else if(this.form.repairState == 'No_Processed'){
             this.form.repairState = '不处理';
         }else if(this.form.repairState == 'Cancelled'){
             this.form.repairState = '已取消';
        }
        this.open = true;
        this.title = "报修详细信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repairId != null) {
            updateRepair(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addRepair(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const repairIds = row.repairId || this.ids;
      this.$confirm('是否确认删除报修信息编号为"' + repairIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delRepair(repairIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报修信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportRepair(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
