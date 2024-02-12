import java.util.ArrayList;
import java.util.List;

public class Teacher {
   private String name;
   private List<String> subjects;
   private List<Session> schedule;
   private List<String> claSs;

   public Teacher(){}
   public Teacher(String name) {
      this.name = name;
      this.subjects = new ArrayList<>();
      this.schedule = new ArrayList<>();
      this.claSs = new ArrayList<>();
   }

   public String getName() {
      return name;
   }

   public List<String> getSubjects() {
      return new ArrayList<>(subjects);
   }

   public List<String> getClaSs() {
      return new ArrayList<>(claSs);
   }

   public List<Session> getSchedule() {
      return new ArrayList<>(schedule);
   }

   public void addSubject(String subject) {
      subjects.add(subject);
   }

   public void addClass(String className) {
      claSs.add(className);
   }

   public void addToSchedule(Session session, String className) {
      session.setClassName(className);
      schedule.add(session);
   }

}
