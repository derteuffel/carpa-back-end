package com.derteuffel.services;

import com.derteuffel.entities.Courrier;
import com.derteuffel.entities.Fichier;
import com.derteuffel.repositories.FichierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    @Autowired
    private FichierRepository fichierRepository;

    public Fichier store(MultipartFile file, Courrier courrier) throws IOException {

        String fileName = StringUtils.cleanPath(courrier.getObjet());
        Fichier fichier = new Fichier(fileName, file.getContentType(), file.getBytes());
        return fichierRepository.save(fichier);
    }

    public Fichier getFichier(String id){
        return fichierRepository.findById(id).get();
    }

    public Fichier getFichierByName(String name){
        return fichierRepository.findByName(name);
    }

    public Stream<Fichier> getAllFichiers(){
        return fichierRepository.findAll().stream();
    }
}
