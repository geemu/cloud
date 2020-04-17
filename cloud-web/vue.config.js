'use strict'
const defaultSettings = require('./src/settings.js')


module.exports = {
    publicPath: '/',
    outputDir: 'dist',
    assetsDir: 'static',
    lintOnSave: process.env.NODE_ENV === 'development',
    productionSourceMap: false,
    devServer: {
        port: defaultSettings.port,
        open: true,
        overlay: {
            warnings: false,
            errors: true
        }
    }
}