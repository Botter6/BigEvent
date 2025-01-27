import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
export default defineConfig({
    plugins: [
        vue(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        host: '0.0.0.0',
        // port: 4000,
        proxy: {
            '/api': {
                target: 'http://localhost:8080',
                // secure: false, // 请求是否为https
                changeOrigin: true,
                rewrite:(path)=>path.replace(/^\/api/,'') //api替换为'',
            },
        },
    }
})
