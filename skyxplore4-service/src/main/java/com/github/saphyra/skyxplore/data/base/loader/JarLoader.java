package com.github.saphyra.skyxplore.data.base.loader;

import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
//TODO remove
class JarLoader<K, V> extends AbstractLoader<V> {
    private final String jarPath;
    private final AbstractDataService<K, V> dataService;

    JarLoader(Class<V> clazz, AbstractDataService<K, V> dataService, FileUtil fileUtil) {
        super(clazz, fileUtil);
        this.dataService = dataService;
        this.jarPath = dataService.getJarPath();
    }

    @Override
    public void load() {
        log.debug("Loading from JAR... JarPath: {}", jarPath);

        JarFile jarFile = getJarEntries();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            loadJarEntry(jarFile, entries.nextElement());
        }
    }

    private JarFile getJarEntries() {
        try {
            CodeSource src = AbstractDataService.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                JarURLConnection urlcon = (JarURLConnection) (jar.openConnection());
                return urlcon.getJarFile();
            } else {
                throw new IllegalStateException("CodeSource cannot be resolved");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadJarEntry(JarFile jarFile, JarEntry entry) {
        String entryName = entry.getName();
        if (entryName.startsWith(jarPath) && entryName.endsWith(".json")) {
            String contentString = readJarEntry(jarFile, entry);
            V content = fileUtil.readValue(contentString, clazz);
            dataService.addItem(content, entryName.substring(jarPath.length()));
        }
    }

    private String readJarEntry(JarFile jarFile, JarEntry entry) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(entry)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new String(builder.toString().getBytes(), StandardCharsets.UTF_8);
    }
}
