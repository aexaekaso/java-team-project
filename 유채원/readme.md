```java
String koFormat = "| %-7s | %-15s | %-15s | %-15s | %6s |\n";
String enFormat = "| %-10s | %-15s | %-15s | %-15s | %6s |\n";
System.out.format("+------------+-----------------+-----------------+-----------------+--------+\n");
System.out.printf(enFormat, "Name", "ID", "Password", "Phone Number", "Coupon");
System.out.format("+------------+-----------------+-----------------+-----------------+--------+\n");
System.out.printf(koFormat, "홍길동", "abc123", "abcabc", "010-1234-1234", "0");
System.out.printf(enFormat, "jack", "def456", "defdef", "010-5678-5678", "10");
System.out.format("+------------+-----------------+-----------------+-----------------+--------+\n");
```

![image](https://user-images.githubusercontent.com/46701146/199724390-29495eab-39b5-4abf-9417-787d7af7e0a7.png)
