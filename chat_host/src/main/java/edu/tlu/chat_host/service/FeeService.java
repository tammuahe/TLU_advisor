package edu.tlu.chat_host.service;

import edu.tlu.chat_host.dto.RemainingFeeResponse;
import edu.tlu.chat_host.security.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FeeService {
    private final CurrentUserService currentUserService;

    public RemainingFeeResponse getRemainingFee() throws Exception {
        String studentId = currentUserService.getCurrentUser().getCode();
        if (studentId == null || studentId.isBlank()) {
            throw new CredentialNotFoundException("Student ID not found for current user. Try updating user profile or logging in again. If the problem persists, contact \"Phòng tiếp sinh viên\".");
        }
        Document document = Jsoup.connect("https://e-bills.vn/pay/thanglong?customer=" + studentId).get();
        String name = document.select("#hoten").attr("value");

        if (name.isBlank()) {
            throw new IllegalArgumentException("Student " + studentId + " not found. Try updating user profile or logging in again. If the problem persists, contact \"Phòng tiếp sinh viên\".");
        }
        Double amount = Double.valueOf(document.select("#totalAmount").text());
        System.out.println("amount: " + amount + "\n student ID: " + studentId);
        return new RemainingFeeResponse(studentId, name, amount);
    }
}
