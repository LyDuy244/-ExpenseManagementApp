# -ExpenseManagementApp
Các phần mềm cần có:
+ JDK 20 (https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
+ Apache Tomcat 9 (https://tomcat.apache.org/download-90.cgi)
+ Apache NetBeans IDE 14 (https://netbeans.apache.org/download/nb14/)
+ MySQL Workbench 8.0 CE (https://dev.mysql.com/downloads/workbench/)
#### Khởi động phần mềm:
Mở MySQL Workbench 8.0 CE:
+ Tạo mới một database với tên "expensedb"
![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/56b96d7e-cc23-4c14-9083-66dfb5c24f09)

+ Import file database:
  + Server -> Data import -> Chọn Import from Self-Contained File -> Chọn đường dẫn đến file expensedb.sql
  + Default Target Schema -> Chọn "expensedb"
  + Start Import
  ![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/5379c1a8-7edb-48eb-aa50-c0949eb00f89)

  
  + Refresh lại database và kiểm tra. Nếu xuất hiện các bảng như hình là đúng. 
  ![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/a7131d13-471f-40ef-ba3f-30b08857df16)

#### Mở Apache NetBeans IDE 18:
+ Mở project ExpenseManagementApp
+ Mở file databases.properties và sửa username và password cho phù hợp.
![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/5bc5370f-6cb1-48ff-a976-cd71251b1f57)

+ Bấm chuột phải vào project và bấm "Build".
![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/00ad181b-d802-4ee0-aeb3-8a6206fb1862)

+ Sau khi build xong thì bấm "Run".
![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/b33937bd-09f1-499e-9057-ab98ca4ec1af)

+ Khi thấy trang web này hiện lên trang chủ thì quá trình khởi động web thành công.
![image](https://github.com/LyDuy244/ExpenseManagementApp/assets/118716140/3eafa191-92fe-4837-9e40-b7f4e88e69c6)

****
### Các tài khoản có trong hệ thống:
+ TK: admin   MK: 123456
+ TK: user1   MK: 123456
+ TK: user2   MK: 123456
+ TK: user3   MK: 123456
+ TK: user4   MK: 123456
+ TK: user5   MK: 123456
