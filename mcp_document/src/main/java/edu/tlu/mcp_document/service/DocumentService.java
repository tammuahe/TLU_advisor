package edu.tlu.mcp_document.service;

import io.modelcontextprotocol.spec.McpSchema;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ChatClient chatClient;

    @McpTool(description = "Get document related to user question")
    public McpSchema.CallToolResult getDocument(@McpToolParam(description = "User question") String prompt) {
        System.out.println("prompt: " + prompt);
        System.out.println("hyDE: " + chatClient.prompt(prompt).call().content());
        return McpSchema.CallToolResult.builder().addTextContent("{\n" +
                "              \"MaHP\": \"241IS330\",\n" +
                "              \"StudyUnitID\": \"241IS33006\",\n" +
                "              \"ScheduleStudyUnitID\": \"241IS33006\",\n" +
                "              \"KTESurvey\": 0,\n" +
                "              \"DGKS\": 1,\n" +
                "              \"SurveyClassID\": \"\",\n" +
                "              \"SurveyData\": \"\",\n" +
                "              \"ListOfProfessorID\": \"100726\",\n" +
                "              \"ListOfProfessorName\": \"Đinh Thủy Tiên\",\n" +
                "              \"DiemTK_10\": \"8.7\",\n" +
                "              \"DiemTK_4\": \"4.0\",\n" +
                "              \"DiemTK_Chu\": \"A\",\n" +
                "              \"IsPass\": \"1\",\n" +
                "              \"StudentID\": \"A44249\",\n" +
                "              \"StudyProgramName\": \"Khoa học máy tính - Khóa 35\",\n" +
                "              \"CurriculumType\": 2,\n" +
                "              \"MaCTDT\": \"DHCQK35TI\",\n" +
                "              \"TenCTDT\": \"Khoa học máy tính - Khóa 35\",\n" +
                "              \"TrinhDoDaoTao\": \"Đại học\",\n" +
                "              \"ChuyenNganhDaoTao\": \"Khoa học máy tính\",\n" +
                "              \"HinhThucDaoTao\": \"Chính quy\",\n" +
                "              \"BatBuoc\": \"Tự Chọn\",\n" +
                "              \"CurriculumID\": \"IS330\",\n" +
                "              \"TenHP\": \"Dữ liệu lớn\",\n" +
                "              \"BoMon\": \"Khoa Công nghệ thông tin\",\n" +
                "              \"Khoa\": \"Khoa Công nghệ thông tin\",\n" +
                "              \"STC\": 2,\n" +
                "              \"TCHocPhan\": null,\n" +
                "              \"LT\": 18,\n" +
                "              \"TH\": 24,\n" +
                "              \"TS\": 2,\n" +
                "              \"HPHocTruoc\": \"IS222\",\n" +
                "              \"HPTienQuyet\": \"\",\n" +
                "              \"HPTuongDuong\": null,\n" +
                "              \"GhiChu\": \"\",\n" +
                "              \"DiemTK_10_1\": \"8.7\",\n" +
                "              \"DiemTK_4_1\": \"4.0\",\n" +
                "              \"DiemTK_Chu_1\": \"A\",\n" +
                "              \"IsPass_1\": \"1\",\n" +
                "              \"GhiChuNhomTuChon\": \"NHOM-LCN (chọn 5/34 tín chỉ)\",\n" +
                "              \"DiemChuyenMien\": \"\",\n" +
                "              \"TenKhoiKienThuc\": \"2.2. Kiến thức ngành\",\n" +
                "              \"TenKhoaKienThucCha\": \"2. Kiến thức giáo dục chuyên nghiệp\",\n" +
                "              \"TongToTCTichLuyCTDT\": 145.0,\n" +
                "              \"GhiChuNhomTuChon_CH\": \"5.0 / 34.0 tín chỉ\",\n" +
                "              \"RankIndex\": 60\n" +
                "            },").build();
    }
}
