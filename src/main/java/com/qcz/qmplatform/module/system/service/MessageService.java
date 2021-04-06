package com.qcz.qmplatform.module.system.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qcz.qmplatform.module.system.domain.Message;
import com.qcz.qmplatform.module.system.mapper.MessageMapper;
import com.qcz.qmplatform.module.system.vo.MessageVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author quchangzhong
 * @since 2021-03-26
 */
@Service
public class MessageService extends ServiceImpl<MessageMapper, Message> {

    public List<MessageVO> getList(Message message) {
        return baseMapper.selectList(message);
    }

    /**
     * 查询接收人的未读的通知数
     *
     * @param receiver 接收人id
     */
    public Map<String, Long> selectNoReadCount(String receiver) {
        Map<String, Long> res = new HashMap<>();

        List<Map<String, Long>> typeCounts = baseMapper.selectTypeCount(0, receiver);
        long sum = 0;
        for (Map<String, Long> typeCount : typeCounts) {
            Long count = typeCount.get("count");
            res.put(String.valueOf(typeCount.get("type")), count);
            sum += count;
        }
        res.put("all", sum);
        return res;
    }

    /**
     * 设置消息已读
     *
     * @param messageIds 消息id
     */
    public boolean setHasRead(String[] messageIds) {
        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("message_id", messageIds);
        updateWrapper.set("read", 1);
        return this.update(updateWrapper);
    }

}
