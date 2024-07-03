package org.example.furnituresaleproject.service.provider;

import org.example.furnituresaleproject.entity.Provider;

import java.util.List;

public interface IProviderService {
    public List<Provider> getAllProvider();
    public Provider getProviderByName(String name);
    public void addProvider(Provider provider);
    public void updateProvider(Provider provider);
    public void deleteProvider(int id);
}
