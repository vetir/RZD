package ru.task.siyatovskiy.rzd.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hierarchy {

    private Long id;
    private Long objectId;
    private Long parentObjId;
    private Long changeId;
    private Long prevId;
    private Long nextId;
    private String updateDate;
    private String startDate;
    private String endDate;
    private int isActive;
}
