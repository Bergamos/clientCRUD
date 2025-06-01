package com.bergamos.clientCRUD.config;

import com.bergamos.clientCRUD.dto.ClientDTO;
import com.bergamos.clientCRUD.entities.Client;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    @Qualifier("updateModelMapper")
    public ModelMapper updateModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<ClientDTO, Client>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        return modelMapper;

    }
}
