package com.hacker.domain;

import javax.persistence.*;

/**
 * Created by dinesh.rathore on 25/02/16.
 */
@Entity
@Table(name = "feeds")
public class Feed extends BaseDomain {

    private Long id;



    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
