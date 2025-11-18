import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.nio.charset.StandardCharsets;

public class BookingManager {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final int RECORD_SIZE = 60; // Each record is 60 bytes

    public ArrayList<BookingInfo> readBookings(String pathName) throws IOException, ParseException {
        ArrayList<BookingInfo> bookings = new ArrayList<>();

        try (RandomAccessFile file = new RandomAccessFile(pathName, "r")) {
            byte[] buffer = new byte[RECORD_SIZE];

            while (file.read(buffer) == RECORD_SIZE) {
                String record = new String(buffer, StandardCharsets.UTF_8).trim();

                // Extract start date, end date, and ID correctly
                String startStr = record.substring(0, 10).trim(); // First 10 characters for start date (YYYY-MM-DD)
                String endStr = record.substring(10, 20).trim(); // Next 10 characters for end date (YYYY-MM-DD)
                String id = record.substring(20, 40).trim(); // Next 20 characters for ID

                // Debugging output to verify correct extraction
                System.out.println("Start Date: [" + startStr + "], End Date: [" + endStr + "], ID: [" + id + "]");

                // Validate and parse the dates
                Date startDate = parseDate(startStr);
                Date endDate = parseDate(endStr);

                if (startDate != null && endDate != null) {
                    bookings.add(new BookingInfo(startDate, endDate, id));
                } else {
                    System.out.println("Skipping invalid record with dates: Start Date: [" + startStr + "], End Date: [" + endStr + "]");
                }
            }
        }
        return bookings;
    }

    private Date parseDate(String dateStr) {
        try {
            // Attempt to parse the date string
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            // If parsing fails, return null
            return null;
        }
    }


	
	public ArrayList<BookingInfo> sortBookings(ArrayList<BookingInfo> inputList) {
		if (inputList == null || inputList.size() <= 1)
			return inputList;
			return mergeSort (inputList);
	}
	
	private ArrayList<BookingInfo> mergeSort (ArrayList<BookingInfo> list){
		if (list.size() <= 1)
			return list;
		int mid = list.size() / 2;
		ArrayList<BookingInfo> left = new ArrayList<>(list.subList(0, mid));
		ArrayList<BookingInfo> right = new ArrayList<>(list.subList(mid, list.size()));
		return merge(mergeSort(left), mergeSort(right));

	}
	private ArrayList<BookingInfo> merge (ArrayList<BookingInfo> left, ArrayList<BookingInfo> right) {
		ArrayList<BookingInfo> merged = new ArrayList<>();
		int i = 0, j = 0;
		while (i < left.size() && j < right.size()) {
			if (left.get(i).getStartDate().compareTo(right.get(j).getStartDate()) > 0){
				merged.add(left.get(i++));
				
			} else {
				merged.add(right.get(j++));
			}
		}
		while (i < left.size()) merged.add(left.get(i++));
		while (j < right.size()) merged.add(right.get(j++));
		return merged;
	}

	public ArrayList<BookingInfo> removeConflicts(ArrayList<BookingInfo> sortedList) {
		ArrayList<BookingInfo> result = new ArrayList<>();
		for (BookingInfo booking : sortedList) {
			if (result.isEmpty() || !booking.getStartDate().before(result.get(result.size()-1).getEndDate()));{
				result.add(booking);
			}
		}
		return result;
	}
	
	public void saveBookingInfo(String pathName, ArrayList<BookingInfo> sortedList) throws IOException {
		try (RandomAccessFile file = new RandomAccessFile(pathName, "rw")) {
			file.setLength(0);
			for (BookingInfo booking : sortedList) {
				String record = String.format("%-20s%-20s%-20s", DATE_FORMAT.format(booking.getStartDate()), DATE_FORMAT.format(booking.getEndDate()), booking.getId());
				file.write(record.getBytes());
						}
			}
		
	}
	
	public ArrayList<BookingInfo> bookingSearch(String pathName, Date minDate, Date maxDate) throws IOException, ParseException {
		ArrayList<BookingInfo> results = new ArrayList<>();
		
		try(RandomAccessFile file = new RandomAccessFile(pathName, "r")){
			long left = 0, right = file.length() / RECORD_SIZE;
			while (left < right) {
				long mid = (left + right) / 2;
				file.seek(mid * RECORD_SIZE);
				byte[] buffer = new byte [20];
				file.read(buffer);
				Date startDate = DATE_FORMAT.parse(new String(buffer).trim());
				if (startDate.before(minDate)) {
					right = mid;
					
				} else {
					left = mid + 1;
				}
			}
			
			file.seek(left * RECORD_SIZE);
			while (file.getFilePointer() < file.length()) {
				byte[] buffer = new byte[RECORD_SIZE];
				file.read(buffer);
				String record = new String(buffer);
				Date startDate = DATE_FORMAT.parse(record.substring(0, 20).trim());
				if (startDate.after(maxDate))
					break;
				
				Date endDate = DATE_FORMAT.parse(record.substring(20, 40).trim());
				String id = record.substring(40, 60).trim();
				results.add(new BookingInfo(startDate, endDate, id));
				
			}
		}
		return results;
	}
}
