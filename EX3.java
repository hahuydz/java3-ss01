/*
1. Xác định Dữ liệu Đầu vào (Input) và Kết quả Đầu ra (Output)
Để hệ thống hoạt động ổn định và xử lý được các tình huống nghiệp vụ, chúng ta cần định nghĩa cấu trúc dữ liệu như sau:

Dữ liệu đầu vào (Input)
Tham số	Kiểu dữ liệu	Mô tả
userId	String/Long	Mã định danh của khách hàng đặt món.
foodName	String	Tên món ăn khách chọn (VD: "Mì xào bò").
quantityRequested	Integer	Số lượng món ăn khách hàng muốn đặt.
Kết quả mong đợi (Output)
Trường hợp Thành công: Trả về một đối tượng OrderResponse (Trạng thái: Thành công, Thông báo: "Đặt món thành công", Số dư còn lại, Số lượng kho cập nhật).
Trường hợp Thất bại: Trả về mã lỗi và thông báo chi tiết (VD: "Hết hàng", "Số dư tài khoản không đủ", hoặc "Tài khoản bị khóa do số dư âm").
2. Thiết kế Kiến trúc (Loose Coupling & DI)
Sử dụng nguyên lý Dependency Injection để tiêm các Repository vào Service thông qua ApplicationContext.

InventoryRepository: Kiểm tra số lượng tồn kho.
UserAccountRepository: Kiểm tra số dư và thực hiện trừ tiền.
3. Quy trình xử lý Logic
Hệ thống sẽ thực hiện theo các bước nghiêm ngặt sau để tránh các "bẫy" dữ liệu:

Bước 1: Nhận yêu cầu đặt món từ userId.
Bước 2: Kiểm tra kho (Inventory Check)
Truy vấn số lượng tồn kho của foodName.
Xử lý bẫy: Nếu khách gọi "Mì xào bò" nhưng số lượng kho trả về bằng 0 -> Báo lỗi "Hết hàng".
Bước 3: Kiểm tra tài khoản (Account Check)
Truy vấn số dư tài khoản của userId.
Xử lý bẫy: Nếu số dư tài khoản bị âm (< 0) -> Báo lỗi "Tài khoản không hợp lệ".
Nếu số dư nhỏ hơn đơn giá món ăn -> Báo lỗi "Không đủ tiền".
Bước 4: Thực thi giao dịch
Trừ số lượng tồn kho.
Trừ tiền tài khoản người dùng.
Bước 5: Trả về kết quả thành công cho người dùng.
 */
