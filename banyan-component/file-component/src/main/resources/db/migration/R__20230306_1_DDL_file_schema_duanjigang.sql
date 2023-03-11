DROP TABLE IF EXISTS file_detail;
CREATE TABLE file_detail
(
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    url               varchar(512) NOT NULL COMMENT '文件访问地址',
    size              bigint(20)   DEFAULT NULL COMMENT '文件大小，单位字节',
    filename          varchar(256) DEFAULT NULL COMMENT '文件名称',
    original_filename varchar(256) DEFAULT NULL COMMENT '原始文件名',
    base_path         varchar(256) DEFAULT NULL COMMENT '基础存储路径',
    path              varchar(256) DEFAULT NULL COMMENT '存储路径',
    ext               varchar(32)  DEFAULT NULL COMMENT '文件扩展名',
    content_type      varchar(32)  DEFAULT NULL COMMENT 'MIME类型',
    platform          varchar(32)  DEFAULT NULL COMMENT '存储平台',
    th_url            varchar(512) DEFAULT NULL COMMENT '缩略图访问路径',
    th_filename       varchar(256) DEFAULT NULL COMMENT '缩略图名称',
    th_size           bigint(20)   DEFAULT NULL COMMENT '缩略图大小，单位字节',
    th_content_type   varchar(32)  DEFAULT NULL COMMENT '缩略图MIME类型',
    object_id         varchar(32)  DEFAULT NULL COMMENT '文件所属对象id',
    object_type       varchar(32)  DEFAULT NULL COMMENT '文件所属对象类型，例如用户头像，评价图片',
    attr              text COMMENT '附加属性',
    is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除',
    created_by VARCHAR(100) NOT NULL DEFAULT '' COMMENT '创建用户',
    modified_by VARCHAR(100) NOT NULL DEFAULT '' COMMENT '更新用户',
    gmt_create  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    gmt_modified DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='文件记录表';

ALTER TABLE file_detail ADD UNIQUE uniq_p_key (url, is_deleted);

