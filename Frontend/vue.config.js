const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        proxy: {
            '/auth': {
                target: 'http://localhost:8080/', // Spring boot의 주소 및 포트
                changeOrigin: true,
            },
            '/attractions': {
                target: 'http://localhost:8080/', // Spring boot의 주소 및 포트
                changeOrigin: true,
            },
            '/areas': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            },
            '/reviews': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            },
            '/plans': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            },
            '/users': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            },
            '/notices': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            },
        },
    },
});
