package com.spring.secondskill.dao;

import com.spring.secondskill.domain.OrderInfo;
import com.spring.secondskill.domain.SecondsKillOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface OrderDao {

    @Select("select * from secondskill_order where user_id=#{userId} and goods_id=#{goodsId}")
    public SecondsKillOrder getSecondsKillOrderByUserIdGoodsId(@Param("userId")long userId, @Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into secondskill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    public int insertSecondsKillOrder(SecondsKillOrder secondsKillOrder);

    @Select("select * from order_info where id = #{orderId}")
    public OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    public void deleteOrders();

    @Delete("delete from secondskill_order")
    public void deleteSecondsKillOrders();

    @Select("select * from secondskill_order where goods_id=#{goodsId}")
    public List<SecondsKillOrder> listByGoodsId(@Param("goodsId") long goodsId);
}
