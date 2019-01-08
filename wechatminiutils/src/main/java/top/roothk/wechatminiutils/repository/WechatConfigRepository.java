package top.roothk.wechatminiutils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.roothk.wechatminiutils.entity.WechatConfig;

@Repository
public interface WechatConfigRepository extends JpaRepository<WechatConfig, Long> {

    WechatConfig findByMiniAppId(String appId);

    WechatConfig findByMiniSecret(String secret);

    WechatConfig findByMiniAppIdAndMiniSecret(String appId, String secret);

}