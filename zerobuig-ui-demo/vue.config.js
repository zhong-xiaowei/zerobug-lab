const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    devServer: {
        host: '0.0.0.0',
        port: 10000,
        open: true,
        proxy: {
            [process.env.VUE_APP_BASE_API]: {
                target: 'http://127.0.0.1:8080',
                changeOrigin: true,
                pathRewrite: {
                    ['^' + process.env.VUE_APP_BASE_API]: ''
                }
            }
        }
    },
    configureWebpack: {
        resolve: {
            alias: {
                '@': resolve('src')
            }
        },
    },
}
