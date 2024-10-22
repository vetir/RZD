package ru.task.siyatovskiy.rzd.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "files")
@Getter
@Setter
public class FileProperties {

    private String addressObjects;
    private String addressHierarchy;
}
