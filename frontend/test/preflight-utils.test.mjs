/* 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
import test from 'node:test';import assert from 'node:assert/strict';import {readFile} from 'node:fs/promises';
const source=await readFile(new URL('../public/preflight-utils.js',import.meta.url),'utf8');
const {validateSource,csvEncode,paginate,localDate,basicHeader}=await import('data:text/javascript;base64,'+Buffer.from(source).toString('base64'));
test('nonempty CSV remains byte-for-byte unchanged',()=>assert.equal(validateSource('a\r\n"b,c"\r\n'),'a\r\n"b,c"\r\n'));
test('blank and non-text inputs rejected',()=>{for(const s of ['', '  ',null,12])assert.throws(()=>validateSource(s));});
test('UTF8 byte budget is enforced',()=>assert.throws(()=>validateSource('中'.repeat(90000))));
test('CSV encoding handles commas quotes and newlines',()=>assert.equal(csvEncode([['a,b','a"b','a\nb']]),'\uFEFF"a,b","a""b","a\nb"\r\n'));
test('spreadsheet formula prefixes escaped',()=>{const r=csvEncode([['=SUM(1)',' +cmd','-2','@cmd','\t=2']]);for(const s of ["'=SUM(1)","' +cmd","'-2","'@cmd","'\t=2"])assert.ok(r.includes(s));});
test('HTML-looking content stays data not markup',()=>assert.ok(csvEncode([['<img src=x onerror=alert(1)>']]).includes('<img')));
test('decimal strings are not converted into JS floats',()=>assert.ok(csvEncode([['9007199254740993.01']]).includes('9007199254740993.01')));
test('pagination has bounded first and last pages',()=>{const rows=Array.from({length:123},(_,i)=>i);assert.equal(paginate(rows,-2).page,1);assert.equal(paginate(rows,99).page,3);assert.equal(paginate(rows,3).rows.length,23);});
test('empty result has one empty page',()=>assert.deepEqual(paginate([],9),{rows:[],page:1,pages:1}));
test('local date avoids UTC rollover',()=>assert.equal(localDate(new Date(2026,9,23,0,1)),'2026-10-23'));
test('Basic header uses UTF8',()=>assert.equal(Buffer.from(basicHeader('用户','密码').slice(6),'base64').toString(),'用户:密码'));
test('Basic header rejects missing fields and colon username',()=>{assert.throws(()=>basicHeader('u:x','p'));assert.throws(()=>basicHeader('','p'));assert.throws(()=>basicHeader('u',''));});
