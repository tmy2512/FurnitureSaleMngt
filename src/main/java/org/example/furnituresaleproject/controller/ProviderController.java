package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.dto.ProductDTO;
import org.example.furnituresaleproject.dto.ProviderDTO;
import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.service.provider.IProviderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/providers")
@CrossOrigin("*")
public class ProviderController {

    @Autowired
    private IProviderService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public List<ProviderDTO> getAllProvider() {
        return service.getAllProvider().stream().map(provider -> modelMapper.map(provider, ProviderDTO.class))
                .collect(Collectors.toList());

    }

    @GetMapping("/name")
    public Provider getProviderByName(@RequestParam String name) {
        return service.getProviderByName(name);
    }

    @PostMapping()
    public void addProvider(@RequestBody  Provider provider) {
        service.addProvider(provider);
    }

    @PutMapping()
    public void updateProvider(@RequestBody  Provider provider) {

        service.updateProvider(provider);
    }

    @DeleteMapping()
    public void deleteProvider(@RequestParam  int id) {
        service.deleteProvider(id);
    }

}


