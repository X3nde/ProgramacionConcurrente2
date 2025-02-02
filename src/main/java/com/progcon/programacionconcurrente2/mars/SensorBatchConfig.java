package com.progcon.programacionconcurrente2.mars;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SensorBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<SensorData> sensorDataReader() {
        return new FlatFileItemReaderBuilder<SensorData>()
                .name("sensorDataReader")
                .resource(new ClassPathResource("sensors.csv"))
                .delimited()
                .names("id", "value")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(SensorData.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<SensorData, SensorData> sensorDataProcessor() {
        return sensorData -> {
            sensorData.setValue(sensorData.getValue() * 2); // Ejemplo: Duplicar el valor
            return sensorData;
        };
    }

    @Bean
    public ItemWriter<SensorData> sensorDataWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<SensorData>()
                .dataSource(dataSource)
                .sql("INSERT INTO sensors (id, value) VALUES (:id, :value)")
                .beanMapped()
                .build();
    }

    @Bean
    public Step sensorProcessingStep(ItemReader<SensorData> reader,
                                     ItemProcessor<SensorData, SensorData> processor,
                                     ItemWriter<SensorData> writer) {
        return stepBuilderFactory.get("sensorProcessingStep")
                .<SensorData, SensorData>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job sensorProcessingJob(Step sensorProcessingStep) {
        return jobBuilderFactory.get("sensorProcessingJob")
                .start(sensorProcessingStep)
                .build();
    }
}
