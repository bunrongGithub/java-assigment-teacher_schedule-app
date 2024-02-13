public class Session {
   private Subject subject;
   private String day;
   private int startTime;
   private int endTime;
   private String className;
   public Session(){}
   public Session(Subject subject, String day, int startTime, int endTime, String className) {
      this.subject = subject;
      this.day = day;
      this.startTime = startTime;
      this.endTime = endTime;
      this.className = className;
   }
   public String getClassName() {
      return className;
   }

   public Subject getSubject() {
      return subject;
   }

   public String getDay() {
      return day;
   }

   public int getStartTime() {
      return startTime;
   }

   public int getEndTime() {
      return endTime;
   }

   public void setClassName(String className) {
      this.className = className;
   }
}
