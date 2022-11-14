package com.tuya.sdf.demo.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author developer@tuya.com
 * @description
 * @date 2021/06/01
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdaasUserPageResult<T> {
    Integer pageNumber;
    Integer pageSize;
    Integer totalCount;
    Integer totalPages;
    List<T> results;
}
