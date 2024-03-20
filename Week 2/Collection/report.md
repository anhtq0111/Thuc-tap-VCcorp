# Collections

- ArrayList, HashSet, HashMap :  đều là một phần của Java Collections FrameWork. - how it work when add or compare
    - ArrayList
        
        Triển khai từ List interface
        
        Cho phép lặp phần tử

        Các phần tử truy cập theo index

        Các phương thức : add, get, set, remove, iterating over elements
    - HashSet

        Triển khai từ Set interface

        Không cho phép lặp lại phần tử

        Các phần tử không có vị trí

        Sử dụng hash table để lưu trữ phần tử 

        Các phương thức : add, remove, check
    - HashMap

        Triển khai từ Map interface

        Lưu theo cặp key-value, key là unique, cho phép lặp value

        Các phần tử không có vị trí

        Các phương thức : get, put, remove

- HashCode và Equals : dùng để check sự duy nhất của một phần tử trong set trước khi thêm một phần tử khác.
    - hashCode : trả về số là vị trí của phần tử trong bảng hash
    - equals : trả về đối tượng đó có giống đối tượng đã có trong set hay không

    