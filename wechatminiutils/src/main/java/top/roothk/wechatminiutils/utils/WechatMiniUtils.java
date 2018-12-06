package top.roothk.wechatminiutils.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utils - 微信小程序
 *
 * @author roothk
 * @version 1.0
 */
@Component
public class WechatMiniUtils {

    private final static String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
    private final static String GET_WXACODE_UNLIMIT_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${top.roothk.config.wechat.miniProgram.appId}")
    private String appId;
    @Value("${top.roothk.config.wechat.miniProgram.secret}")
    private String secret;

    /**
     * 获取redis中的小程序AccessToken
     *
     * @return
     */
    public String getAccessToken() {
        if (stringRedisTemplate.hasKey("wechatMiniAccessToken"))
            return stringRedisTemplate.opsForValue().get("wechatMiniAccessToken");
        else
            return getwechatMiniToken();
    }

    /**
     * 获取小程序AccessToken
     *
     * @return
     */
    private String getwechatMiniToken() {
//        Setting setting = SystemUtils.getSetting();
        RestTemplate restTemplate = new RestTemplate();
        String accessTokenData = restTemplate.getForObject(GET_ACCESSTOKEN_URL
                + "?grant_type=client_credential&appid=" + appId
                + "&secret=" + secret, String.class);
        JSONObject data = JSONObject.parseObject(accessTokenData);
        stringRedisTemplate.opsForValue().set("wechatMiniAccessToken", data.getString("access_token"), data.getLong("expires_in"), TimeUnit.SECONDS);
        return data.getString("access_token");
    }

    /**
     * 获取小程序分享二维码
     *
     * @param page          分享路径
     * @param sceneData     分享携带参数(函数内会改为k=v&k=v&k=v)(参数拼接之后不能超过32字节)最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
     * @param width         二维码宽度
     * @param autoColor     自动配置线条颜色
     * @param lineColor     autoColor为false: 使用 rgb 设置颜色 {"r":0,"g":0,"b":0}
     * @param isHyaline     是否需要透明底色
     * @return              二维码图片的base64
     */
    public String getWXACodeUnlimit(String page, Map<String, String> sceneData, Integer width, Boolean autoColor, Map<String, String> lineColor, Boolean isHyaline) {
        JSONObject data = new JSONObject();
        data.put("page", page);
        data.put("scene", format(sceneData));
        if (null != width)
            data.put("width", width);
        if (null != autoColor)
            data.put("auto_color", autoColor);
        if (null != lineColor)
            data.put("line_color", JSONObject.toJSON(lineColor).toString());
        if (null != isHyaline)
            data.put("is_hyaline", isHyaline);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<String>(data.toJSONString(), headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(GET_WXACODE_UNLIMIT_URL + "?access_token=" + getAccessToken(), HttpMethod.POST,
                httpEntity, byte[].class);
        BASE64Encoder encoder = new BASE64Encoder();
        return response != null ? encoder.encode(response.getBody()) : "";
    }

    private String format(Map<String, String> data) {
        StringBuffer str = new StringBuffer();
        data.forEach((k, v) -> {
            str.append("&").append(k).append("=").append(v);
        });
        str.deleteCharAt(0);
        return str.toString();
    }

}