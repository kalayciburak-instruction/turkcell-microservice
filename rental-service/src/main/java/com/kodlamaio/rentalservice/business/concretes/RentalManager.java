package com.kodlamaio.rentalservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.UpdateRentalResponse;
import com.kodlamaio.rentalservice.business.rules.RentalBusinessRules;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var models = repository.findAll();
        var response = models
                .stream()
                .map(model -> mapper.forResponse().map(model, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);
        var model = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(model, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        var model = mapper.forRequest().map(request, Rental.class);
        model.setId(null);
        repository.save(model);
        var response = mapper.forResponse().map(model, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);
        var model = mapper.forRequest().map(request, Rental.class);
        model.setId(id);
        repository.save(model);
        var response = mapper.forResponse().map(model, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        repository.deleteById(id);
    }
}