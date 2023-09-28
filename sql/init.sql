CREATE TABLE IF NOT EXISTS orders
(
    id            BIGINT AUTO_INCREMENT COMMENT '订单序号' PRIMARY KEY,
    orderNumber   VARCHAR(256)                       NOT NULL COMMENT '订单号',
    name          VARCHAR(256)                       NOT NULL COMMENT '金额',
    money         VARCHAR(256)                       NOT NULL COMMENT '金额',
    paymentType   VARCHAR(256)                       NULL COMMENT '支付方式',
    paymentStatus BOOLEAN  DEFAULT FALSE             NOT NULL COMMENT '支付状态',
    callBack      BOOLEAN  DEFAULT FALSE             NOT NULL COMMENT '回调状态',
    createTime    DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    paymentTime   DATETIME                           NULL COMMENT '付款时间',
    isDelete      TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除(0为未删除,1为已删除)'
) COMMENT '订单列表';