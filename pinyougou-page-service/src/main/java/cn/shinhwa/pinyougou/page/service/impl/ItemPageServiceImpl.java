package cn.shinhwa.pinyougou.page.service.impl;

import cn.shinhwa.pinyougou.mapper.TbGoodsDescMapper;
import cn.shinhwa.pinyougou.mapper.TbGoodsMapper;
import cn.shinhwa.pinyougou.mapper.TbItemCatMapper;
import cn.shinhwa.pinyougou.mapper.TbItemMapper;
import cn.shinhwa.pinyougou.page.service.ItemPageService;
import cn.shinhwa.pinyougou.pojo.TbGoods;
import cn.shinhwa.pinyougou.pojo.TbGoodsDesc;
import cn.shinhwa.pinyougou.pojo.TbItem;
import cn.shinhwa.pinyougou.pojo.TbItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Value("${pageDir}")
    private String pageDir;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private TbGoodsMapper tbGoodsMapper;

    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Autowired
    private TbItemMapper itemMapper;


    @Override
    public boolean genItemHtml(Long goodsId) {

        try {
            Configuration configuration = freeMarkerConfig.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Map dataModel = new HashMap();
            //加载商品数据
            TbGoods goods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods", goods);
            //加载商品扩展表数据
            TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc", goodsDesc);

            //3.商品分类
            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1", itemCat1);
            dataModel.put("itemCat2", itemCat2);
            dataModel.put("itemCat3", itemCat3);

            //4.SKU列表
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andStatusEqualTo("1");//状态为有效
            criteria.andGoodsIdEqualTo(goodsId);//指定SPU ID
            example.setOrderByClause("is_default desc");//按照状态降序，保证第一个为默认
            List<TbItem> itemList = itemMapper.selectByExample(example);
            dataModel.put("itemList", itemList);


            Writer out = new FileWriter(pageDir + goodsId + ".html");
            template.process(dataModel, out);
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean deleteItemHtml(Long[] goodsIds) {
        try {
            for (Long goodsId : goodsIds) {
                new File(pageDir + goodsId + ".html").delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
