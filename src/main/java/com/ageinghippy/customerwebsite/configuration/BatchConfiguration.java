package com.ageinghippy.customerwebsite.configuration;

import com.ageinghippy.customerwebsite.model.Customer;
import com.ageinghippy.customerwebsite.repository.CustomerRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
public class BatchConfiguration {

    @Bean
    @Qualifier("customerLoadJob")
    public Job job(
            JobRepository jobRepository,
            Step customerJobStep) {

        return new JobBuilder("customer-loader", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(customerJobStep)
                .build();
    }

    @Bean
    public Step customerJobStep(
            JobRepository jobRepository,
            ItemReader<Customer> customerFileItemReader,
            ItemProcessor<Customer, Customer> customerItemProcessor,
            ItemWriter<Customer> customerItemWriter,
            PlatformTransactionManager transactionManager) {

        return new StepBuilder("jobStep", jobRepository)
                .<Customer, Customer>chunk(10, transactionManager)
                .reader(customerFileItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .faultTolerant()
                .skipLimit(10)
                .noSkip(IOException.class)
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> customerFileItemReader(
            @Value("${customer.input.file}") String inputFile) {

        return new FlatFileItemReaderBuilder<Customer>()
                .name("customer-file-reader")
                .resource(new ClassPathResource(inputFile))
                .delimited()
                .names("fullName", "emailAddress", "age", "address")
                .linesToSkip(1)
                .fieldSetMapper(
                        new BeanWrapperFieldSetMapper<>() {
                            {
                                setTargetType(Customer.class);
                            }
                        })
                .build();
    }

    @Component
    public static class CustomerItemWriter implements ItemWriter<Customer> {

        @Autowired
        private CustomerRepository customerRepository;

        @Override
        public void write(@NonNull Chunk<? extends Customer> customers)
                throws InterruptedException {

            customerRepository.saveAll(customers);

            System.out.println("Saved customers: " + customers);
        }
    }

    @Component
    public static class customerItemProcessor implements ItemProcessor<Customer, Customer> {

        @Override
        public Customer process(Customer customer) {
            System.out.println("Loading: "+customer.toString());
            return customer;
        }
    }

}
