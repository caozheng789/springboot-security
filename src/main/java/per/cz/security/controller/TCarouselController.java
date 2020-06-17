package per.cz.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import per.cz.security.entity.PluginPage;
import per.cz.security.entity.TCarousel;
import per.cz.security.result.ResultData;
import per.cz.security.service.TCarouselService;


@RestController
@RequestMapping("/tCarousels")
public class TCarouselController {

    @Autowired
    private TCarouselService tCarouselService;


    /**
     * 保存
     * @param tCarousel
     * @return
     */
    @PostMapping
    public TCarousel save(@RequestBody TCarousel tCarousel) {
        tCarouselService.save(tCarousel);

        return tCarousel;
    }

    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public TCarousel get(@PathVariable Long id) {
        return tCarouselService.getById(id);
    }

    /**
     * 修改
     * @param tCarousel
     * @return
     */
    @PutMapping
    public TCarousel update(@RequestBody TCarousel tCarousel) {
        tCarouselService.update(tCarousel);

        return tCarousel;
    }

    /**
     * 列表
     * @param pluginPage
     * @return
     */
    @GetMapping
    public ResultData list(PluginPage<TCarousel> pluginPage) {
        return tCarouselService.list(pluginPage);
    }

    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        tCarouselService.delete(id);
    }
}
