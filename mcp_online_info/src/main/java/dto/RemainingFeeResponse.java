package dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class RemainingFeeResponse {
    private final String studentId;
    private final String name;
    private final Double amount;
}
