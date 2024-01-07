package com.gm.wj.dto.base;


import org.springframework.lang.NonNull;

import static com.gm.wj.util.BeanUtils.updateProperties;


/**
*输出DTO的转换器接口。
*
*<b>实现类型必须等于DTO类型</b>

 */
public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {

    /**
     * Convert from domain.(shallow)
     *
     * @param domain domain data
     * @return converted dto data
     */
    @SuppressWarnings("unchecked")
    @NonNull
    default <T extends DTO> T convertFrom(@NonNull DOMAIN domain) {

        updateProperties(domain, this);

        return (T) this;
    }
}

