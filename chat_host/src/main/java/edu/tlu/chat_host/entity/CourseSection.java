package edu.tlu.chat_host.entity;

import edu.tlu.chat_host.enums.Semester;
import jakarta.persistence.*;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToMany
    @JoinTable(name = "course_section_professors", joinColumns = @JoinColumn(name = "course_section_id"), inverseJoinColumns = @JoinColumn(name = "professor_id"))
    private Set<Professor> professors;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private Semester semester;
}
