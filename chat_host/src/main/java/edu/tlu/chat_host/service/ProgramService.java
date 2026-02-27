package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.ProgramResponse;
import edu.tlu.chat_host.entity.Program;
import edu.tlu.chat_host.mapper.ProgramMapper;
import edu.tlu.chat_host.repository.ProgramRepository;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;
    private final CurrentUserService currentUserService;

    public List<ProgramResponse> findAll() {
        return programRepository.findAll().stream().map(ProgramMapper::toDto).toList();
    }

    public List<ProgramResponse> findUserProgram(){
        return currentUserService.getCurrentUser().getPrograms().stream().map(ProgramMapper::toDto).toList();
    }

}
