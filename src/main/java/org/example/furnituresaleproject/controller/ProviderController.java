package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.service.provider.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/providers")
@CrossOrigin("*")
public class ProviderController {

    @Autowired
    private IProviderService service;

    @GetMapping()
    public List<Provider> getAllProvider() {
        return service.getAllProvider();
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


