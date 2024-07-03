package org.example.furnituresaleproject.service.provider;

import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.repository.IProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService implements IProviderService{

    @Autowired
    private IProviderRepository repository;


    @Override
    public List<Provider> getAllProvider() {
        return repository.findAll();
    }

    @Override
    public Provider getProviderByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void addProvider(Provider provider) {
        repository.save(provider);

    }

    @Override
    public void updateProvider(Provider provider) {
        Optional<Provider> optionalProvider = repository.findById(provider.getId());
        provider.setName(provider.getName());
        repository.save(provider);
    }

    @Override
    public void deleteProvider(int id) {
        Optional<Provider> optionalProvider = repository.findById(id);
        Provider provider = optionalProvider.get();
        repository.delete(provider);
    }
}
