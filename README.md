# SpringCloud-Seed快速学习框架
- http://localhost:8800
- http://localhost:9000/get?name=Jack
- http://localhost:9500/show?name=Jack
- http://localhost:9800/producerA/get?name=Jack
- http://localhost:9800/consumerB/show?name=Jack

-- -- 
## 1、SpringCloud-SSO单点登陆
> 参考网址：https://www.cnblogs.com/huanzi-qch/p/10249227.html
> 说明：感谢原作者，图文并茂，解决了自己不少学习困难

1）zuul使用
> 对比原版进行了改动，原版中将权限判断拆成一个微服务，我直接将权限判断和Zuul放在一起
> 因为代码中只对producerA和consumerB前缀的请求做了权限拦截，没有登录的时候会跳转登录页面
> “已经登录”直接返回请求的数据
> “没有登录”或者“登录超时”则再次跳转登录页面

