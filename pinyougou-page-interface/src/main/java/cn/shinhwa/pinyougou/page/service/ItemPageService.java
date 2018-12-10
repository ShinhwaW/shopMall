package cn.shinhwa.pinyougou.page.service;

public interface ItemPageService {
    /**
     * 生成商品详细页
     *
     * @param goodsId
     */
    public boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详细页
     * @param goodsId
     * @return
     */
    public boolean deleteItemHtml(Long[] goodsIds);


}
