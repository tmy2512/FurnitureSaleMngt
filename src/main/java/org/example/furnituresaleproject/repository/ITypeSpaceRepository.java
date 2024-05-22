package org.example.furnituresaleproject.repository;

import org.example.furnituresaleproject.entity.TypeSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeSpaceRepository extends JpaRepository<TypeSpace, Integer> {
}
