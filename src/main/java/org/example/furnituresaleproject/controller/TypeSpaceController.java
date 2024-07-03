package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.entity.TypeSpace;
import org.example.furnituresaleproject.service.provider.IProviderService;
import org.example.furnituresaleproject.service.typespace.ITypeSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/typespaces")
@CrossOrigin("*")
public class TypeSpaceController {

    @Autowired
    private ITypeSpaceService service;

    @GetMapping()
    public List<TypeSpace> getAllProvider() {
        return service.getAllTypeSpace();
    }

    @GetMapping("/name")
    public TypeSpace getTypeSpaceByName(@RequestParam String name) {
        return service.getTypeSpaceByName(name);
    }

    @PostMapping()
    public void addTypeSpace(@RequestBody  TypeSpace typeSpace) {
        service.addTypeSpace(typeSpace);
    }

    @PutMapping()
    public void updateTypeSpace(@RequestBody  TypeSpace typeSpace) {

        service.updateTypeSpace(typeSpace);
    }

    @DeleteMapping()
    public void deleteProvider(@RequestParam  int id) {
        service.deleteTypeSpace(id);
    }

}


