package com.spring.secondskill.dao;

import com.spring.secondskill.domain.SecondsKillGoods;
import com.spring.secondskill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    //取出所有GoodsVo的数据
    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.secondskill_price from secondskill_goods sg left join goods g on sg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    //根据ID取GoodsVo
    @Select("select g.*, sg.stock_count, sg.start_date, sg.end_date, sg.secondskill_price from secondskill_goods sg left join goods g on sg.goods_id = g.id where g.id = #{goodsId}")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);


    //减少储备数量
    @Update("update secondskill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(SecondsKillGoods g);

    //重置储备数量
    @Update("update secondskill_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    public int resetStock(SecondsKillGoods g);

}
