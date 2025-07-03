package com.gravifox.tvb.domain.admin.controller;

import com.gravifox.tvb.domain.admin.domain.Term;
import com.gravifox.tvb.domain.admin.dto.NewTerm;
import com.gravifox.tvb.domain.admin.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/v1/terms")
public class AdminTermController {
    @Autowired
    private TermService termService;

    @PostMapping
    public ResponseEntity<String> createTerm(@RequestBody NewTerm term) {
        return ResponseEntity.ok(termService.createTerm(term));
    }
}
