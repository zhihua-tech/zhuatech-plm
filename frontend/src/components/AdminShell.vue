<!-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ -->
<script setup>
const groups=[
 {label:'产品数据',items:[['/admin/dashboard','产品驾驶舱','⌂'],['/admin/products','产品与 BOM','▱'],['/admin/documents','技术文档','▤']]},
 {label:'研发协同',items:[['/admin/changes','工程变更','⇄'],['/admin/projects','研发项目','◫'],['/admin/reviews','技术评审','✓']]},
 {label:'平台管理',items:[['/admin/classification','分类与编码','#'],['/admin/access','组织与权限','♙'],['/admin/audit','审计追踪','↻']]}
]
</script>
<template><div class="shell">
 <aside class="side"><div class="brand"><span>ZH</span><div><b>ZhuaTech PLM</b><small>PRODUCT DATA CENTER</small></div></div><button class="workspace-pick"><i></i><span><b>工业控制产品线</b><small>产品数据工作区</small></span><em>⌄</em></button>
 <section v-for="group in groups" :key="group.label"><p>{{group.label}}</p><nav><RouterLink v-for="i in group.items" :key="i[1]" :to="i[0]"><i>{{i[2]}}</i><span>{{i[1]}}</span><b v-if="i[1]==='工程变更'">4</b></RouterLink></nav></section>
 <div class="side-foot"><span>顾</span><div><b>顾远</b><small>产品平台主管</small></div><button>•••</button></div>
 </aside>
 <main class="main"><header class="top"><div><small>产品数据中心</small><b><slot name="title"/></b></div><label class="global-search">⌕ <input placeholder="搜索产品、物料、图文档或变更单"/><kbd>⌘ K</kbd></label><div class="top-actions"><button>?</button><button>♢<i></i></button><span>2026 / 07 / 30</span></div></header><div class="content"><slot/></div><footer>© 2026 上海如静知华信息科技有限公司 · <a href="https://www.zhuatech.cn/">知华科技</a></footer></main>
 </div></template>
