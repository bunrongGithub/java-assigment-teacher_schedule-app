
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class TeacherScheduleApp {
   private static final List<Teacher> teachers = new ArrayList<>();
   /** DISPLAY OPTION. */
   public static void display() {
      System.out.println("Choose option:");
      System.out.println("1 - Register Teacher");
      System.out.println("2 - Display all Teachers");
      System.out.println("3 - Add Class & Subject for Teacher");
      System.out.println("4 - Add Schedule for each Teacher");
      System.out.println("5 - Generate Schedule");
      System.out.println("0 - Exit");
   }
   /**
    *
    * FUNCTION
    *
    */
   private static boolean isTimeRangeValid(int startTime, int endTime) {
      return startTime >= 0 && endTime > startTime && endTime <= 24;
   }
   public static boolean doesTimeExistOnTeacher(Teacher teacher, int startTime, int endTime,String day) {
      List<Session> teacherSchedule = teacher.getSchedule();
      for (Session session : teacherSchedule) {
         if (startTime < session.getEndTime() && endTime > session.getStartTime() && session.getDay().equalsIgnoreCase(day)) {
            return true;
         }
      }
      return false;
   }
   /* FIND TEACHER */
   public static Teacher findTeacher(String name) {
      for (Teacher teacher : teachers) {
         if (teacher.getName().equalsIgnoreCase(name)) {
            return teacher;
         }
      }
      return null;
   }

   public static boolean checkSubjectExits(Teacher teacher,String subject){
      List<String> teacherList = teacher.getSubjects();
      for (String subjectList : teacherList) {
         if(subjectList.equalsIgnoreCase(subject)){
            return false;
         }
      }
      return true;
   }
   public static boolean checkClassExist(Teacher teacher ,String className){
      List<String> checkClass = teacher.getClaSs();
      for (String check: checkClass)
         if(check.equalsIgnoreCase(className)) return false;
      return true;
   }
   /** FUNCTION FOR REGISTERS  */
   public static void registerTeacher(Scanner scanner) {
      int numOfTeacher;
      System.out.print("Enter the number of teacher: ");
      try {
         numOfTeacher = scanner.nextInt();
         scanner.nextLine();
         for (int i = 0; i < numOfTeacher; i++) {
            System.out.print("Enter teacher name: ");
            String teacherName = scanner.nextLine();
            Teacher teacherFind = findTeacher(teacherName);
            if(teacherFind == null){
               Teacher teacher = new Teacher(teacherName);
               teachers.add(teacher);
            }else System.out.println("This name already exits!");
         }
         System.out.println("Teacher registered successfully!");
      } catch (Exception e){
         System.out.print("Invalid input . Please enter a valid number." + e.getMessage());
      }
   }

   /* check exist time slot , subject , day , for each teacher ofter generate schedule */
   public static boolean checkSessionExist(List<Teacher> teachers,Session session) {
      for (Teacher teacher:teachers){
        for (Session checked: teacher.getSchedule()){
            if(checked.getSubject().getName().contains(session.getSubject().getName())
                    && checked.getDay().equalsIgnoreCase(session.getDay())
                    && checked.getStartTime() == session.getStartTime()
                    && checked.getEndTime() == session.getEndTime()
                    && checked.getClassName().equalsIgnoreCase(session.getClassName())){
               return true;

            }
        }
      }
      return false;
  }
   /** DISPLAY ALL ABOUT INFO OF EACH TEACHER LIKE SUBJECT,CLASS NAME */
   public static void displayTeacher() {
      System.out.println("List of Teachers:\n");
      for (Teacher teacher : teachers) {
         System.out.println("Teacher: " + teacher.getName());
         List<String> subjects = teacher.getSubjects();
         List<String> classes = teacher.getClaSs();
         if (!subjects.isEmpty()) {
            System.out.println("Subject: " + String.join(",", subjects));
         }
         if (!classes.isEmpty()) {
            System.out.println("her class: " + String.join(",", classes));
         }
         System.out.println();
      }
   }
   /** REGISTER CLASS NAME & SUBJECT FOR SPACIFICE TEACHER*/
   public static void addClassForTeacher(Scanner scanner) {
      System.out.print("Enter teacher name: ");
      String teacherName = scanner.next();
      Teacher teacher = findTeacher(teacherName);
      if (teacher != null) {
         System.out.print("Enter class name: ");
         String className = scanner.next();
         System.out.print("Enter subject for this class: ");
         String subject = scanner.next();
         if(checkSubjectExits(teacher,subject) && checkClassExist(teacher,className)){
            teacher.addClass(className);
            teacher.addSubject(subject);
            System.out.println("Class & Subject successfully added to " + teacher.getName()+"!");
         } else if (!checkClassExist(teacher,className) | !checkSubjectExits(teacher,subject)) {
            System.out.println("Name: "+teacher.getName() + " or Subject " + subject + " already Exits");
         }
      }else System.out.println("Teacher Name: "+ teacherName +" not found!");
   }
   public static void addSchedule(Scanner scanner) {
      System.out.print("Enter teacher name: ");
      String teacherName = scanner.next();
      Teacher teacher = findTeacher(teacherName);
      if (teacher != null) {
         System.out.print("Enter subject for this session: ");
         String subjectName = scanner.next();
         if (teacher.getSubjects().contains(subjectName)) {
            Subject subject = new Subject(subjectName);
            System.out.print("Enter day for this session: ");
            String day = scanner.next();
            try {
               System.out.print("Enter start time: ");
               int startTime = scanner.nextInt();
               System.out.print("Enter end time: ");
               int endTime = scanner.nextInt();
               scanner.nextLine();
               if (isTimeRangeValid(startTime, endTime)) {
                  if(!doesTimeExistOnTeacher(teacher,startTime,endTime,day)){
                     System.out.print("Enter class name: ");
                     String className = scanner.next();
                     if (teacher.getClaSs().contains(className)) {
                        Session session = new Session(subject, day, startTime, endTime, className);
                        if(!(checkSessionExist(teachers,session))){
                           teacher.addToSchedule(new Session(subject, day, startTime, endTime, className), className);
                           System.out.println("Schedule added successfully!\n");
                        }else System.out.println("Cannot add because session already exist with another teacher!");
                     } else {
                        System.out.println("Invalid class for this teacher or class not found!\n");
                     }
                  }else System.out.println("Start time & End-Time exist for " + teacher.getName());
               } else System.out.println("Invalid time range or class conflict!\n");
            } catch (Exception e) {
               System.out.println("Invalid input. Please enter valid integer values for start time and end time!\n");
            }
         } else System.out.println("Subject for this teacher not found!\n");

      } else System.out.println("Teacher not found!");
   }
   public static void generateSchedule() {
      System.out.println("*-------------------------------------*");
      System.out.println("Schedule for all Teachers:");
      System.out.println("*-------------------------------------*");

      for (Teacher teacher : teachers) {
         System.out.println(
               "+ Teacher: " + teacher.getName() + "     taught class: " + String.join(",", teacher.getClaSs()));
         List<Session> sessions = teacher.getSchedule();
         for (Session session : sessions) {
            System.out.println("      Subject: " + session.getSubject().getName() +
                  ", Day: " + session.getDay() +
                  ", Time: " + session.getStartTime() + "-" + session.getEndTime() +
                  ", Class: " + session.getClassName());
         }
      }
      System.out.println();
   }

   public static void main(String[] args) {
      try (Scanner scanner = new Scanner(System.in)) {
         int option;
         do {
            display();
            option = scanner.nextInt();
            switch (option) {
               case 1 -> registerTeacher(scanner);
               case 2 -> displayTeacher();
               case 3 -> addClassForTeacher(scanner);
               case 4 -> addSchedule(scanner);
               case 5 -> generateSchedule();
               case 0 -> System.out.println("Exiting the application. Goodbye!");
               default -> System.out.println("Invalid option!");
            }
         } while (option != 0);
      }
   }
}
