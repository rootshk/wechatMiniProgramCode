package top.roothk.wechatminiutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.wechatminiutils.repository.WechatConfigRepository;
import top.roothk.wechatminiutils.utils.WechatMiniUtils;

import java.util.HashMap;
import java.util.Map;

@EnableCaching //启用缓存
@EnableJpaAuditing
@RestController
@RequestMapping("/")
@SpringBootApplication
public class WechatminiutilsApplication {

    @Autowired
    private WechatMiniUtils wechatMiniUtils;
    @Autowired
    private WechatConfigRepository wechatConfigRepository;

    public static void main(String[] args) {
        SpringApplication.run(WechatminiutilsApplication.class, args);
    }

    @GetMapping
    public String index(@RequestParam Long configId) {
        Map<String, String> sceneData = new HashMap<>();
        sceneData.put("promotingClerkId", "226");
        sceneData.put("shareId", "226");
        return wechatMiniUtils.getWXACodeUnlimit("pages/index/index", sceneData, null, null, null, null, wechatConfigRepository.findById(configId).get());
    }

    @GetMapping("clean")
    public void clean() {
        wechatMiniUtils.cleanAll();
    }
}
