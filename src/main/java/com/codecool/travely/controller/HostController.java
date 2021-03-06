package com.codecool.travely.controller;

import com.codecool.travely.dto.response.BadgeDto;
import com.codecool.travely.model.user.Host;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/hosts")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class HostController {

    private final HostService hostService;

    @PreAuthorize("hasRole('HOST') or hasRole('CUSTOMER')")
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

    @GetMapping("/image/badge/{badge}/download")
    public byte[] downloadBadgeImage(@PathVariable String badge) {
        return hostService.downloadBadgeImage(badge);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/earn-badges/{hostId}")
    public void earnBadges(@PathVariable Long hostId) {
        hostService.earnBadges(hostId);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('CUSTOMER')")
    @GetMapping("/host-badges/{hostId}")
    public ResponseEntity<List<BadgeDto>> getHostBadges(@PathVariable Long hostId) {
        return ResponseEntity.ok(hostService.getByHost(hostId));
    }

    @PreAuthorize("hasRole('HOST')")
    @PutMapping("/update-host/{id}")
    public ResponseEntity<String> updateHost(@PathVariable Long id, @RequestBody Host host) {
        hostService.updateHost(id, host);
        return ResponseEntity.ok("Host details have been updated.");
    }

}
