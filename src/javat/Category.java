package javat;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class Category {

    JdbcTemplate template;

    private String catcode;

    private String catdesc;

    public String getCatcode(){
        return catcode;
    }

    public void setCatcode(String catcode){
        this.catcode = catcode;
    }

    public String getCatdesc(){
        return catdesc;
    }

    public void setCatdesc(String catdesc){
        this.catdesc = catdesc;
    }

    public List<Map<String, Object>> getCat (String cat){
        return template.queryForList("SELECT * from category where catcode=?", cat);
    }
}
