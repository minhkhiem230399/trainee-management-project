package com.edu.hutech.controllers;

import com.edu.hutech.dtos.AjaxResponse;
import com.edu.hutech.entities.Trainee;
import com.edu.hutech.entities.Trainer;
import com.edu.hutech.entities.User;
import com.edu.hutech.services.core.UserService;
import com.edu.hutech.services.implementation.CourseServiceImpl;
import com.edu.hutech.services.implementation.TraineeServiceImpl;
import com.edu.hutech.services.implementation.TrainerServiceImpl;
import com.edu.hutech.services.implementation.UserServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User Controller
 * author: KhiemKM
 */
@Controller
@RequestMapping()
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/login")
    public String getLogin() {
        return "pages/user-views/login";
    }

    /**
     * Check mail
     *
     * @param email
     * @return
     */
    @PostMapping("/check-email")
    public ResponseEntity<AjaxResponse> checkEmail(@RequestBody String email) {
        if (userService.checkEmail(email)) {
            return ResponseEntity.ok(new AjaxResponse(400, null));
        }
        return ResponseEntity.ok(new AjaxResponse(200, email));
    }

    @GetMapping("/403")
    public String error() {
        return "/pages/util-views/404";
    }

    /**
     * Change password
     *
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/change-password")
    public ResponseEntity<AjaxResponse> updateUserPassword(@RequestBody String data, final HttpServletRequest request) {
        JSONObject json = new JSONObject(data);

        String old = json.getString("old");
        String news = json.getString("new");
        String re = json.getString("retype");

        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            User user = userService.findById((Integer) session.getAttribute("userId"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (old.equals(news)) {
                return ResponseEntity.ok(new AjaxResponse(401, data));
            }
            if (!news.equals(re)) {
                return ResponseEntity.ok(new AjaxResponse(402, data));
            }
            if (!encoder.matches(old, user.getPassword())) {
                return ResponseEntity.ok(new AjaxResponse(400, data));
            }
            user.setPassword(encoder.encode(news));
            userService.save(user);
        }
        return ResponseEntity.ok(new AjaxResponse(200, data));
    }

    /**
     * Change password 1
     *
     * @param data
     * @param request
     * @return
     */
    @PostMapping("/change-password1")
    public ResponseEntity<AjaxResponse> updateUserPassword1(@RequestBody String data, final HttpServletRequest request) {

        JSONObject json = new JSONObject(data);
        String old = json.getString("old");
        String news = json.getString("new");
        String re = json.getString("retype");

        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            User user = userService.findById((Integer) session.getAttribute("userId"));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (old.equals(news)) {
                return ResponseEntity.ok(new AjaxResponse(401, data));
            }

            if (!news.equals(re)) {
                return ResponseEntity.ok(new AjaxResponse(402, data));
            }

            if (!encoder.matches(old, user.getPassword())) {
                return ResponseEntity.ok(new AjaxResponse(400, data));
            }

            user.setPassword(encoder.encode(news));
            userService.save(user);
        }
        return ResponseEntity.ok(new AjaxResponse(200, data));
    }

    /**
     * Get user details
     *
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/my-account")
    public String showDetailsUser(final ModelMap model, final HttpServletRequest request) {
        HttpSession session = request.getSession();
        Trainer trainer = null;
        Trainee trainee = null;
        Integer traineeId = null;
        Integer trainerId = null;

        if (session.getAttribute("userId") != null) {
            if (session.getAttribute("role").equals("ROLE_TRAINER")) {
                trainerId = (Integer) session.getAttribute("userId");
                User user = userService.findById(trainerId);
                trainer = user.getTrainer();
                model.addAttribute("userss", trainer);
            } else {
                traineeId = (Integer) session.getAttribute("userId");
                User user = userService.findById(traineeId);
                trainee = user.getTrainee();
                model.addAttribute("userss", trainee);
            }
        }
        model.addAttribute("accountRole", session.getAttribute("role"));
        return "pages/user-views/user-details";
    }

}
