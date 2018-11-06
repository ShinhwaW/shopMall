package cn.shinhwa.pinyougou.sellergoods.service;

import cn.shinhwa.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 品牌接口
 * @author Administrator
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();

	public PageResult findPage(int pageNum,int pageSize);

	public void add(TbBrand tbBrand);

	public void update(TbBrand brand);

	public TbBrand findOne(Long id);

	public void delete(Long[] ids);

	public PageResult findPage(TbBrand brand,int pageNum,int pageSize);

	public List<Map> selectOptionList();


}
