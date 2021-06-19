package com.edu.hutech.controllers;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.TraineeCourse;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Home Controller
 * author: KhiemKM
 */
@Controller
@RequestMapping(value = {"/dashboard", "/"})
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    /**
     * View home page
     *
     * @param model
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping()
    public String viewHomePage(Model model, @RequestParam(name = "start-date", required = false) String startDate,
                               @RequestParam(name = "end-date", required = false) String endDate) {

        int waitingCourse = 0;
        int releaseCourse = 0;
        int runningCourse = 0;
        int waitingTrainee = 0;
        int releaseTrainee = 0;
        int runningTrainee = 0;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");


        List<Course> listCourse;
        List<Trainee> listTrainee = traineeRepository.findAll();
        listTrainee.removeIf(trainee -> trainee.getDelFlag() == 1);


        Set<Integer> listId = new HashSet<>();

        if (startDate == null && endDate == null) {
            listCourse = courseRepository.findAll();
            listCourse.removeIf(course -> course.getDelFlag() == 1);
            for (Course c : listCourse) {
                for (TraineeCourse traineeCourse : c.getTraineeCourses()) {
                    listId.add(traineeCourse.getTrainee().getId());
                }
                if (c.getStatusProgress().equals("FINISHED") && c.getDelFlag() == 0) {
                    releaseCourse++;
                }
                if (c.getStatusProgress().equals("WAITING") && c.getDelFlag() == 0) {
                    waitingCourse++;
                }
                if (c.getStatusProgress().equals("RUNNING") && c.getDelFlag() == 0) {
                    runningCourse++;
                }
            }
            model.addAttribute("totalTrainee", listTrainee.size());
            model.addAttribute("rTrainee", listTrainee.size() - listId.size());
        } else {
            assert startDate != null;
            LocalDate startDt = LocalDate.parse(startDate, formatter1);
            assert false;
            LocalDate endDt = LocalDate.parse(endDate, formatter1);
            listCourse = courseRepository.findAll();
            listCourse.removeIf(course -> course.getDelFlag() == 1);

            if (CollectionUtils.isNotEmpty(listCourse)) {

                Iterator<Course> it = listCourse.iterator();
                while (it.hasNext()) {
                    Course course = it.next();
                    if (course != null) {
                        LocalDate start = LocalDate.parse(course.getOpenDate(), formatter2);
                        LocalDate end = LocalDate.parse(course.getEndDate(), formatter2);
                        if (start.isBefore(startDt) || end.isAfter(endDt)) {
                            it.remove();
                        }
                    } else {
                        break;
                    }
                }

                for (Course c : listCourse) {
                    for (TraineeCourse traineeCourse : c.getTraineeCourses()) {
                        listId.add(traineeCourse.getTrainee().getId());
                        listTrainee.add(traineeCourse.getTrainee());

                    }
                    if (c.getStatusProgress().equals("FINISHED") && c.getDelFlag() == 0) {
                        releaseCourse++;
                    }
                    if (c.getStatusProgress().equals("WAITING") && c.getDelFlag() == 0) {
                        waitingCourse++;
                    }
                    if (c.getStatusProgress().equals("RUNNING") && c.getDelFlag() == 0) {
                        runningCourse++;
                    }
                }
                listTrainee.removeIf(trainee -> trainee.getDelFlag() == 1);
                model.addAttribute("totalTrainee", listId.size());
                model.addAttribute("rTrainee", 0);
            }
        }

        model.addAttribute("totalCourse", listCourse.size());

        model.addAttribute("wCourse", waitingCourse);
        model.addAttribute("releCourse", releaseCourse);
        model.addAttribute("runCourse", runningCourse);
        model.addAttribute("wTrainee", listId.size());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "pages/index";
    }

}
