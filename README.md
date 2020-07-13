# SpringCloud-Seed快速学习框架
- http://localhost:8800
- http://localhost:9000/get?name=Jack
- http://localhost:9500/show?name=Jack
- http://localhost:9500/login?url=Jack
- http://localhost:9800/producerA/get?name=Jack
- http://localhost:9800/consumerB/show?name=Jack
- http://localhost:9800/consumerB/login?url=Jack

-- -- 
## 1、SpringCloud-SSO单点登陆
> 参考网址：https://www.cnblogs.com/huanzi-qch/p/10249227.html
> 说明：感谢原作者，图文并茂，解决了自己不少学习困难

1）测试的时候，将C-ServerConsumer中的public ModelAndView loginPage(@RequestParam String url)方法改成public
> 但是在真正使用的时候不需要将该接口进行暴露，在zuul中进行内部调用即可，可以将public改成private

2）zuul使用