(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-register"],{"06fe":function(n,t,e){"use strict";e.r(t);var r=e("847e"),a=e.n(r);for(var o in r)"default"!==o&&function(n){e.d(t,n,(function(){return r[n]}))}(o);t["default"]=a.a},3737:function(n,t,e){var r=e("e374");"string"===typeof r&&(r=[[n.i,r,""]]),r.locals&&(n.exports=r.locals);var a=e("4f06").default;a("ecd0923c",r,!0,{sourceMap:!1,shadowMode:!1})},"690b":function(n,t,e){"use strict";e.r(t);var r=e("a40b"),a=e("06fe");for(var o in a)"default"!==o&&function(n){e.d(t,n,(function(){return a[n]}))}(o);e("c2d6");var i,u=e("f0c5"),c=Object(u["a"])(a["default"],r["b"],r["c"],!1,null,"5f01758d",null,!1,r["a"],i);t["default"]=c.exports},"847e":function(n,t,e){"use strict";var r=e("ee27");e("d3b7"),Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=r(e("d0af")),o={data:function(){return{name:"",pwd:""}},methods:{register:function(){console.log("this.name:"+this.name),uni.request({url:"/api/user/register",data:{pwd:this.pwd,account:this.name},method:"POST",header:{"content-type":"application/x-www-form-urlencoded"}}).then((function(n){console.log(n);var t=(0,a.default)(n,2),e=t[0],r=t[1];if(null==e)if(null!=r){var o=r.data;null!=o?"1"===o.code?(uni.setStorageSync("userId",o.uid),uni.setStorageSync("userName",o.account),uni.setStorageSync("token",o.token),uni.reLaunch({url:"./index/index"}),console.log("register success.")):uni.showToast({title:o.errmsg}):uni.showToast({title:"注册失败"})}else uni.showToast({title:"注册失败"});else uni.showToast({title:e})})).catch((function(n){console.log(n),uni.showToast({title:n})})).finally((function(){console.log("always executed")}))}}};t.default=o},a40b:function(n,t,e){"use strict";var r,a=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("v-uni-view",{staticClass:"container"},[e("v-uni-input",{attrs:{placeholder:"输入账号"},model:{value:n.name,callback:function(t){n.name=t},expression:"name"}}),e("v-uni-input",{staticClass:"pwdbt",attrs:{placeholder:"输入密码"},model:{value:n.pwd,callback:function(t){n.pwd=t},expression:"pwd"}}),e("v-uni-button",{staticClass:"btn",attrs:{type:"primary"},on:{click:function(t){arguments[0]=t=n.$handleEvent(t),n.register()}}},[n._v("注册")])],1)},o=[];e.d(t,"b",(function(){return a})),e.d(t,"c",(function(){return o})),e.d(t,"a",(function(){return r}))},c2d6:function(n,t,e){"use strict";var r=e("3737"),a=e.n(r);a.a},d0af:function(n,t,e){"use strict";function r(n){if(Array.isArray(n))return n}e.r(t);e("a4d3"),e("e01a"),e("d28b"),e("e260"),e("d3b7"),e("3ca3"),e("ddb0");function a(n,t){if("undefined"!==typeof Symbol&&Symbol.iterator in Object(n)){var e=[],r=!0,a=!1,o=void 0;try{for(var i,u=n[Symbol.iterator]();!(r=(i=u.next()).done);r=!0)if(e.push(i.value),t&&e.length===t)break}catch(c){a=!0,o=c}finally{try{r||null==u["return"]||u["return"]()}finally{if(a)throw o}}return e}}e("a630"),e("fb6a"),e("25f0");function o(n,t){(null==t||t>n.length)&&(t=n.length);for(var e=0,r=new Array(t);e<t;e++)r[e]=n[e];return r}function i(n,t){if(n){if("string"===typeof n)return o(n,t);var e=Object.prototype.toString.call(n).slice(8,-1);return"Object"===e&&n.constructor&&(e=n.constructor.name),"Map"===e||"Set"===e?Array.from(e):"Arguments"===e||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(e)?o(n,t):void 0}}function u(){throw new TypeError("Invalid attempt to destructure non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function c(n,t){return r(n)||a(n,t)||i(n,t)||u()}e.d(t,"default",(function(){return c}))},e374:function(n,t,e){var r=e("24fb");t=r(!1),t.push([n.i,'@charset "UTF-8";\n/**\n * 这里是uni-app内置的常用样式变量\n *\n * uni-app 官方扩展插件及插件市场（https://ext.dcloud.net.cn）上很多三方插件均使用了这些样式变量\n * 如果你是插件开发者，建议你使用scss预处理，并在插件代码中直接使用这些变量（无需 import 这个文件），方便用户通过搭积木的方式开发整体风格一致的App\n *\n */\n/**\n * 如果你是App开发者（插件使用者），你可以通过修改这些变量来定制自己的插件主题，实现自定义主题功能\n *\n * 如果你的项目同样使用了scss预处理，你也可以直接在你的 scss 代码中使用如下变量，同时无需 import 这个文件\n */\n/* 颜色变量 */\n/* 行为相关颜色 */\n/* 文字基本颜色 */\n/* 背景颜色 */\n/* 边框颜色 */\n/* 尺寸变量 */\n/* 文字尺寸 */\n/* 图片尺寸 */\n/* Border Radius */\n/* 水平间距 */\n/* 垂直间距 */\n/* 透明度 */\n/* 文章场景相关 */.pwdbt[data-v-5f01758d]{margin-top:10px}.btn[data-v-5f01758d]{margin-top:30px;margin-left:10px;margin-right:10px}',""]),n.exports=t}}]);