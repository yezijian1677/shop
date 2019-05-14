package cur.pro.controller;

import cur.pro.entity.dto.GameDTO;
import cur.pro.exception.NotFoundException;
import cur.pro.services.TagService;
import cur.pro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "tag")
public class TagController extends AbstractController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/{tag}/games")
    public String getGames(@PathVariable(value = "tag") Integer tag) {
        String name = tagService.getNameById(tag);
        if (name == null) {
            throw new NotFoundException();
        }
        this.getModel().addAttribute("id", tag);
        this.getModel().addAttribute("name", name);
        return "taggames";
    }

    @PostMapping(value = "/{tag}/games")
    @ResponseBody
    public Result<List<GameDTO>> getGamesByTag(@PathVariable(value = "tag") Integer tag,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        return tagService.getGamesByTag(tag, page);
    }

}
