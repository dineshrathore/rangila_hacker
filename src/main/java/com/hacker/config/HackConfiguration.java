package com.hacker.config;

import io.dropwizard.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * Created by sachin.bj on 25/02/16.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HackConfiguration extends Configuration
{
    private Map<String,String> db;
}
