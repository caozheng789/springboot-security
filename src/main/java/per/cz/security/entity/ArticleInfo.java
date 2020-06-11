package per.cz.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 文章表
 * @author Administrator
 */
@Data
@TableName(value = "article_info")
@ToString
public class ArticleInfo extends BaseEntity<Long> {


    private String userName;

    private String thematicUrl;

    private String title;

    /**
     * 一级菜单
     */
    private String artFirstMenu;

    /**
     * 二级菜单
     */
    private String artSubMenu;


    private String data;

}