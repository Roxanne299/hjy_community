<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="单元名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单元名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单元编号" prop="unitCode">
        <el-input
          v-model="queryParams.unitCode"
          placeholder="请输入单元编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否有电梯" prop="unitHaveElevator" label-width="120px">
        <el-select v-model="queryParams.unitHaveElevator" placeholder="请选择是否有电梯" clearable size="small">
          <el-option
                 v-for="dict in statusOptions"
                 :key="dict.dictValue"
                 :label="dict.dictLabel"
                 :value="dict.dictValue"
                    />
        </el-select>
      </el-form-item>

      <el-form-item label="所属楼栋" prop="buildingId">
        <el-select v-model="queryParams.buildingId" placeholder="所属楼栋" clearable size="small">
          <el-option
                  v-for="item in options"
                  :key="item.buildingId"
                  :label="item.buildingName"
                  :value="item.buildingId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:unit:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:unit:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:unit:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:unit:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
          label="序号"
          type="index"
          width="50"
          align="center">
      </el-table-column>
      <el-table-column label="单元id" v-if="show" align="center" prop="unitId" />
      <el-table-column label="小区名称" align="center" prop="communityName" />
      <el-table-column label="楼栋" align="center" prop="buildingName" />
      <el-table-column label="单元名称" align="center" prop="unitName" />
      <el-table-column label="层数" align="center" prop="unitLevel" />
      <el-table-column label="建筑面积" align="center" prop="unitAcreage" />
      <el-table-column label="是否有电梯" align="center" prop="unitHaveElevator" :formatter="statusFormat"/>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:unit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:unit:remove']"
          >删除</el-button>
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

    <!-- 添加或修改单元 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

       <el-form-item label="楼栋" prop="buildingId">
       <el-select v-model="form.buildingId" filterable placeholder="请选择楼栋" @change="selectedBuildingId" class="avatar-container right-menu-item hover-effect">
             <el-option
               v-for="item in options"
               :key="item.buildingId"
               :label="item.buildingName"
               :value="item.buildingId">
             </el-option>
       	  </el-select>
      </el-form-item>
        <el-form-item label="单元名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单元名称" />
        </el-form-item>
        <el-form-item label="单元编号" prop="unitCode">
          <el-input v-model="form.unitCode" placeholder="请输入单元编号" />
        </el-form-item>
        <el-form-item label="层数" prop="unitLevel">
          <el-input v-model="form.unitLevel" placeholder="请输入层数" />
        </el-form-item>
        <el-form-item label="建筑面积" prop="unitAcreage">
          <el-input v-model="form.unitAcreage" placeholder="请输入建筑面积" />
        </el-form-item>
        </el-form-item>
               <el-form-item label="是否有电梯" label-width="82px">
                 <el-radio-group v-model="form.unitHaveElevator">
                   <el-radio
                     v-for="dict in statusOptions"
                     :key="dict.dictValue"
                     :label="dict.dictValue"
                   >{{dict.dictLabel}}</el-radio>
                 </el-radio-group>
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
import { listUnit, getUnit, delUnit, addUnit, updateUnit, exportUnit } from "@/api/property/unit";
import { listBuilding, getBuilding, delBuilding, addBuilding, updateBuilding, exportBuilding,queryPullDown } from "@/api/property/building";

export default {
  name: "Unit",
  components: {
  },
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
      // 单元 表格数据
      unitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
       //
       statusOptions: [],
       //楼栋数组
       options:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        unitId: null,
        communityId: null,
        buildingId: null,
        unitName: null,
        unitCode: null,
        unitLevel: null,
        unitAcreage: null,
        unitHaveElevator: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        buildingId: [
          { required: true, message: "请选择楼栋", trigger: "blur" }
        ],
        unitLevel: [
          { required: true, message: "层数不能为空", trigger: "blur" },
          { pattern: /^[1-9]\d*$/, message: '请输入正确的整数格式',trigger: "change" }
        ],
        unitHaveElevator: [
          { required: true, message: "是否有电梯不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yes_no").then(response => {
          this.statusOptions = response.data;
        });
    queryPullDown().then(response => {
      this.options = response.data;
    })
  },
  methods: {
    //获取选中的楼栋
        selectedBuildingId(detail) {

      },
    // 是否有电梯字典翻译
    statusFormat(row, column) {
    return this.selectDictLabel(this.statusOptions, row.unitHaveElevator);
     },
    /** 查询单元 列表 */
    getList() {
      this.loading = true;
      listUnit(this.queryParams).then(response => {
        this.unitList = response.rows;
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
        unitId: null,
        communityId: null,
        buildingId: null,
        unitName: null,
        unitCode: null,
        unitLevel: null,
        unitAcreage: null,
        unitHaveElevator: "0",
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
      this.ids = selection.map(item => item.unitId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加单元 ";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const unitId = row.unitId || this.ids
      getUnit(unitId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改单元 ";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.unitId != null) {
            updateUnit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUnit(this.form).then(response => {
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
      const unitIds = row.unitId || this.ids;
      this.$confirm('是否确认删除单元 编号为"' + unitIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUnit(unitIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有单元 数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUnit(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
