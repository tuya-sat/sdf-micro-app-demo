module.exports = {
  debuggerConfig: {
    target: "http://mysaas.com:3000", // Local deployed server
    username: "", // Entry SaaS admin user name
    password: "", // Entry SaaS password
    logSign: true,
    needProxyApi: ["/api", "/open-api", "/custom-api"],
  },
};
