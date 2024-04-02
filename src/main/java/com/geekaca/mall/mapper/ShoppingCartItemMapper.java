package com.geekaca.mall.mapper;

import com.geekaca.mall.controller.vo.ShoppingCartItemVO;
import com.geekaca.mall.domain.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Kyon_Yjx
 * @description 针对表【tb_newbee_mall_shopping_cart_item】的数据库操作Mapper
 * @createDate 2024-03-31 16:15:33
 * @Entity com.geekaca.mall.domain.ShoppingCartItem
 */
@Mapper
public interface ShoppingCartItemMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ShoppingCartItem record);

    int insertSelective(ShoppingCartItem record);

    ShoppingCartItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShoppingCartItem record);

    int updateByPrimaryKey(ShoppingCartItem record);

    ShoppingCartItem selectByUserIdAndGoodId(Long userId, Long goodsId);

    int selectCountByUserId(Long userId);

    List<ShoppingCartItemVO> getGoodsListByUserId(Long userId);

    ShoppingCartItemVO getCartItemsByID(@Param("cartItemId") long cartItemId);

    int logicDeleteByPrimaryKey(Long id);
}
