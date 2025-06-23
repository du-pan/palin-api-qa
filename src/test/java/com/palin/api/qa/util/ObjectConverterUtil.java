package com.palin.api.qa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.testng.Assert.assertNotNull;

@Slf4j
public class ObjectConverterUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JSONObject getEntityJsonObject(final String fileSource) {
        log.info("Construct an JSON Object.");
        if (fileSource.matches("^(.+)/([^/]+)(.json)$")) {
            log.info("Get JSON Object from file: ['{}']", fileSource);
            String fileContent = "";
            try {
                log.info("Read from file [{}]", fileSource);
                fileContent = new String(Files.readAllBytes(Path.of(fileSource)));
            } catch (IOException e) {
                log.error("IOException exception detected.");
                log.error("File cannot be read.");
            }
            final JSONObject jsonObject = new JSONObject(fileContent);
            assertNotNull(jsonObject);
            return jsonObject;
        } else {
            log.info("File source is not valid OR body has already been sent as JSONObject.");
            log.info("Returning empty JSONObject.");
            return new JSONObject();
        }
    }
}
