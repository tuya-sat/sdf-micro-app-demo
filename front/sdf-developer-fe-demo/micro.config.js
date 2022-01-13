module.exports = {
  debuggerConfig: {
    target: 'http://mysaas.com:3000',
    username: 'xxx', // 超级管理员账号 
    password: 'xxx', // 超级管理员密码
    logSign: true,
    needProxyApi: ["/api", "/open-api", "/custom-api"],
  },
};
