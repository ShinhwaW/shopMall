package cn.shinhwa.pinyougou.pojogroup;

import cn.shinhwa.pinyougou.pojo.TbGoods;
import cn.shinhwa.pinyougou.pojo.TbGoodsDesc;
import cn.shinhwa.pinyougou.pojo.TbItem;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: shinhwa
 * @Date: 2018/11/15 23:10
 * @Description: 商品组合类
 */
public class Goods implements Serializable {

    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> itemList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbGoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(TbGoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<TbItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TbItem> itemList) {
        this.itemList = itemList;
    }
}
