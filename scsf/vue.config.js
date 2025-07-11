module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Spring Boot 后端地址
        changeOrigin: true,
        pathRewrite: { '^/api': '/api' }
      }
    }
  }
}
