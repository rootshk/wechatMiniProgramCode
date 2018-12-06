package top.roothk.wechatminiutils.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.wechatminiutils.utils.WechatMiniUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 小程序码
 */
@RestController
@RequestMapping("/common/wechatMiniCode")
public class WechatMiniCodeController {

    @Autowired
    private WechatMiniUtils wechatMiniUtils;

    @GetMapping("/base64")
    public String getBase64(@RequestParam(name = "promotingClerkId", required = false) String promotingClerkId,
                            @RequestParam(name = "shareId", required = false) String shareId,
                            @RequestParam(name = "pages", required = false) String pages) {
        Map<String, String> sceneData = new HashMap<>();
        if (StringUtils.isNotBlank(promotingClerkId) || StringUtils.isNotBlank(shareId)) {
            if (StringUtils.isNotBlank(promotingClerkId))
                sceneData.put("pId", promotingClerkId);
            if (StringUtils.isNotBlank(shareId))
                sceneData.put("shareId", shareId);
        } else {
            sceneData.put("a", "a");
        }
        return wechatMiniUtils.getWXACodeUnlimit(pages, sceneData, null, null, null, null);
    }

}
