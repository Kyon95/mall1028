package com.geekaca.mall.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @TableName tb_newbee_mall_goods_info
 */
@Data
public class GoodsInfo implements Serializable {
    /**
     * 商品表主键id
     */
    private Long goodsId;

    /**
     * 商品名
     */
    private String goodsName;

    /**
     * 商品简介
     */
    private String goodsIntro;

    /**
     * 关联分类id
     */
    private Long goodsCategoryId;

    /**
     * 商品主图
     */
    private String goodsCoverImg;

    /**
     * 商品轮播图
     */
    private String goodsCarousel;

    /**
     * 商品详情
     */
    private String goodsDetailContent;

    /**
     * 商品价格
     */
    private Integer originalPrice;

    /**
     * 商品实际售价
     */
    private Integer sellingPrice;

    /**
     * 商品库存数量
     */
    private Integer stockNum;

    /**
     * 商品标签
     */
    private String tag;

    /**
     * 商品上架状态 1-下架 0-上架
     */
    private Integer goodsSellStatus;

    /**
     * 添加者主键id
     */
    private Integer createUser;

    /**
     * 商品添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改者主键id
     */
    private Integer updateUser;

    /**
     * 商品修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        GoodsInfo other = (GoodsInfo) that;
        return (this.getGoodsId() == null ? other.getGoodsId() == null : this.getGoodsId().equals(other.getGoodsId()))
                && (this.getGoodsName() == null ? other.getGoodsName() == null : this.getGoodsName().equals(other.getGoodsName()))
                && (this.getGoodsIntro() == null ? other.getGoodsIntro() == null : this.getGoodsIntro().equals(other.getGoodsIntro()))
                && (this.getGoodsCategoryId() == null ? other.getGoodsCategoryId() == null : this.getGoodsCategoryId().equals(other.getGoodsCategoryId()))
                && (this.getGoodsCoverImg() == null ? other.getGoodsCoverImg() == null : this.getGoodsCoverImg().equals(other.getGoodsCoverImg()))
                && (this.getGoodsCarousel() == null ? other.getGoodsCarousel() == null : this.getGoodsCarousel().equals(other.getGoodsCarousel()))
                && (this.getGoodsDetailContent() == null ? other.getGoodsDetailContent() == null : this.getGoodsDetailContent().equals(other.getGoodsDetailContent()))
                && (this.getOriginalPrice() == null ? other.getOriginalPrice() == null : this.getOriginalPrice().equals(other.getOriginalPrice()))
                && (this.getSellingPrice() == null ? other.getSellingPrice() == null : this.getSellingPrice().equals(other.getSellingPrice()))
                && (this.getStockNum() == null ? other.getStockNum() == null : this.getStockNum().equals(other.getStockNum()))
                && (this.getTag() == null ? other.getTag() == null : this.getTag().equals(other.getTag()))
                && (this.getGoodsSellStatus() == null ? other.getGoodsSellStatus() == null : this.getGoodsSellStatus().equals(other.getGoodsSellStatus()))
                && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGoodsId() == null) ? 0 : getGoodsId().hashCode());
        result = prime * result + ((getGoodsName() == null) ? 0 : getGoodsName().hashCode());
        result = prime * result + ((getGoodsIntro() == null) ? 0 : getGoodsIntro().hashCode());
        result = prime * result + ((getGoodsCategoryId() == null) ? 0 : getGoodsCategoryId().hashCode());
        result = prime * result + ((getGoodsCoverImg() == null) ? 0 : getGoodsCoverImg().hashCode());
        result = prime * result + ((getGoodsCarousel() == null) ? 0 : getGoodsCarousel().hashCode());
        result = prime * result + ((getGoodsDetailContent() == null) ? 0 : getGoodsDetailContent().hashCode());
        result = prime * result + ((getOriginalPrice() == null) ? 0 : getOriginalPrice().hashCode());
        result = prime * result + ((getSellingPrice() == null) ? 0 : getSellingPrice().hashCode());
        result = prime * result + ((getStockNum() == null) ? 0 : getStockNum().hashCode());
        result = prime * result + ((getTag() == null) ? 0 : getTag().hashCode());
        result = prime * result + ((getGoodsSellStatus() == null) ? 0 : getGoodsSellStatus().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsIntro=").append(goodsIntro);
        sb.append(", goodsCategoryId=").append(goodsCategoryId);
        sb.append(", goodsCoverImg=").append(goodsCoverImg);
        sb.append(", goodsCarousel=").append(goodsCarousel);
        sb.append(", goodsDetailContent=").append(goodsDetailContent);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", stockNum=").append(stockNum);
        sb.append(", tag=").append(tag);
        sb.append(", goodsSellStatus=").append(goodsSellStatus);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}