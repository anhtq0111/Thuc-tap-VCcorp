- Processes và thread: 

    Processes là những trường hợp của một chương trình, chạy độc lập với tiến trình khác

    Thread là thành phần bên trong của tiến trình, dùng để thực thi đoạn code một cách đồng thời. Đoạn code mà thread thực thi được gọi là task

- Race condition: 

    Khi nhiều thread dùng chung một biến có thể thay đổi giá trị mà không được đồng bộ việc truy cập dẫn đến không đạt được kết quả mong đợi

- Lock : 

    Để giải quyết race condition, ta có thể dùng đồng bộ ngầm synchronized hoặc dùng Lock interface. 

- Triển khai Lock: what is different bt read and write

    ReentrantLock: Khóa loại trừ lẫn nhau, gần giống như synchronized. Một thread có thể nắm giữ lock nhiều lần. Còn tính năng khóa công bằng -> chặn starvation

  ![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/aa20aece-565f-473f-b7f2-9ea32c556166)


    ReadWriteLock: Khi một tiến trình đang đọc thì tiến trình khác không được ghi và ngược lại

    StampedLock: Tương tự như ReadWriteLock nhưng hàm lock trả về một biến có kiểu giá trị là long. Cho phép optimistics read: đọc nhưng không khóa hoàn toàn, kiểm tra
  lại xem có thay đổi hay không.
  
  ![image](https://github.com/anhtq0111/Thuc-tap-VCcorp/assets/111045275/c0d05cf3-890a-438f-b9f4-ce4a1f614924)


    Semaphore: Giới hạn số permit bằng một giá trị n do đó không quá n thread được phép cùng thực thi

    what is deadlock: when a thread is hold a resource for a long time. Then other thread is blocked indenfinitely when waiting for resource

- Atomic operation: 

    Thực hiện việc điều hành song song một cách an toàn trên nhiều luồng mà không cần sử dụng đồng bộ (synchronized) hoặc Lock

- Atomic Integer:

    Một sự thay thế của Integer để có thể atomic operation

    Ngoài ra còn có nhiều atomic class như AtomicBoolean, AtomicLong, AtomicReference

- ConcurrentHashMap: 

    Implementation của interface ConcurrentMap (kế thừa map)

- Lambda expression :

    Override lại hàm abstract của functional interface. Khi đó không cần tạo một đối tượng mới bằng new mà chỉ cần viết mình hàm abstract đó thì sẽ trả về một đối tượng.

- Virtual Thread: 

    Là một instance của java.lang.Thread, không bị ràng buộc với một OS thread nào như platform thread

    To simulate a lot of threads, the Java runtime maps a large number of virtual threads to a small number of OS threads

    Use virtual threads in high-throughput concurrent applications, especially those that consist of a great number of concurrent tasks that spend much of their time waiting

    
