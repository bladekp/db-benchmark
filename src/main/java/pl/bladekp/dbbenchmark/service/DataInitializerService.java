package pl.bladekp.dbbenchmark.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import pl.bladekp.dbbenchmark.config.ClientDatabaseContextHolder;

import javax.persistence.Entity;
import javax.sql.DataSource;
import java.util.EnumSet;
import java.util.stream.Stream;

import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import static org.hibernate.tool.schema.TargetType.DATABASE;
import static org.hibernate.tool.schema.TargetType.STDOUT;

@Component
@Slf4j
public class DataInitializerService {

    private final DataSource dataSource;

    @Autowired
    public DataInitializerService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void createDatabase() {
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder().applySetting(DATASOURCE, dataSource);
        MetadataSources metadataSources = new MetadataSources(registryBuilder.build());
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        for (BeanDefinition def : scanner.findCandidateComponents("pl.bladekp.dbbenchmark.model")) {
            try {
                metadataSources.addAnnotatedClass(Class.forName(def.getBeanClassName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        new SchemaExport()
                .setFormat(true)
                .setHaltOnError(false)
                .create(EnumSet.of(STDOUT, DATABASE), metadataSources.buildMetadata());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        Stream
                .of(ClientDatabaseContextHolder.ClientDatabaseEnum.values())
                .filter(ClientDatabaseContextHolder.ClientDatabaseEnum::isEnabled)
                .forEach(db -> ClientDatabaseContextHolder.execute(this::createDatabase, db));
    }
}
