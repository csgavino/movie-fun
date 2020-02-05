package org.superbiz.moviefun.blobstore;

import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class FileStore implements BlobStore {

    @Override
    public void put(Blob blob) throws IOException {
        File targetFile = new File(blob.name);

        targetFile.delete();
        targetFile.getParentFile().mkdirs();
        targetFile.createNewFile();

        try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(blob.inputStream, outputStream);
        }
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        File file = new File(name);
        if (!file.exists()) {
            return Optional.empty();
        }

        Tika tika = new Tika();

        Blob blob = new Blob(name, new FileInputStream(file), tika.detect(file));
        return Optional.of(blob);
    }

    @Override
    public void deleteAll() {
        // ...
    }
}