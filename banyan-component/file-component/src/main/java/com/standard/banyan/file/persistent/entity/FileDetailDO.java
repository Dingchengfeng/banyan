package com.standard.banyan.file.persistent.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件记录表
 * </p>
 *
 * @author duanjigang
 * @since 2023-03-06
 */
@Getter
@Setter
@TableName("file_detail")
public class FileDetailDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件访问地址
     */
    @TableField("url")
    private String url;

    /**
     * 文件大小，单位字节
     */
    @TableField("size")
    private Long size;

    /**
     * 文件名称
     */
    @TableField("filename")
    private String filename;

    /**
     * 原始文件名
     */
    @TableField("original_filename")
    private String originalFilename;

    /**
     * 基础存储路径
     */
    @TableField("base_path")
    private String basePath;

    /**
     * 存储路径
     */
    @TableField("`path`")
    private String path;

    /**
     * 文件扩展名
     */
    @TableField("ext")
    private String ext;

    /**
     * MIME类型
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 存储平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 缩略图访问路径
     */
    @TableField("th_url")
    private String thUrl;

    /**
     * 缩略图名称
     */
    @TableField("th_filename")
    private String thFilename;

    /**
     * 缩略图大小，单位字节
     */
    @TableField("th_size")
    private Long thSize;

    /**
     * 缩略图MIME类型
     */
    @TableField("th_content_type")
    private String thContentType;

    /**
     * 文件所属对象id
     */
    @TableField("object_id")
    private String objectId;

    /**
     * 文件所属对象类型，例如用户头像，评价图片
     */
    @TableField("object_type")
    private String objectType;

    /**
     * 附加属性
     */
    @TableField("attr")
    private String attr;

    /**
     * 逻辑删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    /**
     * 创建用户
     */
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 更新用户
     */
    @TableField(value = "modified_by", fill = FieldFill.INSERT_UPDATE)
    private String modifiedBy;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;
}
