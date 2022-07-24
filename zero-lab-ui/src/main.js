import Vue from 'vue'
import App from './App.vue'
import router from './router'
// 加载基础ElementUI
import ElementUI from "element-ui";
Vue.use(ElementUI);

// bpmnProcessDesigner 需要引入
import MyPD from "@/components/bpmnProcessDesigner/package/index.js";
Vue.use(MyPD);

import { vuePlugin } from "@/components/bpmnProcessDesigner/package/highlight";
import "highlight.js/styles/atom-one-dark-reasonable.css";
Vue.use(vuePlugin);

import "@/components/bpmnProcessDesigner/package/theme/index.scss";
import "@/components/bpmnProcessDesigner/package/theme/element-variables.scss";
import "bpmn-js/dist/assets/diagram-js.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css";
import "bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css";

Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')
