/* Copyright 2026 上海如静知华信息科技有限公司 */
import {defineConfig} from 'vite';import vue from '@vitejs/plugin-vue';
export default defineConfig({plugins:[vue()],server:{host:'0.0.0.0',port:5173,proxy:{'/api':'http://localhost:8080'}},build:{outDir:'dist'}})
