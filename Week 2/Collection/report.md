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
    - hashCode : return hash value of an object and use to compute of object in hash table
    - equals : return true if two object has the same memory location.
        - Ex: int a = new Int(1); 
        
            int b = new Int(1); 
        
            int c = a; 

            a.equals(b) //false

            a.equals(c) //true

    - So when add new object to a set of object with same type, we should override hashCode and equals method.