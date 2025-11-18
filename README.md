# Project---SS

**Booking Manager**

- Java program that maangeses booking records that are stored in fixed length binary files
- Sort bookings by start date (merge sort)
- remove bookings that conflict: dates that are overlapping.
- sorted bookings saved back to disk
- search bookinhs within a date range

  **File Formatting**

  - record is 60 bytes
  - start date is 10 chars: yyyy-MM-dd
  - end date is 10 chars
  - ID is 20 chars
  - padding is 20 chars
 
    **How to Run**

    javac BookingInfo.java BookingManager.java BookingManagerTester.java

    java BookingManagerTester

    - test.bin must exist in the same directory
   

**Files Included**

BookingInfo.java
BookingManager.java
BookingManagerTester.java
