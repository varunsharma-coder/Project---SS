
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingManagerTester {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // Read and display bookings
            ArrayList<BookingInfo> bookings = manager.readBookings("test.bin");
            System.out.println("Read Bookings: " + bookings);

            // Sort and display sorted bookings
            ArrayList<BookingInfo> sortedBookings = manager.sortBookings(bookings);
            System.out.println("Sorted Bookings: " + sortedBookings);

            // Remove conflicts and display results
            ArrayList<BookingInfo> noConflictBookings = manager.removeConflicts(sortedBookings);
            System.out.println("Non-Conflicting Bookings: " + noConflictBookings);

            // Save sorted bookings
            manager.saveBookingInfo("sorted_test.bin", sortedBookings);

            // Search bookings in a date range
            Date minDate = format.parse("2001-01-01");
            Date maxDate = format.parse("2001-01-06");
            ArrayList<BookingInfo> searchResults = manager.bookingSearch("sorted_test.bin", minDate, maxDate);
            System.out.println("Search Results: " + searchResults);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    
}


}