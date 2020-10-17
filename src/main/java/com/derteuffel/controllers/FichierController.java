package com.derteuffel.controllers;

import com.derteuffel.entities.Courrier;
import com.derteuffel.entities.Fichier;
import com.derteuffel.helpers.FileResponseBody;
import com.derteuffel.messages.ResponseMessage;
import com.derteuffel.repositories.CourrierRepository;
import com.derteuffel.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/upload")
public class FichierController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private CourrierRepository courrierRepository;


    @PostMapping("/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        System.out.println("je suis la !!!");

        Courrier courrier = courrierRepository.getOne(id);
        String message = "";
        try {

            fileStorageService.store(file, courrier);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileResponseBody>> getListFiles() {
        List<FileResponseBody> files = fileStorageService.getAllFichiers().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new FileResponseBody(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Fichier fichier = fileStorageService.getFichier(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fichier.getName() + "\"")
                .body(fichier.getData());
    }


    @GetMapping("/files/name/{id}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String name) {
        Fichier fichier = fileStorageService.getFichierByName(name);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fichier.getName() + "\"")
                .body(fichier.getData());
    }
}
