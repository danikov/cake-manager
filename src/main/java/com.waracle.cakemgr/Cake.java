package com.waracle.cakemgr;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "Cake", uniqueConstraints = {@UniqueConstraint(columnNames = "TITLE")})
@Data
public class Cake implements Serializable {
    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "DESCRIPTION", unique = false, nullable = false, length = 100)
    private String description;

    @Column(name = "IMAGE", unique = false, nullable = false, length = 300)
    private String image;
}