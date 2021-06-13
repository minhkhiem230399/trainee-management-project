package com.edu.hutech.controllers;

import java.util.List;
import java.util.Optional;

import com.edu.hutech.entities.Course;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.utils.page.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/general-management/subject-list")
public class SubjectController {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Display subject details when user click in name of a subject in subject-list view
     * @param model
     * @param courseId is the ID of subject which user click on
     * @param page is the page number in paging, default is 1
     * @return the subject-details view which contains subject infor
     */
    @GetMapping("/subject-details")
    public String displaySubjectDetail(Model model, @RequestParam("id") int courseId, @RequestParam("page") Optional<Integer> page) {

        int cPage = page.orElse(1);
        int pageSize = 5;

        Course course = courseRepository.getOne(courseId);
        return "pages/general-views/subject-details";
    }
}
