# Report week 1

## 1.1. Xây dựng chương trình java bất kì có xử dụng đầy đủ 4 tính chất của oop, 5 tính chất trong SOLID
- Chương trình quản lý thông tin sinh viên cơ bản
- Thao tác : thêm, sửa, xóa thông tin sinh viên
- Các lớp : 
    - Lớp interface : IRepository gồm 4 phương thức add, getAll, update, delete, sử dụng overload ở phương thức add để test polymorphism
    - Lớp triển khai: StudentRepository, thêm phương thức test để test static
    - Lớp Student gồm 3 thuộc tính id, name, age tuân theo tính đóng gói, các phương thức truy cập và cập nhật thuộc tính được định nghĩa public

## 1.2. Yêu cầu (2) Đọc ghi file

- Chương trình đọc ghi file theo định dạng Binary và Text, list các file, đọc nội dung các file