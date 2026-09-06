package com.appopay.aml.controller.rule;


import com.appopay.aml.entity.ruleConfig.Rule;
import com.appopay.aml.model.rule.PaginatedRule;
import com.appopay.aml.model.rule.RuleDTO;
import com.appopay.aml.service.rule.RuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rule")
@Validated
public class RuleController {

    @Autowired
    private RuleService ruleService;

//    @PostMapping("/testTrx")
//    public ResponseEntity<Boolean> testTrx(TransactionDTO transaction) {
//        return ResponseEntity.ok(ruleService.checkValidity(transaction));
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Rule> getRule(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(ruleService.getById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") long id) {
        ruleService.deleteEntityById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedRule> getAllRules(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(ruleService.findAll(PageRequest.of(page, size)));
    }

    @PostMapping(value = "/activate/{id}/{isActive}")
    public ResponseEntity<Rule> activate(@PathVariable("id") Integer id, @PathVariable("isActive") boolean isActive) {
        return ResponseEntity.ok().body(ruleService.activate(id, isActive));
    }

    @PostMapping(value = "/")
    public ResponseEntity<Rule> createRule(@Valid @RequestBody RuleDTO ruleDTO) {
        return ResponseEntity.ok().body(ruleService.create(ruleDTO));
    }


}
