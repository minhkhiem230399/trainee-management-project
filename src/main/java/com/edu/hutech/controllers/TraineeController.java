package com.edu.hutech.controllers;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.dtos.TraineeScoreDto;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.AttendanceRepository;
import com.edu.hutech.repositories.TraineeRepository;
import com.edu.hutech.services.implementation.RoleService;
import com.edu.hutech.services.implementation.TraineeCourseService;
import com.edu.hutech.services.implementation.TraineeServiceImpl;
import com.edu.hutech.services.implementation.UserServiceImpl;
import com.edu.hutech.utils.page.Pagination;
import com.edu.hutech.utils.sort.GenericComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Trainee Controller
 * author: KhiemKM
 */
@Controller
@Transactional
@RequestMapping("/trainee-management")
public class TraineeController {
    @Autowired
    TraineeRepository traineeRepository;

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    RoleService roleService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    TraineeCourseService traineeCourseService;

    @Autowired
    UserServiceImpl userService;

    /**
     * View trainee management
     *
     * @param model
     * @param request
     * @param page
     * @param size
     * @param field
     * @param view
     * @param search
     * @return
     */
    @GetMapping()
    public String displayTraineeManagement(Model model, final HttpServletRequest request,
                                           @RequestParam("page") Optional<Integer> page,
                                           @RequestParam("size") Optional<Integer> size,
                                           @RequestParam("field") Optional<String> field,
                                           @RequestParam("view") Optional<String> view,
                                           @RequestParam(value = "search", required = false) String search) {

        int cPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String sortField = field.orElse("default");
        String modeView = view.orElse("list");
        pageSize = pageSize < 5 ? 5 : Math.min(pageSize, 500);
        List<Trainee> listTrainees = traineeRepository.findScoreByAllTrainee();
        if (sortField.contains("-asc")) {
            String[] splits = sortField.split("-asc", 2);
            Collections.sort(listTrainees, new GenericComparator(true, splits[0]));
        } else {
            if (sortField.equals("default")) {
            } else {
                Collections.sort(listTrainees, new GenericComparator(false, sortField));
            }
        }
        if (search != null) {
            listTrainees.removeIf(trainee -> !trainee.getUser().getAccount().contains(search));
        }

        List<Trainee> trainees = Pagination.getPage(listTrainees, cPage, pageSize);

        int totalPages = (int) Math.ceil((double) listTrainees.size() / (double) pageSize);

        HttpSession session = request.getSession();
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", "Update success!");
        }
        session.removeAttribute("message");

        model.addAttribute("modeView", modeView);
        model.addAttribute("trainees", trainees);
        model.addAttribute("cPage", cPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("totalElements", listTrainees.size());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("field", sortField);
        model.addAttribute("search", search);

        PaginationRange p = Pagination.paginationByRange(cPage, listTrainees.size(), pageSize, 5);
        model.addAttribute("paginationRange", p);
        return "pages/trainee-views/trainee-management";
    }

    /**
     * Message create account trainee
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/add-trainee")
    public String addView(final ModelMap model, final HttpServletRequest request) {
        model.addAttribute("message", "");
        String messsage = request.getParameter("message");
        if (messsage != null && messsage.equalsIgnoreCase("success")) {
            model.addAttribute("message", "<div class=\"alert alert-success\">" +
                    "  <strong>Success!</strong> Thêm mới thành công." +
                    "</div>");
        }
        model.addAttribute("trainee", new Trainee());
        return "pages/trainee-views/trainee-create-new";
    }

    /**
     * Create account trainee -> ROLE_ADMIN
     *
     * @param model
     * @param trainee
     * @return
     */
    @Transactional
    @PostMapping("/add-trainee")
    public String add(final ModelMap model,
                      @ModelAttribute("trainee") Trainee trainee) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        trainee.getUser().setPassword(encoder.encode(trainee.getUser().getPassword()));

        String account = trainee.getEmail();
        trainee.getUser().setAccount(account.substring(0, account.indexOf("@")));

        trainee.getUser().setRoles(roleService.findByName("ROLE_TRAINEE"));
        if (!userService.checkEmail(trainee.getEmail())) {
            traineeService.save(trainee);
        } else {
            return null;
        }
        model.addAttribute("trainer", new Trainer());
        return "redirect:/trainee-management/add-trainee?message=success";
    }

    /**
     * Update trainee
     *
     * @param request
     * @param trainee
     * @return
     */
    @Transactional
    @PostMapping("/update-trainee")
    public String update(final HttpServletRequest request,
                         @ModelAttribute("trainee") Trainee trainee) {
        traineeService.update(trainee);
        HttpSession session = request.getSession();
        session.setAttribute("message", "update");
        return "redirect:/trainee-management";
    }

    /**
     * Delete trainee
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<AjaxResponse> delete(@RequestBody Integer id) {
        traineeService.delete(id);
        return ResponseEntity.ok(new AjaxResponse(200, "OK"));
    }

    /**
     * View trainee details
     *
     * @param model
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/trainee-details")
    public String displayAllTraineeDetails(Model model, @RequestParam("id") Integer id, final HttpServletRequest request) {
        Trainee trainee = traineeService.findById(id);
        HttpSession session = request.getSession();
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINEE")) {
                model.addAttribute("role", 2);
            }
        }
        model.addAttribute("trainee", trainee);
        return "pages/trainee-views/trainee-details";
    }

    public static String uploadDirection = System.getProperty("user.dir") + "/upload";

    /**
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/avatar-trainee")
    public String editAvatar(@RequestParam Integer id, final ModelMap model, final HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINEE")) {
                model.addAttribute("role", 2);
            }
        }
        model.addAttribute("trainee", traineeService.findById(id));
        return "pages/trainee-views/trainee-avatar";
    }

    /**
     *
     * @param id
     * @param photo
     * @param model
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws EntityNotFoundException
     */
    @PostMapping("/avatar-trainee")
    public String editedAvatar(@RequestParam Integer id, @RequestParam("photo") MultipartFile photo, final ModelMap model)
            throws IllegalStateException, IOException, EntityNotFoundException {
        StringBuilder filename = new StringBuilder();
        Trainee getData = traineeRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Error"));
        Path filenameAndPath = Paths.get(uploadDirection, photo.getOriginalFilename());
        System.out.println(photo.getOriginalFilename());
        filename.append(photo.getOriginalFilename());
        try {
            Files.write(filenameAndPath, photo.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData.setPhoto(filename.toString());

        traineeService.update(getData);
        model.addAttribute("msg", "Successfully" + filename.toString());

        return "redirect:/my-account";
    }

}
