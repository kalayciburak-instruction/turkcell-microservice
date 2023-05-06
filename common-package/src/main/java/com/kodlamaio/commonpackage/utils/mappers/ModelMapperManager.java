package com.kodlamaio.commonpackage.utils.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelMapperManager implements ModelMapperService{
    @Override
    public ModelMapper forResponse() {
        return null;
    }

    @Override
    public ModelMapper forRequest() {
        return null;
    }
}
