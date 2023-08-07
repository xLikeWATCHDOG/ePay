package cn.watchdog.epay.service.impl;

import cn.watchdog.epay.mapper.CommoditiesMapper;
import cn.watchdog.epay.model.entity.Commodities;
import cn.watchdog.epay.service.CommoditiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xLikeWATCHDOG
 */
@Service
@Slf4j
public class CommoditiesServiceImpl extends ServiceImpl<CommoditiesMapper, Commodities> implements CommoditiesService {
    @Resource
    private CommoditiesMapper commoditiesMapper;
}
