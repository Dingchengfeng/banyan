package com.standard.banyan.common.i18n;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * 国际化 语言枚举类
 * @author dingchengfeng
 */
@Getter
@ToString
public enum LanguageEnum {
    /**
     * 美式英文
     */
    LANGUAGE_EN_US(Locale.US.toString()),
    /**
     * 简体中文
     */
    LANGUAGE_ZH_CN(Locale.SIMPLIFIED_CHINESE.toString());

    private final String language;

    LanguageEnum(String language) {
        this.language = language;
    }

    /**
     * 获取指定语言类型(如果没有对应的语言类型,则返回中文)
     *
     * @param language 语言类型
     * @return
     */
    public static String getLanguageType(String language) {
        if (StringUtils.isEmpty(language)) {
            return LANGUAGE_ZH_CN.language;
        }
        for (LanguageEnum languageEnum : LanguageEnum.values()) {
            if (languageEnum.language.equalsIgnoreCase(language)) {
                return languageEnum.language;
            }
        }
        return LANGUAGE_ZH_CN.language;
    }
}
