package animals.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public enum StorageService {
    JSON(new JsonMapper()),
    YAML(new YAMLMapper()),
    XML(new XmlMapper());

    private static final Logger log = Logger.getLogger(StorageService.class.getName());
    private static final String baseName;

    private final ObjectMapper objectMapper;

    StorageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static StorageService of(final String type) {
        return StorageService.valueOf(type.toUpperCase());
    }

    static {
        final Properties properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream("application.xml"));
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
        baseName = properties.getProperty("baseName", "animals");
        log.config(() -> "Storage file base name is " + baseName);
    }

    private File getFile() {
        return new File(baseName + "." + this.name().toLowerCase());
    }

    public void load(final KnowledgeTree tree) {
        final var file = getFile();
        log.entering(StorageService.class.getName(), "load", file.getAbsolutePath());
        try {
            tree.setRoot(objectMapper.readValue(file, TreeNode.class));
        } catch (IOException error) {
            log.warning(error.getMessage());
        }
        log.exiting(StorageService.class.getName(), "load");
    }

    public void save(final KnowledgeTree tree) {
        final var file = getFile();
        log.entering(StorageService.class.getName(), "save", file.getAbsolutePath());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, tree.getRoot());
        } catch (IOException error) {
            log.warning(error.getMessage());
        }
        log.exiting(StorageService.class.getName(), "save", "Knowledge Base saved successfully.");
    }
}
