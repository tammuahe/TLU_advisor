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
@Table(name = "course_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseSection {

    @Id
    @Column(name = "course_section_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String semester;

    @ManyToMany
    @JoinTable(name = "course_section_professors", joinColumns = @JoinColumn(name = "course_section_id"), inverseJoinColumns = @JoinColumn(name = "professor_id"))
    private Set<Professor> professors;
}
