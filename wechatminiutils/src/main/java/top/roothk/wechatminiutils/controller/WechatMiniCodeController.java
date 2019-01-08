package top.roothk.wechatminiutils.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.roothk.wechatminiutils.repository.WechatConfigRepository;
import top.roothk.wechatminiutils.utils.JsonUtils;
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
    @Autowired
    private WechatConfigRepository wechatConfigRepository;

    @GetMapping("/base64")
    public String getBase64(@RequestParam(name = "promotingClerkId", required = false) String promotingClerkId,
                            @RequestParam(name = "shareId", required = false) String shareId,
                            @RequestParam(name = "pages", required = false) String pages,
                            @RequestParam(name = "configId", required = false, defaultValue = "1") Long configId) {
        Map<String, String> sceneData = new HashMap<>();
        if (StringUtils.isNotBlank(promotingClerkId) || StringUtils.isNotBlank(shareId)) {
            if (StringUtils.isNotBlank(promotingClerkId))
                sceneData.put("pId", promotingClerkId);
            if (StringUtils.isNotBlank(shareId))
                sceneData.put("shareId", shareId);
        } else {
            sceneData.put("a", "a");
        }
        return wechatMiniUtils.getWXACodeUnlimit(pages, sceneData, null, null, null, null, wechatConfigRepository.findById(configId).get());
    }

    @PostMapping("/base64")
    public JSONObject getBase64(@RequestBody JSONObject rec) {
        String pages = null;
        Long configId = null;
        Integer width = null;
        Boolean autoColor = null;
        Boolean isHyaline = null;
        JSONArray sceneDataArray = null;
        Map<String, Object> lineColor = null;
        Map<String, String> sceneData = new HashMap<>();

        if (rec.containsKey("pages"))
            pages = rec.getString("pages");
        if (rec.containsKey("configId"))
            configId = rec.getLong("configId");
        if (rec.containsKey("sceneData"))
            sceneDataArray = rec.getJSONArray("sceneData");
        if (rec.containsKey("width"))
            width = rec.getInteger("width");
        if (rec.containsKey("autoColor"))
            autoColor = rec.getBoolean("autoColor");
        if (rec.containsKey("lineColor"))
            lineColor = rec.getJSONObject("lineColor").getInnerMap();
        if (rec.containsKey("isHyaline"))
            isHyaline = rec.getBoolean("isHyaline");

        if (null != sceneDataArray && sceneDataArray.size() > 0) {
            for (int i = 0; i < sceneDataArray.size(); i++) {
                JSONObject data = sceneDataArray.getJSONObject(i);
                sceneData.put(data.getString("key"), data.getString("value"));
            }
        } else {
            sceneData.put("a", "a");
        }

        if (StringUtils.isBlank(pages))
            return JsonUtils.getRoot(400, "pages为空", null);
        if (null == configId || configId <= 0)
            return JsonUtils.getRoot(400, "configId不能为空或者不能小于1", null);

        return JsonUtils.getRoot(200, "success",
                wechatMiniUtils.getWXACodeUnlimit(
                        pages, sceneData, width, autoColor, lineColor, isHyaline,
                        wechatConfigRepository.findById(configId).get()));
    }

}
