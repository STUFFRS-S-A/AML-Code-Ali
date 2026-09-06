package com.appopay.aml.model.rule;

import com.appopay.aml.entity.ruleConfig.ConditionLogic;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedConditionLogic {

    List<ConditionLogic> data;
    Long totalDocuments;

}
