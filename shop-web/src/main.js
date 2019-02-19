import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import 'element-ui/lib/theme-chalk/index.css'
/*import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n*/

import '@/styles/index.scss' // global css
import VueParticles from 'vue-particles'
import App from './App'
import router from './router'
import store from './store'

import '@/icons' // icon
import '@/permission.js' // permission.js control
let echarts = require('echarts/lib/echarts')

// 引入折线图等组件
require('echarts/lib/chart/line')
require('echarts/lib/chart/bar')
require('echarts/lib/chart/map')
require('echarts/lib/chart/effectScatter')
require('echarts/lib/chart/scatter')
require('echarts/lib/chart/custom')
require('echarts/map/js/china')
require('echarts/map/js/world')
require('echarts/extension/bmap/bmap');


require('echarts/map/js/province/anhui')
require('echarts/map/js/province/aomen')
require('echarts/map/js/province/beijing')
require('echarts/map/js/province/chongqing')
require('echarts/map/js/province/fujian')
require('echarts/map/js/province/gansu')
require('echarts/map/js/province/guangdong')
require('echarts/map/js/province/guangxi')
require('echarts/map/js/province/guizhou')
require('echarts/map/js/province/hainan')
require('echarts/map/js/province/hebei')
require('echarts/map/js/province/heilongjiang')
require('echarts/map/js/province/henan')
require('echarts/map/js/province/hubei')
require('echarts/map/js/province/hunan')
require('echarts/map/js/province/jiangsu')
require('echarts/map/js/province/jiangxi')
require('echarts/map/js/province/jilin')
require('echarts/map/js/province/liaoning')
require('echarts/map/js/province/neimenggu')
require('echarts/map/js/province/ningxia')
require('echarts/map/js/province/qinghai')
require('echarts/map/js/province/shandong')
require('echarts/map/js/province/shanghai')
require('echarts/map/js/province/shanxi')
require('echarts/map/js/province/shanxi1')
require('echarts/map/js/province/sichuan')
require('echarts/map/js/province/taiwan')
require('echarts/map/js/province/tianjin')
require('echarts/map/js/province/xianggang')
require('echarts/map/js/province/xinjiang')
require('echarts/map/js/province/xizang')
require('echarts/map/js/province/zhejiang')


// 引入提示框和title组件，图例
require('echarts/lib/component/tooltip')
require('echarts/lib/component/title')
require('echarts/lib/component/legend')
require('echarts/lib/component/toolbox')
import {
  Button,
  Select,
  Container,
  Header,
  Row,
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  Menu,
  MenuItem,
  Submenu,
  MenuItemGroup,
  Main,
  ColorPicker,
  TableColumn,
  Table,
  Breadcrumb,
  BreadcrumbItem,
  Badge,
  Aside,
  Input,
  Form,
  FormItem,
  Dialog,
  Radio,
  RadioGroup,
  Message,
  Pagination,
  Loading,
  Checkbox,
  CheckboxGroup,
  MessageBox,
  Option,
  DatePicker,
  Carousel,
  CarouselItem,
  Tag,
  Scrollbar,
  InputNumber,
  Tooltip,
  Card,
  TabPane,
  Tabs
} from 'element-ui';

Vue.use(CarouselItem)
Vue.use(CheckboxGroup)
Vue.use(Carousel)
Vue.use(Option)
Vue.use(DatePicker)
Vue.use(Checkbox)
Vue.use(RadioGroup)
Vue.use(Loading)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Radio)
Vue.use(FormItem)
Vue.use(Form)
Vue.use(Aside)
Vue.use(Input)
Vue.use(BreadcrumbItem)
Vue.use(Badge)
Vue.use(Breadcrumb)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(ColorPicker)
Vue.use(Button)
Vue.use(Select)
Vue.use(Container)
Vue.use(Header)
Vue.use(Row)
Vue.use(Col)
Vue.use(DropdownMenu)
Vue.use(Dropdown)
Vue.use(DropdownItem)
Vue.use(DropdownMenu)
Vue.use(MenuItemGroup)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(Submenu)
Vue.use(Main)
Vue.use(Tag)
Vue.use(Scrollbar)
Vue.use(InputNumber);
Vue.use(VueParticles)
Vue.use(Tooltip)
Vue.use(Card)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.prototype.$echarts = echarts
/*
Vue.use(ElementUI, { locale })*/
Vue.prototype.$ELEMENT = {size: 'small', zIndex: 3000};
Vue.config.productionTip = false
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
