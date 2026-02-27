package edu.tlu.chat_host.service;

import edu.tlu.chat_host.enums.Semester;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SemesterService {

    public Semester getCurrentSemester() {
        Integer thisMonth = LocalDateTime.now().getMonthValue();
        if (thisMonth >= 10 || thisMonth <= 1) {
            return Semester.SEMESTER_1;
        }
        if (thisMonth <= 5) {
            return Semester.SEMESTER_2;
        } else {
            return Semester.SEMESTER_3;
        }
    }

    public Integer getCurrentYear() {
        return LocalDateTime.now().getYear();
    }
}
