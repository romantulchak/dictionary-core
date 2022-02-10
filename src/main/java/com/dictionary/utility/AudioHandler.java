package com.dictionary.utility;

import com.dictionary.constant.DictionaryContents;
import com.dictionary.exception.file.AudioCreationException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class AudioHandler {

    @Value("${dictionary.app.audio.folder}")
    private String folder;

    @Value("${dictionary.app.audio.folder.name}")
    private String folderName;

    @Value("${dictionary.app.host}")
    private String host;

    private AudioHandler() {
    }

    /**
     * Decode Base64 to File and store it on disk
     * then return path to file otherwise empty string
     *
     * @param source to get file from Base64
     * @return path to file
     * @throws AudioCreationException if something went wrong during file creation
     */
    public String handleAudioFromBase64(String source) throws AudioCreationException {
        if (source != null && !source.isEmpty()) {
            String replace = source.replace(DictionaryContents.BASE64_SPLITERATOR, "");
            byte[] decodedSource = Base64.decodeBase64(replace);
            String audioName = UUID.randomUUID() + DictionaryContents.AUDIO_FILE_EXTENSION;
            Path fileDestination = Paths.get(folder, audioName);
            try {
                Files.write(fileDestination, decodedSource);
                return String.join("/", host, folderName, audioName);
            } catch (IOException e) {
                throw new AudioCreationException();
            }
        }
        return "";
    }
}
