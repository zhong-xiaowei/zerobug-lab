<template>
  <div class="panel-tab__content">
    <el-form size="mini" label-width="90px" @submit.native.prevent>
      <!-- <el-form-item label="ID">
        <el-input v-model="elementBaseInfo.id" :disabled="idEditDisabled" clearable @change="updateBaseInfo('id')" />
      </el-form-item>
      <el-form-item label="名称">
        <el-input v-model="elementBaseInfo.name" clearable @change="updateBaseInfo('name')" />
      </el-form-item>


      <template v-if="elementBaseInfo.$type === 'bpmn:Process'">
        <el-form-item label="版本标签">
          <el-input v-model="elementBaseInfo.versionTag" clearable @change="updateBaseInfo('versionTag')" />
        </el-form-item>
        <el-form-item label="可执行">
          <el-switch v-model="elementBaseInfo.isExecutable" active-text="是" inactive-text="否" @change="updateBaseInfo('isExecutable')" />
        </el-form-item>
      </template>
      <el-form-item v-if="elementBaseInfo.$type === 'bpmn:SubProcess'" label="状态">
        <el-switch v-model="elementBaseInfo.isExpanded" active-text="展开" inactive-text="折叠" @change="updateBaseInfo('isExpanded')" />
      </el-form-item> -->

      <div v-if="elementBaseInfo.$type === 'bpmn:Process'">
        <el-form-item label="流程标识" prop="key">
          <el-input v-model="modelInfo.key" placeholder="请输入流标标识"
                    :disabled="modelInfo.id !== undefined && modelInfo.id.length > 0" @change="handleKeyUpdate"/>
        </el-form-item>
        <el-form-item label="流程名称" prop="name">
          <el-input v-model="modelInfo.name" placeholder="请输入流程名称" clearable @change="handleNameUpdate"/>
        </el-form-item>
      </div>
      <div v-else>
        <el-form-item label="ID">
          <el-input v-model="elementBaseInfo.id" clearable @change="updateBaseInfo('id')"/>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="elementBaseInfo.name" clearable @change="updateBaseInfo('name')"/>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>
<script>
export default {
  name: "ElementBaseInfo",
  props: {
    businessObject: Object,
    type: String,
    idEditDisabled: {
      type: Boolean,
      default: true
    },
    modelInfo: Object
  },
  data() {
    return {
      elementBaseInfo: {}
    };
  },
  watch: {
    businessObject: {
      immediate: false,
      handler: function(val) {
        if (val) {
          this.$nextTick(() => this.resetBaseInfo());
        }
      }
    }
  },
  methods: {
    resetBaseInfo() {
      this.bpmnElement = window?.bpmnInstances?.bpmnElement || {};
      this.elementBaseInfo = JSON.parse(JSON.stringify(this.bpmnElement.businessObject));
      if (this.elementBaseInfo && this.elementBaseInfo.$type === "bpmn:SubProcess") {
        this.$set(this.elementBaseInfo, "isExpanded", this.elementBaseInfo.di?.isExpanded);
      }
    },
    updateBaseInfo(key) {
      if (key === "id") {
        window.bpmnInstances.modeling.updateProperties(this.bpmnElement, {
          id: this.elementBaseInfo[key],
          di: { id: `${this.elementBaseInfo[key]}_di` }
        });
        return;
      }
      if (key === "isExpanded") {
        window?.bpmnInstances?.modeling.toggleCollapse(this.bpmnElement);
        return;
      }
      const attrObj = Object.create(null);
      attrObj[key] = this.elementBaseInfo[key];
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, attrObj);
    },
    handleKeyUpdate(value) {
      if (!value) {
        return;
      }
      if (!value.match(/[a-zA-Z_][\-_.0-9_a-zA-Z$]*/)) {
        console.log('key 不满足 XML NCName 规则，所以不进行赋值');
        return;
      }
      console.log('key 满足 XML NCName 规则，所以进行赋值');

      // 在 BPMN 的 XML 中，流程标识 key，其实对应的是 id 节点
      this.elementBaseInfo['id'] = value;
      this.updateBaseInfo('id');
    },
    handleNameUpdate(value) {
      if (!value) {
        return
      }
      this.elementBaseInfo['name'] = value;
      this.updateBaseInfo('name');
    },
  },
  beforeDestroy() {
    this.bpmnElement = null;
  }
};
</script>
