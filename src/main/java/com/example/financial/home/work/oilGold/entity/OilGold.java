package com.example.financial.home.work.oilGold.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 原油黄金价格数据表
 * </p>
 *
 * @author auto-generate
 * @since 2026-04-25 22:20:22
 */
@Getter
@Setter
@TableName("oil_gold")
public class OilGold extends Model<OilGold> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品种名称
     */
    @TableField("price_name")
    private String priceName;

    /**
     * 交易日期
     */
    @TableField("date")
    private String date;

    /**
     * 开盘价
     */
    @TableField("open")
    private BigDecimal open;

    /**
     * 收盘价
     */
    @TableField("close")
    private BigDecimal close;

    /**
     * 最高价
     */
    @TableField("high")
    private BigDecimal high;

    /**
     * 最低价
     */
    @TableField("low")
    private BigDecimal low;

    /**
     * 成交量
     */
    @TableField("volume")
    private BigDecimal volume;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private Date updatedAt;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}
