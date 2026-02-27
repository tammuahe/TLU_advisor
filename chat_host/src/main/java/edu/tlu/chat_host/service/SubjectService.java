package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.SubjectResponse;
import edu.tlu.chat_host.entity.Subject;
import edu.tlu.chat_host.mapper.SubjectMapper;
import edu.tlu.chat_host.repository.ProgramRepository;
import edu.tlu.chat_host.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProgramRepository programRepository;

    public SubjectResponse findById(@NonNull Long id) {
        return SubjectMapper.toDto(subjectRepository.findById(id).orElseThrow());
    }

    public List<SubjectResponse> findByProgramId(@NonNull Long programId) {
        return programRepository.findById(programId).orElseThrow().getSubjects().stream().map(SubjectMapper::toDto).toList();
    }

}
