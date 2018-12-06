package top.roothk.wechatminiutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.wechatminiutils.utils.WechatMiniUtils;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class WechatminiutilsApplication {

    @Autowired
    private WechatMiniUtils wechatMiniUtils;

    public static void main(String[] args) {
        SpringApplication.run(WechatminiutilsApplication.class, args);
    }

    @GetMapping
    public String index() {
        Map<String, String> sceneData = new HashMap<>();
        sceneData.put("promotingClerkId", "226");
        sceneData.put("shareId", "226");
        return wechatMiniUtils.getWXACodeUnlimit("pages/index/index", sceneData, null, null, null, null);
    }
}
