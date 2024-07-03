package org.example.furnituresaleproject.service.typespace;

import org.example.furnituresaleproject.entity.Provider;
import org.example.furnituresaleproject.entity.TypeSpace;

import java.util.List;

public interface ITypeSpaceService {
    public List<TypeSpace> getAllTypeSpace();
    public TypeSpace getTypeSpaceByName(String name);
    public void addTypeSpace(TypeSpace typeSpace);
    public void updateTypeSpace(TypeSpace typeSpace);
    public void deleteTypeSpace(int id);
}
