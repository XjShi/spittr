package com.spittr.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spittr.exception.InvalidParameterException;
import com.spittr.exception.NoPermissionException;
import com.spittr.exception.spitter.SpitterNotFoundException;
import com.spittr.mapper.SpittleMapper;
import com.spittr.pojo.Attachment;
import com.spittr.pojo.Spittle;
import com.spittr.service.AttachmentService;
import com.spittr.service.CommentService;
import com.spittr.service.SpitterService;
import com.spittr.service.SpittleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpittleServiceImpl implements SpittleService {
    @Autowired
    private SpittleMapper spittleMapper;
    @Autowired
    private SpitterService spitterService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AttachmentService attachmentService;

    public List<Spittle> getList() {
        return spittleMapper.selectAll();
    }

    public List<Spittle> getListByUsername(String username) {
        if (!this.queryIfUserExistByUsername(username)) {
            throw new SpitterNotFoundException();
        }
        List<Spittle> spittles = spittleMapper.selectByUsername(username);
        return spittles;
    }

    public Spittle getSpittleDetail(long spittleId) {
        return spittleMapper.selectOne(spittleId);
    }

    public Spittle saveSpittle(Spittle spittle, String attachmentText) {
        if (!this.queryIfUserExistByUsername(spittle.getUsername())) {
            throw new SpitterNotFoundException();
        }
        if (attachmentText != null && attachmentText.length() > 0) {
            List<Attachment> attachmentList = null;
            try {
                attachmentList = convertFromJson(attachmentText);
            } catch (IOException e) {
                throw new InvalidParameterException("attachment json string error.");
            }
            attachmentService.save(attachmentList);
        }
        spittleMapper.insertSpittle(spittle);
        return spittle;
    }

    public boolean queryIfExistById(long spittleId) {
        return spittleMapper.selectSpittleCountById(spittleId) > 0;
    }

    public Spittle getLastestOne(String username) {
        if (username.length() == 0)
            throw new InvalidParameterException("invalid username");
        return spittleMapper.getLatestOne(username);
    }

    public boolean deleteSpittle(String username, long id) {
        boolean hasPermission = isSpitterHasChangePermission(username, id);
        if (!hasPermission) {
            throw new NoPermissionException("no permission to delete the spittle.");
        }
        spittleMapper.deleteSpittleById(id);
        return true;
    }

    private boolean queryIfUserExistByUsername(String username) {
        return spitterService.queryIfExistsByName(username);
    }

    public boolean isSpitterHasChangePermission(String username, long id) {
        Spittle spittle = spittleMapper.selectOne(id);
        return spittle.getUsername().equals(username);
    }

    private List<Attachment> convertFromJson(String json) throws IOException {
        Assert.hasLength(json, "json string doesn't have length.");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        List<Attachment> attachments = mapper.readValue(json, List.class);
        return attachments;
    }
}
