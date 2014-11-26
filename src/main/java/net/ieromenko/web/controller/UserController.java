package net.ieromenko.web.controller;

import com.google.common.collect.Lists;
import net.ieromenko.domain.SearchCriteria;
import net.ieromenko.domain.User;
import net.ieromenko.service.UserService;
import net.ieromenko.web.form.Message;
import net.ieromenko.web.form.UserGrid;
import net.ieromenko.web.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


/**
 * Created by Alex Ieromenko on 04.11.14.
 */
@RequestMapping("/users")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel) {
        List<User> users = userService.findAll();
        uiModel.addAttribute("users", users);
        return "users/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        User user = userService.findById(id);
        uiModel.addAttribute("user", user);
        return "users/show";
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
    public String update(@Valid User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", "Ошибка!"));
            uiModel.addAttribute("user", user);
            return "users/update";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", "Сохранение прошло успешно"));
        userService.save(user);
        return "redirect:/users/" + UrlUtil.encodeUrlPathSegment(user.getId().toString(),
                httpServletRequest); // catch here
    }

    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("user", userService.findById(id));
        return "users/update";
    }

    @RequestMapping(value = "/{id}", params = "delete")
    public String delete(User user, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", "user_remove_fail"));
            uiModel.addAttribute("user", user);
            return "users/show";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", "Удаление прошло успешно"));
        userService.delete(user);
        return "redirect:/users/";
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult bindingResult,
                         Model uiModel, HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", "Ошибка!" ));
            uiModel.addAttribute("user", user);
            return "users/create";
        }
        uiModel.asMap().clear();
        redirectAttributes.addFlashAttribute("message", new Message("success", "Сохранение прошло успешно"));
        userService.save(user);
        return "redirect:/users/" + UrlUtil.encodeUrlPathSegment(user.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        User user = new User();
        uiModel.addAttribute("user", user);
        return "users/create";
    }

    @RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public UserGrid listGrid(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "rows", required = false) Integer rows,
                             @RequestParam(value = "sidx", required = false) String sortBy,
                             @RequestParam(value = "sord", required = false) String order,
                             @RequestParam(value = "name", required = false) String name) {

        if (name == null) {
            name = "%";
        } else {
            name = "%" + name + "%";
        }

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setName(name);

        Sort sort = null;
        String orderBy = sortBy;
        if (orderBy != null && order != null)

        {
            if (order.equals("desc")) {
                sort = new Sort(Sort.Direction.DESC, orderBy);
            } else
                sort = new Sort(Sort.Direction.ASC, orderBy);
        }

        PageRequest pageRequest;
        if (sort != null)

        {
            pageRequest = new PageRequest(page - 1, rows, sort);
        } else

        {
            pageRequest = new PageRequest(page - 1, rows);
        }

        Page<User> userPage = userService.findUserByCriteria(searchCriteria, pageRequest);

        UserGrid userGrid = new UserGrid();
        userGrid.setCurrentPage(userPage.getNumber() + 1);
        userGrid.setTotalPages(userPage.getTotalPages());
        userGrid.setTotalRecords(userPage.getTotalElements());
        userGrid.setUserData(Lists.newArrayList(userPage.iterator()));
        return userGrid;
    }

}








