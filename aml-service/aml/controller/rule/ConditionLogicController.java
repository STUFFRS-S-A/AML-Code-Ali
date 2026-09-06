package com.appopay.aml.controller.rule;


import com.appopay.aml.entity.ruleConfig.ConditionLogic;
import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.model.rule.PaginatedConditionLogic;
import com.appopay.aml.model.rule.PaginatedRule;
import com.appopay.aml.service.rule.ConditionLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("conditionLogic")
public class ConditionLogicController {

    @Autowired
    private ConditionLogicService conditionLogicService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConditionLogic> getConditionLogic(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(conditionLogicService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedConditionLogic> getAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(conditionLogicService.findAll(PageRequest.of(page, size)));
    }


    @PostMapping(value = "/{ruleId}/{allRequired}")
    public ResponseEntity<ConditionLogic> createConditionLogic(@PathVariable("ruleId") long ruleId, @PathVariable("allRequired") boolean allRequired) {
        return ResponseEntity.ok().body(conditionLogicService.create(ruleId, allRequired));
    }

}
