package org.example.furnituresaleproject.service.typespace;

import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.entity.TypeSpace;
import org.example.furnituresaleproject.repository.IProviderRepository;
import org.example.furnituresaleproject.repository.ITypeSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeSpaceService implements ITypeSpaceService {

    @Autowired
    private ITypeSpaceRepository repository;


    @Override
    public List<TypeSpace> getAllTypeSpace() {
        return repository.findAll();
    }

    @Override
    public TypeSpace getTypeSpaceByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void addTypeSpace(TypeSpace typeSpace) {
        repository.save(typeSpace);

    }

    @Override
    public void updateTypeSpace(TypeSpace typeSpace) {
        Optional<TypeSpace> optionalTypeSpace = repository.findById(typeSpace.getId());
        typeSpace.setName(typeSpace.getName());
        repository.save(typeSpace);
    }

    @Override
    public void deleteTypeSpace(int id) {
        Optional<TypeSpace> optionalTypeSpace = repository.findById(id);
        TypeSpace typeSpace = optionalTypeSpace.get();
        repository.delete(typeSpace);
    }
}
