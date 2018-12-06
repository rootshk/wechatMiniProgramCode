wechatMiniProgramCode
微信小程序二维码生成器

技术栈: Spring boot v2.1.1 + Redis

获取二维码图片的base64编码数据
\[GET\] /common/wechatMiniCode/base64
{
  pages: "/pages/index/index",
  promotingClerkId: "xxx" //参数按需要自定义
  shareId: "xxx"
}
