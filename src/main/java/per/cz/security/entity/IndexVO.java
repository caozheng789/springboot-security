package per.cz.security.entity;

import lombok.Data;

/**
 * @author : zheng
 * @version : 1.0
 * @desc :
 * @date : 2020/6/8 11:06
 */
@Data
public class IndexVO {

    private String indexName;
    private idxSQL idxSQL;

}

class idxSQL{
    private boolean dynamic;
    private String properties;

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
