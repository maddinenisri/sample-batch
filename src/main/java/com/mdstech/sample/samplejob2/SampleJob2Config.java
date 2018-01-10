package com.mdstech.sample.samplejob2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Configuration
@EnableBatchProcessing
public class SampleJob2Config {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("file:/Users/srini/IdeaProjects/java8-file-handler/target/data_*.csv")
    private Resource[] resources;

    @Bean(name="readFiles")
    public Job readFiles(@Qualifier("slaveStep") Step step) {
        return jobBuilderFactory.get("readFiles").incrementer(new RunIdIncrementer()).
                start(step).build();
    }

    @Bean(name="slaveStep")
    public Step slaveStep(@Qualifier("custoemrWrtier") ItemWriter<CustomerDomain> itemWriter, ChunkListener chunkListener, StepExecutionListener stepExecutionListener) {
        return stepBuilderFactory.get("slaveStep").<CustomerDomain, CustomerDomain>chunk(5000)
                .reader(multiResourceItemReader()).writer(itemWriter)
//                .listener(processListener).listener(itemReaderListener())
//                .listener(itemWriterListener())
                .listener(stepExecutionListener)
                .listener(chunkListener)
                .build();
    }

    @Bean
    public MultiResourceItemReader<CustomerDomain> multiResourceItemReader() {
        log.info("Multi reader called");
        MultiResourceItemReader<CustomerDomain> resourceItemReader = new MultiResourceItemReader<CustomerDomain>();
        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @Bean
    public FlatFileItemReader<CustomerDomain> reader() {
        log.info("reader called");
        FlatFileItemReader<CustomerDomain> reader = new FlatFileItemReader<CustomerDomain>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        String[] tokens = {"customerId","name","houseNo","streetName","state", "zipCode"};
        tokenizer.setNames(tokens);
        //reader.setStrict(false);
//        reader.setResource(new ClassPathResource( "/Users/srini/IdeaProjects/java8-file-handler/target/"+filename));
        DefaultLineMapper<CustomerDomain> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFiledSetMapper());
        reader.setLineMapper(lineMapper);
        return reader;
    }


//    @Autowired
//    ResourcePatternResolver resoursePatternResolver;
//
//    @Autowired
//    private JobBuilderFactory jobs;
//
//    @Autowired
//    private StepBuilderFactory steps;
//
//    @Bean(name = "flatfileToDbPartitioningJob")
//    public Job flatfileToDbPartitioningJob() throws UnexpectedInputException, MalformedURLException, ParseException {
//        return jobs.get("flatfileToDbPartitioningJob")
//                .start(partitionStep())
//                .build();
//    }
//
//    @Bean
//    public Step partitionStep() throws UnexpectedInputException, MalformedURLException, ParseException {
//        return steps.get("partitionStep")
//                .partitioner("slaveStep", partitioner())
//                .step(slaveStep())
//                .taskExecutor(taskExecutor())
//                .build();
//    }
//
//    @Bean
//    public Step slaveStep() throws UnexpectedInputException, MalformedURLException, ParseException {
//        return steps.get("slaveStep")
//                .<CustomerDomain, CustomerDomain>chunk(1)
//                .reader(reader(null))
//                .writer(writer(null))
//                .build();
//    }
//
//    @Bean
//    public CustomMultiResourcePartitioner partitioner() {
//        CustomMultiResourcePartitioner partitioner = new CustomMultiResourcePartitioner();
//        Resource[] resources;
//        try {
//            resources = resoursePatternResolver.getResources("file:/Users/srini/IdeaProjects/java8-file-handler/target/data_*.csv");
//        } catch (IOException e) {
//            throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
//        }
//        partitioner.setResources(resources);
//        return partitioner;
//    }
//
//    @Bean
//    @StepScope
//    public FlatFileItemReader<CustomerDomain> reader(@Value("#{stepExecutionContext[fileName]}") String filename) throws UnexpectedInputException, ParseException {
//        FlatFileItemReader<CustomerDomain> reader = new FlatFileItemReader<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        String[] tokens = {"customerId","name","houseNo","streetName","state", "zipCode"};
//        tokenizer.setNames(tokens);
//        //reader.setStrict(false);
//        reader.setResource(new ClassPathResource( "/Users/srini/IdeaProjects/java8-file-handler/target/"+filename));
//        DefaultLineMapper<CustomerDomain> lineMapper = new DefaultLineMapper<>();
//        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(new RecordFiledSetMapper());
//        reader.setLineMapper(lineMapper);
//        return reader;
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(5);
//        taskExecutor.setCorePoolSize(5);
//        taskExecutor.setQueueCapacity(5);
//        taskExecutor.afterPropertiesSet();
//        return taskExecutor;
//    }
//
//    @Bean
//    @StepScope
//    public ItemWriter<CustomerDomain> writer(EntityManagerFactory emf) {
//        JpaItemWriter<CustomerDomain> jpaItemWriter = new CustomerWriter();
//        jpaItemWriter.setEntityManagerFactory(emf);
//        return jpaItemWriter;
//    }
}
