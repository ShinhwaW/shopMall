package cn.shinhwa.pinyougou.manager.controller;

import cn.shinhwa.pinyougou.pojo.TbBrand;
import cn.shinhwa.pinyougou.sellergoods.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAll")
    public List<TbBrand> findAll() {
        return brandService.findAll();
    }

    @RequestMapping("/findPage")
    public PageResult findPage(int page, int size) {
        return brandService.findPage(page, size);
    }
    @RequestMapping("/add")
    public Result add(@RequestBody TbBrand brand){

        try {
            brandService.add(brand);
            return new Result(true,"添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"添加失败！");
        }

    }

}
