package cn.shinhwa.pinyougou.sellergoods.service.impl;

import cn.shinhwa.pinyougou.mapper.TbBrandMapper;
import cn.shinhwa.pinyougou.pojo.TbBrand;
import cn.shinhwa.pinyougou.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }
}
