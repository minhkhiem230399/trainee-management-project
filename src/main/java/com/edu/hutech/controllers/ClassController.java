package com.edu.hutech.controllers;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.dtos.CourseDto;
import com.edu.hutech.dtos.LabelValueDTO;
import com.edu.hutech.entities.*;
import com.edu.hutech.functiondto.CourseSearchDto;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.TraineeCourseRepository;
import com.edu.hutech.repositories.TraineeSubjectRepository;
import com.edu.hutech.services.implementation.*;
import com.edu.hutech.utils.page.Pagination;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Class Controller
 * Author: KhiemKM
 */
@Controller
@RequestMapping("/class-management")
public class ClassController {

    @Autowired
    TrainerServiceImpl trainerService;

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    TraineeCourseService traineeCourseService;

    @Autowired
    TraineeCourseRepository traineeCourseRepository;

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    TrainingObjectServiceIpml trainingObjectService;

    @Autowired
    TraineeSubjectRepository traineeSubjectRepository;


    /**
     * Handing the request representing classes infor
     *
     * @param model
     * @param page  is page number in paging
     * @param size  is the quantity of element in a page
     * @param field is the field that user want to sorted by
     * @return class-management.html
     */
    @GetMapping()
    public String displayCourseList(Model model, @RequestParam("page") Optional<Integer> page, HttpServletRequest request,
                                    @RequestParam(value = "size") Optional<Integer> size, @RequestParam("field") Optional<String> field,
                                    @RequestParam(value = "search") Optional<String> search, HttpServletResponse response) {

        int cPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String sortField = field.orElse("default");
        String searchX = search.orElse(null);

        Page<CourseDto> classPage;
        CourseSearchDto x = new CourseSearchDto();
        x.setPageIndex(cPage);
        x.setPageSize(pageSize);
        x.setText(searchX);

        HttpSession session = request.getSession();
        Integer traineeId = null;
        Integer trainerId = null;
        assert false;
        if (session.getAttribute("userId") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINER")) {
                trainerId = (Integer) session.getAttribute("userId");
            } else {
                traineeId = (Integer) session.getAttribute("userId");
            }
        }

        classPage = courseService.searchByDto(x, trainerId, traineeId);

        model.addAttribute("classPage", classPage);
        model.addAttribute("cPage", cPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("field", sortField);

        PaginationRange p = Pagination.paginationByRange(cPage, classPage.getTotalElements(), pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/class-views/class-management";
    }

    /**
     * get trainers to create class
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/add-class")
    public String addClass(final ModelMap model, final HttpServletRequest request) {
        model.addAttribute("trainers", trainerService.getAll());
        return "pages/class-views/class-create-new";
    }

    /**
     * create add
     *
     * @param model
     * @param data
     * @return
     */
    @PostMapping("/add-class")
    public ResponseEntity<AjaxResponse> add(final ModelMap model,
                                            @RequestBody String data) {
        JSONObject json = new JSONObject(data);
        Course course = new Course();
        course.setName(json.getString("name"));
        course.setOpenDate(json.getString("start"));
        course.setEndDate(json.getString("end"));
        course.setPlanCount(json.getInt("planCount"));
        course.setTrainer(trainerService.findById(json.getInt("trainer")));
        JSONArray arr = json.getJSONArray("listObject");
        List<Object> list = arr.toList();

        Set<Integer> hashSet = new HashSet<>();
        for (Object l : list) {
            hashSet.add((Integer) l);
        }
        List<TrainingObjective> trainingObjectiveList = new ArrayList<>();
        for (Integer i : hashSet) {
            TrainingObjective trainingObjective = trainingObjectService.findById(i);
//            trainingObjectService.save(trainingObjective);
            trainingObjectiveList.add(trainingObjective);
        }
        course.setTrainingObjectives(trainingObjectiveList);
        courseService.save(course);

        return ResponseEntity.ok(new AjaxResponse(200, data));
    }

    /**
     * get object name to create class
     *
     * @param term
     * @return
     */
    @RequestMapping(value = "/trainingObjectName")
    @ResponseBody
    public List<LabelValueDTO> plantNamesAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<TrainingObjective> all = trainingObjectService.getAll();
        List<LabelValueDTO> labelValueDTOS = new ArrayList<>();
        for (TrainingObjective trainingObjective : all) {
            if (trainingObjective.getName().toLowerCase().contains(term.toLowerCase())) {
                LabelValueDTO labelValueDTO = new LabelValueDTO();
                labelValueDTO.setLabel(trainingObjective.getName());
                labelValueDTO.setValue(trainingObjective.getId());
                labelValueDTOS.add(labelValueDTO);
            }
        }
        return labelValueDTOS;

    }

