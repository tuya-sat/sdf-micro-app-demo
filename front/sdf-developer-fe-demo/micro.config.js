module.exports = {
  debuggerConfig: {
    target: "xxx", // 需要代理的host
    username: "xxx", // 超级管理员账号
    password: "xxx", // 超级管理员密码
    logSign: true,
    needProxyApi: ["/api", "/open-api", "/custom-api"],
  },
};
