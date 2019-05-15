package cur.pro.controller;

import cur.pro.exception.NotFoundException;
import cur.pro.services.GameService;
import cur.pro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequestMapping(value = "game")
public class GameController extends AbstractController {

    @Autowired
    private GameService gameService;


    @GetMapping(value = "{id}")
    public String gameInfo(@PathVariable(value = "id") Integer id) {
        if (!gameService.exists(id)) {
            throw new NotFoundException();
        }
        this.getModel().addAttribute("id", id);
        return "gameInfo";
    }

    /**
     * 根据游戏id获取游戏详情
     * @param id
     * @return
     */
    @PostMapping(value = "{id}")
    @ResponseBody
    public Result selectById(@PathVariable(value = "id") Integer id) {
        return gameService.getById(id);
    }

}
