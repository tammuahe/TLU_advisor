-- Insert Faculties (IDs are auto-generated)
INSERT INTO faculties (name) VALUES ('Phòng Đào tạo');
INSERT INTO faculties (name) VALUES ('Khoa Du lịch');
INSERT INTO faculties (name) VALUES ('Khoa Công nghệ thông tin');
INSERT INTO faculties (name) VALUES ('Khoa Khoa học xã hội và nhân văn');
INSERT INTO faculties (name) VALUES ('Khoa Truyền thông đa phương tiện');
INSERT INTO faculties (name) VALUES ('Khoa Khoa học Sức khỏe');
INSERT INTO faculties (name) VALUES ('Khoa Âm nhạc ứng dụng');
INSERT INTO faculties (name) VALUES ('Khoa Kinh tế - Quản lý');
INSERT INTO faculties (name) VALUES ('Bộ môn Giáo dục thể chất');
INSERT INTO faculties (name) VALUES ('Bộ môn Ngôn ngữ Anh');
INSERT INTO faculties (name) VALUES ('Bộ môn Ngôn ngữ Pháp');
INSERT INTO faculties (name) VALUES ('Bộ môn Ngôn ngữ Nhật');
INSERT INTO faculties (name) VALUES ('Bộ môn Ngôn ngữ Hàn Quốc');
INSERT INTO faculties (name) VALUES ('Bộ môn Ngôn ngữ Trung Quốc');
INSERT INTO faculties (name) VALUES ('Bộ môn Toán');
INSERT INTO faculties (name) VALUES ('Trung tâm Elearning');

-- Insert Program (Manual ID)
INSERT INTO programs (program_id, name, level, major, training_mode) 
VALUES (1, 'Ngành Khoa học máy tính', 'Đại học', 'Khoa học máy tính', 'Chính quy');

-- Insert Professor (Manual ID)
INSERT INTO professors (professor_id, name) VALUES (1, 'TBD');

-- Insert Subjects (Manual IDs)
-- Faculty Mapping Helper:
-- CNTT (IT): Khoa Công nghệ thông tin
-- KHXH&NV (Humanities): Khoa Khoa học xã hội và nhân văn
-- GDTC (Phys Ed): Bộ môn Giáo dục thể chất
-- Foreign Languages: Respective Departments
-- Training Dept: Soft skills / General default

