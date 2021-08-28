package com.codecool.travely.controller;

import com.codecool.travely.model.Host;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/hosts")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('HOST')")
@AllArgsConstructor
public class HostController {

    private final HostService hostService;

    @GetMapping("/{id}")
    public ResponseEntity<Host> getById(@PathVariable Long id) {
        return new ResponseEntity<>(hostService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/image/{hostId}/download")
    public byte[] downloadImage(@PathVariable Long hostId) {
        return hostService.downloadImage(hostId);
    }

    @PostMapping(
            path = "/image/upload/{hostId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadHostProfileImage(@PathVariable("hostId") Long hostId,
                                       @RequestParam("file") MultipartFile file) {
        hostService.uploadHostProfileImage(hostId, file);
    }


}