    /**
     * View class details
     *
     * @param model
     * @param id
     * @param page
     * @param size
     * @param view
     * @return
     */
    @GetMapping("/class-details")
    public String detail(Model model, @RequestParam Integer id,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size,
                         @RequestParam("view") Optional<String> view) {
        int cPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String modeView = view.orElse("list");

        pageSize = pageSize < 5 ? 5 : Math.min(pageSize, 500);
        int pass = 0;
        int failed = 0;
        int drop = 0;

        Course course = courseService.findById(id);
        List<TraineeCourse> traineeCourseList = null;
        traineeCourseList = traineeCourseService.getTraineeCourseByCourseId(id);
        List<Trainee> traineeList = new ArrayList<>();
        for (TraineeCourse traineeCourse : traineeCourseList) {
            traineeList.add(traineeCourse.getTrainee());
            if (traineeCourse.getScore() >= 5) {
                pass++;
            } else {
                if (traineeCourse.getScore() > 0) {
                    failed++;
                }
            }
            if (traineeCourse.getScore() == 0) {
                drop++;
            }
        }

        List<Trainee> trainees = Pagination.getPage(traineeList, cPage, pageSize);

        int totalPages = (int) Math.ceil((double) traineeList.size() / (double) pageSize);

        model.addAttribute("modeView", modeView);
        model.addAttribute("traineeCourses", traineeCourseList);
        model.addAttribute("trainees", trainees);
        model.addAttribute("cPage", cPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("totalElements", traineeCourseList.size());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pass", pass);
        model.addAttribute("failed", failed);
        model.addAttribute("drop", drop);

        PaginationRange p = Pagination.paginationByRange(cPage, traineeList.size(), pageSize, 5);
        model.addAttribute("paginationRange", p);

        model.addAttribute("class", course);

        return "pages/class-views/class-details";
    }

    /**
     * Add trainee to class
     *
     * @param account
     * @return
     */
    @PostMapping("/add-trainee")
    public ResponseEntity<AjaxResponse> addTrainee(@RequestBody String account) {
        JSONObject json = new JSONObject(account);

        if (traineeService.getTraineeByAccount(json.get("account").toString()) == null) {
            return ResponseEntity.ok(new AjaxResponse(401, "Email not valid!"));
        }

        Trainee trainee = traineeService.getTraineeByAccount(json.get("account").toString());
        if (traineeCourseService.checkExistTrainee(json.getInt("classId"), trainee.getId())) {
            return ResponseEntity.ok(new AjaxResponse(402, "Email existed!"));
        }

        Course courseInDB = courseService.findById(json.getInt("classId"));

        List<TrainingObjective> trainingObjectiveList = courseInDB.getTrainingObjectives();
        List<TraineeSubject> traineeSubjects = new ArrayList<>();

        for (TrainingObjective trainingObjective : trainingObjectiveList) {
            TraineeSubject traineeSubject = new TraineeSubject();
            traineeSubject.setTrainingObjective(trainingObjective);
            traineeSubject.setTrainee(trainee);
            traineeSubject.setCourse(courseInDB);
            traineeSubjects.add(traineeSubject);
        }

        trainee.setTraineeSubjects(traineeSubjects);
        TraineeCourse traineeCourse = new TraineeCourse();
        traineeCourse.setTrainee(trainee);
        traineeCourse.setScore(0.0);

        courseInDB.setCurrCount(courseInDB.getCurrCount() + 1);

        if (courseInDB.getCurrCount() > courseInDB.getPlanCount()) {
            return ResponseEntity.ok(new AjaxResponse(400, "Max count!"));
        }
        courseInDB.addTraineeCourses(traineeCourse);
        courseService.update(courseInDB);
        return ResponseEntity.ok(new AjaxResponse(200, account));
    }

    /**
     * View trainee details
     *
     * @param model
     * @param traineeId
     * @param courseId
     * @return
     */
    @GetMapping("/trainee-details")
    public String displayAllTraineeDetails(Model model, @RequestParam("traineeId") Integer traineeId,
                                           @RequestParam("courseId") Integer courseId) {
        Trainee trainee = traineeService.findById(traineeId);
        Double finalScore = traineeCourseService.getScoreByTraineeId(courseId, traineeId);
        TraineeCourse traineeCourse = traineeCourseService.getByTCourseIdAndTraineeId(courseId, traineeId);
        double scale = Math.pow(10, 1);

        if (trainee != null) {
            model.addAttribute("trainee", trainee);
        } else {
            model.addAttribute("trainee", new Trainee());
        }
        if (finalScore == null) {
            model.addAttribute("finalScore", (int) (Math.round(0 * scale) / scale) * 10);
        } else {
            model.addAttribute("finalScore", (int) (Math.round(finalScore * scale) / scale) * 10);
        }
//        model.addAttribute("presentAttendance", presentAttendance);
//        model.addAttribute("totalAttendance", totalAttendance);
        model.addAttribute("listNameAndScore", courseService.findSubjectByCourseIdAndTraineeId(traineeCourse.getCourse().getId(), traineeId));
//        model.addAttribute("listDateAndAttendance", listDateAndAttendance);

        return "pages/class-views/class-trainee-details";
    }

    /**
     * Create new training object
     *
     * @param name
     * @return
     */
    @PostMapping("/add-training-object")
    public ResponseEntity<AjaxResponse> addTrainingObject(@RequestBody String name) {
        TrainingObjective trainingObjective = new TrainingObjective();
        trainingObjective.setName(name);
        trainingObjectService.save(trainingObjective);

        return ResponseEntity.ok(new AjaxResponse(200, trainingObjective));
    }

    /**
     * Add/Update score to trainee -> ROLE_ADMIN
     *
     * @param data
     * @return
     */
    @PostMapping("/update-score")
    public ResponseEntity<AjaxResponse> updateScore(@RequestBody String data) {

        JSONObject json = new JSONObject(data);
        Integer score = json.getInt("score");
        Integer id = json.getInt("subId");
        Optional<TraineeSubject> traineeSubject = traineeSubjectRepository.findById(id);
        traineeSubject.orElseThrow().setScore(score);

        Integer courseId = traineeSubject.orElseThrow().getCourse().getId();
        Integer traineeId = traineeSubject.orElseThrow().getTrainee().getId();
        List<TraineeSubject> traineeSubjects = courseService.findSubjectByCourseIdAndTraineeId(courseId, traineeId);

        Double finalScore = 0.0;
        for (TraineeSubject traineeSubject1 : traineeSubjects) {
            finalScore += traineeSubject1.getScore();
        }
        TraineeCourse traineeCourse = traineeCourseService.getByTCourseIdAndTraineeId(courseId, traineeId);
        traineeCourse.setScore(finalScore / traineeSubjects.size());

        traineeCourseRepository.save(traineeCourse);
        traineeSubjectRepository.save(traineeSubject.orElseThrow());
        return ResponseEntity.ok(new AjaxResponse(200, finalScore / traineeSubjects.size()));
    }

    /**
     * Add/Update score to trainee -> ROLE_TRAINER
     *
     * @param data
     * @return
     */
    @PostMapping("/update-score-review")
    public ResponseEntity<AjaxResponse> updateScoreReview(@RequestBody String data) {
        JSONObject json = new JSONObject(data);

        Integer score = json.getInt("score");
        Integer id = json.getInt("subId");
        Optional<TraineeSubject> traineeSubject = traineeSubjectRepository.findById(id);
        traineeSubject.orElseThrow().setScoreReview(score);
        traineeSubjectRepository.save(traineeSubject.orElseThrow());
        return ResponseEntity.ok(new AjaxResponse(200, data));
    }

}
