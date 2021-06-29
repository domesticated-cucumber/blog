package com.leverx.shishlo.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags", schema = "public")
public class Tag extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Article> articles;

}
