package com.edu.hutech.controllers;

import java.util.List;
import java.util.Optional;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.utils.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * General Management Controller
 * author: KhiemKM
 */
@Controller
@RequestMapping("/general-management")
public class GeneralManagement {

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * View list trainee
     *
     * @param model
     * @return trainee-list view
     */
    @GetMapping("/trainee-list")
    public String displayTraineeList(Model model, @RequestParam("page") Optional<Integer> page) {
        int cPage = page.orElse(1);
        int pageSize = 5;

        List<Trainee> trainees = traineeRepository.findAll();
        model.addAttribute("trainees", trainees);

        List<Trainee> traineesAfterPaging = Pagination.getPage(trainees, cPage, pageSize);
        int currIndex = trainees.indexOf(traineesAfterPaging.get(0));
        int totalPages = (int) Math.ceil( (double)trainees.size()/ (double) pageSize);
        int totalElements = trainees.size();

        model.addAttribute("trainees", traineesAfterPaging);
        model.addAttribute("cPage", cPage);
        model.addAttribute("currIndex", currIndex);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements",totalElements);
        model.addAttribute("size",pageSize);

        PaginationRange p = Pagination.paginationByRange(cPage, totalElements, pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/general-views/trainee-list";
    }

    /**
     * Display subject list
     * @param model
     * @param page is the page number in paging
     * @return subject-list view
     */
    @GetMapping("/subject-list")
    public String displaySubjectList(Model model, @RequestParam("page") Optional<Integer> page) {

        int cPage = page.orElse(1);
        int pageSize = 5;

        List<Course> courses = courseRepository.findAll();

        List<Course> coursesAfterPaging = Pagination.getPage(courses, cPage, pageSize);
        int currIndex = courses.indexOf(coursesAfterPaging.get(0));
        int totalPages = (int) Math.ceil( (double)courses.size()/ (double) pageSize);
        int totalElements = courses.size();

        model.addAttribute("courses", coursesAfterPaging);
        model.addAttribute("cPage", cPage);
        model.addAttribute("currIndex", currIndex);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements",totalElements);
        model.addAttribute("size",pageSize);

        PaginationRange p = Pagination.paginationByRange(cPage, totalElements, pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/general-views/subject-list";
    }

}
