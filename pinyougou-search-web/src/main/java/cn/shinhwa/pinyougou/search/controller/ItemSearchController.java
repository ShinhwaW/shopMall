package cn.shinhwa.pinyougou.search.controller;

import cn.shinhwa.pinyougou.search.service.ItemSearchService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;

    @RequestMapping("search")
    public Map<String,Object> search(@RequestBody Map searchMap) {
        return itemSearchService.search(searchMap);
    }

}
