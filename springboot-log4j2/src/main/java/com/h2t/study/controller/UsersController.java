package com.h2t.study.controller;

import com.h2t.study.po.Users;
import com.h2t.study.service.UsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hetiantian
 * @since 2019-08-13
 */
@RestController
@RequestMapping("/user")
public class UsersController {
    @Resource
    private UsersService usersService;

    @PostMapping
    public Object save(@RequestBody Users users) {
        return usersService.insert(users);
    }

    @PostMapping("/userslist")
    public Object saveBatch(@RequestBody List<Users> objList) {
        return usersService.insertBatch(objList);
    }

    @DeleteMapping("/{id}")
    public Object removeById(@PathVariable Long id) {
        System.out.println("delete....");
        return usersService.deleteById(id);
    }

    @DeleteMapping
    public Object remove(@RequestBody Users users) {
        return usersService.delete(users);
    }

    @DeleteMapping("/list")
    public Object removeBatch(@RequestBody List<Long> idList) {
        return usersService.deleteByIds(idList);
    }


    @PutMapping()
    public Object modifyById(@RequestBody Users users) {
        return usersService.modifyById(users);
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        return usersService.selectById(id);
    }

    @PostMapping("/list")
    public Object list(@RequestBody Users users) {
        return usersService.selectList(users);
    }

    @PostMapping("/idList")
    public Object listById(@RequestBody List<Long> idList) {
        return usersService.selectByIds(idList);
    }

    @PostMapping("/page")
    public Object page(@RequestBody Users users,
                       @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "2") Integer pageSize) {
        return usersService.selectPage(users, pageNo, pageSize);
    }
}
