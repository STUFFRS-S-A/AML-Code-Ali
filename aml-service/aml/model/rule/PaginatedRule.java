package com.appopay.aml.model.rule;

import com.appopay.aml.entity.ruleConfig.Rule;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedRule {

    List<Rule> data;
    Long totalDocuments;

}
