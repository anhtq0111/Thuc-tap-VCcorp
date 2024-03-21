# Serialize 
Là một cơ chế chuyển đổi trạng thái của một đối tượng thành một luồng byte

Ngược lại : Deserialize sử dụng luồng byte để tái tạo chính xác đối tượng Java cũ trong bộ nhớ  

Đây là cơ chế để duy trì trạng thái của đối tượng qua các nền tảng khác nhau và dùng để di chuyển đối tượng qua mạng 

The transient keyword is used with fields to indicate that they should not be serialized. The value of a transient field will not be included in the byte stream and will be set to its default value when the object is deserialized.

