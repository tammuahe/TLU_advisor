package edu.tlu.chat_host.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @Column(name = "subject_id")
    private Long id;

    private String name;

    private Integer credits;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @ManyToMany
    @JoinTable(name = "subject_prerequisites", joinColumns = @JoinColumn(name = "subject_id"), inverseJoinColumns = @JoinColumn(name = "prerequisite_id"))
    private Set<Subject> prerequisites;
}