-- Elective Courses (NHOM-AD)
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (1, 'AD205 - Kỹ năng soạn thảo văn bản (MS Office)', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (2, 'AD206 - Ẩm thực Việt Nam', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Du lịch'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (3, 'AD207 - Kỹ năng soạn thảo văn bản (MS Open)', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (4, 'AD212 - Phương pháp hùng biện và các thủ thuật tranh biện', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (5, 'AD213 - Hát - Nhạc', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Âm nhạc ứng dụng'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (6, 'AD214 - Nâng cao chất lượng giọng hát', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Âm nhạc ứng dụng'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (7, 'AD215 - Ứng dụng Power Point', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (8, 'AD216 - Kỹ năng sống', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (9, 'AD240 - Nhiếp ảnh cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Truyền thông đa phương tiện'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (10, 'AD241 - Dẫn chương trình (MC)', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Truyền thông đa phương tiện'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (11, 'AD242 - Thiết kế mỹ thuật', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Truyền thông đa phương tiện'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (12, 'AD243 - Hội họa cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Truyền thông đa phương tiện'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (13, 'AD250 - Dinh dưỡng và tiết chế', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học Sức khỏe'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (14, 'AD312 - Bóng đá', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (15, 'AD313 - Bóng đá nâng cao', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (16, 'AD314 - Nhảy hiện đại', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (17, 'AD315 - Nhảy hiện đại nâng cao', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));

-- Political Theory Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (18, 'ML113 - Triết học Mác - Lênin', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (19, 'ML114 - Kinh tế chính trị Mác - Lênin', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (20, 'ML115 - Chủ nghĩa xã hội khoa học', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (21, 'ML202 - Tư tưởng Hồ Chí Minh', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (22, 'ML204 - Lịch sử Đảng Cộng sản Việt Nam', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));

-- English Language Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (23, 'GE111 - Tiếng Anh sơ cấp 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (24, 'GE112 - Tiếng Anh sơ cấp 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (25, 'GE121 - Tiếng Anh sơ trung cấp 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (26, 'GE222 - Tiếng Anh sơ trung cấp 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (27, 'GE231 - Tiếng Anh trung cấp 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (28, 'GE232 - Tiếng Anh trung cấp 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (29, 'GE333 - Tiếng Anh trung cấp 3', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Anh'));

-- Second Foreign Language Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (30, 'GF101 - Tiếng Pháp 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Pháp'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (31, 'GF102 - Tiếng Pháp 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Pháp'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (32, 'GJ101 - Tiếng Nhật 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Nhật'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (33, 'GI101 - Tiếng Ý 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (34, 'GI102 - Tiếng Ý 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (35, 'GJ102 - Tiếng Nhật 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Nhật'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (36, 'GK101 - Tiếng Hàn 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Hàn Quốc'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (37, 'GK102 - Tiếng Hàn 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Hàn Quốc'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (38, 'GZ101 - Tiếng Trung 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Trung Quốc'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (39, 'GZ102 - Tiếng Trung 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Ngôn ngữ Trung Quốc'));

-- General Education Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (40, 'CS100 - Tin đại cương', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (41, 'CS102 - Tin học văn phòng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (42, 'EC102 - Nhập môn kinh tế học', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Kinh tế - Quản lý'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (43, 'MA101 - Logic, suy luận toán học và kỹ thuật đếm', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (44, 'NA151 - Khoa học môi trường', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học Sức khỏe'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (45, 'SH131 - Pháp luật đại cương', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (46, 'VL101 - Tiếng Việt thực hành', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Khoa học xã hội và nhân văn'));

-- Foundation Courses (All IT Faculty or Math)
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (47, 'CS121 - Lập trình cơ sở 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (48, 'CS212 - Kiến trúc máy tính', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (49, 'MA120 - Đại số tuyến tính', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (50, 'CS110 - Kỹ thuật số', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (51, 'CS122 - Lập trình hướng đối tượng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (52, 'MA110 - Giải tích 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (53, 'MA111 - Giải tích 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (54, 'MI201 - Toán rời rạc', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (55, 'CF213 - Cấu trúc dữ liệu và giải thuật', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (56, 'IS222 - Cơ sở dữ liệu', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (57, 'MA239 - Xác suất thống kê', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (58, 'NW212 - Mạng máy tính', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (59, 'CS315 - Nguyên lý hệ điều hành', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));

-- Major Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (60, 'CF231 - Lý thuyết thông tin và mã hóa', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (61, 'CF301 - Ngôn ngữ hình thức và Otomat', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (62, 'CS320 - Học máy', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (63, 'CS321 - Tiền xử lý dữ liệu', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (64, 'IS314 - Hệ thống thông tin', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (65, 'IS322 - Hệ quản trị cơ sở dữ liệu', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (66, 'IS332 - Phân tích thiết kế hướng đối tượng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (67, 'MI312 - Đồ họa', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán')); -- or IT? "MI" likely Math/Informatics. Sticking to Math as MI201 was Math. But MI312 Graphics involves CS. I'll put IT to be safe or Math. TLU "MI" codes usually Mathematics & Informatics. I'll put IT.
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (68, 'MI322 - Trí tuệ nhân tạo và công nghệ tri thức', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (69, 'SE302 - Công nghệ phần mềm', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (70, 'SE312 - Kiểm thử và đảm bảo chất lượng phần mềm', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (71, 'SE380 - Project', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (72, 'SE422 - Phát triển dự án', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));

-- Elective Major Courses (NHOM-LCN)
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (73, 'CS223 - Lập trình Java', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (74, 'CS224 - Lập trình .Net', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (75, 'MA238 - Thống kê nâng cao', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Toán'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (76, 'CS314 - Lập trình ứng dụng di động', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (77, 'IS325 - Cơ sở dữ liệu phân tán', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (78, 'IS326 - Khai phá dữ liệu', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (79, 'IS330 - Dữ liệu lớn', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (80, 'IS383 - Hệ thống thông tin nâng cao', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (81, 'IS345 - An toàn thông tin', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (82, 'IT320 - Phát triển ứng dụng với Python', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (83, 'NW312 - Thiết kế và quản trị mạng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (84, 'NW332 - An toàn mạng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (85, 'CS425 - Một số vấn đề hiện đại trong công nghệ thông tin', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));

-- Graduation Requirements
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (86, 'IP401 - Thực tập ngành Khoa học máy tính', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (87, 'CS487 - CĐTN ngành Khoa học máy tính', 3, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (88, 'CS499 - KLTN ngành Khoa học máy tính', 10, (SELECT faculty_id FROM faculties WHERE name = 'Khoa Công nghệ thông tin'));

-- Physical Education Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (89, 'PG118 - GDTC: Thể dục cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (90, 'PG102 - GDTC: Thể dục cổ truyền cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (91, 'PG106 - GDTC: Bóng bàn cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (92, 'PG110 - GDTC: Bơi cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (93, 'PG113 - GDTC: Bóng rổ cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (94, 'PG115 - GDTC: Bóng chuyền cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (95, 'PG120 - GDTC: Thể dục cơ bản nâng cao', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (96, 'PG145 - GDTC: Gym cơ bản', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (97, 'PG146 - GDTC: Gym nâng cao 1', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (98, 'PG147 - GDTC: Gym nâng cao 2', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (99, 'PG148 - GDTC: Gym nâng cao 3', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (100, 'PG100 - Giáo dục thể chất', 3, (SELECT faculty_id FROM faculties WHERE name = 'Bộ môn Giáo dục thể chất'));

-- National Defense Education Courses
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (101, 'PG121 - Giáo dục quốc phòng', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (102, 'PG122 - Đường lối quốc phòng và an ninh của Đảng cộng sản Việt Nam', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (103, 'PG123 - Công tác quốc phòng và an ninh', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (104, 'PG124 - Quân sự chung', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));
INSERT INTO subjects (subject_id, name, credits, faculty_id) VALUES (105, 'PG125 - Kỹ thuật chiến đấu bộ binh và chiến thuật', 3, (SELECT faculty_id FROM faculties WHERE name = 'Phòng Đào tạo'));


-- Insert Course Sections
-- Note: Room, Capacity, Schedule are not in CourseSection entity. Only id, subject, year, semester.
-- CS121 (ID: 47)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (1, 47, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (2, 47, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (3, 47, 2025, 'SEMESTER_1'); -- 2025-Fall

-- CS122 (ID: 51)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (4, 51, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (5, 51, 2025, 'SEMESTER_1'); -- 2025-Fall

-- CF213 (ID: 55)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (6, 55, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (7, 55, 2025, 'SEMESTER_1'); -- 2025-Fall

-- IS222 (ID: 56)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (8, 56, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (9, 56, 2025, 'SEMESTER_1'); -- 2025-Fall

-- CS320 (ID: 62)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (10, 62, 2025, 'SEMESTER_1'); -- 2025-Fall
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (11, 62, 2026, 'SEMESTER_2'); -- 2026-Spring

-- SE302 (ID: 69)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (12, 69, 2025, 'SEMESTER_1'); -- 2025-Fall
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (13, 69, 2026, 'SEMESTER_2'); -- 2026-Spring

-- GE111 (ID: 23)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (14, 23, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (15, 23, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (16, 23, 2025, 'SEMESTER_1'); -- 2025-Fall

-- ML113 (ID: 18)
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (17, 18, 2025, 'SEMESTER_2'); -- 2025-Spring
INSERT INTO course_sections (course_section_id, subject_id, year, semester) VALUES (18, 18, 2025, 'SEMESTER_1'); -- 2025-Fall

-- Insert Course Section Professors (Links sections to TBD Professor - ID 1)
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (1, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (2, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (3, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (4, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (5, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (6, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (7, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (8, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (9, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (10, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (11, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (12, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (13, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (14, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (15, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (16, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (17, 1);
INSERT INTO course_section_professors (course_section_id, professor_id) VALUES (18, 1);
