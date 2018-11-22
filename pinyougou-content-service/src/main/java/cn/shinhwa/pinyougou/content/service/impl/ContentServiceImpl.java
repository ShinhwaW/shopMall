package cn.shinhwa.pinyougou.content.service.impl;


import cn.shinhwa.pinyougou.content.service.ContentService;
import cn.shinhwa.pinyougou.mapper.TbContentMapper;
import cn.shinhwa.pinyougou.pojo.TbContent;
import cn.shinhwa.pinyougou.pojo.TbContentExample;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbContent content) {
        contentMapper.insert(content);
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent content) {
        Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
        redisTemplate.boundHashOps("content").delete(categoryId);
        System.out.println("更新前的categoryId" + categoryId);
        contentMapper.updateByPrimaryKey(content);
        System.out.println("更新后的categoryId" + content.getCategoryId());

        if (categoryId.longValue()!=content.getCategoryId().longValue()){
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();
            redisTemplate.boundHashOps("content").delete(categoryId);
            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();

        if (content != null) {
            if (content.getTitle() != null && content.getTitle().length() > 0) {
                criteria.andTitleLike("%" + content.getTitle() + "%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                criteria.andUrlLike("%" + content.getUrl() + "%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                criteria.andPicLike("%" + content.getPic() + "%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                criteria.andStatusLike("%" + content.getStatus() + "%");
            }

        }

        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {

        List<TbContent> contentList = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);

        if (contentList == null) {
            System.out.println("-----Read data from Database and store into Redis-----");
            //根据广告分类ID查询广告列表
            TbContentExample contentExample = new TbContentExample();
            TbContentExample.Criteria criteria2 = contentExample.createCriteria();
            criteria2.andCategoryIdEqualTo(categoryId);
            criteria2.andStatusEqualTo("1");//开启状态
            contentExample.setOrderByClause("sort_order");//排序
            contentList = contentMapper.selectByExample(contentExample);
            redisTemplate.boundHashOps("content").put(categoryId, contentList);
        } else {
            System.out.println("-----Read data from Redis-----");
        }
        return contentList;
    }

}
