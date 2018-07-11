package com.spittr.web;

import com.spittr.annotation.Authorization;
import com.spittr.enums.ResponseCode;
import com.spittr.exception.InvalidParameterException;
import com.spittr.manager.TokenManager;
import com.spittr.pojo.BaseResponse;
import com.spittr.pojo.Spittle;
import com.spittr.service.SpittleService;
import org.apache.commons.lang3.StringUtils;
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

    private static final int DEFAULT_SPITTLES_PER_PAGE = 25;
    private int spittlesPerPage = DEFAULT_SPITTLES_PER_PAGE;

    @Autowired
    private SpittleService spittleService;
    @Autowired
    private TokenManager tokenManager;

    @Authorization
    @RequestMapping(value = "/timeline")
    public BaseResponse<List<Spittle>> getTimeline(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") String pageIndex,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize,
                                                   HttpServletRequest request) {
        if (!StringUtils.isNumeric(pageIndex) || !StringUtils.isNumeric(pageSize)) {
            throw new InvalidParameterException("请求参数错误");
        }

        String username = tokenManager.getValidUsername(request);
        List<Spittle> spittleList = spittleService.getListByUsernameAndPage(username, Integer.valueOf(pageIndex), Integer.valueOf(pageSize));
        return new BaseResponse<>(spittleList);
    }


    /**
     * get spittle list
     * @param username
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public BaseResponse<List<com.spittr.pojo.Spittle>> show(@RequestParam(value = "username", required = false) String username,
                                                            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") String pageIndex,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "20") String pageSize) {
        logger.info("get spittle of [" + username + "]. pageIndex = " + pageIndex + " pageSize = " + pageSize);

        if (!StringUtils.isNumeric(pageIndex) || !StringUtils.isNumeric(pageSize)) {
            throw new InvalidParameterException("请求参数错误");
        }

        return new BaseResponse<List<Spittle>>(ResponseCode.SUCCESS.getCode(),
                "get spittle list successfully",
                spittleService.getListByUsernameAndPage(username, Integer.parseInt(pageIndex), Integer.parseInt(pageSize)));
    }

    /**
     * post a new spittle
     * @param text          text content
     * @param attachment    attachment, when more than one separate with ","
     * @param enabled       enabled or not, it decide whether this spittle is shown in list
     * @return
     */
    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse<com.spittr.pojo.Spittle> post(@RequestParam("text") String text,
                                                      @RequestParam(value = "attachment", required = false) String attachment,
                                                      @RequestParam(value = "enabled", required = false) Boolean enabled,
                                                      HttpServletRequest request) {
        logger.info("post spittle with text: " + text + ", attachment is: " + attachment);

        String username = tokenManager.getValidUsername(request);
        enabled = enabled == null ? true : enabled;
        spittleService.saveSpittle(username, text, attachment, enabled);

        Spittle spittle = spittleService.getLastestOne(username);
        return new BaseResponse<com.spittr.pojo.Spittle>(spittle);
    }

    /**
     * delete a spittle by id
     * @param id        spittle id
     * @return
     */
    @Authorization
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public BaseResponse<com.spittr.pojo.Spittle> delete(@PathVariable("id") long id,
                                                        HttpServletRequest request) {
        logger.info("delete spittle[" + id + "]");

        String username = tokenManager.getValidUsername(request);
        spittleService.deleteSpittle(username, id);

        return new BaseResponse<com.spittr.pojo.Spittle>(null, "delete spittle successfully");
    }
}
