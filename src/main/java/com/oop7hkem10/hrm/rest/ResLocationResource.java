package com.oop7hkem10.hrm.rest;

import com.oop7hkem10.hrm.model.ResLocationDTO;
import com.oop7hkem10.hrm.service.ResLocationService;
import com.oop7hkem10.hrm.util.ReferencedException;
import com.oop7hkem10.hrm.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/resLocations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResLocationResource {

    private final ResLocationService resLocationService;

    public ResLocationResource(final ResLocationService resLocationService) {
        this.resLocationService = resLocationService;
    }

    @GetMapping
    public ResponseEntity<List<ResLocationDTO>> getAllResLocations() {
        return ResponseEntity.ok(resLocationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResLocationDTO> getResLocation(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(resLocationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createResLocation(
            @RequestBody @Valid final ResLocationDTO resLocationDTO) {
        final Long createdId = resLocationService.create(resLocationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateResLocation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ResLocationDTO resLocationDTO) {
        resLocationService.update(id, resLocationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteResLocation(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = resLocationService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        resLocationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
