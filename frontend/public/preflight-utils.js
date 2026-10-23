/* 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export const MAX_BYTES=262144;
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export function validateSource(value){if(typeof value!=='string'||!value.trim())throw new Error('请先选择文件或填写CSV内容');if(new TextEncoder().encode(value).length>MAX_BYTES)throw new Error('CSV超过256 KiB');return value;}
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export function csvEncode(rows){return '\uFEFF'+rows.map(row=>row.map(cell=>{let v=String(cell??'');if(/^[=+@\-]/.test(v.trimStart())||/^[\t\r]/.test(v))v="'"+v;return '"'+v.replaceAll('"','""')+'"';}).join(',')).join('\r\n')+'\r\n';}
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export function paginate(rows,page,size=50){const pages=Math.max(1,Math.ceil(rows.length/size));const current=Math.min(pages,Math.max(1,page));return {rows:rows.slice((current-1)*size,current*size),page:current,pages};}
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export function localDate(date=new Date()){return [date.getFullYear(),String(date.getMonth()+1).padStart(2,'0'),String(date.getDate()).padStart(2,'0')].join('-');}
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
export function basicHeader(username,password){if(!username||!password||username.includes(':'))throw new Error('请输入有效账号和密码，账号不能含冒号');const bytes=new TextEncoder().encode(username+':'+password);return 'Basic '+btoa(String.fromCharCode(...bytes));}
