package com.jayhello.spi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CustomServiceLoader<S> {

    private static final String PREFIX = "META-INF/services/";

    private final Class<S> service;
    private final ClassLoader loader;

    private CustomServiceLoader(Class<S> service, ClassLoader loader) {
        this.service = service;
        this.loader = loader;
    }

    public static <S> CustomServiceLoader<S> load(Class<S> service) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return new CustomServiceLoader<>(service, cl);
    }

    public List<S> loadAll() {
        List<S> providers = new ArrayList<>();
        String fullName = PREFIX + service.getName();
        try {
            Enumeration<URL> configs = loader.getResources(fullName);
            while (configs.hasMoreElements()) {
                URL url = configs.nextElement();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int ci = line.indexOf('#');
                        if (ci >= 0) {
                            line = line.substring(0, ci);
                        }
                        line = line.trim();
                        if (!line.isEmpty()) {
                            Class<?> c = Class.forName(line, false, loader);
                            S p = service.cast(c.getDeclaredConstructor().newInstance());
                            providers.add(p);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load service " + service.getName() + ": " + e.getMessage(), e);
        }
        return providers;
    }
}
