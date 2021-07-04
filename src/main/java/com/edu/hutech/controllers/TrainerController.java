package com.edu.hutech.controllers;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.models.PaginationRange;
import com.edu.hutech.repositories.TrainerRepository;
import com.edu.hutech.services.core.UserService;
import com.edu.hutech.services.implementation.RoleService;
import com.edu.hutech.services.implementation.TrainerServiceImpl;
import com.edu.hutech.services.implementation.UserServiceImpl;
import com.edu.hutech.utils.page.Pagination;
import com.edu.hutech.utils.sort.GenericComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Trainer Controller
 * author: KhiemKM
 */
@Controller
@RequestMapping("/trainer-management")
public class TrainerController {


    @Autowired
    TrainerServiceImpl trainerService;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleService roleService;

    /**
     * Get list trainer display
     *
     * @param model
     * @param page
     * @param request
     * @param size
     * @param field
     * @param search
     * @return
     */
    @GetMapping()
    public String displayTrainerList(Model model, @RequestParam("page") Optional<Integer> page, final HttpServletRequest request,
                                     @RequestParam("size") Optional<Integer> size,
                                     @RequestParam("field") Optional<String> field, @RequestParam(value = "search", required = false) String search) {


        int cPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String sortField = field.orElse("default");

        pageSize = pageSize < 5 ? 5 : pageSize > 500 ? 500 : pageSize;

        List<Trainer> trainers = trainerService.getAll();
        model.addAttribute("trainers", trainers);


        if (sortField.contains("-asc")) {
            String[] splits = sortField.split("-asc", 2);
            Collections.sort(trainers, new GenericComparator(true, splits[0]));
        } else {
            if (sortField.equals("default")) {
            } else {
                Collections.sort(trainers, new GenericComparator(false, sortField));
            }
        }

        if (search != null) {
            trainers.removeIf(trainer -> !trainer.getUser().getAccount().contains(search));
        }

        List<Trainer> trainersAfterPaging = Pagination.getPage(trainers, cPage, pageSize);

        int currIndex = 0;
        if (trainers.size() > 0) {
            currIndex = trainers.indexOf(trainersAfterPaging.get(0));
        }
        int totalPages = (int) Math.ceil((double) trainers.size() / (double) pageSize);
        int totalElements = trainers.size();

        model.addAttribute("trainers", trainersAfterPaging);
        model.addAttribute("cPage", cPage);
        model.addAttribute("currIndex", currIndex);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("size", pageSize);
        model.addAttribute("field", sortField);

        HttpSession session = request.getSession();
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", "Update success!");
        }
        session.removeAttribute("message");

        PaginationRange p = Pagination.paginationByRange(cPage, totalElements, pageSize, 5);
        model.addAttribute("paginationRange", p);

        return "pages/trainer-views/trainer-management";
    }

    /**
     * Message create account trainer
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/add-trainer")
    public String addView(final ModelMap model, final HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", "Add success!");
        }
        session.removeAttribute("message");

        model.addAttribute("trainer", new Trainer());
        return "pages/trainer-views/trainer-create-new";
    }

    /**
     * Create account trainer -> ROLE_ADMIN
     *
     * @param model
     * @param request
     * @param trainer
     * @return
     */
    @PostMapping("/add-trainer")
    public String add(final ModelMap model, final HttpServletRequest request,
                      @ModelAttribute("trainer") Trainer trainer) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        trainer.getUser().setPassword(encoder.encode(trainer.getUser().getPassword()));

        if (!userService.checkEmail(trainer.getEmail())) {
            String account = trainer.getEmail();
            trainer.getUser().setAccount(account.substring(0, account.indexOf("@")));
            trainer.getUser().setRoles(roleService.findByName("ROLE_TRAINER"));
            trainerService.save(trainer);
            model.addAttribute("trainer", new Trainer());

            HttpSession session = request.getSession();
            session.setAttribute("message", "update");
        } else {
            return null;
        }

        return "redirect:/trainer-management/add-trainer";
    }

    public static String uploadDirection = System.getProperty("user.dir") + "/upload";

    /**
     * Update account trainer
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/update-trainer")
    public String updateView(@RequestParam Integer id,
                             final ModelMap model, final HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINER")) {
                model.addAttribute("role", 1);
            }
        }
        model.addAttribute("trainer", trainerService.findById(id));
        return "pages/trainer-views/trainer-update";
    }

    /**
     * Update account trainer
     *
     * @param request
     * @param trainer
     * @return
     */
    @PostMapping("/update-trainer")
    public String update(final HttpServletRequest request,
                         @ModelAttribute("trainer") Trainer trainer) {
        trainerService.update(trainer);
        HttpSession session = request.getSession();
        session.setAttribute("message", "update");
        return "redirect:/trainer-management";
    }

    /**
     * Delete account trainer
     *
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<AjaxResponse> delete(@RequestBody Integer id) {
        trainerService.delete(id);
        return ResponseEntity.ok(new AjaxResponse(200, "OK"));
    }

    /**
     * @param id
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/avatar-trainer")
    public String editAvatar(@RequestParam Integer id, final ModelMap model, final HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (session.getAttribute("role") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINER")) {
                model.addAttribute("role", 1);
            }
        }

        model.addAttribute("trainer", trainerService.findById(id));
        return "pages/trainer-views/trainer-avatar";
    }

    /**
     * @param id
     * @param photo
     * @param model
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws EntityNotFoundException
     */
    @PostMapping("/avatar-trainer")
    public String editedAvatar(@RequestParam Integer id, @RequestParam("photo") MultipartFile photo, final ModelMap model)
            throws IllegalStateException, IOException, EntityNotFoundException {
        StringBuilder filename = new StringBuilder();
        Trainer getData = trainerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Error"));
        Path filenameAndPath = Paths.get(uploadDirection, photo.getOriginalFilename());
        System.out.println(photo.getOriginalFilename());
        filename.append(photo.getOriginalFilename());
        try {
            Files.write(filenameAndPath, photo.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData.setPhoto(filename.toString());

        trainerService.update(getData);
        model.addAttribute("msg", "Successfully" + filename.toString());
        return "redirect:/my-account";
    }

}
