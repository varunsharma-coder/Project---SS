/**
* A booking info class with start date, end date, and id.
*/
import java.text.SimpleDateFormat;  
import java.util.Date;  

public class BookingInfo {
   private String id;
   private Date start;
   private Date end;

   /**
    * Constructs a booking info.
    * @param starts  the starting date of booking
    * @param ends    the ending date of booking
    * @param id      the id of the booking
    */
   public BookingInfo(Date start, Date end, String id) {
       this.start = start;
       this.end = end;
       this.id = id;
   }

   /**
    * Gets the starting date of the booking
    * @return  the starting date
    */
   public Date getStartDate() {
       return start;
   }

    /**
    * Gets the ending date of the booking
    * @return  the ending date
    */
    public Date getEndDate() {
        return end;
    }

   /**
    * Gets the id of this booking
    * @return  the id 
    */
   public String getId() {
       return id;
   }

   /**
    * Returns a string representation of (id, start, end).
    * @return  the string representation of this booking in the format of (id, start, end)
    */
   public String toString() {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       return "(" + id + ", " + formatter.format(start) + ", "+ formatter.format(end) + ")";
   }
}