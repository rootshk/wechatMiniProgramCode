# wechatMiniProgramCode
## 微信小程序二维码生成器

### 技术栈: Spring boot v2.1.1 + Redis + Cache + Hibernate + MySQL
### 食用方法: 按照application.yml的数据库配置好数据库名和用户密码, 运行Redis, 然后直接运行即可

获取二维码图片的base64编码数据

\[GET\] /common/wechatMiniCode/base64

{

  pages: "/pages/index/index",
  
  promotingClerkId: "xxx", //参数按需要自定义
  
  shareId: "xxx",
  
  configId: 1
  
}

\[POST\] /common/wechatMiniCode/base64

{

  pages: "/pages/index/index",
  
  sceneData: [
  
      {
          key: "promotingClerkId",
          value: "xxx"
      }
  
  ], //参数按需要自定义
  
  width: 200,
  
  autoColor: true,
  
  isHyaline: true,
  
  lineColor: {
  
      r: 0,
      
      g: 0,
      
      b: 0
      
  },
  
  configId: 1
  
}
