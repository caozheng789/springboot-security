package per.cz.security.entity.dto;

import lombok.Data;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/16 10:52
 */
@Data
public class LikedCountDTO {

    private String key;
    private Integer value;

    public LikedCountDTO(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
}
