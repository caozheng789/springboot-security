package per.cz.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Data
@TableName(value = "menu_info")
public class Menu implements Serializable{
    /**
     * 自增长主键id
     */
    @TableId(value = "menu_id")
    private Integer menuId;

    /**
     * 资源名称
     */
    @TableField(value = "menu_text")
    private String menuText;

    /**
     * 资源访问路径
     */
    @TableField(value = "menu_url")
    private String menuUrl;

    /**
     * 上级目录id
     */
    @TableField(value = "menu_parent_id")
    private String menuParentId;

    /**
     * 菜单等级
     */
    @TableField(value = "menu_level")
    private String menuLevel;

    /**
     * 该条记录创建时间
     */
    @TableField(value = "menu_create_time")
    private Date menuCreateTime;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children =  new ArrayList<>();

}