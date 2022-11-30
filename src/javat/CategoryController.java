package javat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequestMapping
@Controller
public class CategoryController {

    @Autowired
    Catdao dao;

    @GetMapping(path="/category")
    public String showCategorypage(ModelMap model) throws ClassNotFoundException, SQLException {

        List<Category> list = dao.display();
        model.addAttribute("categorylist", list);

        model.put("id", list.get(0).getCatcode());

        model.put("desc", list.get(0).getCatdesc());

        return "category";
    }

    @GetMapping(path="/")
    public String showCategorypage2(ModelMap model) throws ClassNotFoundException, SQLException {

        List<Category> list = dao.display();
        model.addAttribute("categorylist", list);

        model.put("id", list.get(0).getCatcode());

        model.put("desc", list.get(0).getCatdesc());

        return "category";
    }

    @GetMapping(path="/add-todo")
    public String showtodopage(){
        return "catser";
    }

    @PostMapping(path="/add-todo")
    public String addTodo(ModelMap model, @RequestParam String catcode, @RequestParam String catdesc) throws SQLException, ClassNotFoundException{
        List<Map<String, Object>> x = dao.getcat(catcode);
        String iid1, ccdesc1;

        x.forEach(rowMap ->{
            String iid = (String) rowMap.get("catcode");
            model.put("id", iid);
            String ccdesc = (String) rowMap.get("catdesc");
            model.put("desc", ccdesc);
        });

        if(x.isEmpty()==false){
            model.put("errorMessage", "Record Existing");
            return "redirect:/category";
        }

        Category cc = new Category();
        cc.setCatcode(catcode);
        cc.setCatdesc(catdesc);
        dao.insertData(cc);
        model.addAttribute("cc", cc);

        return "redirect:/category";
    }

    @GetMapping(path="/update-todo")
    public String showUpdateTodoPage(ModelMap model, @RequestParam(defaultValue = "") String id) throws SQLException, ClassNotFoundException{

        model.put("id", id);

        List<Map<String, Object>> x = dao.getcat(id);
        String iid1, ccdesc1;

        x.forEach(rowMap ->{
            String iid = (String) rowMap.get("catcode");
            model.put("id", iid);
            String ccdesc = (String) rowMap.get("catdesc");
            model.put("desc", ccdesc);
        });

        return "catedit";
    }

    @PostMapping(path="/update-todo")
    public String showUpdate(ModelMap model, @RequestParam String catcode, @RequestParam String catdesc) throws SQLException, ClassNotFoundException{

        String iid = (String) model.get("id");

        Category cc = new Category();

        cc.setCatcode(catcode);
        cc.setCatdesc(catdesc);

        dao.EditData(cc, iid);

        return "redirect:/";
    }

    @GetMapping(path="/delete-todo")
    public String deleteTodo(ModelMap model, @RequestParam String id) throws SQLException, ClassNotFoundException{

        dao.deleteData(id);

        model.clear();
        return "redirect:/";
    }
}
