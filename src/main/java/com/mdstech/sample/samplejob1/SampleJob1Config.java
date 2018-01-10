package com.mdstech.sample.samplejob1;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class SampleJob1Config {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("input/samplejob1_data.csv")
    private Resource inputCsv;

    @Bean
    public ItemReader<TransactionDomain> itemReader()
            throws UnexpectedInputException, ParseException {
        FlatFileItemReader<TransactionDomain> reader = new FlatFileItemReader<TransactionDomain>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        String[] tokens = { "username", "userid", "transactiondate", "amount" };
        tokenizer.setNames(tokens);
        reader.setResource(inputCsv);
        DefaultLineMapper<TransactionDomain> lineMapper =
                new DefaultLineMapper<TransactionDomain>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
        reader.setLineMapper(lineMapper);
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public ItemProcessor<TransactionDomain, TransactionDomain> itemProcessor() {
        return new CustomItemProcessor();
    }

    @Bean
    public ItemWriter<TransactionDomain> itemWriter(EntityManagerFactory emf) {
        JpaItemWriter<TransactionDomain> jpaItemWriter = new CustomWriter();
        jpaItemWriter.setEntityManagerFactory(emf);
        return jpaItemWriter;
    }

    @Bean
    protected Step step1(ItemReader<TransactionDomain> reader,
                         ItemProcessor<TransactionDomain, TransactionDomain> processor,
                         ItemWriter<TransactionDomain> writer) {
        return steps.get("step1").<TransactionDomain, TransactionDomain> chunk(10)
                .reader(reader).processor(processor).writer(writer).build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobs.get("firstBatchJob").start(step1).build();
    }
}
