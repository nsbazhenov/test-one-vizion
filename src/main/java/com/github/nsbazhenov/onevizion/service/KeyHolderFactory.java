package com.github.nsbazhenov.onevizion.service;

import org.springframework.jdbc.support.KeyHolder;

public interface KeyHolderFactory {
    KeyHolder newKeyHolder();
}