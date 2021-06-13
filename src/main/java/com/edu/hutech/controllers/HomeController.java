package com.edu.hutech.controllers;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.services.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = {"/dashboard", "/"})
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    UserServiceImpl userService;



    @Autowired
    private TraineeRepository traineeRepository;


    /**
     * Handling Home request
     *
     * @param model
     * @return the reporting view of trainee and class
     */
    @GetMapping()
    public String viewHomePage(Model model, @RequestParam("start-date") Optional<String> startDate,
                               @RequestParam("end-date") Optional<String> endDate) {

        int waitingCourse = 0;
        int releaseCourse = 0;
        int runningCourse = 0;
        int waitingTrainee = 0;
        int releaseTrainee = 0;
        int runningTrainee = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Course> listCourse = new ArrayList<>();
        List<Trainee> listTrainee = traineeRepository.findAll();
        listTrainee.removeIf(trainee -> trainee.getDelFlag() == 1);

        Set<Integer> listId = new HashSet<>();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String defaultEndDate = "yyyy-MM-dd";
        String defaultStartDate = "yyyy-MM-dd";

        String start = startDate.orElse(defaultStartDate);
        String end = endDate.orElse(defaultEndDate);

        if (start.equals(defaultStartDate) && end.equals(defaultEndDate)) {
            listCourse = courseRepository.findAll();
            listCourse.removeIf(course -> course.getDelFlag() == 1);
            for (Course c : listCourse) {
                for (TraineeCourse traineeCourse : c.getTraineeCourses()){
                    listId.add(traineeCourse.getTrainee().getId());
                }
                if (c.getStatusProgress().equals("FINISHED") && c.getDelFlag() == 0){
                    releaseCourse++;
                }

                if (c.getStatusProgress().equals("WAITING") && c.getDelFlag() == 0){
                    waitingCourse++;
                }

                if(c.getStatusProgress().equals("RUNNING") && c.getDelFlag() == 0){
                    runningCourse++;
                }
            }

        } else {

            Date dateStart = new Date();
            Date dateEnd = new Date();
            try {
                dateStart = simpleDateFormat.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                dateEnd = simpleDateFormat.parse(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }

           // listCourse = courseRepository.findAllByOpenDateGreaterThanEqualAndEndDateLessThanEqual(dateStart, dateEnd);

//            for (Course c : listCourse) {
//                if (c.getStatus().equals("Done"))
//                    releaseCourse++;
//                else if (c.getStatus().equals("Waiting"))
//                    waitingCourse++;
//                else
//                    runningCourse++;
//            }
//            for (Course c : listCourse) {
//                listTrainee.addAll(c.getTrainee());
//            }
//
//            for (Trainee f : listTrainee) {
//                if (dateEnd.compareTo(f.getTraineeStatus().getStartDay()) < 0)
//                    waitingTrainee++;
//                else if (dateEnd.compareTo(f.getTraineeStatus().getEndDate()) > 0)
//                    releaseTrainee++;
//                else
//                    runningTrainee++;
//            }
        }

        model.addAttribute("totalCourse", listCourse.size());
        model.addAttribute("totalTrainee", listTrainee.size());
        model.addAttribute("wCourse", waitingCourse);
        model.addAttribute("releCourse", releaseCourse);
        model.addAttribute("runCourse", runningCourse);
        model.addAttribute("wTrainee", listId.size());
        model.addAttribute("rTrainee", listTrainee.size() - listId.size());
        model.addAttribute("rnTrainee", runningTrainee);
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);
        return "pages/index";
    }

}
