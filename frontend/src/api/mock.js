/* Copyright 2026 上海如静知华信息科技有限公司 */
export const products=[
 {code:'PRD-SV750',name:'知华伺服驱动器',model:'SV-750',version:'V2.4',stage:'量产',owner:'顾远',status:'已发布',progress:100},
 {code:'PRD-IO240',name:'工业远程 IO 模块',model:'IO-240',version:'V1.8',stage:'量产',owner:'林澈',status:'已发布',progress:100},
 {code:'PRD-GW100',name:'边缘计算网关',model:'GW-100',version:'V0.9',stage:'试制',owner:'周岚',status:'验证中',progress:76},
 {code:'PRD-HMI10',name:'10.1 寸工业触控屏',model:'HMI-101',version:'V1.2',stage:'设计',owner:'许亦',status:'设计中',progress:43}
]
export const parts=[
 {no:'ASM-SV750-MAIN',name:'伺服驱动主控组件',category:'组件',version:'B.3',spec:'含主控板与功率板',state:'已发布'},
 {no:'PCB-CTRL-207',name:'伺服主控 PCB',category:'电子件',version:'C.2',spec:'6 层沉金板 / 1.6mm',state:'已发布'},
 {no:'IC-MCU-1048',name:'工业级 MCU',category:'电子件',version:'A.1',spec:'Cortex-M7 / 480MHz',state:'替代评估'},
 {no:'ENC-AL-075',name:'铝合金散热外壳',category:'结构件',version:'B.1',spec:'6061-T6 阳极氧化',state:'已发布'},
 {no:'FAN-DC-4020',name:'直流散热风扇',category:'标准件',version:'A.0',spec:'24V / 40×40×20mm',state:'已发布'}
]
export const changes=[
 {no:'ECR-20260730-018',title:'替换主控芯片并升级驱动固件',product:'SV-750',type:'设计变更',risk:'高',owner:'顾远',due:'08-01',status:'评审中'},
 {no:'ECN-20260729-011',title:'散热器表面处理规范优化',product:'SV-750',type:'工艺变更',risk:'中',owner:'林澈',due:'08-04',status:'待实施'},
 {no:'ECR-20260728-006',title:'网关电源输入范围扩展',product:'GW-100',type:'设计变更',risk:'中',owner:'周岚',due:'08-06',status:'草稿'},
 {no:'ECN-20260724-003',title:'IO 模块标签模板更新',product:'IO-240',type:'文档变更',risk:'低',owner:'许亦',due:'07-29',status:'验证中'}
]
export const documents=[
 {no:'DOC-SV750-SPEC',name:'SV-750 产品技术规格书',type:'产品规格',version:'V2.4',owner:'顾远',status:'已发布',updated:'今天 09:42'},
 {no:'DWG-SV750-ASM',name:'SV-750 总装二维图',type:'工程图纸',version:'B.3',owner:'林澈',status:'已发布',updated:'昨天 16:18'},
 {no:'SOP-SV750-TEST',name:'SV-750 出厂测试规范',type:'测试规范',version:'V1.7',owner:'陈静',status:'评审中',updated:'07-28 14:06'},
 {no:'DOC-GW100-DVT',name:'GW-100 DVT 验证计划',type:'验证文件',version:'V0.6',owner:'周岚',status:'编制中',updated:'07-27 11:25'}
]
export const bom=[
 {level:1,no:'ASM-SV750-MAIN',name:'伺服驱动主控组件',version:'B.3',qty:'1 套',source:'自制',effect:'V2.4'},
 {level:2,no:'PCB-CTRL-207',name:'伺服主控 PCB',version:'C.2',qty:'1 块',source:'自制',effect:'V2.4'},
 {level:3,no:'IC-MCU-1048',name:'工业级 MCU',version:'A.1',qty:'1 颗',source:'采购',effect:'V2.4'},
 {level:1,no:'ENC-AL-075',name:'铝合金散热外壳',version:'B.1',qty:'1 件',source:'采购',effect:'V2.4'},
 {level:1,no:'FAN-DC-4020',name:'直流散热风扇',version:'A.0',qty:'2 件',source:'采购',effect:'V2.4'}
]
