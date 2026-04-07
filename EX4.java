/*
1. Phân tích Vấn đề (Problem Analysis)
Hệ thống cần tích hợp EmailSender và SmsSender vào NotificationService. Nhiệm vụ là lựa chọn phương thức tiêm phụ thuộc tối ưu nhất trong 3 cách: Constructor, Field, và Setter Injection để đảm bảo tính ổn định và dễ bảo trì.

2. Bảng so sánh các giải pháp Dependency Injection
Dưới đây là bảng so sánh chi tiết giữa hai giải pháp phổ biến nhất để đưa ra quyết định thiết kế:

Tiêu chí	Constructor Injection (Khuyên dùng)	Field Injection (Dùng @Autowired)
Tính bất biến (Immutability)	Có. Cho phép dùng final. Đảm bảo các phụ thuộc không bị thay đổi sau khi khởi tạo.	Không. Các trường không thể để final, dễ dẫn đến trạng thái không ổn định.
Kiểm thử (Unit Test)	Dễ dàng. Có thể truyền trực tiếp Mock objects qua constructor mà không cần Spring Container.	Khó khăn. Phải sử dụng Reflection hoặc khởi chạy toàn bộ Spring Context để tiêm Mock.
Phát hiện lỗi	Sớm. Lỗi hiện diện ngay lúc biên dịch hoặc khởi tạo nếu thiếu phụ thuộc.	Muộn. Dễ gây ra NullPointerException tại thời điểm thực thi (Runtime).
Tính minh bạch	Cao. Nhìn vào hàm khởi tạo là biết ngay lớp cần những gì để hoạt động.	Thấp. Các phụ thuộc bị ẩn bên trong lớp, khó kiểm soát quy mô của class.
3. Xử lý "Bẫy" dữ liệu (Sự cố mạng SMS)
Tình huống: Hệ thống kết nối đến dịch vụ SMS bị đứt giữa chừng.

Giải pháp thiết kế:

Cơ chế Retry (Thử lại): Thiết lập logic tự động gửi lại (ví dụ: tối đa 3 lần) với khoảng trễ tăng dần để đối phó với sự cố mạng tạm thời.
Xử lý Exception: Sử dụng try-catch để bao bọc tiến trình gửi. Nếu thất bại hoàn toàn, hệ thống phải ghi Log lỗi chi tiết và lưu trạng thái "Chờ gửi lại" (Pending) vào cơ sở dữ liệu.
Dự phòng (Fallback): Nếu kênh SMS lỗi kéo dài, hệ thống có thể tự động chuyển hướng thông báo quan trọng qua kênh Email làm dự phòng.
4. Chốt lựa chọn giải pháp
Giải pháp lựa chọn: Constructor Injection

Lý do:

Độ tin cậy: Đảm bảo NotificationService luôn ở trạng thái sẵn sàng và hợp lệ ngay sau khi được tạo ra.
Dễ bảo trì: Giúp mã nguồn sạch hơn, tuân thủ nguyên tắc lập trình hướng đối tượng (OOP) và dễ dàng viết Unit Test để giả lập các tình huống lỗi mạng SMS (bẫy dữ liệu).
An toàn: Tránh được các lỗi tiềm ẩn liên quan đến việc thiếu phụ thuộc khi ứng dụng vận hành.
 */
