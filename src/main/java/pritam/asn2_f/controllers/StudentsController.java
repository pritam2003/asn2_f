package pritam.asn2_f.controllers;


import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pritam.asn2_f.models.Student;
import pritam.asn2_f.models.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class StudentsController {
    // get from database
    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")   // endpoint students/view
    public String getAllUsers(Model model){
        System.out.println("Getting all students"); // type sout and press tab to auto-generate this line
        List<Student> students = studentRepo.findAll();
        // end of database call
        model.addAttribute("st", students); // st is the name of the variable that will be used in the view (html file
        return "students/display";
    }

    @GetMapping("/students/{uid}")
    public String getStudentDetails(@PathVariable int uid, Model model) {
        Student student = studentRepo.findByUid(uid).get(0);
        model.addAttribute("student", student);
        return "students/studentDetails";
    }

    @GetMapping("/students/add")   // endpoint students/add
    public String showAddForm() {
        return "students/add";
    }

    @PostMapping("/students/add")  // endpoint students/add
    public String addStudent(@RequestParam Map<String, String> newstudent, HttpServletResponse response){
        System.out.println("ADD student");

        // get the values from the form
        String newName = newstudent.get("name");
        int newWeight = Integer.parseInt(newstudent.get("weight"));
        int newHeight = Integer.parseInt(newstudent.get("height"));
        String newHair = newstudent.get("hair");
        double newGpa = Double.parseDouble(newstudent.get("gpa"));
        String newColour = newstudent.get("colour");
        studentRepo.save(new Student(newName, newWeight, newHeight, newHair, newGpa, newColour)); // save to database
        response.setStatus(201);
        return "redirect:/students/view";
    }

    @GetMapping("/students/{uid}/edit")
    public String showUpdateForm(@PathVariable int uid, Model model) {
        Student student = studentRepo.findByUid(uid).get(0);
        model.addAttribute("student", student);
        return "students/edit";
    }

    @PostMapping("/students/{uid}/edit")
    public String updateStudent(@PathVariable int uid, @RequestParam Map<String, String> updatedStudent, HttpServletResponse response) {
        System.out.println("EDIT student");

        Student studentToUpdate = studentRepo.findByUid(uid).get(0);
        studentToUpdate.setName(updatedStudent.get("name"));
        studentToUpdate.setWeight(Integer.parseInt(updatedStudent.get("weight")));
        studentToUpdate.setHeight(Integer.parseInt(updatedStudent.get("height")));
        studentToUpdate.setHair(updatedStudent.get("hair"));
        studentToUpdate.setGpa(Double.parseDouble(updatedStudent.get("gpa")));
        studentToUpdate.setColour(updatedStudent.get("colour"));
        studentRepo.save(studentToUpdate);
        return "redirect:/students/view";
    }

    @PostMapping("/students/{uid}/delete")
    public String deleteStudent(@PathVariable int uid) {
        System.out.println("DELETE student");

        studentRepo.deleteById(uid);
        return "redirect:/students/view";
    }

    @GetMapping("/students/stats")
    public String getStats(Model model) {
        List<Student> students = studentRepo.findAll();

        // Calculate highest and lowest GPA
        Student highestGPAStudent = students.stream()
                .max(Comparator.comparingDouble(Student::getGpa))
                .orElse(null);
        Student lowestGPAStudent = students.stream()
                .min(Comparator.comparingDouble(Student::getGpa))
                .orElse(null);

        // Calculate tallest and shortest students
        Student tallestStudent = students.stream()
                .max(Comparator.comparingInt(Student::getHeight))
                .orElse(null);
        Student shortestStudent = students.stream()
                .min(Comparator.comparingInt(Student::getHeight))
                .orElse(null);

        model.addAttribute("highestGPAStudent", highestGPAStudent);
        model.addAttribute("lowestGPAStudent", lowestGPAStudent);
        model.addAttribute("tallestStudent", tallestStudent);
        model.addAttribute("shortestStudent", shortestStudent);

        return "students/stats";
    }
}
