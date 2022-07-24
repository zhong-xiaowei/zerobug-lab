<template>
  <div class="editor-container">
    <my-process-designer
        :key="`designer-${reloadIndex}`"
        :options="{
        taskResizingEnabled: true,
        eventResizingEnabled: true,
        minimap: {
          open: true
        }
      }"
        v-model="xmlString"
        v-bind="controlForm"
        keyboard
        ref="processDesigner"
        @element-click="elementClick"
        @element-contextmenu="elementContextmenu"
        @init-finished="initModeler"
    >
    </my-process-designer>
    <my-properties-panel :key="`penal-${reloadIndex}`" :bpmn-modeler="modeler" :prefix="controlForm.prefix"
                         class="process-panel"/>

  </div>
</template>

<script>

import translations from "@/components/bpmnProcessDesigner/src/translations";
// 自定义渲染（隐藏了 label 标签）
import CustomRenderer from "@/components/bpmnProcessDesigner/src/modules/custom-renderer";
// 自定义元素选中时的弹出菜单（修改 默认任务 为 用户任务）
import CustomContentPadProvider from "@/components/bpmnProcessDesigner/package/designer/plugins/content-pad";
// 自定义左侧菜单（修改 默认任务 为 用户任务）
import CustomPaletteProvider from "@/components/bpmnProcessDesigner/package/designer/plugins/palette";
import Log from "@/components/bpmnProcessDesigner/package/Log";
// 任务resize
// import resizeTask from "bpmn-js-task-resize/lib";
// bpmn theme plugin
// import sketchyRendererModule from "bpmn-js-sketchy";
// 小地图
import minimapModule from "diagram-js-minimap";

import UserSql from "@/components/bpmnProcessDesigner/src/modules/extension/user.json";
import clickoutside from "element-ui/lib/utils/clickoutside";
import RewriteAutoPlace from "@/components/bpmnProcessDesigner/src/modules/auto-place/rewriteAutoPlace";

export default {
  name: "BpmnEditor",
  directives: {
    clickoutside: clickoutside
  },
  data() {
    return {
      xmlString: "",
      modeler: null,
      reloadIndex: 0,
      controlDrawerVisible: false,
      infoTipVisible: false,
      pageMode: false,
      translationsSelf: translations,
      controlForm: {
        processId: "",
        processName: "",
        simulation: true,
        labelEditing: false,
        labelVisible: false,
        prefix: "flowable",
        headerButtonSize: "mini",
        events: ["element.click", "element.contextmenu"],
        // additionalModel: []
        moddleExtension: {user: UserSql},
        additionalModel: [
          CustomContentPadProvider,
          CustomPaletteProvider,
          minimapModule,
          {
            __init__: ["autoPlaceSelectionBehavior"],
            autoPlace: ["type", RewriteAutoPlace]
          }
        ]
      },
      addis: {
        CustomContentPadProvider,
        CustomPaletteProvider
      }
    };
  },
  created() {
  },
  methods: {
    initModeler(modeler) {
      setTimeout(() => {
        this.modeler = modeler;
        const canvas = modeler.get("canvas");

        const rootElement = canvas.getRootElement();
        Log.prettyPrimary("Process Id:", rootElement.id);
        Log.prettyPrimary("Process Name:", rootElement.businessObject.name);
      }, 10);
    },
    reloadProcessDesigner(notDeep) {
      this.controlForm.additionalModel = [];
      for (let key in this.addis) {
        if (this.addis[key]) {
          this.controlForm.additionalModel.push(this.addis[key]);
        }
      }
      !notDeep && (this.xmlString = undefined);
      this.reloadIndex += 1;
      this.modeler = null; // 避免 panel 异常
    },
    changeLabelEditingStatus(status) {
      this.addis.labelEditing = status ? {labelEditingProvider: ["value", ""]} : false;
      this.reloadProcessDesigner();
    },
    changeLabelVisibleStatus(status) {
      this.addis.customRenderer = status ? CustomRenderer : false;
      this.reloadProcessDesigner();
    },
    elementClick(element) {
      console.log(element);
      this.element = element;
    },
    elementContextmenu(element) {
      console.log("elementContextmenu:", element);
    },
    changePageMode(mode) {
      const theme = mode
          ? {
            // dark
            stroke: "#ffffff",
            fill: "#333333"
          }
          : {
            // light
            stroke: "#000000",
            fill: "#ffffff"
          };
      const elements = this.modeler.get("elementRegistry").getAll();
      this.modeler.get("modeling").setColor(elements, theme);
    },
    toggle() {
      console.log(this.modeler);
      console.log(this.modeler.get("toggleMode"));
      this.modeler.get("toggleMode").toggleMode();
    }
  }
}
</script>

<style scoped>

.editor-container {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  display: flex;
}
</style>
