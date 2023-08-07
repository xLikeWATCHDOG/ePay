CREATE TABLE IF NOT EXISTS orders
(
    id              BIGINT AUTO_INCREMENT COMMENT '订单序号' PRIMARY KEY,
    orderNumber     VARCHAR(256)                       NOT NULL COMMENT '订单号',
    commodityNumber TINYINT                            NOT NULL COMMENT '商品编号',
    money           VARCHAR(256)                       NOT NULL COMMENT '金额',
    paymentType     VARCHAR(256)                       NULL COMMENT '支付方式',
    paymentStatus   BOOLEAN  DEFAULT FALSE             NOT NULL COMMENT '支付状态',
    sign            VARCHAR(256)                       NOT NULL COMMENT '签名',
    userName        VARCHAR(256)                       NOT NULL COMMENT '用户名称',
    userEmail       VARCHAR(256)                       NOT NULL COMMENT '用户邮箱',
    callBack        BOOLEAN  DEFAULT false             NOT NULL COMMENT '回调状态',
    createTime      DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    paymentTime     DATETIME                           NULL COMMENT '付款时间',
    isDelete        TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除(0为未删除,1为已删除)'
) COMMENT '订单列表';

CREATE TABLE IF NOT EXISTS commodities
(
    id           BIGINT AUTO_INCREMENT COMMENT '商品编号' PRIMARY KEY,
    name         VARCHAR(256)                       NOT NULL COMMENT '商品名称',
    money        VARCHAR(256)                       NOT NULL COMMENT '金额',
    introduction TEXT                               NOT NULL COMMENT '简介',
    createTime   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    updateTime   DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isDelete     TINYINT  DEFAULT 0                 NOT NULL COMMENT '是否删除(0为未删除,1为已删除)'
) COMMENT '商品列表';