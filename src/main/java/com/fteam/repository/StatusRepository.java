package com.fteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fteam.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
