/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package top.roothk.wechatminiutils.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity - 基类
 *
 * @author 福禄仓
 * @version 5.0
 */
@MappedSuperclass
public abstract class IdEntity<ID extends Serializable> extends BaseEntity<ID> {

    private static final long serialVersionUID = 4986248622677853489L;

    /**
     * ID
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
//    @TableGenerator(name = "tableGenerator", table = "IdGenerator")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * 判断是否为新建对象
     *
     * @return 是否为新建对象
     */
    @Transient
    public boolean isNew() {
        return getId() == null;
    }
}