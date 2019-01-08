package top.roothk.wechatminiutils.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class WechatConfig extends IdEntity<Long> {

    private static final long serialVersionUID = -7107339462034428410L;

    /**
     * 微信小程序AppId
     */
    private String miniAppId;

    /**
     * 微信小程序Secret
     */
    private String miniSecret;

}
