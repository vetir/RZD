package ru.task.siyatovskiy.rzd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private Long id;
    private Long objectId;
    private String objectGuid;
    private Long changeId;
    private String name;
    private String typeName;
    private Integer level;
    private Integer operTypeId;
    private Long prevId;
    private Long nextId;
    private String updateDate;
    private String startDate;
    private String endDate;
    private Integer isActual;
    private Integer isActive;
}
