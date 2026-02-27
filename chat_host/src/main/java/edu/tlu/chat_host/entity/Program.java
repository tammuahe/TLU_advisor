package edu.tlu.chat_host.entity;

import edu.tlu.chat_host.enums.ProgramLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProgramLevel level;

    @ManyToMany
    @JoinTable(name = "programs_subjects",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
