package cn.watchdog.epay.service.impl;

import cn.watchdog.epay.mapper.OrdersMapper;
import cn.watchdog.epay.model.entity.Orders;
import cn.watchdog.epay.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xLikeWATCHDOG
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;
}
