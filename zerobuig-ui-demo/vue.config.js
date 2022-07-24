const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    configureWebpack: {
        devServer: {
            proxy: {
                '/api': {
                    target: 'http://127.0.0.1:8080',
                    changeOrigin: true,
                    pathRewrite: {
                        '^/api': '/'
                    }
                }
            }
        },
        resolve: {
            alias: {
                '@': resolve('src')
            }
        },
    },
}
