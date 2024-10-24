<h1>HỆ THỐNG QUẢN LÝ CỬA HÀNG ĐIỆN THOẠI</h1>

 ---


<h2>Giới Thiệu Dự Án</h2>
 Dự án này nhằm xây dựng một ứng dụng quản lý cửa hàng điện thoại, giúp tối ưu hóa quy trình quản lý thông qua việc sử dụng ngôn ngữ Java và JavaFX. Ứng dụng sẽ cung cấp các chức năng thiết yếu như quản lý sản phẩm, khách hàng và hóa đơn, tạo ra một hệ thống lưu trữ cơ sở dữ liệu hiệu quả và dễ sử dụng.
 

 <h2>Mục Tiêu</h2>

 1. Giao diện

    - Giao diện đồ họa bằng JavaFX cho dự án
      
2. Chức năng chính
   

+ Quản lý Sản phẩm:


    - Thêm/Sửa/Xóa Sản phẩm: Người dùng có thể dễ dàng quản lý danh sách sản phẩm, bao gồm các thông tin chi tiết như tên, giá cả,..
    - Tìm kiếm: Hỗ trợ tìm kiếm nhanh chóng để thuận tiện trong việc quản lý.
  
+ Quản lý Khách hàng:
    - Quản lý Thông tin Khách hàng: Lưu trữ và cập nhật thông tin khách hàng như tên, số điện thoại và địa chỉ, giúp theo dõi mối quan hệ với khách hàng.
    - Danh sách Khách hàng: Hiển thị danh sách khách hàng một cách trực quan, dễ dàng cho việc truy xuất thông tin.
    - Tìm kiếm: cho phép tìm kiếm khách hành bằng số điện thoại, thuận tiện cho việc truy xuất và quản lý.


+ Quản lý Hóa đơn:
    - Tạo hóa đơn mới: Dễ dàng tạo hóa đơn cho khách hàng, tự động tính toán tổng số tiền cần thanh toán.
    - Chỉnh sửa và xóa: Dễ dàng chỉnh sửa hoặc xóa hóa đơn nếu phát hiện lỗi hoặc sai sót.
    - Lưu trữ và Truy xuất Hóa đơn: Lưu trữ tất cả hóa đơn đã phát hành và cho phép tìm kiếm, xem lại lịch sử giao dịch một cách hiệu quả.
    - Tìm kiếm: Cho phép tìm kiếm hóa đơn theo ID để hỗ trợ nhanh chóng trong việc chỉnh sửa, xóa hoặc xuất hóa đơn.
 
+ Dữ liệu
    - Dữ liệu được lưu trữ trong Database



<h2> Thành Viên Nhóm </h2>

   - Lê Thị Dương -	23010351 
     
   - Lưu Thị Ngà  - 22010181

<h2>Bảng phân công nhiệm vụ </h2>


---
| stt |          Nhiệm vụ             |   Người phụ trách  | Đánh giá |
|-----|-------------------------------|--------------------|----------|
|  1  | Phân tích yêu cầu và lên ý tưởng kế hoạch thực hiện dự án|Lê Thị Dương| 50% |
|     |                                                          |Lưu Thị Ngà | 50% |
|  2  |    Vẽ lưu đồ thuật toán hệ thống                         |Lê Thị Dương| 50% |
|     |                                                          |Lưu Thị Ngà | 50% |
|  3  |    Thiết kế cơ sở dữ liệu                                |Lê Thị Dương| 100%|
|  4  |    Phát triển code                                       |Lê Thị Dương| 75% |
|     |                                                          |Lưu Thị Ngà | 25% |
|  5  |    kiểm thử hệ thống                                     |Lê Thị Dương| 50% |
|     |                                                          |Lưu Thị Ngà | 50% |
|  6  |    Lên video demo dự án                                  |Lê Thị Dương| 60% |
|     |                                                          |Lưu Thị Ngà | 40% |
|  77  |    Viết báo cáo                                          |Lê Thị Dương| 15% |
|     |                                                          |Lưu Thị Ngà | 85% |















