<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="访客姓名" prop="visitorName">
        <el-input
          v-model="queryParams.visitorName"
          placeholder="请输入访客姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="访客手机号" prop="visitorPhoneNumber" label-width="85px">
        <el-input
          v-model="queryParams.visitorPhoneNumber"
          placeholder="请输入访客手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="到访时间" prop="visitorDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.visitorDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择到访时间">
        </el-date-picker>
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
          v-hasPermi="['system:visitor:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visitorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
      </el-table-column>
      <el-table-column label="id" v-if="show" align="center" prop="visitorId" />
      <el-table-column label="访客姓名" align="center" prop="visitorName" />
      <el-table-column label="访客手机号" align="center" prop="visitorPhoneNumber" />
      <el-table-column label="小区名称" align="center" prop="communityName" />
      <el-table-column label="到访时间" align="center" prop="visitorDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.visitorDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <span>--</span>
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

    <!-- 添加或修改访客邀请 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="访客姓名" prop="visitorName">
          <el-input v-model="form.visitorName" placeholder="请输入访客姓名" />
        </el-form-item>
        <el-form-item label="访客手机号" prop="visitorPhoneNumber">
          <el-input v-model="form.visitorPhoneNumber" placeholder="请输入访客手机号" />
        </el-form-item>
        <el-form-item label="到访时间" prop="visitorDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.visitorDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择到访时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="创建人id" prop="createById">
          <el-input v-model="form.createById" placeholder="请输入创建人id" />
        </el-form-item>
        <el-form-item label="创建人openid" prop="createByOpenId">
          <el-input v-model="form.createByOpenId" placeholder="请输入创建人openid" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVisitor, getVisitor, delVisitor, addVisitor, updateVisitor, exportVisitor } from "@/api/property/visitor";

export default {
  name: "Visitor",
  data() {
    return {
      show: false,
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
      // 访客邀请 表格数据
      visitorList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        visitorName: null,
        visitorPhoneNumber: null,
        visitorDate: null,
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
    /** 查询访客邀请 列表 */
    getList() {
      this.loading = true;
      listVisitor(this.queryParams).then(response => {
        this.visitorList = response.rows;
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
        visitorId: null,
        visitorName: null,
        visitorPhoneNumber: null,
        visitorDate: null,
        createById: null,
        createByOpenId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.ids = selection.map(item => item.visitorId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加访客邀请 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const visitorId = row.visitorId || this.ids
      getVisitor(visitorId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改访客邀请 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.visitorId != null) {
            updateVisitor(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addVisitor(this.form).then(response => {
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
      const visitorIds = row.visitorId || this.ids;
      this.$confirm('是否确认删除访客邀请 编号为"' + visitorIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVisitor(visitorIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有访客邀请 数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportVisitor(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
