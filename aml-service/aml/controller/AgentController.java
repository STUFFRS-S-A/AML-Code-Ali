package com.appopay.aml.controller;


import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.Merchant;
import com.appopay.aml.model.*;
import com.appopay.aml.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agent")
public class AgentController {

    private static final Logger log = LoggerFactory.getLogger(AgentController.class);
    @Autowired
    private AgentService agentService;

    @PostMapping(value = "/")
    public ResponseEntity<Agent> createAgent(@RequestBody AgentDTO request) {
        log.info("creating agent with name: " + request.getCompRegName());
        return ResponseEntity.ok().body(agentService.createAgent(request));
    }

    @PutMapping(value = "/")
    public ResponseEntity<Agent> updateOne(@RequestBody AgentDTO request) {
        log.info("updating agent with id: " + request.getId());
        return ResponseEntity.ok().body(agentService.updateOne(request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Agent> getById(@PathVariable("id") Long id) {
        log.info("retreiving agent with id: " + id);
        return ResponseEntity.ok().body(agentService.getById(id));
    }

    @PostMapping(value = "/findAll/{page}/{size}")
    public ResponseEntity<PaginatedAgent> getAllAgents(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return ResponseEntity.ok().body(agentService.findAll(PageRequest.of(page, size)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable("id") long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> uploadFile(@ModelAttribute() UploadDocumentDTO uploadDocumentDTO) {
        log.info("uploading file  with agent id: " + uploadDocumentDTO.getId());
        return ResponseEntity.ok(agentService.uploadDocuments(uploadDocumentDTO));
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<String>> updateFile(@ModelAttribute() UploadDocumentDTO uploadDocumentDTO) {
        log.info("uploading file  with merchant id: " + uploadDocumentDTO.getId());
        return ResponseEntity.ok(agentService.updateDocuments(uploadDocumentDTO));
    }

    @PutMapping("/clearField/{id}")
    public ResponseEntity<Void> clearField(@PathVariable Long id, @RequestParam DeleteOption fieldOption) {
        agentService.clearField(fieldOption,id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/vip")
    public ResponseEntity<Agent> updateFile(@RequestBody MPADetailsDTO mpaDetailsDTO) {
        log.info("uptating vip agent with id: " + mpaDetailsDTO.getMpaId());
        return ResponseEntity.ok(agentService.updateToVIP(mpaDetailsDTO));
    }


}