<h2>Sơ đồ cấu trúc và sơ đồ thuật toán </h2>

<h3>1. Biểu đồ Class digram </h3>

![Ảnh chụp màn hình 2024-10-15 190900](https://github.com/user-attachments/assets/e8e2230c-974a-48b4-9e5e-7d773449a7bb)


<h3>2. Biểu đồ Sequence diagram</h3>

- Biểu đồ trình tự UML với chức năng đăng nhập

![6D3F4E44-F540-4F4A-A265-4A380F29AB49](https://github.com/user-attachments/assets/0098b0d6-6cdd-44eb-aa3d-a0fb1b8c61a6)



- Biểu đồ trình tự UML với Chức năng chính

![sequence diagram](https://github.com/user-attachments/assets/719e9722-34ee-4d31-9343-74a49ec612ca)

---


- Biểu đồ trình tự UML với Chức năng thêm sản phẩm


![IMG_6153](https://github.com/user-attachments/assets/629de7f0-8bba-44ae-882c-4434ab840147)



- Biểu đồ trình tự UML với Chức năng sửa sản phẩm 


![Ảnh chụp màn hình 2024-10-20 002840](https://github.com/user-attachments/assets/5b974a3b-d53a-4cc1-bcb4-38b48ed08c73)




- Biểu đồ trình tự UML với Chức năng xoá sản phẩm

![IMG_6156](https://github.com/user-attachments/assets/5e34ebab-fa94-444a-a764-46ac53bc3060)

---


- Biểu đồ trình tự UML với Chức năng thêm khách hàng

![IMG_6152](https://github.com/user-attachments/assets/6a09f46d-e26f-48f6-a684-5d4322e458c4)




- Biểu đồ trình tự UML với Chức năng sửa khách hàng

![IMG_6150](https://github.com/user-attachments/assets/a9c88254-6707-4cfb-ae3a-40f591b756a8)



- Biểu đồ trình tự UML với Chức năng xóa khách hàng

  ![Ảnh chụp màn hình 2024-10-20 001513](https://github.com/user-attachments/assets/1ab721ed-6146-4ec4-8364-9e3f0f65f36d)

---


- Biểu đồ trình tự UML với Chức năng thêm hóa đơn

![IMG_6151](https://github.com/user-attachments/assets/76e9746f-3ae4-4d73-a0a6-93b31cebed55)




- Biểu đồ trình tự UML với Chức năng sửa hoá đơn
  

![IMG_6149](https://github.com/user-attachments/assets/ab203a23-30eb-4c94-a9ef-3c2597e9f758)




- Biểu đồ trình tự UML với Chức năng xoá hoá đơn
  

![IMG_6155](https://github.com/user-attachments/assets/e6b44bcb-2601-433b-8926-7d813ba400c2)


- Mô hình quan hệ

![IMG_6684](https://github.com/user-attachments/assets/51b5fc6e-144b-4998-b1aa-d4601cdd26b1)

  


---



<h2> Giao diện </h2>

 + Giao diện đầu tiên
 

![image](https://github.com/user-attachments/assets/76df609d-833d-4f10-ba36-e5968e56144f)



 + Giao diện chức năng chính
   
![image](https://github.com/user-attachments/assets/d09439a7-b18a-4708-a533-8dca8eea7ee8)



 + Dữ liệu login

![image](https://github.com/user-attachments/assets/3c0da441-b469-437e-9c0d-756e25e0fc5a)

 + Xuất hoá đơn ra file


![IMG_6673](https://github.com/user-attachments/assets/0ad02ebf-ec8a-4717-83f7-3f4ac107138b)


<h2>DEMO SẢN PHẨM</h2>

[DEMO](https://youtu.be/MJElhcTYSIQ?feature=shared)

<h2>BÀI BÁO CÁO</h2>
https://github.com/user-attachments/files/17457744/laptrinhhuongdoituong.docx





