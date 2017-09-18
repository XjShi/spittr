package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.enums.ResponseCode;
import com.spittr.manager.TokenManager;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spittle;
import com.spittr.service.CommentService;
import com.spittr.service.SpittleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/spittle")
public class SpittleController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
    private int spittlesPerPage = DEFAULT_SPITTLES_PER_PAGE;

    @Autowired
    private SpittleService spittleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List<Spittle>> show() {
        return new BaseResponse<List<Spittle>>(ResponseCode.SUCCESS.getCode(),
                "get spittle list successfully",
                spittleService.getList());
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public BaseResponse<List<com.spittr.pojo.Spittle>> showByUsername(@PathVariable String username) {
        return new BaseResponse<List<com.spittr.pojo.Spittle>>(ResponseCode.SUCCESS.getCode(),
                "get spittle list successfully",
                spittleService.getListByUsername(username));
    }

    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<com.spittr.pojo.Spittle> post(@RequestParam("text") String text,
                                                      @RequestParam(value = "attachment", required = false) String attachment,
                                                      @RequestParam(value = "enabled", required = false) Boolean enabled,
                                                      HttpServletRequest request) {
        String username = tokenManager.getValidUsername(request);
        enabled = enabled == null ? true : enabled;
        com.spittr.pojo.Spittle spittle = new com.spittr.pojo.Spittle(username, text, enabled);
        spittle.setAttachment(attachment);
        spittleService.saveSpittle(spittle);
        spittle = spittleService.getLastestOne(username);
        return new BaseResponse<com.spittr.pojo.Spittle>(spittle);
    }

    @Authorization
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse<com.spittr.pojo.Spittle> delete(@PathVariable("id") long id,
                                                        HttpServletRequest request) {
        String username = tokenManager.getValidUsername(request);
        spittleService.deleteSpittle(username, id);
        return new BaseResponse<com.spittr.pojo.Spittle>(null, "delete spittle successfully");
    }
}
