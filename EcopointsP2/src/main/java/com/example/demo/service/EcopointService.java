package com.example.demo.service;

import com.example.demo.repository.EcoPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EcopointService {
    @Autowired
    private final EcoPointRepository repo;
}
