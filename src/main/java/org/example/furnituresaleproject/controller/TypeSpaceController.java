package org.example.furnituresaleproject.controller;

import org.example.furnituresaleproject.dto.ProviderDTO;
import org.example.furnituresaleproject.dto.TypeSpaceDTO;
import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.entity.TypeSpace;
import org.example.furnituresaleproject.service.provider.IProviderService;
import org.example.furnituresaleproject.service.typespace.ITypeSpaceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/typespaces")
@CrossOrigin("*")
public class TypeSpaceController {

    @Autowired
    private ITypeSpaceService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    public List<TypeSpaceDTO> getAllTypeSpace() {

        return service.getAllTypeSpace().stream().map(typespace -> modelMapper.map(typespace, TypeSpaceDTO.class))
                .collect(Collectors.toList());
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


